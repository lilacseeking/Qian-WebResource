package org.lilacseeking.video.core.User.Service;

import com.alibaba.fastjson.JSONObject;
import org.lilacseeking.video.infrastructure.Model.DTO.TeacherDTO;
import org.lilacseeking.video.infrastructure.utils.Page;

/**
 * @Auther: lilacseeking
 * @Date: 2019/3/30 12:08
 * @Description:教师服务类
 */
public interface TeacherService {
    /**
     * 获取教师列表
     * @param filter
     * @return
     */
    Page getTeacherList(JSONObject filter);

    /**
     * 申请成为教师
     * @param teacherDTO
     * @return
     */
    TeacherDTO applyTeacher(TeacherDTO teacherDTO);

    /**
     * 教师审核
     * @param teacherDTO
     * @return
     */
    TeacherDTO auditTeacher(TeacherDTO teacherDTO);
}
