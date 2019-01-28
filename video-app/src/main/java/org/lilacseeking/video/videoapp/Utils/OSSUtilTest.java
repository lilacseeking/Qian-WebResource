package org.lilacseeking.video.videoapp.Utils;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class OSSUtilTest {
    private static String endpoint = "oss-cn-beijing.aliyuncs.com";
    private static String accessKeyId = "LTAI1VcTye0NFaFM";
    private static String accessKeySecret = "ArmHD4PsXUfoZovIJNwQDtelMcwanS";
    private static String bucketName = "lilacseeking";
    private static OSSClient ossClient = OSSUtil.createOSSClient(endpoint, accessKeyId, accessKeySecret);

    //**************************************************一些展示**************************************************

    /**
     * 打印所有的bucket
     */
    @Test
    public void listBuckets() {
        // 创建OSSClient实例
        OSSClient ossClient = OSSUtil.createOSSClient(endpoint, accessKeyId, accessKeySecret);
        // 使用访问OSS
        // *********************列举bucket*********************
        List<Bucket> buckets = ossClient.listBuckets();
        for (Bucket bucket : buckets) {
            System.out.println(" - " + bucket.getName());
        }
    }

    /**
     * 展示文件
     */
    @Test
    public void showObjects() {
        List<OSSObjectSummary> list = OSSUtil.listObjects(ossClient, bucketName, 200, null);
        for (OSSObjectSummary ossObjectSummary : list) {
            System.out.println(ossObjectSummary.getKey());
        }
    }

    /**
     * 删除文件
     */
    @Test
    public void deleteObjects() {
        List<String> keys = new ArrayList<>();
        keys.add("bb/b.txt");
        keys.add("bb/cc/c.txt");
//        keys.add("ossUtilTest/a.jpg");
//        keys.add("ossUtilTest/multipartUpload");
        OSSUtil.deleteObjects(ossClient, bucketName, keys);
    }
//**************************************************文件上传下载工具类测试**************************************************

    /**
     * 测试字符串的上传下载
     */
    @Test
    public void testUploadString() {
        OSSUtil.uploadString(ossClient, bucketName, key("uploadString"), "this is a test", null);
        OSSUtil.downloadToLocalFile(ossClient, bucketName, key("uploadString"), "e://aaaa.txt");
    }

    /**
     * 测试byte数组的上传下载
     */
    @Test
    public void testUploadByteArr() {
        OSSUtil.uploadByteArr(ossClient, bucketName, key("uploadByteArr"), "this is a byteArr test".getBytes(), null);
        OSSUtil.downloadToLocalFile(ossClient, bucketName, key("uploadByteArr"), "e://aaaa.txt");
    }

    /**
     * 测试流的上传下载
     */
    public void testUploadStream(InputStream fileInputStream) {
        OSSUtil.uploadStream(ossClient, bucketName, key("aaaaa.txt"), fileInputStream, null);
//        OSSUtil.downloadToLocalFile(ossClient, bucketName, key("uploadStream"), "e://aaaa.txt");
    }

    /**
     * 测试本地文件的上传下载
     */
    @Test
    public void testDownloadToLocalFile() {
        OSSUtil.uploadLocalFile(ossClient, bucketName, key("birthday.jpg"), new File("e:/birthday.jpg"), null);
//        OSSUtil.downloadToLocalFile(ossClient, bucketName, key("a.jpg"), "e://test/b.jpg");
    }

    /**
     * 测试上传整个文件夹（上传后的根级目录为所传绝对路径最后一个文件夹名）
     * 如：上传的绝对路径：e://aa//bb，在oss中根级目录为bb
     */
    @Test
    public void testUploadDir(){
        OSSUtil.uploadLocalDir(ossClient, bucketName,"e://aa//bb");
    }

    /**
     * 测试追加上传
     */
    @Test
    public void testAppendUpload() {
        AppendObjectRequest appendObjectRequest = new AppendObjectRequest(bucketName, key("appendUpload5"), new ByteArrayInputStream("Hello OSS1".getBytes()));
        appendObjectRequest.setPosition(0L);
        AppendObjectResult appendObjectResult = OSSUtil.appendUpload(ossClient, appendObjectRequest);

        appendObjectRequest.setPosition(appendObjectResult.getNextPosition());
        appendObjectRequest.setInputStream(new ByteArrayInputStream(" Hello OSS2".getBytes()));
        appendObjectResult = OSSUtil.appendUpload(ossClient, appendObjectRequest);

        appendObjectRequest.setPosition(appendObjectResult.getNextPosition());
        appendObjectRequest.setInputStream(new ByteArrayInputStream(" Hello OSS3".getBytes()));
        appendObjectResult = OSSUtil.appendUpload(ossClient, appendObjectRequest);

        OSSUtil.downloadToLocalFile(ossClient, bucketName, key("appendUpload5"), "e://bbb.txt");
    }

    /**
     * 测试断点续传
     */
    @Test
    public void testBreakpointContinuingly() {
        UploadFileRequest uploadFileRequest = OSSUtil.createUploadFileRequest(bucketName, key("breakpointContinuingly"), "e://aaaa.txt", 5, 1 * 1024 * 1024, null);
        // 断点续传上传
        try {
            OSSUtil.breakpointContinuingly(ossClient, uploadFileRequest);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    /**
     * 测试分片上传
     */
    @Test
    public void multipartUpload() {
        //1、先初始化一个分片上传事件
        String key = key("test.jpg");
        String uploadId = OSSUtil.initiateMultipartUpload(ossClient, bucketName, key);
        List<PartETag> partETags = new ArrayList();
        File file = new File("e://test/pictest.jpg");
        //2、创建一个分片上传请求(此步骤可做成多线程，所以这几步没有封装成统一的方法)
        //①一个一个上传
//        long partSize = 100 * 1024;
//        int partCount = OSSUtil.calPartCount(partSize,file);
//        long fileLength = file.length();
//        for (int i = 0; i < partCount; i++) {
//            long startPos = i * partSize;
//            long curPartSize = (i + 1 == partCount) ? (fileLength - startPos) : partSize;
//            UploadPartResult uploadPartResult = OSSUtil.uploadPart(ossClient, bucketName,key,uploadId,startPos,curPartSize,i+1,file);
//            partETags.add(uploadPartResult.getPartETag());
//        }
        //②一部分上传
        List<UploadPartResult> uploadPartResultList = OSSUtil.multiUploadPart(ossClient, bucketName, key, uploadId, file, 100 * 1024, null, null);
        for (UploadPartResult uploadPartResult : uploadPartResultList) {
            partETags.add(uploadPartResult.getPartETag());
        }
        //3、排序partETags
        OSSUtil.sortPartETag(partETags);
        //4、完成上传
        OSSUtil.completeMultipartUpload(ossClient, bucketName, key, uploadId, partETags);
    }

    /**
     * 断点续传下载
     */
    @Test
    public void breakpointResume() {
        // 下载请求，10个任务并发下载，启动断点续传
        DownloadFileRequest downloadFileRequest = new DownloadFileRequest(bucketName, key("a.jpg"));
        downloadFileRequest.setDownloadFile("e://test//aa.jpg");
        downloadFileRequest.setTaskNum(5);
        downloadFileRequest.setEnableCheckpoint(true);

        try {
            OSSUtil.breakpointResume(ossClient, downloadFileRequest);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    /**
     * 下载oss中的文件到本地文件
     */
    @Test
    public void downloadObject() {
        OSSUtil.downloadToLocalFile(ossClient, bucketName, "customer/productPic/TMS.png", "e://test/aaa.jpg");
    }

    //**************************************************图片工具类测试**************************************************

    /**
     * 创建修改后图片的url
     */
    @Test
    public void createPicUrl() {
//        List<String> styles = new ArrayList<>();
//        styles.add("resize,m_fixed,w_75,h_50");
//        styles.add("rotate,90");
//        System.out.println(OSSUtil.createPicUrl(ossClient,bucketName, key("a.jpg"), null, OSSUtil.ProcessType.IMAGE, styles).toString());
        System.out.println(OSSUtil.createPicUrl(ossClient, bucketName, key("birthday.jpg"), null,null));
    }


    /**
     * 下载图片
     */
    @Test
    public void downloadPicByOperate() {
        List<String> styles = new ArrayList<>();
        styles.add("resize,m_fixed,w_100,h_100");
        styles.add("rotate,90");
        OSSUtil.downloadPicByOperate(ossClient, bucketName, key("a.jpg"), OSSUtil.ProcessType.IMAGE, styles, "e://test/abc.jpg");
    }

    /**
     * 获取文件的url
     */
    @Test
    public void getObjectUrl(){
        //设置文件的权限
//        ossClient.setObjectAcl(bucketName,key("birthday.jpg"),CannedAccessControlList.PublicRead);

        System.out.println(OSSUtil.createObjUrl(ossClient,bucketName,key("birthday.jpg"),null).toString());
    }

    //**************************************************其他**************************************************
    @After(value = "org.lilacseeking.Utils.OSSUtilTest")
    public void shutdownOSSClient() throws Exception {
        // 关闭ossClient
        ossClient.shutdown();
    }

    private static String key(String key) {
        return "ossUtilTest/" + key;
    }
}
