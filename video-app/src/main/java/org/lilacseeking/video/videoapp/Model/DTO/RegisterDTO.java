package org.lilacseeking.video.videoapp.Model.DTO;


import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: lilacseeking
 * @Date: 2018/8/27 22:56
 * @Description: 用户注册数据传输对象，不可修改
 */
@Data
public class RegisterDTO implements Serializable {
    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 姓名
     */
    private String name;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 生日
     */
    private String birthday;



}
