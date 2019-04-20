package org.lilacseeking.video.core.Course.Service;

import com.alibaba.fastjson.JSONObject;
import org.lilacseeking.video.core.Course.Model.VideoCoursePO;
import org.lilacseeking.video.infrastructure.Model.DTO.CourseDTO;
import org.lilacseeking.video.infrastructure.utils.Page;

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

    /**
     * 获取课程详情
     * @param courseDTO
     * @return
     */
    VideoCoursePO getCourseInfo(CourseDTO courseDTO);

    /**
     * 下架课程
     * @param courseDTO
     * @return
     */
    CourseDTO shelveCourse(CourseDTO courseDTO);


}
