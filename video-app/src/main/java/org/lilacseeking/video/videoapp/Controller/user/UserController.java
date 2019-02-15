package org.lilacseeking.video.videoapp.Controller.user;

import io.swagger.annotations.ApiOperation;
import org.lilacseeking.video.videoapp.Api.Result;
import org.lilacseeking.video.videoapp.Model.DTO.LoginDTO;
import org.lilacseeking.video.videoapp.Model.DTO.UserBasicInfoDTO;
import org.lilacseeking.video.videoapp.Service.UserService;
import org.lilacseeking.video.videoapp.Utils.Page;
import org.lilacseeking.video.videoapp.Utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
