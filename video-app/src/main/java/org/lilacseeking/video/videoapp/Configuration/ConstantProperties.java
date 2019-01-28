package org.lilacseeking.video.videoapp.Configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Auther: lilacseeking
 * @Date: 2018/11/9 22:26
 * @Description: 系统配置：短信平台、对象存储
 */
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

    public ConstantProperties() {
    }

    public ConstantProperties(Sms sms, Oss oss) {
        this.sms = sms;
        this.oss = oss;
    }

    public Sms getSms() {
        return sms;
    }

    public void setSms(Sms sms) {
        this.sms = sms;
    }

    public Oss getOss() {
        return oss;
    }

    public void setOss(Oss oss) {
        this.oss = oss;
    }

    public static class Sms{
        /**
         * 短信平台Id
         */
        public String accessKeyId;
        /**
         * 短信平台密钥
         */
        public String accessKeySecret;

        public String getAccessKeyId() {
            return accessKeyId;
        }

        public void setAccessKeyId(String accessKeyId) {
            this.accessKeyId = accessKeyId;
        }

        public String getAccessKeySecret() {
            return accessKeySecret;
        }

        public void setAccessKeySecret(String accessKeySecret) {
            this.accessKeySecret = accessKeySecret;
        }

        public Sms() {
        }

        public Sms(String accessKeyId, String accessKeySecret) {
            this.accessKeyId = accessKeyId;
            this.accessKeySecret = accessKeySecret;
        }
    }

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

        public Oss(){

        }

        public Oss(String endpoint, String accessKeyId, String accessKeySecret, String bucketName) {
            this.endpoint = endpoint;
            this.accessKeyId = accessKeyId;
            this.accessKeySecret = accessKeySecret;
            this.bucketName = bucketName;
        }

        public String getEndpoint() {
            return endpoint;
        }

        public void setEndpoint(String endpoint) {
            this.endpoint = endpoint;
        }

        public String getAccessKeyId() {
            return accessKeyId;
        }

        public void setAccessKeyId(String accessKeyId) {
            this.accessKeyId = accessKeyId;
        }

        public String getAccessKeySecret() {
            return accessKeySecret;
        }

        public void setAccessKeySecret(String accessKeySecret) {
            this.accessKeySecret = accessKeySecret;
        }

        public String getBucketName() {
            return bucketName;
        }

        public void setBucketName(String bucketName) {
            this.bucketName = bucketName;
        }
    }
}
