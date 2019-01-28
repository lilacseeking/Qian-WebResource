package org.lilacseeking.video.videoapp.Model.DTO;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: lilacseeking
 * @Date: 2019/1/27 17:40
 * @Description:
 */
@Data
@Builder
@Accessors(chain = true)
public class UserDTO implements Serializable {

    /**
     * 用户名
     */
    private String username;

    /**
     * 默认用户
     */
    private Integer defaultUser = 0;

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
    private Integer age = 0;

    /**
     * 性别
     */
    private Integer gender = 0;

    /**
     * 生日
     */
    private Date birthday;
}
