package org.lilacseeking.video.infrastructure.Model.Factory;

import java.util.HashMap;

/**
 * @Author： lvming
 * @Date： Created in 15:13 2019/2/15
 * @Description： 视频转码任务队列
 * @Modified By：
 * @Version:
 */
public class VideoEncodeSetFactory {
    /**
     * 视频转码集合
     */
    private static HashMap encodeMap = null;

    /**
     * 构造转码任务队列
     * @return
     */
    public static HashMap getEncodeMap() {
        if (null == encodeMap){
            encodeMap = new HashMap();
        }
        return encodeMap;
    }

    /**
     * 根据key获取转码任务
     * @param key
     * @return
     */
    public static Object getEncodeObject(String key){
        return getEncodeMap().get(key);
    }

    /**
     * 将转码任务放置队列
     * @param key
     * @param src
     */
    public static void putEncodeObject(String key, Object src){
        getEncodeMap().put(key,src);
    }

    /**
     * 删除转码任务
     * @param key
     */
    public static void removeEncodeObject(String key){
        getEncodeMap().remove(key);
    }
}
