package org.lilacseeking.video.service.Config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Auther: lilacseeking
 * @Date: 2018/11/9 22:26
 * @Description: 系统配置：短信平台、对象存储
 */
@Data
@ConfigurationProperties(prefix = "lilacseeking")
public class ConstantProperties {
    /**
     * 短信平台配置
     */
    public Sms sms;
    /**
     * Oss对象存储配置
     */
    public Oss oss;
    /**
     * 文件上传相关配置
     */
    public Upload upload;
    /**
     * 文件转码相关配置
     */
    public Encode encode;

    @Data
    public static class Sms{
        /**
         * 短信平台Id
         */
        public String accessKeyId;
        /**
         * 短信平台密钥
         */
        public String accessKeySecret;

    }

    @Data
    public static class Oss{
        /**
         * 外部域名
         */
        public String endpoint;
        /**
         * AccessKey
         */
        public String accessKeyId;
        /**
         * AccessKeySecret
         */
        public String accessKeySecret;
        /**
         * 存储空间
         */
        public String bucketName;

    }

    @Data
    public static class Upload{
        /**
         * 本地转码文件临时目录
         */
        public String encodeTargetRoute;
        /**
         * 本地转码源文件临时目录
         */
        public String sourceRoute;
        /**
         * Oss视频文件存放目录
         */
        public String videoRoute;
        /**
         * 缩略图路径
         */
        public String thumbnailRoute;
    }
    @Data
    public static class Encode{
        /**
         * 超清
         */
        private String FHD;
        /**
         * 高清
         */
        private String HD;
        /**
         * 标清
         */
        private String SD;
    }
}
