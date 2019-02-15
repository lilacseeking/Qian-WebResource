package org.lilacseeking.video.videoapp.Service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.ctrip.framework.apollo.core.utils.StringUtils;
import org.lilacseeking.video.videoapp.Configuration.ConstantProperties;
import org.lilacseeking.video.videoapp.Dao.UserRepository;
import org.lilacseeking.video.videoapp.Eumns.ErrorCodeEumn;
import org.lilacseeking.video.videoapp.Eumns.SmsTemltateEnum;
import org.lilacseeking.video.videoapp.Exception.BusinessException;
import org.lilacseeking.video.videoapp.Model.DTO.LoginDTO;
import org.lilacseeking.video.videoapp.Model.DTO.RegisterDTO;
import org.lilacseeking.video.videoapp.Model.PO.UserPO;
import org.lilacseeking.video.videoapp.Model.DTO.UserBasicInfoDTO;
import org.lilacseeking.video.videoapp.Service.RedisService;
import org.lilacseeking.video.videoapp.Service.UserService;
import org.lilacseeking.video.videoapp.Utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.ParseException;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RedisService redisService;
    @Autowired
    ConstantProperties constantProperties;

    /**
     * 分页查查询所有用户
     * @param params
     * @return
     * @throws ParseException
     */
    public Page listAllUser(String params) throws ParseException {
        JSONObject value = JSONObject.parseObject(params).getJSONObject("params");
        int rows = Integer.valueOf(value.getString("rows"));
        int page = Integer.valueOf(value.getString("page"));
        String filter = value.getString("optionParam");
        Page page1 = new Page();
        page1.setCurrentPage(page);
        page1.setRows(rows);
        return userRepository.list(page1, filter);
    }

    /**
     * 用户注册
     * @param registerDTO
     * @return
     */
    public UserBasicInfoDTO register(RegisterDTO registerDTO){
//        UserPO userPO = UserPO.builder().build();
        UserPO userPO = new UserPO();
        BeanCopyUtil.copyPropertiesIgnoreNull(registerDTO,userPO);
        //密码加密
        userPO.setPassword(MD5Util.MD5(userPO.getPassword()));
        userPO.setYanzhi(StringUtil.getRandomString(12));
//        密码加密
//        持久化数据
        UserPO result = userRepository.saveOrUpdate(userPO);
        UserBasicInfoDTO userBasicInfoDTO = UserBasicInfoDTO.builder().build();
        BeanCopyUtil.copyPropertiesIgnoreNull(result,userBasicInfoDTO);
        return userBasicInfoDTO;
    }

    /**
     * 用户密码登录
     * @param loginDTO
     * @return
     * @throws BusinessException
     */
    public UserBasicInfoDTO loginByPwd(LoginDTO loginDTO){
        if (StringUtils.isBlank(loginDTO.getMobile())){
            throw new BusinessException(ErrorCodeEumn.MOBILE_NOT_NULL.getName());
        }
        UserPO userPOByMobile = userRepository.getUserPOByMobile(loginDTO.getMobile());
        if (!MD5Util.MD5(loginDTO.getPassword()).equals(userPOByMobile.getPassword())){
            throw new BusinessException(ErrorCodeEumn.PASSWORD_NOT_CORRECT.getName());
        }
        // 保存用户信息token
        UserBasicInfoDTO userBasicInfoDTO = UserBasicInfoDTO.builder().build();
        BeanCopyUtil.copyPropertiesIgnoreNull(loginDTO, userBasicInfoDTO);
        userBasicInfoDTO = redisService.saveUserToken(userBasicInfoDTO);
        return userBasicInfoDTO;
    }

    /**
     * 发送登录短信验证码
     * @param loginDTO
     * @return
     */
    public SendSmsResponse sendMobileCode(LoginDTO loginDTO){
        // 生成6位随机数字
        String verifyCode = String.valueOf((int)((Math.random()*9+1)*100000));
        redisService.saveSmsCode(loginDTO.getMobile(),verifyCode);
        try {
//            SendSmsResponse sendSmsResponse = SmsUtil.sendSms(loginDTO.getMobile(), SmsTemltateEnum.USER_LOGIN, verifyCode);
            SmsUtil.getInstance(constantProperties);
            SendSmsResponse sendSmsResponse = SmsUtil.getInstance(constantProperties).sendSms(loginDTO.getMobile(), SmsTemltateEnum.USER_LOGIN, verifyCode);
            return sendSmsResponse;
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 手机验证码登录
     * @param loginDTO
     * @return
     */
    public UserBasicInfoDTO mobileLogin(LoginDTO loginDTO){
        String smsCode = redisService.getSmsCode(loginDTO.getMobile());
        // 验证验证码输入是否正确
        if (!loginDTO.getVerifyCode().equals(smsCode)){
            throw new BusinessException(ErrorCodeEumn.MOBILE_CODE_ERROR.getName());
        }
        // Redis缓存置该用户验证码信息置为失效
         redisService.invalid(loginDTO.getMobile());
        UserPO userPO = userRepository.getUserPOByMobile(loginDTO.getMobile());
        // 保存session

        // 保存用户信息token
        UserBasicInfoDTO userBasicInfoDTO = UserBasicInfoDTO.builder().build();
        BeanCopyUtil.copyPropertiesIgnoreNull(userPO, userBasicInfoDTO);
        userBasicInfoDTO = redisService.saveUserToken(userBasicInfoDTO);
        return userBasicInfoDTO;
    }
    /**
     * 修改密码 and 重置密码
     * @param loginDTO 里含有新旧密码
     * @return
     * @throws BusinessException
     */
    public UserBasicInfoDTO resetPassword(LoginDTO loginDTO) throws BusinessException {
        // 参数校验
        if (StringUtils.isBlank(loginDTO.getMobile())){
            throw new BusinessException(ErrorCodeEumn.MOBILE_NOT_NULL.getName());
        }
        if (StringUtils.isBlank(loginDTO.getNewPassword())){
            throw new BusinessException(ErrorCodeEumn.NEW_PASSWORD_NOT_NULL.getName());
        }
        //查找该用户信息
        UserPO userPOByMobile = userRepository.getUserPOByMobile(loginDTO.getMobile());
        if (!MD5Util.MD5(loginDTO.getPassword()).equals(userPOByMobile.getPassword())){
            throw new BusinessException(ErrorCodeEumn.PASSWORD_NOT_CORRECT.getName());
        }
        userPOByMobile.setPassword(MD5Util.MD5(loginDTO.getNewPassword()));
        UserPO userPO = userRepository.saveOrUpdate(userPOByMobile);
        UserBasicInfoDTO userBasicInfoDTO = UserBasicInfoDTO.builder().build();
        BeanCopyUtil.copyPropertiesIgnoreNull(userPO,userBasicInfoDTO);
        return userBasicInfoDTO;
    }

    /**
     * 封存（注销）用户
     * @param list
     * @return
     */
    public Integer achieveUser(List list){
        return null;
    }
}
