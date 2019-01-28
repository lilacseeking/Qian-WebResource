package org.lilacseeking.video.videoapp.Model.PO;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Auther: lilacseeking
 * @Date: 2018/11/12 20:17
 * @Description:
 */
@Data
@Builder
@Accessors(chain = true)
@Entity
@Table(name = "teacher_info")
public class TeacherPO extends BaseEntityPO{

    /**
     * 用户编号
     */
    private Long userId;

    /**
     * 身份证号
     */
    @Column(name="id_card_no", nullable=false, unique = true,length = 18)
    private String idCardNo;

    /**
     * 身份证文件名
     */
    @Column(name="id_card_file_name", nullable=false,length = 64)
    private String idCardFileName;

    /**
     * 学历证书编号
     */
    @Column(name="degree_no", nullable=false, unique = true,length = 20)
    private String degreeNo;

    /**
     * 学历证书文件名
     */
    @Column(name="degree_file_name", nullable=false,length = 11)
    private String degreeFileName;

    /**
     * 研究方向
     */
    @Column(name="research_area", nullable=false,length = 18)
    private String researchArea;

    /**
     * 个人简介
     */
    @Column(name="personal_resume", nullable=false,length = 1024)
    private String personalResume;

    /**
     * 工作经历
     */
    @Column(name="work_experience",length = 1024)
    private String workExprience;

    /**
     * 认证状态
     */
    @Column(name="verify_status", nullable=false,length = 18)
    private String verifyStatus;

}
