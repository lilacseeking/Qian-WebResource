package org.lilacseeking.video.videoapp.Utils;

import com.aliyun.oss.OSSClient;
import com.google.common.primitives.Bytes;
import org.lilacseeking.video.videoapp.Service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.InputStream;

/**
 * @Auther: lilacseeking
 * @Date: 2018/12/17 22:09
 * @Description:
 */
@Component
public class UploadUtils {

    // 上传工具类
    @Autowired
    private static UploadUtils uploadUtils;
    // Redis服务
    @Autowired
    private RedisService redisService;
    // 初始化
    @PostConstruct
    public void init(){
        uploadUtils = this;
        uploadUtils.redisService = this.redisService;

    }
    /**
     * 流的上传
     * @param fileInputStream
     */
    public static void uploadFileStream(InputStream fileInputStream){
//        new OSSUtilTest().testUploadStream(fileInputStream);
    }
    /**
     * 图片上传
     */
    public static String uploadImage(InputStream inputStream){
        String uuidCode = uploadUtils.redisService.getUuidCode();
        // 同步返回文件链接，异步返回
        String thumbnailUrl = OSSUtil.putObject(inputStream, uuidCode);
        return thumbnailUrl;
    }
}
