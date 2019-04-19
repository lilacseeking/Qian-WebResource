package org.lilacseeking.video.videoapp.Model.DTO;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Auther: lilacseeking
 * @Date: 2018/8/27 22:57
 * @Description: 用户登录数据传输对象，不可修改
 */
@Data
@Accessors(chain = true)
public class LoginDTO implements Serializable {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机登录验证码
     */
    private String verifyCode;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 新密码（重置密码时使用）
     */
    private String newPassword;
    /**
     * 登录来源
     */
    private String origin;

}
