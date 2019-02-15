package org.lilacseeking.video.videoapp.Controller;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.lilacseeking.video.videoapp.Eumns.ErrorCodeEumn;
import org.lilacseeking.video.videoapp.Exception.BusinessException;
import org.lilacseeking.video.videoapp.Model.DTO.LoginDTO;
import org.lilacseeking.video.videoapp.Model.DTO.RegisterDTO;
import org.lilacseeking.video.videoapp.Model.DTO.UserBasicInfoDTO;
import org.lilacseeking.video.videoapp.Service.UserService;
import org.lilacseeking.video.videoapp.Utils.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: lilacseeking
 * @Date: 2018/8/27 21:57
 * @Description: 基本配置类
 */
@RequestMapping(value = "/common")
@Api(value = "/common", tags = {"公共接口"})
@RestController
public class CommonController {

    @Autowired
    private UserService userService;
    @Autowired
    private ResponseUtil responseUtil;
    @Autowired
    private static Logger LOGGER = LoggerFactory.getLogger(CommonController.class);
    /**
     * 用户注册
     */
    @ApiOperation(value = "用户注册" ,notes = "用户注册")
    @RequestMapping(value = "/register" ,method = RequestMethod.POST)
    public void register(@RequestBody RegisterDTO registerDTO, HttpServletResponse res){
        LOGGER.info("用户注册接口调用，用户注册DTO对象：{}", JSON.toJSONString(registerDTO));
        UserBasicInfoDTO register = userService.register(registerDTO);
        responseUtil.putSuccess(register);
        responseUtil.writeMessage(res);
    }
    /**
     * 用户密码登录
     */
    @ApiOperation(value = "用户登录" ,notes = "用户登录")
    @RequestMapping(value = "/loginByPwd" ,method = RequestMethod.POST)
    public void loginByPwd(@RequestBody LoginDTO loginDTO, HttpServletResponse res){
        UserBasicInfoDTO userBasicInfoDTO = userService.loginByPwd(loginDTO);
        responseUtil.putSuccess(userBasicInfoDTO);
        responseUtil.writeMessage(res);
    }

    /**
     * 手机验证码登录
     */
    @ApiOperation(value = "手机验证码登录" ,notes = "用户登录")
    @RequestMapping(value = "/mobileLogin" ,method = RequestMethod.POST)
    public void mobileLogin(@RequestBody LoginDTO loginDTO,HttpServletResponse res){
        try{
            UserBasicInfoDTO userBasicInfoDTO = userService.mobileLogin(loginDTO);
            responseUtil.putSuccess(userBasicInfoDTO);
        }catch (Exception e){
            e.printStackTrace();
            responseUtil.putError(e);
        }
        responseUtil.writeMessage(res);
    }

    /**
     * 发送手机验证码
     */
    @ApiOperation(value = "发送手机验证码" ,notes = "用户登录")
    @RequestMapping(value = "/sendMobileCode" ,method = RequestMethod.POST)
    public void sendMobileCode(@RequestBody LoginDTO loginDTO,HttpServletResponse res){
        SendSmsResponse sendSmsResponse = userService.sendMobileCode(loginDTO);
        if (!sendSmsResponse.getCode().equals("OK")){
            throw new BusinessException(ErrorCodeEumn.MOBILE_CODE_SEND_FAILED.getName());
        }
        responseUtil.putSuccess();
        responseUtil.writeMessage(res);
    }

    /**
     * 统一异常处理
     */
    @ApiOperation(value = "测试异常统一处理程序" ,notes = "异常处理")
    @RequestMapping(value = "/testException" ,method = RequestMethod.POST)
    public void testException(@RequestBody String loginDTO,HttpServletResponse res){
        throw new BusinessException(ErrorCodeEumn.NEW_PASSWORD_NOT_NULL.getName());
    }
}
