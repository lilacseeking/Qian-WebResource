package org.lilacseeking.video.videoapp.Model.DTO;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Auther: lilacseeking
 * @Date: 2018/11/11 21:16
 * @Description: 用户、教师、管理员通用用户信息，Token保存VO类
 */
@Data
@Builder
@Accessors(chain = true)
public class UserBasicInfoDTO {

    /**
     * Token
     */
    private String token;

    /**
     * 用户名
     */
    private String username;

    /**
     * 默认用户
     */
    private Integer defaultUser;

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

}
