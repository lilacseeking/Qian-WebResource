package org.lilacseeking.video.videoapp.Service.course;


import com.alibaba.fastjson.JSONObject;
import org.lilacseeking.video.videoapp.Model.DTO.ContentDTO;
import org.lilacseeking.video.videoapp.Model.DTO.CourseDTO;
import org.lilacseeking.video.videoapp.Model.DTO.CourseUserDTO;
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
