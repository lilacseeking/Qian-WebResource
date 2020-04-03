package org.lilacseeking.video.videoapp.Utils;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.event.ProgressEvent;
import com.aliyun.oss.event.ProgressEventType;
import com.aliyun.oss.event.ProgressListener;
import com.aliyun.oss.model.PutObjectRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @Auther: lilacseeking
 * @Date: 2019/2/6 21:30
 * @Description:
 */
public class PutObjectProgressListener implements ProgressListener {
    private long bytesWritten = 0;
    private long totalBytes = -1;
    private boolean succeed = false;

    @Override
    public void progressChanged(ProgressEvent progressEvent) {
        long bytes = progressEvent.getBytes();
        ProgressEventType eventType = progressEvent.getEventType();
        switch (eventType) {
            case TRANSFER_STARTED_EVENT:
                System.out.println("Start to upload......");
                break;
            case REQUEST_CONTENT_LENGTH_EVENT:
                this.totalBytes = bytes;
                System.out.println(this.totalBytes + " bytes in total will be uploaded to OSS");
                break;
            case REQUEST_BYTE_TRANSFER_EVENT:
                this.bytesWritten += bytes;
                if (this.totalBytes != -1) {
                    int percent = (int)(this.bytesWritten * 100.0 / this.totalBytes);
                    System.out.println(bytes + " bytes have been written at this time, upload progress: " + percent + "%(" + this.bytesWritten + "/" + this.totalBytes + ")");
                } else {
                    System.out.println(bytes + " bytes have been written at this time, upload ratio: unknown" + "(" + this.bytesWritten + "/...)");
                }
                break;
            case TRANSFER_COMPLETED_EVENT:
                this.succeed = true;
                System.out.println("Succeed to upload, " + this.bytesWritten + " bytes have been transferred in total");
                break;
            case TRANSFER_FAILED_EVENT:
                System.out.println("Failed to upload, " + this.bytesWritten + " bytes have been transferred");
                break;
            default:
                break;
        }
    }

    public boolean isSucceed() {
        return succeed;
    }
}
