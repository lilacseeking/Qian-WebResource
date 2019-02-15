package org.lilacseeking.video.videoapp.Service;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import org.lilacseeking.video.videoapp.Model.DTO.LoginDTO;
import org.lilacseeking.video.videoapp.Model.DTO.RegisterDTO;
import org.lilacseeking.video.videoapp.Model.DTO.UserBasicInfoDTO;
import org.lilacseeking.video.videoapp.Utils.Page;
import org.springframework.stereotype.Service;
import java.text.ParseException;
import java.util.List;

@Service
public interface UserService {

    /**
     * 分页查查询所有用户
     * @param params
     * @return
     */
    Page listAllUser(String params) throws ParseException;

    /**
     * 用户注册
     * @param registerDTO
     * @return
     */
    UserBasicInfoDTO register(RegisterDTO registerDTO);

    /**
     * 用户密码登录
     * @param loginDTO
     * @return
     */
    UserBasicInfoDTO loginByPwd(LoginDTO loginDTO);

    /**
     * 发送验证码
     * @param loginDTO
     * @return
     */
    SendSmsResponse sendMobileCode(LoginDTO loginDTO);

    /**
     * 验证码登录
     * @param loginDTO
     * @return
     */
    UserBasicInfoDTO mobileLogin(LoginDTO loginDTO);

    /**
     * 修改密码 and 重置密码
     * @param loginDTO 包含用户基本信息
     * @return
     */
    UserBasicInfoDTO resetPassword(LoginDTO loginDTO);

    /**
     * 封存用户
     * @param list
     * @return
     */
    Integer achieveUser(List list);
}
