package org.lilacseeking.video.videoapp.Controller.teacher;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.lilacseeking.video.videoapp.Controller.CommonController;
import org.lilacseeking.video.videoapp.Model.DTO.RegisterDTO;
import org.lilacseeking.video.videoapp.Model.DTO.TeacherDTO;
import org.lilacseeking.video.videoapp.Service.teacher.TeacherService;
import org.lilacseeking.video.videoapp.Utils.Page;
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
 * @Date: 2019/3/30 12:06
 * @Description: 教师相关Controller
 */
@RequestMapping(value = "/teacher")
@Api(value = "/teacher", tags = {"教师相关接口"})
@RestController
public class TeacherController {
    // 教师服务
    @Autowired
    TeacherService teacherService;

    @Autowired
    private ResponseUtil responseUtil;

    @Autowired
    private static Logger LOGGER = LoggerFactory.getLogger(CommonController.class);
    /**
     * 获取教师列表
     */
    @ApiOperation(value = "获取教师列表" ,notes = "获取教师列表")
    @RequestMapping(value = "/getTeacherList" ,method = RequestMethod.POST)
    public void getTeacherList(@RequestBody JSONObject filter, HttpServletResponse res){
        LOGGER.info("获取教师列表请求参数：{}",filter.toJSONString());
        Page resultList = teacherService.getTeacherList(filter);
        responseUtil.putSuccess(resultList);
        responseUtil.writeMessage(res);
    }
    /**
     * 申请成为教师
     */
    @ApiOperation(value = "申请成为教师" ,notes = "申请成为教师")
    @RequestMapping(value = "/applyTeacher" ,method = RequestMethod.POST)
    public void applyTeacher(@RequestBody TeacherDTO teacherDTO, HttpServletResponse res){
        LOGGER.info("申请成为教师请求参数：{}",JSON.toJSONString(teacherDTO));
        TeacherDTO result = teacherService.applyTeacher(teacherDTO);
        responseUtil.putSuccess(result);
        responseUtil.writeMessage(res);
    }
    /**
     * 教师审核
     */
    @ApiOperation(value = "教师审核" ,notes = "教师审核")
    @RequestMapping(value = "/auditTeacher" ,method = RequestMethod.POST)
    public void auditTeacher(@RequestBody TeacherDTO teacherDTO, HttpServletResponse res){
        LOGGER.info("申请成为教师请求参数：{}",JSON.toJSONString(teacherDTO));
        TeacherDTO result = teacherService.auditTeacher(teacherDTO);
        responseUtil.putSuccess(result);
        responseUtil.writeMessage(res);
    }

}
