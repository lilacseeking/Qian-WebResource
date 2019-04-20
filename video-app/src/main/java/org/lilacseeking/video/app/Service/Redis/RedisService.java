package org.lilacseeking.video.app.Service.Redis;

import org.lilacseeking.video.infrastructure.Model.DTO.CourseForHomeDTO;
import org.lilacseeking.video.infrastructure.Model.DTO.UserBasicInfoDTO;
import org.lilacseeking.video.infrastructure.Model.DTO.LoginDTO;
import org.lilacseeking.video.infrastructure.Model.DTO.UserBasicInfoDTO;

import java.util.List;

/**
 * @Auther: lilacseeking
 * @Date: 2018/10/30 19:52
 * @Description: Redis工具类
 */
public interface RedisService {

    // 手机验证码
    String QIAN_MANAGE_SMS_CODE = "Qian-Manage-sms-Code";
    // 用户信息
    String QIAN_MANAGE_USER_INFO = "Qian-Manage-user-Info";
    // 课程列表
    String QIAN_HOME_COURSE_LIST = "Qian-Home-course-List";

    // 保存验证码
    Boolean saveSmsCode(String mobile, String code);

    // 查询验证码
    String getSmsCode(String mobile);

    // 验证码置为失效
    void invalid(String mobile);

    // 保存登录token
    UserBasicInfoDTO saveUserToken(UserBasicInfoDTO userBasicInfoDTO);
    // 获取用户token
    UserBasicInfoDTO getUserToken(UserBasicInfoDTO userBasicInfoDTO);
    // 移除用户token
    String removeUserToken(LoginDTO loginDTO);
    /**
     * 获取唯一字符编号
     * @return
     */
     String getUuidCode();

    //获取首页课程列表
    List<CourseForHomeDTO> getCourseListForHome();
}
