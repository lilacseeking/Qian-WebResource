package org.lilacseeking.video.core.User.Service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import org.apache.commons.lang3.StringUtils;
import org.lilacseeking.video.core.User.Model.UserPO;
import org.lilacseeking.video.core.User.Repository.UserRepository;
import org.lilacseeking.video.core.User.Service.UserService;
import org.lilacseeking.video.infrastructure.Exception.BusinessException;
import org.lilacseeking.video.infrastructure.Model.DTO.LoginDTO;
import org.lilacseeking.video.infrastructure.Model.DTO.RegisterDTO;
import org.lilacseeking.video.infrastructure.Model.DTO.UserBasicDTO;
import org.lilacseeking.video.infrastructure.Model.DTO.UserBasicInfoDTO;
import org.lilacseeking.video.infrastructure.enums.ErrorCodeEumn;
import org.lilacseeking.video.infrastructure.utils.BeanCopyUtil;
import org.lilacseeking.video.infrastructure.utils.MD5Util;
import org.lilacseeking.video.infrastructure.utils.Page;
import org.lilacseeking.video.infrastructure.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.text.ParseException;
import java.util.Date;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
//    @Autowired
//    RedisService redisService;
//    @Autowired
//    ConstantProperties constantProperties;

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
        Date birthdayDate = StringUtil.stringToDate(registerDTO.getBirthday(),"yyyy-MM-dd");
        int age = StringUtil.getAge(birthdayDate);
        UserPO userPO = new UserPO();
        BeanCopyUtil.copyPropertiesIgnoreNull(registerDTO,userPO);
        //密码加密
        userPO.setPassword(MD5Util.MD5(userPO.getPassword()));
        userPO.setAge(age);
        userPO.setYanzhi(StringUtil.getRandomString(12));
        userPO.setBirthday(birthdayDate);
//        密码加密
//        持久化数据
        UserPO result = userRepository.saveOrUpdate(userPO);
        UserBasicInfoDTO userBasicInfoDTO = new UserBasicInfoDTO();
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
        String param = "";
        if (StringUtils.isNotBlank(loginDTO.getUsername())){
            param = loginDTO.getUsername();
        }
        if (StringUtils.isNotBlank(loginDTO.getEmail())){
            param = loginDTO.getEmail();

        }
        if (StringUtils.isNotBlank(loginDTO.getMobile())){
            param = loginDTO.getMobile();
        }
        Assert.isTrue(StringUtils.isNotBlank(param),ErrorCodeEumn.MOBILE_EMAIL_USERNAME_NOT_NULL_ALL.getName());
        loginDTO.setOrigin(loginDTO.getOrigin() == null? "" : loginDTO.getOrigin());
        UserPO user = userRepository.getUserByMobileOrEmailOrUsername(param);
        if (user.getDefaultUser() == 0 && loginDTO.getOrigin().equals("ADMIN")){
            throw new BusinessException(ErrorCodeEumn.USER_NOT_TEACHER_OR_ADMIN.getName());
        }
        Assert.isTrue(null !=user,ErrorCodeEumn.USER_NOT_EXIST.getName());
        if (!MD5Util.MD5(loginDTO.getPassword()).equals(user.getPassword())){
            throw new BusinessException(ErrorCodeEumn.PASSWORD_NOT_CORRECT.getName());
        }
        // 保存用户信息token
        UserBasicInfoDTO userBasicInfoDTO = new UserBasicInfoDTO();
        BeanCopyUtil.copyPropertiesIgnoreNull(user, userBasicInfoDTO);
        // TODO 工程结构修改
//        userBasicInfoDTO = redisService.saveUserToken(userBasicInfoDTO);
        return userBasicInfoDTO;
    }

    /**
     * 发送登录短信验证码
     * @param loginDTO
     * @return
     */
    public SendSmsResponse sendMobileCode(LoginDTO loginDTO){
        // 判断当前用户是否允许登录
        if (StringUtils.isBlank(loginDTO.getMobile())){
            throw new BusinessException(ErrorCodeEumn.MOBILE_NOT_NULL.getName());
        }
        // 判断当前用户是否存在或是否适配系统
        UserPO user = userRepository.getUserByMobileOrEmailOrUsername(loginDTO.getMobile());
        Assert.isTrue(null !=user,ErrorCodeEumn.USER_NOT_EXIST.getName());
        if (user.getDefaultUser() == 0 && loginDTO.getOrigin().equals("ADMIN")){
            throw new BusinessException(ErrorCodeEumn.USER_NOT_TEACHER_OR_ADMIN.getName());
        }
        // 生成6位随机数字
        String verifyCode = String.valueOf((int)((Math.random()*9+1)*100000));
        // TODO 工程结构修改
//        redisService.saveSmsCode(loginDTO.getMobile(),verifyCode);
        try {
//            SendSmsResponse sendSmsResponse = SmsUtil.sendSms(loginDTO.getMobile(), SmsTemltateEnum.USER_LOGIN, verifyCode);
            // TODO 工程结构修改
//            SmsUtil.getInstance(constantProperties);
//            SendSmsResponse sendSmsResponse = SmsUtil.getInstance(constantProperties).sendSms(loginDTO.getMobile(), SmsTemltateEnum.USER_LOGIN, verifyCode);
//            return sendSmsResponse;
            verifyCode = "111";
            return new SendSmsResponse();
        } catch (Exception e) {
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
        // TODO 工程结构修改
        String smsCode = "";
//        String smsCode = redisService.getSmsCode(loginDTO.getMobile());
        // 验证验证码输入是否正确
        if (!loginDTO.getVerifyCode().equals(smsCode)){
            throw new BusinessException(ErrorCodeEumn.MOBILE_CODE_ERROR.getName());
        }
        // Redis缓存置该用户验证码信息置为失效
        // TODO 工程结构修改
//         redisService.invalid(loginDTO.getMobile());
        UserPO userPO = userRepository.getUserByMobileOrEmailOrUsername(loginDTO.getMobile());
        // 保存session

        // 保存用户信息token
        UserBasicInfoDTO userBasicInfoDTO = new UserBasicInfoDTO();
        BeanCopyUtil.copyPropertiesIgnoreNull(userPO, userBasicInfoDTO);
        // TODO 工程结构修改
//        userBasicInfoDTO = redisService.saveUserToken(userBasicInfoDTO);
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
        UserPO userPOByMobile = userRepository.getUserByMobileOrEmailOrUsername(loginDTO.getMobile());
        if (!MD5Util.MD5(loginDTO.getPassword()).equals(userPOByMobile.getPassword())){
            throw new BusinessException(ErrorCodeEumn.PASSWORD_NOT_CORRECT.getName());
        }
        userPOByMobile.setPassword(MD5Util.MD5(loginDTO.getNewPassword()));
        UserPO userPO = userRepository.saveOrUpdate(userPOByMobile);
        UserBasicInfoDTO userBasicInfoDTO = new UserBasicInfoDTO();
        BeanCopyUtil.copyPropertiesIgnoreNull(userPO,userBasicInfoDTO);
        return userBasicInfoDTO;
    }

    /**
     * 注销登录
     *
     * @param loginDTO
     * @return
     */
    @Override
    public String loginOut(LoginDTO loginDTO) {
        // 参数校验
        Assert.isTrue(StringUtils.isNotBlank(loginDTO.getMobile()),ErrorCodeEumn.MOBILE_NOT_NULL.getName());
        // TODO 工程结构修改
//        return redisService.removeUserToken(loginDTO);
        return null;
    }

    /**
     * 封存（注销）用户
     * @param list
     * @return
     */
    public Integer achieveUser(List list){
        return null;
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    @Override
    public UserPO getUserInfo(UserBasicDTO userDTO) {
        Assert.isTrue(null != userDTO.getId(),ErrorCodeEumn.USER_ID_NOT_NULL.getName());
        return userRepository.getUserInfo(userDTO);
    }

    /**
     * 修改用户信息
     *
     * @param userPO
     * @return
     */
    @Override
    public UserPO updateUserInfo(UserPO userPO) {
        return userRepository.saveOrUpdate(userPO);
    }
}
