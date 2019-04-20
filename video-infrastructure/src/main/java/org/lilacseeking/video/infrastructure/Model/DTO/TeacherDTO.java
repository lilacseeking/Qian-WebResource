package org.lilacseeking.video.infrastructure.Model.DTO;

import lombok.Data;
import org.lilacseeking.video.infrastructure.enums.YesOrNoEnum;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: lilacseeking
 * @Date: 2019/3/30 12:49
 * @Description:
 */
@Data
public class TeacherDTO implements Serializable {
    /**
     * 用户编号
     */
    private Long userId;
    /**
     * 用户名
     */
    private String username;

    /**
     * 邮箱
     */
    private String email;
    /**
     * 创建日期
     */
    private Date gmtCreate;

    /**
     * 教师编号
     */
    private Long teacherId;

    /**
     * 教师手机号
     */
    private String teacherMobile;

    /**
     * 教师姓名
     */
    private String teacherName;

    /**
     * 身份证号
     */
    private String idCardNo;

    /**
     * 身份证文件名
     */
    private String idCardFileName;

    /**
     * 学历证书编号
     */
    private String degreeNo;

    /**
     * 学历证书文件名
     */
    private String degreeFileName;

    /**
     * 研究方向
     */
    private String researchArea;

    /**
     * 个人简介
     */
    private String personalResume;

    /**
     * 工作经历
     */
    private String workExperience;

    /**
     * 认证状态
     */
    private YesOrNoEnum verifyStatus;

}
