package org.lilacseeking.video.videoapp.Model.PO;

import lombok.Data;
import lombok.experimental.Accessors;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Auther: lilacseeking
 * @Date: 2018/8/1 13:14
 * @Description:用户基本信息表
 * 此表为用户基本信息表，仅保存用户基本信息。
 */
@Data
//@Builder
@Accessors(chain = true)
@Entity
@Table(name = "user")
public class UserPO extends BaseEntityPO {

    /**
     * 用户名
     */
    @Column(name="username", nullable=false, length = 20, unique = true)
    private String username;

    /**
     * 密码
     */
    @Column(name="password", nullable=false, length = 50)
    private String password;

    /**
     * 默认用户
     */
    @Column(name="default_user")
    private Integer defaultUser = 0;

    /**
     * 姓名
     */
    @Column(name="name", length = 20)
    private String name;

    /**
     * 邮箱
     */
    @Column(name="email", nullable=false, length = 50,unique = true)
    private String email;

    /**
     * 手机
     */
    @Column(name="mobile", nullable=false, unique = true,length = 11)
    private String mobile;

    /**
     * 年龄
     */
    @Column(name="age")
    private Integer age = 0;

    /**
     * 性别
     */
    @Column(name="gender")
    private Integer gender = 0;

    /**
     * 生日
     */
    @Column(name="birthday")
    private Date birthday;

    /**
     * 密码加密盐值
     */
    @Column(name="yanzhi", nullable=false, length = 32)
    private String yanzhi = "YouAreMySunshine";

}
