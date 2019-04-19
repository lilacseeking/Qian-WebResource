package org.lilacseeking.video.infrastructure.Model.Factory;


import java.util.HashMap;

/**
 * @Author： lvming
 * @Date： Created in 18:20 2019/2/13
 * @Description： 视频上传任务队列
 * @Modified By：
 * @Version:
 * @description: 1 任务开始之前，在此队列暂存
 *                 2 任务进行过程中，进度信息及时更新
 *                 3 任务成功完成后，持久化至数据库（包含成功和失败两种场景），在此队列中移除任务
 *                 4 此队列应是线程安全的，以保证多个线程正确更新数据
 */
public class FileUploadSetFactory {

    /**
     * 视频上传集合
     */
    private static HashMap uploadMap = null;

    /**
     * 获取视频上传集合
     * @return
     */
    public static HashMap getUploadMap(){
        if (null == uploadMap){
            uploadMap = new HashMap();
        }
        return uploadMap;
    }

    /**根据key获取上传任务
     *
     * @return
     */
    public static Object getUploadObject(String key) {
        return getUploadMap().get(key);
    }
    /**
     * 添加任务至上传集合
     * @param key
     * @param src
     */
    public static void putUploadMap(String key,Object src){
        getUploadMap().put(key,src);
    }
}
