package org.lilacseeking.video.core.User.Service.Impl;

import com.alibaba.fastjson.JSONObject;
import org.lilacseeking.video.core.User.Model.TeacherPO;
import org.lilacseeking.video.core.User.Model.UserPO;
import org.lilacseeking.video.core.User.Repository.TeacherRepository;
import org.lilacseeking.video.core.User.Service.TeacherService;
import org.lilacseeking.video.core.User.Service.UserService;
import org.lilacseeking.video.infrastructure.Model.DTO.TeacherDTO;
import org.lilacseeking.video.infrastructure.Model.DTO.UserBasicDTO;
import org.lilacseeking.video.infrastructure.enums.ErrorCodeEumn;
import org.lilacseeking.video.infrastructure.utils.BeanCopyUtil;
import org.lilacseeking.video.infrastructure.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @Auther: lilacseeking
 * @Date: 2019/3/30 12:08
 * @Description: 教师服务实现类
 */
@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private UserService userService;

    /**
     * 获取教师列表
     *
     * @param filter
     * @return
     */
    @Override
    public Page getTeacherList(JSONObject filter) {
        Integer page = filter.getInteger("page");
        Integer rows = filter.getInteger("rows");
        Assert.isTrue(page!=null&& rows!=null,ErrorCodeEumn.PAGE_INFO_FORMAT_WRONG.getName());
        return teacherRepository.getTeacherList(filter,new Page(page,rows));
    }

    /**
     * 申请成为教师
     *
     * @param teacherDTO
     * @return
     */
    @Override
    public TeacherDTO applyTeacher(TeacherDTO teacherDTO) {
        TeacherPO teacherPO = new TeacherPO();
        Assert.isTrue(null != teacherDTO.getUserId(),ErrorCodeEumn.USER_ID_NOT_NULL.getName());
        BeanCopyUtil.copyPropertiesIgnoreNull(teacherDTO,teacherPO);
        teacherPO = teacherRepository.saveOrUpdate(teacherPO);
        TeacherDTO result = new TeacherDTO();
        BeanCopyUtil.copyPropertiesIgnoreNull(teacherPO,result);
        result.setTeacherId(teacherPO.getId());
        return result;
    }

    /**
     * 教师审核
     *
     * @param teacherDTO
     * @return
     */
    @Override
    public TeacherDTO auditTeacher(TeacherDTO teacherDTO) {
        // 修改教师申请记录
        Assert.isTrue(null != teacherDTO.getTeacherId(),ErrorCodeEumn.TEACHER_ID_NOT_NULL.getName());
        Assert.isTrue(null != teacherDTO.getVerifyStatus(),ErrorCodeEumn.VERIFY_STATUS_NOT_NULL.getName());
        TeacherPO teacherPO = teacherRepository.getTeacherByTeacherId(teacherDTO.getTeacherId());
        teacherPO.setVerifyStatus(teacherDTO.getVerifyStatus());
        teacherPO = teacherRepository.saveOrUpdate(teacherPO);
        // 修改用户角色
        UserBasicDTO userBasicDTO = new UserBasicDTO();
        userBasicDTO.setId(teacherPO.getUserId());
        UserPO userInfo = userService.getUserInfo(userBasicDTO);
        userInfo.setDefaultUser(1);
        UserPO userPO = userService.updateUserInfo(userInfo);
        // 整理返回结果
        TeacherDTO result = new TeacherDTO();
        BeanCopyUtil.copyPropertiesIgnoreNull(teacherPO,result);
        result.setTeacherId(teacherPO.getId());
        result.setTeacherName(userPO.getName());
        result.setTeacherMobile(userPO.getMobile());
        return result;
    }
}
