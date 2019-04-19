package org.lilacseeking.video.app.Common;

import org.lilacseeking.video.app.Utils.OSSUtil;

import java.io.InputStream;
import java.util.concurrent.Callable;

/**
 * @Auther: lilacseeking
 * @Date: 2019/4/14 10:04
 * @Description:
 */
public class AsyncPutObjectUpload implements Callable {

    private InputStream inputStream;

    private String key;

    public AsyncPutObjectUpload(InputStream inputStream,String key){
        this.inputStream = inputStream;
        this.key = key;
    }

    @Override
    public Object call() throws Exception {
        return OSSUtil.putObject(inputStream,key);
    }
}
