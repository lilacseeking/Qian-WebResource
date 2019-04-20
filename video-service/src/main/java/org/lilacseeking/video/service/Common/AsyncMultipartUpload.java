package org.lilacseeking.video.service.Common;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.UploadPartRequest;
import com.aliyun.oss.model.UploadPartResult;
import java.util.concurrent.Callable;

/**
 * @Author： lvming
 * @Date： Created in 11:26 2019/2/12
 * @Description： 异步分片上传文件
 * @Modified By：
 * @Version:
 */
public class AsyncMultipartUpload implements Callable<UploadPartResult> {
    // Oss客户端
    private OSSClient ossClient;
    // 文件分⽚
    private UploadPartRequest uploadPartRequest;

    public AsyncMultipartUpload(OSSClient ossClient, UploadPartRequest uploadPartRequest){
        this.ossClient = ossClient;
        this.uploadPartRequest = uploadPartRequest;
    }

    @Override
    public UploadPartResult call() throws Exception {
        return ossClient.uploadPart(uploadPartRequest);
    }
}
