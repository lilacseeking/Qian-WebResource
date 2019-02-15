package org.lilacseeking.video.videoapp.Utils;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;
import org.lilacseeking.video.videoapp.Common.AsyncMultipartUpload;
import org.lilacseeking.video.videoapp.Configuration.ConstantProperties;
import org.lilacseeking.video.videoapp.Eumns.ErrorCodeEumn;
import org.lilacseeking.video.videoapp.Eumns.ProcessEnum;
import org.lilacseeking.video.videoapp.Exception.BusinessException;
import org.lilacseeking.video.videoapp.Listener.MultipartProgressListener;
import org.lilacseeking.video.videoapp.Model.VO.VideoUploadProcessVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.concurrent.Future;

@Component
public class OSSUtil {
    @Autowired
    private static OSSUtil ossUtil;

    @Autowired
    private ThreadPoolTaskExecutor executor;
    @Autowired
    private ConstantProperties constantProperties;

    private static OSSClient ossClient;

    private static String bucketName;
    // 文件上传路径
    private static String videoRoute;
    // 缩略图上传路径
    private static String thumbnailRoute;

    private static final long DEFAULT_EXPIRATION_TIME = 1000L * 60 * 60 * 24 * 365 * 100;
    //****************************************创建客户端和一些普通常用方法****************************************
    @PostConstruct
    public void init(){

        // Spring 依赖注入
        ossUtil = this;
        ossUtil.executor = this.executor;
        ossUtil.constantProperties = this.constantProperties;

        // 初始化OSS连接客户端
        getOSSClient();
    }
    /**
     * 获取oss客户端
     *
     * @return
     */
    public static OSSClient getOSSClient() {

        String endpoint = ossUtil.constantProperties.getOss().getEndpoint();
        String accessKeyId = ossUtil.constantProperties.getOss().getAccessKeyId();
        String accessKeySecret = ossUtil.constantProperties.getOss().getAccessKeySecret();
        OSSUtil.bucketName = ossUtil.constantProperties.getOss().getBucketName();
        OSSUtil.videoRoute = ossUtil.constantProperties.getUpload().getVideoRoute();
        OSSUtil.thumbnailRoute = ossUtil.constantProperties.getUpload().getThumbnailRoute();
        if (null == ossClient){
            return new OSSClient(endpoint, accessKeyId, accessKeySecret);
        }else{
            return ossClient;
        }

    }

    /**
     * 分片上传
     */
    public static VideoUploadProcessVO multipartUpload(VideoUploadProcessVO videoUploadProcessVO){
        // 1. 初始化⼀个分⽚上传事件
        File sampleFile = new File(videoUploadProcessVO.getVideoOriginPath());
        InitiateMultipartUploadRequest request = new InitiateMultipartUploadRequest(bucketName, videoUploadProcessVO.getVideoName());
        InitiateMultipartUploadResult result = ossClient.initiateMultipartUpload(request);
        String uploadId = result.getUploadId();

        // 2. 上传分⽚(partETags是PartETag的集合 PartETag由分⽚的ETag和分⽚号组成)
        List<Future<UploadPartResult>> futures = new ArrayList<Future<UploadPartResult>>();
        List partETags = new ArrayList<>();

        // 2.1 计算文件分片数，遍历上传，接收返回结果

        final long partSize = 1 * 1024 * 1024L;   // 1MB
        long fileLength = sampleFile.length();
        int partCount = (int) (fileLength / partSize);
        if (fileLength % partSize != 0) {
            partCount++;
        }
        try {
            // 2.2 遍历分⽚上传。
            for (int i = 0; i < partCount; i++) {
                long startPos = i * partSize;
                long curPartSize = (i + 1 == partCount) ? (fileLength - startPos) : partSize;
                InputStream inStream = null;
                inStream = new FileInputStream(sampleFile);
                inStream.skip(startPos);

                // tips 跳过已经上传的分⽚。
                UploadPartRequest uploadPartRequest = new UploadPartRequest();
                uploadPartRequest.setBucketName(bucketName);
                uploadPartRequest.setKey(videoUploadProcessVO.getVideoName());
                uploadPartRequest.setUploadId(uploadId);
                uploadPartRequest.setInputStream(inStream);
                // 设置分⽚⼤小。除了最后⼀个分⽚没有⼤小限制，其他的分⽚最小为100KB。
                uploadPartRequest.setPartSize(curPartSize);
                // 设置分⽚号。每⼀个上传的分⽚都有⼀个分⽚号，取值范围是1~10000，如果超出这个范围，OSS将返回InvalidArgument的错误码。
                uploadPartRequest.setPartNumber(i + 1);
                uploadPartRequest.setProgressListener(new MultipartProgressListener(uploadPartRequest.getUploadId(), videoUploadProcessVO.getVideoName()));
                // 每个分⽚不需要按顺序上传，甚⾄可以在不同客⼾端上传，OSS会按照分⽚号排序组成完整的⽂件。
                Future<UploadPartResult> feature = ossUtil.executor.submit(new AsyncMultipartUpload(ossClient, uploadPartRequest));
                futures.add(feature);
            }
            for (Future<UploadPartResult> future : futures) {
                // 每次上传分⽚之后，OSS的返回结果会包含⼀个PartETag。PartETag将被保存到partETags中。
                partETags.add(future.get().getPartETag());

            }
        } catch (Exception e) {
                throw new BusinessException(ErrorCodeEumn.VIDEO_UPLOAD_FAIL.getName());
        } finally {
        }

        // 排序。partETags必须按分⽚号升序排列。
        Collections.sort(partETags, new Comparator<PartETag>() {
            public int compare(PartETag p1, PartETag p2) {
                return p1.getPartNumber() - p2.getPartNumber();
            }
        });

        // 在执⾏该操作时，需要提供所有有效的partETags。OSS收到提交的partETags后，会逐⼀验证每个分⽚的有效性。当所有的数据分⽚验证通过后，OSS将把这些分⽚组合成⼀个完整的⽂件。
        CompleteMultipartUploadRequest completeMultipartUploadRequest =
                new CompleteMultipartUploadRequest(bucketName, videoUploadProcessVO.getVideoName(), uploadId, partETags);
        ossClient.completeMultipartUpload(completeMultipartUploadRequest);
        videoUploadProcessVO.setUploadStatus(ProcessEnum.SUCCESS.name());
        return videoUploadProcessVO;
    }

    /**
     * 创建文件下载url
     *
     * @param key        所存内容的键名
     * @return
     */
    public static URL createFileAccessUrl(String key) {
        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucketName, key, HttpMethod.GET);
        req.setExpiration(new Date(new Date().getTime() + DEFAULT_EXPIRATION_TIME));
        return ossClient.generatePresignedUrl(req);
    }





    /**
     * 列举Objects
     *
     * @param ossClient  oss客户端
     * @param bucketName 存储空间名
     * @param maxKeys    最多多少条（默认100条）
     * @param prefix     object的前缀（不传就是所有）
     * @return
     */
    public static List<OSSObjectSummary> listObjects(OSSClient ossClient, String bucketName, Integer maxKeys, String prefix) {
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);
        if (maxKeys != null) {
            listObjectsRequest.withMaxKeys(maxKeys);
        }
        if (prefix != null) {
            listObjectsRequest.setPrefix(prefix);
        }
        // 列举Object
        ObjectListing objectListing = ossClient.listObjects(listObjectsRequest);
        return objectListing.getObjectSummaries();
    }
    /**
     * 删除objects
     *
     * @param ossClient  oss客户端
     * @param bucketName 存储空间名
     * @param keys       要删除的文件的key的集合
     * @return 被删除的文件名
     */
    public static List<String> deleteObjects(OSSClient ossClient, String bucketName, List<String> keys) {
        DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(bucketName).withKeys(keys);
        DeleteObjectsResult deleteObjectsResult = ossClient.deleteObjects(deleteObjectsRequest);
        return deleteObjectsResult.getDeletedObjects();
    }

    //****************************************文件上传下载****************************************

    /**
     * 上传字符串
     *
     * @param ossClient  OSSClient实例
     * @param bucketName 存储空间名
     * @param key        所存内容的键名
     * @param value      所存内容
     */
    public static void uploadString(OSSClient ossClient, String bucketName, String key, String value, ObjectMetadata meta) {
        if (meta != null) {
            ossClient.putObject(bucketName, key, new ByteArrayInputStream(value.getBytes()), meta);
        } else {
            ossClient.putObject(bucketName, key, new ByteArrayInputStream(value.getBytes()));
        }
    }

    /**
     * 上传byte数组
     *
     * @param ossClient  OSSClient实例
     * @param bucketName 存储空间名
     * @param key        所存内容的键名
     * @param value      所存内容
     */
    public static void uploadByteArr(OSSClient ossClient, String bucketName, String key, byte[] value, ObjectMetadata meta) {
        if (meta != null) {
            ossClient.putObject(bucketName, key, new ByteArrayInputStream(value), meta);
        } else {
            ossClient.putObject(bucketName, key, new ByteArrayInputStream(value));
        }

    }

    /**
     * 上传流
     *
     * @param ossClient  OSSClient实例
     * @param bucketName 存储空间名
     * @param key        所存内容的键名
     * @param value      所存内容:
     *                   // 上传网络流
     *                   InputStream inputStream = new URL("https://www.aliyun.com/").openStream();
     *                   // 上传文件流
     *                   InputStream inputStream = new FileInputStream("localFile");
     */
    public static void uploadStream(OSSClient ossClient, String bucketName, String key, InputStream value, ObjectMetadata meta) {
        if (meta != null) {
            ossClient.putObject(bucketName, key, value, meta);
        } else {
            ossClient.putObject(bucketName, key, value);
        }
    }

    /**
     * 上传本地文件
     *
     * @param ossClient  OSSClient实例
     * @param bucketName 存储空间名
     * @param key        所存内容的键名
     * @param value      所存内容
     */
    public static void uploadLocalFile(OSSClient ossClient, String bucketName, String key, File value, ObjectMetadata meta) {
        if (meta != null) {
            ossClient.putObject(bucketName, key, value, meta);
        } else {
            ossClient.putObject(bucketName, key, value);
        }
    }

    /**
     * 上传整个文件夹（上传后的根级目录为所传绝对路径最后一个文件夹名）
     * 如：上传的绝对路径：e://aa//bb，在oss中根级目录为bb
     *
     * @param ossClient       OSSClient实例
     * @param bucketName      存储空间名
     * @param dirAbsolutePath 所要上传文件夹的绝对路径
     */
    public static void uploadLocalDir(OSSClient ossClient, String bucketName, String dirAbsolutePath) {
        findAllFilesAndUpload(ossClient, bucketName, dirAbsolutePath, true, null);
    }

    /**
     * 找到根文件夹中所有的文件上传到OSS中
     *
     * @param ossClient            OSSClient实例
     * @param bucketName           存储空间名
     * @param fileDir              文件夹名称
     * @param firstIn              是不是第一次进（涉及到记住根目录的问题）
     * @param rememberRootFilePath （记住的根目录，用来替换掉绝对路径中含有的根目录）
     */
    private static void findAllFilesAndUpload(OSSClient ossClient, String bucketName, String fileDir, boolean firstIn, String rememberRootFilePath) {
        File file = new File(fileDir);
        if (firstIn) {
            String rootFileAbsolutePath = file.getAbsolutePath().replace("\\", "/");
            rememberRootFilePath = rootFileAbsolutePath;
        }
        File[] files = file.listFiles();// 获取目录下的所有文件或文件夹
        if (files == null) {// 如果目录为空，直接退出
            return;
        }
        String rootKey = rememberRootFilePath.substring(rememberRootFilePath.lastIndexOf("/") + 1);
        // 遍历，目录下的所有文件
        for (File f : files) {
            if (f.isFile()) {
                String fileAbsolutePath = f.getAbsolutePath().replace("\\", "/");
                String key = fileAbsolutePath.replace(rememberRootFilePath, rootKey);
                OSSUtil.uploadLocalFile(ossClient, bucketName, key, new File(fileAbsolutePath), null);

            } else if (f.isDirectory()) {
                findAllFilesAndUpload(ossClient, bucketName, f.getAbsolutePath(), false, rememberRootFilePath);
            }
        }
    }

    /**
     * 追加上传
     *
     * @param ossClient           OSSClient实例
     * @param appendObjectRequest AppendObjectRequest appendObjectRequest = new AppendObjectRequest("<yourBucketName>",
     *                            "<yourKey>", new ByteArrayInputStream(content.getBytes()));
     *                            appendObjectRequest.setPosition(0L);
     *                            //appendObjectRequest.setPosition(appendObjectResult.getNextPosition());
     *                            AppendObjectResult appendObjectResult = ossClient.appendObject(appendObjectRequest);
     * @return 返回AppendObjectResult对象，appendObjectResult.getNextPosition()最为下一个AppendObjectRequest appendObjectRequest的position值
     */
    public static AppendObjectResult appendUpload(OSSClient ossClient, AppendObjectRequest appendObjectRequest) {
        return ossClient.appendObject(appendObjectRequest);
    }

    /**
     * 创建UploadFileRequest
     *
     * @param bucketName     存储空间名
     * @param key            所存内容的键名
     * @param fileName       指定上传的本地文件
     * @param taskNum        指定上传并发线程数
     * @param partSize       指定上传的分片大小
     * @param checkpointFile 如果上传过程中出现了错误，再次上传的时候需要指定与上次上传时相同的checkpoint文件（e://aaaa.txt.ucp）——即你输入的文件名.ucp,若上传成功，oss会自动删除
     * @return
     */
    public static UploadFileRequest createUploadFileRequest(String bucketName, String key, String fileName, int taskNum, long partSize, String checkpointFile) {
        UploadFileRequest uploadFileRequest = new UploadFileRequest(bucketName, key);
        uploadFileRequest.setUploadFile(fileName);
        uploadFileRequest.setTaskNum(taskNum);
        uploadFileRequest.setPartSize(partSize);
        uploadFileRequest.setEnableCheckpoint(true);
        if (checkpointFile != null) {
            uploadFileRequest.setCheckpointFile(checkpointFile);
        }
        return uploadFileRequest;
    }

    /**
     * 断点续传
     * <p>
     * 在上传的过程中会记录当前上传的进度信息（记录在checkpoint文件中），
     * 如果上传过程中某一分片上传失败，再次上传时会从checkpoint文件中记录的点继续上传。
     * 这要求再次调用时要指定与上次相同的checkpoint文件。
     * 上传完成后，checkpoint文件会被删除
     *
     * @param ossClient         OSSClient实例
     * @param uploadFileRequest
     * @return
     * @throws Throwable
     */
    public static UploadFileResult breakpointContinuingly(OSSClient ossClient, UploadFileRequest uploadFileRequest) throws Throwable {
        return ossClient.uploadFile(uploadFileRequest);
    }

    /**
     * 计算分片上传时会有多少个part
     *
     * @param partSize 每个分片的大小（5*1024*1024表示5MB）
     * @param file
     * @return
     */
    public static Integer calPartCount(Long partSize, File file) {
        long fileLength = file.length();
        int partCount = (int) (fileLength / partSize);
        if (fileLength % partSize != 0) {
            partCount++;
        }
        if (partCount > 10000) {
            throw new RuntimeException("Total parts count should not exceed 10000");
        }
        return partCount;
    }

    /**
     * 初始化Multipart Upload事件
     *
     * @param ossClient  OSSClient实例
     * @param bucketName 存储空间名
     * @param key        所存内容的键名
     * @return 区分分片上传事件的唯一标识
     */
    public static String initiateMultipartUpload(OSSClient ossClient, String bucketName, String key) {
        InitiateMultipartUploadRequest request = new InitiateMultipartUploadRequest(bucketName, key);
        InitiateMultipartUploadResult result = ossClient.initiateMultipartUpload(request);
        return result.getUploadId();
    }


    /**
     * 上传某个分片
     *
     * @param ossClient  OSSClient实例
     * @param bucketName 存储空间名
     * @param key        文件的key
     * @param uploadId   初始化上传事件得到的uploadID
     * @param partSize   part的大小（除最后一个分片外，其它分片要大于100KB）
     * @param partNumber 此分片的number（范围是1~10000）
     * @param file       要上传的文件
     * @return
     */
    public static UploadPartResult uploadPart(OSSClient ossClient, String bucketName, String key, String uploadId, long startPos, long partSize, int partNumber, File file) {
        UploadPartRequest uploadPartRequest = new UploadPartRequest();
        try {
            InputStream inputStream = new FileInputStream(file);
            inputStream.skip(startPos);
            uploadPartRequest.setBucketName(bucketName);
            uploadPartRequest.setKey(key);
            uploadPartRequest.setUploadId(uploadId);
            uploadPartRequest.setInputStream(inputStream);
            uploadPartRequest.setPartSize(partSize);
            uploadPartRequest.setPartNumber(partNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ossClient.uploadPart(uploadPartRequest);
    }

    /**
     * 上传指定区间的分片（比如将文件分成了100个，您可以指定传50-60的分片，partBeginNum为50，partEndNum为60）
     *
     * @param ossClient    OSSClient实例
     * @param bucketName   存储空间名
     * @param key          文件的key
     * @param uploadId     初始化上传事件得到的uploadID
     * @param file         要上传的文件
     * @param partSize     分片大小
     * @param partBeginNum 分片从哪个数字开始传(如果是null或小于0，默认为0)
     * @param partEndNum   分片到哪个数字结束（如果是null或者大于总的文件分片数，默认为最大分片数）
     * @return UploadPartResult的集合
     */
    public static List<UploadPartResult> multiUploadPart(OSSClient ossClient, String bucketName, String key, String uploadId, File file, long partSize, Integer partBeginNum, Integer partEndNum) {
        int partCount = calPartCount(partSize, file);
        if (partBeginNum == null || partBeginNum < 0) {
            partBeginNum = 0;
        }
        if (partEndNum == null || partEndNum > partCount) {
            partEndNum = partCount;
        }

        List<UploadPartResult> list = new ArrayList<>();
        long fileLength = file.length();
        for (int i = partBeginNum; i < partEndNum; i++) {
            long startPos = i * partSize;
            long curPartSize = (i + 1 == partCount) ? (fileLength - startPos) : partSize;
            UploadPartRequest uploadPartRequest = new UploadPartRequest();
            try {
                InputStream inputStream = new FileInputStream(file);
                inputStream.skip(startPos);
                uploadPartRequest.setBucketName(bucketName);
                uploadPartRequest.setKey(key);
                uploadPartRequest.setUploadId(uploadId);
                uploadPartRequest.setInputStream(inputStream);
                uploadPartRequest.setPartSize(curPartSize);
                uploadPartRequest.setPartNumber(i + 1);
                list.add(ossClient.uploadPart(uploadPartRequest));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    /**
     * 使集合升序排序
     * partETag的列表，它必须是按PartNumber升序排列
     *
     * @param partETags 进行分片上传中保存的partETag的列表
     * @return
     */
    public static List<PartETag> sortPartETag(List<PartETag> partETags) {
        Collections.sort(partETags, Comparator.comparingInt(PartETag::getPartNumber));
        return partETags;
    }

    /**
     * 完成分片上传
     * 所有分片上传完成后，需要调用CompleteMultipartUpload接口来完成整个文件的分片上传。
     *
     * @param ossClient  OSSClient实例
     * @param bucketName 存储空间名
     * @param key        所存内容的键名
     * @param uploadId   区分分片上传事件的唯一标识
     * @param partETags  保存PartETag对象的分片列表
     */
    public static void completeMultipartUpload(OSSClient ossClient, String bucketName, String key, String uploadId, List<PartETag> partETags) {
        CompleteMultipartUploadRequest completeMultipartUploadRequest =
                new CompleteMultipartUploadRequest(bucketName, key, uploadId, partETags);
        ossClient.completeMultipartUpload(completeMultipartUploadRequest);
    }

    /**
     * 取消分片上传事件
     *
     * @param ossClient  OSSClient实例
     * @param bucketName 存储空间名
     * @param key        所存内容的键名
     * @param uploadId   来自于initiateMultipartUpload
     */
    public static void abortMultipartUpload(OSSClient ossClient, String bucketName, String key, String uploadId) {
        AbortMultipartUploadRequest abortMultipartUploadRequest =
                new AbortMultipartUploadRequest(bucketName, key, uploadId);
        //当一个Multipart Upload事件被中止后，就不能再使用这个Upload ID做任何操作，已经上传的Part数据也会被删除。
        ossClient.abortMultipartUpload(abortMultipartUploadRequest);
    }

    /**
     * 获取已上传的分片(简单列举，默认情况下只能列举1000个分片)
     *
     * @param ossClient  OSSClient实例
     * @param bucketName 存储空间名
     * @param key        所存内容的键名
     * @param uploadId   来自于initiateMultipartUpload
     */
    public static PartListing listParts(OSSClient ossClient, String bucketName, String key, String uploadId) {
        // 列举已上传的分片，其中uploadId来自于initiateMultipartUpload
        ListPartsRequest listPartsRequest = new ListPartsRequest(bucketName, key, uploadId);
        //默认情况下，如果存储空间中的分片上传事件的数量大于1000，则只会返回1000个Multipart Upload信息，且返回结果中 IsTruncated 为false，并返回 NextPartNumberMarker作为下此读取的起点。
        return ossClient.listParts(listPartsRequest);
    }

    /**
     * 分页获取所有已上传的分片
     *
     * @param ossClient   OSSClient实例
     * @param bucketName  存储空间名
     * @param key         所存内容的键名
     * @param uploadId    来自于initiateMultipartUpload
     * @param maxPartsNum 每页的Parts个数（null——默认1000）
     */
    public static List<PartListing> pagingListParts(OSSClient ossClient, String bucketName, String key, String uploadId, Integer maxPartsNum) {
        // 列举已上传的分片，其中uploadId来自于initiateMultipartUpload
        ListPartsRequest listPartsRequest = new ListPartsRequest(bucketName, key, uploadId);
        if (maxPartsNum != null) {
            listPartsRequest.setMaxParts(maxPartsNum);
        }

        PartListing partListing;
        List<PartListing> list = new ArrayList();
        do {
            partListing = ossClient.listParts(listPartsRequest);
            list.add(partListing);
            listPartsRequest.setPartNumberMarker(partListing.getNextPartNumberMarker());
        } while (partListing.isTruncated());
        return list;
    }


    /**
     * 简单列举分片上传事件((简单列举，默认情况下只能列举1000个文件))
     *
     * @param ossClient  OSSClient实例
     * @param bucketName 存储空间名
     * @return 存储空间内分片上传事件
     */
    public static MultipartUploadListing listMultipartUploads(OSSClient ossClient, String bucketName) {
        // 列举分片上传事件
        ListMultipartUploadsRequest listMultipartUploadsRequest = new ListMultipartUploadsRequest(bucketName);
        //默认情况下，如果存储空间中的分片上传事件的数量大于1000，则只会返回1000个文件， 且返回结果中 IsTruncated 为 false，返回 NextKeyMarker 和 NextUploadIdMarker 作为下次读取的起点。
        return ossClient.listMultipartUploads(listMultipartUploadsRequest);
    }

    /**
     * 分页列举全部上传事件
     *
     * @param ossClient     OSSClient实例
     * @param bucketName    存储空间名
     * @param maxUploadsNum 每页的文件个数
     * @return
     */
    public static List<MultipartUploadListing> pagingListMultipartUploads(OSSClient ossClient, String bucketName, Integer maxUploadsNum) {
        // 列举分片上传事件
        MultipartUploadListing multipartUploadListing;
        ListMultipartUploadsRequest listMultipartUploadsRequest = new ListMultipartUploadsRequest(bucketName);
        if (maxUploadsNum != null) {
            // 每个页中事件数目
            listMultipartUploadsRequest.setMaxUploads(maxUploadsNum);
        }

        List<MultipartUploadListing> list = new ArrayList<>();
        do {
            multipartUploadListing = ossClient.listMultipartUploads(listMultipartUploadsRequest);
            list.add(multipartUploadListing);
            listMultipartUploadsRequest.setKeyMarker(multipartUploadListing.getNextKeyMarker());
            listMultipartUploadsRequest.setUploadIdMarker(multipartUploadListing.getNextUploadIdMarker());
        } while (multipartUploadListing.isTruncated());
        return list;
    }

    //*********************************************下载*********************************************

    /**
     * 获取文件下载流(可指定下载范围)
     *
     * @param ossClient  OSSClient实例
     * @param bucketName 存储空间名
     * @param key        键名
     * @param begin      下载范围开始处
     * @param end        下载范围结束处
     * @return
     */
    public static InputStream createDownloadStream(OSSClient ossClient, String bucketName, String key, Integer begin, Integer end) {
        OSSObject ossObject;
        if (begin != null && end != null) {
            GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
            // 获取begin~end字节范围内的数据，包括begin和end
            getObjectRequest.setRange(begin, end);
            ossObject = ossClient.getObject(getObjectRequest);
        } else {
            ossObject = ossClient.getObject(bucketName, key);
        }
        return ossObject.getObjectContent();
    }

    /**
     * 读取存储空间中的文件下载到本地文件
     *
     * @param ossClient  OSSClient实例
     * @param bucketName 存储空间名
     * @param key        键名
     * @param localFile  本地文件名
     */
    public static void downloadToLocalFile(OSSClient ossClient, String bucketName, String key, String localFile) {
        ossClient.getObject(new GetObjectRequest(bucketName, key), new File(localFile));
    }

    /**
     * 断点续传下载
     *
     * @param ossClient           OSSClient实例
     * @param downloadFileRequest DownloadFileRequest downloadFileRequest = new DownloadFileRequest("bucketName", "key");
     *                            downloadFileRequest.setDownloadFile("downloadFile");——（传入下载后的文件名，不传为key）
     *                            downloadFileRequest.setTaskNum(10);——分片下载并发数
     *                            downloadFileRequest.setEnableCheckpoint(true);——开启断点续传
     *                            注：如果下载过程中某一分片下载失败，再次下载时会从checkpoint文件中记录的点继续下载。这要求再次调用时要指定与上次相同的checkpoint文件
     *                            1、downloadFileRequest.getCheckpointFile();——第一次创建对象时记住这个
     *                            2 、downloadFileRequest.setCheckpointFile();——断点续传时设置进去
     *                            下载完成后，checkpoint文件会被删除
     * @return 下载成功时，会返回文件的元信息
     * @throws Throwable
     */
    public static ObjectMetadata breakpointResume(OSSClient ossClient, DownloadFileRequest downloadFileRequest) throws Throwable {
        DownloadFileResult downloadRes = ossClient.downloadFile(downloadFileRequest);
        return downloadRes.getObjectMetadata();
    }

    /**
     * @param ossClient OSSClient实例
     * @param request   限定条件
     * @param localFile 本地文件名
     */
    public static void downloadByLimits(OSSClient ossClient, GetObjectRequest request, String localFile) {
        ossClient.getObject(request, new File(localFile));
    }
    //****************************************图片工具类****************************************

    /**
     * 访问图片时process的类型
     */
    public enum ProcessType {
        IMAGE("image"), STYLE("style");
        private String processType;

        ProcessType(String processType) {
            this.processType = processType;
        }

        public String getProcessType() {
            return processType;
        }
    }

    /**
     * 创建访问图片的url(匿名访问，所访问的图片要有访问权限)
     *
     * @param accessType 基于http或是https访问
     * @param bucketName 存储空间名
     * @param endpoint
     * @param objectName 文件名
     * @param process    基于image（对文件操作——后面的params中可传入多个"action，param"）还是style（直接设定一种样式——后面params传入一个控制台定义样式的规则名）
     * @param params
     * @return
     */
    public static String createPicUrlByAuthor(String accessType, String bucketName, String endpoint, String objectName, String process, List<String> params) {
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        for (String param : params) {
            sb2.append("/").append(param);
        }
        sb1.append(accessType).append("://").append(bucketName).append(".").append(endpoint)
                .append("/").append(objectName).append("?x-oss-process=").append(process).append(sb2);
        return sb1.toString();
    }

    /**
     * 创建访问图片的url(提供processType及style的list由方法内部拼装)
     *
     * @param ossClient   OSS客户端
     * @param bucketName  存储空间名
     * @param key         要取的图片的key
     * @param expiration  有效时间（ms，默认超时时间100年）
     * @param processType process类型（image/style），如果是style，styles参数实际上就是长度为1的集合
     * @param styles      对图片的一个个操作
     * @return 图片文件url
     */
    public static URL createPicUrl(OSSClient ossClient, String bucketName, String key, Long expiration, ProcessType processType, List<String> styles) {
        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucketName, key, HttpMethod.GET);
        if (expiration != null && expiration > 0) {
            req.setExpiration(new Date(new Date().getTime() + expiration));
        } else {
            req.setExpiration(new Date(new Date().getTime() + DEFAULT_EXPIRATION_TIME));
        }
        req.setProcess(dealStylesToProcessParam(processType.getProcessType(), styles));
        return ossClient.generatePresignedUrl(req);
    }

    /**
     * 创建访问图片的url(自己写好准确的style字符串)
     *
     * @param ossClient  OSS客户端
     * @param bucketName 存储空间名
     * @param key        要取的图片的key
     * @param expiration 有效时间（ms）
     * @param style      样式"image/resize,m_fixed,w_100,h_100/rotate,90"
     * @return
     */
    public static URL createPicUrl(OSSClient ossClient, String bucketName, String key, Long expiration, String style) {
        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucketName, key, HttpMethod.GET);
        if (expiration != null && expiration > 0) {
            req.setExpiration(new Date(new Date().getTime() + expiration));
        } else {
            req.setExpiration(new Date(new Date().getTime() + 1000 * 60 * 10));
        }
        req.setProcess(style);
        return ossClient.generatePresignedUrl(req);
    }

    /**
     * 下载操作后的图片文件(提供processType及style的list由方法内部拼装)
     *
     * @param ossClient   OSS客户端
     * @param bucketName  存储空间名
     * @param key         文件的key值
     * @param processType process类型（image/style），如果是style，styles参数实际上就是长度为1的集合
     * @param styles      操作文件的行为"action,param"的集合
     * @param fileName    下载后的文件名
     */
    public static void downloadPicByOperate(OSSClient ossClient, String bucketName, String key, ProcessType processType, List<String> styles, String fileName) {
        GetObjectRequest request = new GetObjectRequest(bucketName, key);
        request.setProcess(dealStylesToProcessParam(processType.getProcessType(), styles));
        ossClient.getObject(request, new File(fileName));
    }

    /**
     * 下载操作后的图片文件(自己写好准确的style字符串)
     *
     * @param ossClient  OSS客户端
     * @param bucketName 存储空间名
     * @param key        文件的key值
     * @param style      样式"image/resize,m_fixed,w_100,h_100/rotate,90"
     * @param fileName   下载后的文件名
     */
    public static void downloadPicByOperate(OSSClient ossClient, String bucketName, String key, String style, String fileName) {
        GetObjectRequest request = new GetObjectRequest(bucketName, key);
        request.setProcess(style);
        ossClient.getObject(request, new File(fileName));
    }

    /**
     * 使styles集合成为process的参数
     *
     * @param processType
     * @param styles
     * @return
     */
    private static String dealStylesToProcessParam(String processType, List<String> styles) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < styles.size(); i++) {
            if (i == 0) {
                sb.append(processType).append("/");
            } else {
                sb.append("/");
            }
            sb.append(styles.get(i));
        }
        return sb.toString();
    }
}
