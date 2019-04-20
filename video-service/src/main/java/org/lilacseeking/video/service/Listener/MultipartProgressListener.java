package org.lilacseeking.video.service.Listener;

import com.alibaba.fastjson.JSON;
import com.aliyun.oss.event.ProgressEvent;
import com.aliyun.oss.event.ProgressEventType;
import com.aliyun.oss.event.ProgressListener;
import org.lilacseeking.video.infrastructure.Model.Factory.FileUploadSetFactory;
import org.lilacseeking.video.infrastructure.Model.VO.VideoUploadProcessVO;
import org.lilacseeking.video.service.Lock.DistributedLockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author： lvming
 * @Date： Created in 14:43 2019/2/12
 * @Description： 文件上传下载进度条
 * @Modified By：
 * @Version:
 * 1 当前分片上传信息写入上传对象中
 * 2 每隔2秒获取当前上传进度信息
 * 3 当前线程去获取可修改上传进度实体的锁
 * 4 当前线程去获取上传进度实体，修改上传信息，保存至上传队列
 * 5 释放锁
 * 6 上传任务完成后，保存上传结果至数据库，并将任务从队列中移除。
 *  */
public class MultipartProgressListener implements ProgressListener {

    //分布式锁
    private DistributedLockService distributedLockService;

    public static Logger LOGGER = LoggerFactory.getLogger(MultipartProgressListener.class);
    // 分片编号
    private String uploadId ;
    // 写入字节数
    private long bytesWritten = 0;
    // 总字节数
    private long totalBytes = -1;
    // 成功标志
    private boolean succeed = false;
    // 文件key
    private String videoName;

    // 构造函数
    public MultipartProgressListener(String uploadId,String videoName ){
//        this.distributedLockService = distributedLockService;
        this.videoName = videoName;
        this.uploadId = uploadId;
    }

    @Override
    public void progressChanged(ProgressEvent progressEvent) {
        // 1 获取当前文件上传信息
        VideoUploadProcessVO videoUploadProcessVO = (VideoUploadProcessVO)FileUploadSetFactory.getUploadObject(videoName);
//        // 2 获取锁
//        Boolean lock = distributedLockService.acquireLock(this.videoName);
//        Assert.isTrue(lock,ErrorCodeEumn.VIDEO_UPLOAD_RATE_FAIL.getName());
        LOGGER.info("进度监听转换开始，当前线程是：{},输入参数是：{}",Thread.currentThread().getName(), JSON.toJSONString(progressEvent));
        long bytes = progressEvent.getBytes();
        ProgressEventType eventType = progressEvent.getEventType();
        switch (eventType) {
            case TRANSFER_STARTED_EVENT:
                System.out.println(uploadId +" Start to upload......");
                break;

            case REQUEST_CONTENT_LENGTH_EVENT:
                this.totalBytes = bytes;
                System.out.println(this.totalBytes + " bytes in total will be uploaded to OSS");
                break;

            case REQUEST_BYTE_TRANSFER_EVENT:
                videoUploadProcessVO.setUploadVideoSize(videoUploadProcessVO.getUploadVideoSize() + bytes);
                this.bytesWritten += bytes;
                if (this.totalBytes != -1) {
                    LOGGER.info("分片上传进度获取测试，上传进度:{}%,视频信息:{}",videoUploadProcessVO.getUploadRate(),JSON.toJSONString(videoUploadProcessVO));
                } else {
                    System.out.println(bytes + " bytes have been written at this time, upload ratio: unknown" +
                            "(" + this.bytesWritten + "/...)");
                }
                break;

            case TRANSFER_COMPLETED_EVENT:
                this.succeed = true;
                System.out.println(uploadId +" Succeed to upload, " + this.bytesWritten + " bytes have been transferred in total");
                break;

            case TRANSFER_FAILED_EVENT:
                System.out.println("Failed to upload, " + this.bytesWritten + " bytes have been transferred");
                break;

            default:
                break;
        }
        FileUploadSetFactory.putUploadMap(videoName,videoUploadProcessVO);
//        Boolean unlock = distributedLockService.releaseLock(this.videoName);
//        Assert.isTrue(unlock,ErrorCodeEumn.VIDEO_UPLOAD_RATE_FAIL.getName());
    }

    public boolean isSucceed() {
        return succeed;
    }
}

