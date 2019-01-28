package org.lilacseeking.video.videoapp.Utils;

import java.io.InputStream;

/**
 * @Auther: lilacseeking
 * @Date: 2018/12/17 22:09
 * @Description:
 */
public class UploadUtils {
    /**
     * 流的上传
     * @param fileInputStream
     */
    public static void uploadFileStream(InputStream fileInputStream){
        new OSSUtilTest().testUploadStream(fileInputStream);
    }
}
