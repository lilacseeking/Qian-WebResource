package org.lilacseeking.video.app.Controller.user;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import io.swagger.annotations.ApiOperation;
import org.lilacseeking.video.app.Api.Result;
import org.lilacseeking.video.app.Controller.CommonController;
import org.lilacseeking.video.app.Eumns.ErrorCodeEumn;
import org.lilacseeking.video.app.Exception.BusinessException;
import org.lilacseeking.video.app.Model.DTO.LoginDTO;
import org.lilacseeking.video.app.Model.DTO.RegisterDTO;
import org.lilacseeking.video.app.Model.DTO.UserBasicInfoDTO;
import org.lilacseeking.video.app.Service.user.UserService;
import org.lilacseeking.video.app.Utils.Page;
import org.lilacseeking.video.app.Utils.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    UserService userService;

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
     * 分页查询用户列表
     * @param params
     * @param response
     */

    @RequestMapping(value = "/list")
    public void getUserById(@RequestBody String params , HttpServletResponse response){
        Result result = new Result();
        Page page = null;
        try {
            page = userService.listAllUser(params);
            responseUtil.putSuccess(page);
        }catch (Exception e){
            e.printStackTrace();
            responseUtil.putError(e);
        }
        responseUtil.writeMessage(response);
    }

    /**
     * 重置密码 and 修改密码
     * @param loginDTO
     * @param response
     */
    @RequestMapping(value = "/resetPassword")
    @ApiOperation(value = "/resetPassword",notes = "修改密码")
    public void resetPassword(@RequestBody LoginDTO loginDTO, HttpServletResponse response){
        // 输入原密码，修改密码
        // 输入电话号码，接收短信，重置密码，暂支持修改密码
        UserBasicInfoDTO userBasicInfoDTO = userService.resetPassword(loginDTO);
        responseUtil.putSuccess(userBasicInfoDTO);
        responseUtil.writeMessage(response);
    }

    /**
     * 注销登录
     * @param loginDTO
     */
    @RequestMapping(value = "/loginOut")
    @ApiOperation(value = "/loginOut",notes = "注销登录")
    public void loginOut(@RequestBody LoginDTO loginDTO, HttpServletResponse response){
        String s = userService.loginOut(loginDTO);
        responseUtil.putSuccess("注销登录成功");
        responseUtil.writeMessage(response);

    }

    /**
     * 封存（删除）用户，保留数据
     * @param Id
     * @param response
     */
    @RequestMapping(value = "/achieveUser")
    public void achieveUser(@RequestBody List Id, HttpServletResponse response){
        try{

        }catch (Exception e){

        }
    }
}
