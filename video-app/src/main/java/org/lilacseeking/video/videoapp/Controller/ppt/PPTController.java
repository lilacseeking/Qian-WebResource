package org.lilacseeking.video.videoapp.Controller.ppt;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.lilacseeking.video.videoapp.Model.DTO.PPTDTO;
import org.lilacseeking.video.videoapp.Service.ppt.PPTService;
import org.lilacseeking.video.videoapp.Utils.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Auther: lilacseeking
 * @Date: 2019/3/24 10:10
 * @Description:
 */
@RequestMapping(value = "/ppt")
@Api(value = "/ppt", tags = {"幻灯片显示"})
@RestController
public class PPTController {

    @Autowired
    private ResponseUtil responseUtil;
    @Autowired
    private PPTService pptService;
    private static Logger LOGGER = LoggerFactory.getLogger(PPTController.class);

    /**
     * 获取幻灯片列表
     * @param filter
     * @param response
     */
    @RequestMapping(value = "/getPPTList",method = RequestMethod.POST)
    @ApiOperation(value = "获取幻灯片列表")
    public void getPPTList(@RequestBody JSONObject filter, HttpServletResponse response){
        List<PPTDTO> pptList = pptService.getPPTList(filter);
        responseUtil.putSuccess(pptList);
        responseUtil.writeMessage(response);
    }
}
