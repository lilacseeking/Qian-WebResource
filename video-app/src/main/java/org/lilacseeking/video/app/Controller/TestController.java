package org.lilacseeking.video.app.Controller;

import io.swagger.annotations.Api;
import org.apache.dubbo.config.annotation.Reference;
import org.lilacseeking.video.api.IUploadService;
import org.lilacseeking.video.app.Service.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: lilacseeking
 * @Date: 2019/4/29 11:44
 * @Description: 测试Controller
 */
@RestController
@RequestMapping(value = "/test")
@Api(value = "测试")
public class TestController {

    @Autowired
    private ResponseUtil responseUtil;

    @Reference(version = "1.0.0")
    private IUploadService uploadService;

    @RequestMapping(value = "/testDubboController")
    public void testDubboController(@RequestBody String aaa, HttpServletResponse response){
        uploadService.sayHelloWorld();
        responseUtil.putSuccess();
        responseUtil.writeMessage(response);
    }
}
