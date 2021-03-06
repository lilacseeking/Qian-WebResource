//package org.lilacseeking.course.app;
//
//import com.aliyun.oss.OSSClient;
//import com.aliyun.oss.model.*;
//import org.aspectj.lang.annotation.After;
//import org.junit.runner.RunWith;
//import org.junit.Test;
//import org.lilacseeking.course.app.Common.AsyncMultipartUpload;
//import org.lilacseeking.course.app.Eumns.ProcessEnum;
//import org.lilacseeking.course.app.Listener.MultipartProgressListener;
//import org.lilacseeking.course.app.Lock.DistributedLockService;
//import org.lilacseeking.course.app.Model.Factory.FileUploadSetFactory;
//import org.lilacseeking.course.app.Model.VO.VideoUploadProcessVO;
//import org.lilacseeking.course.app.Service.RedisService;
//import org.lilacseeking.course.app.Utils.OSSUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//import org.springframework.test.context.junit4.SpringRunner;
//import java.io.ByteArrayInputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.InputStream;
//import java.utils.ArrayList;
//import java.utils.Collections;
//import java.utils.Comparator;
//import java.utils.List;
//import java.utils.concurrent.ExecutionException;
//import java.utils.concurrent.Future;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class OSSUtilTest {
//
//    @Autowired
//    private RedisService redisService;
//
//    @Autowired
//    private ThreadPoolTaskExecutor executor;
//
//    //分布式锁
//    @Autowired
//    private DistributedLockService distributedLockService;
//
//    private static String endpoint = "oss-cn-beijing.aliyuncs.com";
//    private static String accessKeyId = "LTAI1VcTye0NFaFM";
//    private static String accessKeySecret = "ArmHD4PsXUfoZovIJNwQDtelMcwanS";
//    private static String bucketName = "lilacseeking";
//    private static OSSClient ossClient = OSSUtil.getOSSClient(endpoint, accessKeyId, accessKeySecret);
//
//    //**************************************************一些展示**************************************************
//
//    /**
//     * 打印所有的bucket
//     */
//    @Test
//    public void listBuckets() {
//        // 创建OSSClient实例
//        OSSClient ossClient = OSSUtil.getOSSClient(endpoint, accessKeyId, accessKeySecret);
//        // 使用访问OSS
//        // *********************列举bucket*********************
//        List<Bucket> buckets = ossClient.listBuckets();
//        for (Bucket bucket : buckets) {
//            System.out.println(" - " + bucket.getName());
//        }
//    }
//
//    /**
//     * 展示文件
//     */
//    @Test
//    public void showObjects() {
//        List<OSSObjectSummary> list = OSSUtil.listObjects(ossClient, bucketName, 200, null);
//        for (OSSObjectSummary ossObjectSummary : list) {
//            System.out.println(ossObjectSummary.getKey());
//        }
//    }
//
//    /**
//     * 删除文件
//     */
//    @Test
//    public void deleteObjects() {
//        List<String> keys = new ArrayList<>();
//        keys.add("bb/b.txt");
//        keys.add("bb/cc/c.txt");
////        keys.add("ossUtilTest/a.jpg");
////        keys.add("ossUtilTest/multipartUpload");
//        OSSUtil.deleteObjects(ossClient, bucketName, keys);
//    }
////**************************************************文件上传下载工具类测试**************************************************
//
//    /**
//     * 测试字符串的上传下载
//     */
//    @Test
//    public void testUploadString() {
//        OSSUtil.uploadString(ossClient, bucketName, key("uploadString"), "this is a test", null);
//        OSSUtil.downloadToLocalFile(ossClient, bucketName, key("uploadString"), "e://aaaa.txt");
//    }
//
//    /**
//     * 测试byte数组的上传下载
//     */
//    @Test
//    public void testUploadByteArr() {
//        OSSUtil.uploadByteArr(ossClient, bucketName, key("uploadByteArr"), "this is a byteArr test".getBytes(), null);
//        OSSUtil.downloadToLocalFile(ossClient, bucketName, key("uploadByteArr"), "e://aaaa.txt");
//    }
//
//    /**
//     * 测试流的上传下载
//     */
//    public void testUploadStream(InputStream fileInputStream) {
//        OSSUtil.uploadStream(ossClient, bucketName, key("aaaaa.txt"), fileInputStream, null);
////        OSSUtil.downloadToLocalFile(ossClient, bucketName, key("uploadStream"), "e://aaaa.txt");
//    }
//
//    /**
//     * 测试本地文件的上传下载
//     */
//    @Test
//    public void testDownloadToLocalFile() {
//        OSSUtil.uploadLocalFile(ossClient, bucketName, key("birthday.jpg"), new File("e:/birthday.jpg"), null);
////        OSSUtil.downloadToLocalFile(ossClient, bucketName, key("a.jpg"), "e://test/b.jpg");
//    }
//
//    /**
//     * 测试上传整个文件夹（上传后的根级目录为所传绝对路径最后一个文件夹名）
//     * 如：上传的绝对路径：e://aa//bb，在oss中根级目录为bb
//     */
//    @Test
//    public void testUploadDir(){
//        OSSUtil.uploadLocalDir(ossClient, bucketName,"e://aa//bb");
//    }
//
//    /**
//     * 测试追加上传
//     */
//    @Test
//    public void testAppendUpload() {
//        AppendObjectRequest appendObjectRequest = new AppendObjectRequest(bucketName, key("appendUpload5"), new ByteArrayInputStream("Hello OSS1".getBytes()));
//        appendObjectRequest.setPosition(0L);
//        AppendObjectResult appendObjectResult = OSSUtil.appendUpload(ossClient, appendObjectRequest);
//
//        appendObjectRequest.setPosition(appendObjectResult.getNextPosition());
//        appendObjectRequest.setInputStream(new ByteArrayInputStream(" Hello OSS2".getBytes()));
//        appendObjectResult = OSSUtil.appendUpload(ossClient, appendObjectRequest);
//
//        appendObjectRequest.setPosition(appendObjectResult.getNextPosition());
//        appendObjectRequest.setInputStream(new ByteArrayInputStream(" Hello OSS3".getBytes()));
//        appendObjectResult = OSSUtil.appendUpload(ossClient, appendObjectRequest);
//
//        OSSUtil.downloadToLocalFile(ossClient, bucketName, key("appendUpload5"), "e://bbb.txt");
//    }
//
//    /**
//     * 测试断点续传
//     */
//    @Test
//    public void testBreakpointContinuingly() {
//        UploadFileRequest uploadFileRequest = OSSUtil.createUploadFileRequest(bucketName, key("breakpointContinuingly"), "e://aaaa.txt", 5, 1 * 1024 * 1024, null);
//        // 断点续传上传
//        try {
//            OSSUtil.breakpointContinuingly(ossClient, uploadFileRequest);
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
//    }
//
//    /**
//     * 测试分片上传
//     */
//    @Test
//    public void multipartUpload() {
//        //1、先初始化一个分片上传事件
//        String key = key("test.jpg");
//        String uploadId = OSSUtil.initiateMultipartUpload(ossClient, bucketName, key);
//        List<PartETag> partETags = new ArrayList();
//        File file = new File("e://test/pictest.jpg");
//        //2、创建一个分片上传请求(此步骤可做成多线程，所以这几步没有封装成统一的方法)
//        //①一个一个上传
////        long partSize = 100 * 1024;
////        int partCount = OSSUtil.calPartCount(partSize,file);
////        long fileLength = file.length();
////        for (int i = 0; i < partCount; i++) {
////            long startPos = i * partSize;
////            long curPartSize = (i + 1 == partCount) ? (fileLength - startPos) : partSize;
////            UploadPartResult uploadPartResult = OSSUtil.uploadPart(ossClient, bucketName,key,uploadId,startPos,curPartSize,i+1,file);
////            partETags.add(uploadPartResult.getPartETag());
////        }
//        //②一部分上传
//        List<UploadPartResult> uploadPartResultList = OSSUtil.multiUploadPart(ossClient, bucketName, key, uploadId, file, 100 * 1024, null, null);
//        for (UploadPartResult uploadPartResult : uploadPartResultList) {
//            partETags.add(uploadPartResult.getPartETag());
//        }
//        //3、排序partETags
//        OSSUtil.sortPartETag(partETags);
//        //4、完成上传
//        OSSUtil.completeMultipartUpload(ossClient, bucketName, key, uploadId, partETags);
//    }
//
//    /**
//     * 断点续传下载
//     */
//    @Test
//    public void breakpointResume() {
//        // 下载请求，10个任务并发下载，启动断点续传
//        DownloadFileRequest downloadFileRequest = new DownloadFileRequest(bucketName, key("a.jpg"));
//        downloadFileRequest.setDownloadFile("e://test//aa.jpg");
//        downloadFileRequest.setTaskNum(5);
//        downloadFileRequest.setEnableCheckpoint(true);
//
//        try {
//            OSSUtil.breakpointResume(ossClient, downloadFileRequest);
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
//    }
//
//    /**
//     * 下载oss中的文件到本地文件
//     */
//    @Test
//    public void downloadObject() {
//        OSSUtil.downloadToLocalFile(ossClient, bucketName, "customer/productPic/TMS.png", "e://test/aaa.jpg");
//    }
//
//    //**************************************************图片工具类测试**************************************************
//
//    /**
//     * 创建修改后图片的url
//     */
//    @Test
//    public void createPicUrl() {
////        List<String> styles = new ArrayList<>();
////        styles.add("resize,m_fixed,w_75,h_50");
////        styles.add("rotate,90");
////        System.out.println(OSSUtil.createPicUrl(ossClient,bucketName, key("a.jpg"), null, OSSUtil.ProcessType.IMAGE, styles).toString());
//        System.out.println(OSSUtil.createPicUrl(ossClient, bucketName, key("birthday.jpg"), null,null));
//    }
//
//
//    /**
//     * 下载图片
//     */
//    @Test
//    public void downloadPicByOperate() {
//        List<String> styles = new ArrayList<>();
//        styles.add("resize,m_fixed,w_100,h_100");
//        styles.add("rotate,90");
//        OSSUtil.downloadPicByOperate(ossClient, bucketName, key("a.jpg"), OSSUtil.ProcessType.IMAGE, styles, "e://test/abc.jpg");
//    }
//
//    /**
//     * 获取文件的url
//     */
//    @Test
//    public void getObjectUrl(){
//        //设置文件的权限
////        ossClient.setObjectAcl(bucketName,key("birthday.jpg"),CannedAccessControlList.PublicRead);
//
//        System.out.println(OSSUtil.createFileAccessUrl(ossClient,bucketName,key("birthday.jpg"),null).toString());
//    }
//
//    //**************************************************其他**************************************************
//    @After(value = "org.lilacseeking.Utils.OSSUtilTest")
//    public void shutdownOSSClient() throws Exception {
//        // 关闭ossClient
//        ossClient.shutdown();
//    }
//
//    private static String key(String key) {
//        return "ossUtilTest/" + key;
//    }
//
//
//    /**
//     * 测试分片上传
//     */
//    @Test
//    public void testMultipartUpload(){
//        final File sampleFile = new File("C:\\Users\\Administrator\\Desktop\\Redis-String案例.avi");
//        String objectName = redisService.getUuidCode() + sampleFile.getName().substring(sampleFile.getName().lastIndexOf("."));
//        // 构造视频上传进度VO
//        VideoUploadProcessVO videoUploadProcessVO = VideoUploadProcessVO.builder()
//                .videoOriginName(sampleFile.getName()).videoName(objectName)
//                .videoOriginPath(sampleFile.getPath()).videoPath("OSS地址").videoLength("视频时长").totalVideoSize(sampleFile.length())
//                .uploadVideoSize(0L).uploadStatus(ProcessEnum.PENDING.name()).build();
//        // 存入上传集合
//        FileUploadSetFactory.putUploadMap(objectName,videoUploadProcessVO);
//        // 1. 初始化⼀个分⽚上传事件
//        InitiateMultipartUploadRequest request = new InitiateMultipartUploadRequest(bucketName, objectName);
//        InitiateMultipartUploadResult result = ossClient.initiateMultipartUpload(request);
//        String uploadId = result.getUploadId();
//        // 2. 上传分⽚
//        // partETags是PartETag的集合 PartETag由分⽚的ETag和分⽚号组成
//        List<Future<UploadPartResult>> futures = new ArrayList<Future<UploadPartResult>>();
//        List partETags = new ArrayList<>();
//
//        // 计算文件有多少个分片
//        final long partSize = 1 * 1024 * 1024L;   // 1MB
//        long fileLength = sampleFile.length();
//        int partCount = (int) (fileLength / partSize);
//        if (fileLength % partSize != 0) {
//            partCount++;
//        }
//        // 遍历分⽚上传。
//        for (int i = 0; i < partCount; i++) {
//            long startPos = i * partSize;
//            long curPartSize = (i + 1 == partCount) ? (fileLength - startPos) : partSize;
//            InputStream inStream = null;
//            try {
//                inStream = new FileInputStream(sampleFile);
//                inStream.skip(startPos);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            // 跳过已经上传的分⽚。
//            UploadPartRequest uploadPartRequest = new UploadPartRequest();
//            uploadPartRequest.setBucketName(bucketName);
//            uploadPartRequest.setKey(objectName);
//            uploadPartRequest.setUploadId(uploadId);
//            uploadPartRequest.setInputStream(inStream);
//            // 设置分⽚⼤小。除了最后⼀个分⽚没有⼤小限制，其他的分⽚最小为100KB。
//            uploadPartRequest.setPartSize(curPartSize);
//            // 设置分⽚号。每⼀个上传的分⽚都有⼀个分⽚号，取值范围是1~10000，如果超出这个范围，OSS将返回InvalidArgument的错误码。
//            uploadPartRequest.setPartNumber(i + 1);
//            uploadPartRequest.setProgressListener(new MultipartProgressListener(uploadPartRequest.getUploadId(),objectName,distributedLockService));
//            // 每个分⽚不需要按顺序上传，甚⾄可以在不同客⼾端上传，OSS会按照分⽚号排序组成完整的⽂件。
//            Future<UploadPartResult> feature = executor.submit(new AsyncMultipartUpload(ossClient,uploadPartRequest));
//            futures.add(feature);
//        }
//        for (Future<UploadPartResult> future :futures){
//            // 每次上传分⽚之后，OSS的返回结果会包含⼀个PartETag。PartETag将被保存到partETags中。
//            try {
//                partETags.add(future.get().getPartETag());
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//        }
//        // 排序。partETags必须按分⽚号升序排列。
//        Collections.sort(partETags, new Comparator<PartETag>() {
//            public int compare(PartETag p1, PartETag p2) {
//                return p1.getPartNumber() - p2.getPartNumber();
//            }
//        });
//        // 在执⾏该操作时，需要提供所有有效的partETags。OSS收到提交的partETags后，会逐⼀验证每个分⽚的有效性。当所有的数据分⽚验证通过后，OSS将把这些分⽚组合成⼀个完整的⽂件。
//        CompleteMultipartUploadRequest completeMultipartUploadRequest =
//                new CompleteMultipartUploadRequest(bucketName, objectName, uploadId, partETags);
//        ossClient.completeMultipartUpload(completeMultipartUploadRequest);
//
//    }
//
//}
