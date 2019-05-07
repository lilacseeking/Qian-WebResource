//package org.lilacseeking.video.app.Controller;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.lilacseeking.video.infrastructure.Exception.BusinessException;
//import org.lilacseeking.video.infrastructure.enums.ErrorCodeEumn;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//import javax.servlet.http.HttpServletResponse;
//
///**
// * @Auther: lilacseeking
// * @Date: 2018/8/27 21:57
// * @Description: 基本配置类
// */
//@RequestMapping(value = "/common")
//@Api(value = "/common", tags = {"公共接口"})
//@RestController
//public class CommonController {
//
//
//
//    /**
//     * 统一异常处理
//     */
//    @ApiOperation(value = "测试异常统一处理程序" ,notes = "异常处理")
//    @RequestMapping(value = "/testException" ,method = RequestMethod.POST)
//    public void testException(@RequestBody String loginDTO,HttpServletResponse res){
//        throw new BusinessException(ErrorCodeEumn.NEW_PASSWORD_NOT_NULL.getName());
//    }
//}
