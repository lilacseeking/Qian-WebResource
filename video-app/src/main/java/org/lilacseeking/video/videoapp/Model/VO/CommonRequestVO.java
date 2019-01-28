package org.lilacseeking.video.videoapp.Model.VO;

import lombok.Data;

/**
 * @Auther: lilacseeking
 * @Date: 2018/10/16 21:35
 * @Description:统一请求参数
 */
@Data
public class CommonRequestVO {
    /**************登录相关************/
    private String username;
    private String password;
    private String email;
    private String mobile;
}
