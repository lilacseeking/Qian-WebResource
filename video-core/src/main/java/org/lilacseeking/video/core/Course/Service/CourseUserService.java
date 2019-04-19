package org.lilacseeking.video.core.Course.Service;

import com.alibaba.fastjson.JSONObject;
import org.lilacseeking.video.core.Course.Model.CourseUserPO;
import org.lilacseeking.video.infrastructure.Model.DTO.ContentDTO;
import org.lilacseeking.video.infrastructure.Model.DTO.CourseUserDTO;
import org.lilacseeking.video.infrastructure.utils.Page;

/**
 * @Auther: lilacseeking
 * @Date: 2019/3/24 15:27
 * @Description:
 */
public interface CourseUserService {
    /**
     * 新增用户课程关系
     * @param courseUserDTO
     * @return
     */
    CourseUserDTO addCourseUser(CourseUserDTO courseUserDTO);

    /**
     * 查询用户课程关系
     * @param filter
     * @return
     */
    CourseUserDTO getCourseUserInfo(JSONObject filter);

    /**
     * 查询用户课程列表
     * @param filter
     * @return
     */
    Page getCourseUserList(JSONObject filter);

    /**
     * 更新用户课程关系
     * @param courseUserDTO
     * @return
     */
    CourseUserPO updateCourseUserPO(CourseUserDTO courseUserDTO);

    /**
     * 更新用户学习进度
     * @param contentDTO
     * @return
     */
    CourseUserDTO updateUserCourseRate(ContentDTO contentDTO);
}
