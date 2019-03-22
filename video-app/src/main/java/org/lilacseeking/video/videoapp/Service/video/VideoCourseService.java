package org.lilacseeking.video.videoapp.Service.video;


import com.alibaba.fastjson.JSONObject;
import org.lilacseeking.video.videoapp.Model.PO.VideoCoursePO;
import org.lilacseeking.video.videoapp.Utils.Page;

/**
 * @Auther: lilacseeking
 * @Date: 2018/10/18 22:46
 * @Description:课程相关接口
 */
public interface VideoCourseService {

    /**
     * 新增课程
     * @param videoCoursePO
     * @return
     */
    VideoCoursePO addVideoClass(VideoCoursePO videoCoursePO);

    /**
     * 获取课程列表
     * @param filter
     * @return
     */
    Page getCourseList(JSONObject filter);
}
