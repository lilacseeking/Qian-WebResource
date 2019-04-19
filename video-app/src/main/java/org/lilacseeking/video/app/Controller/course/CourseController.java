package org.lilacseeking.video.app.Controller.course;

import com.alibaba.fastjson.JSONObject;
import com.querydsl.jpa.JPAQueryBase;
import com.querydsl.jpa.impl.JPAQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.lilacseeking.video.app.Eumns.ErrorCodeEumn;
import org.lilacseeking.video.app.Eumns.YesOrNoEnum;
import org.lilacseeking.video.app.Model.DTO.*;
import org.lilacseeking.video.app.Model.PO.VideoCoursePO;
import org.lilacseeking.video.app.Service.RedisService;
import org.lilacseeking.video.app.Service.course.CourseUserService;
import org.lilacseeking.video.app.Service.user.UserService;
import org.lilacseeking.video.app.Service.course.VideoContentService;
import org.lilacseeking.video.app.Service.course.VideoCourseService;
import org.lilacseeking.video.app.Utils.BeanCopyUtil;
import org.lilacseeking.video.app.Utils.Page;
import org.lilacseeking.video.app.Utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Auther: lilacseeking
 * @Date: 2019/3/22 13:27
 * @Description:课程相关信息
 */
@RequestMapping(value = "/course")
@Api(value = "/course", tags = {"课程"})
@RestController
public class CourseController {
    @Autowired
    UserService userService;

    @Autowired
    private ResponseUtil responseUtil;

    @Autowired
    private RedisService redisService;

    @Autowired
    private VideoCourseService videoCourseService;

    @Autowired
    private VideoContentService videoContentService;

    @Autowired
    private CourseUserService courseUserService;

    private final String channel = "HOME_PAGE";

    /**
     * 创建课程
     * @param courseAndContentDTO
     * @param response
     */
    @RequestMapping(value = "/addVideoClass",method = RequestMethod.POST)
    @ApiOperation(value = "创建课程")
    public void AddVideoClass(@RequestBody CourseAndContentDTO courseAndContentDTO, HttpServletResponse response){
        // 一个课程和多个章节有对应关系,所以使用课程和章节DTO来接收转换为VO对象保存
        // 输入参数校验
        Assert.isTrue(courseAndContentDTO.getCourseDTO() != null,ErrorCodeEumn.COURSE_NOT_NULL.getName());
        // 保存课程信息
        VideoCoursePO videoCoursePO = new VideoCoursePO();
        BeanCopyUtil.copyPropertiesIgnoreNull(courseAndContentDTO.getCourseDTO(),videoCoursePO);
        videoCoursePO = videoCourseService.addVideoClass(videoCoursePO);
        // 保存章节信息
        List<ContentDTO> contentDTOList = courseAndContentDTO.getContentDTOList();
        for (int i = 0; i < contentDTOList.size();i++){
            ContentDTO contentDTO = contentDTOList.get(i);
            contentDTO.setCourseId(videoCoursePO.getId());
            contentDTO.setCourseName(videoCoursePO.getName());
            contentDTOList.set(i,contentDTO);
        }
        videoContentService.addVideoContentList(courseAndContentDTO.getContentDTOList());
        responseUtil.putSuccess(videoCoursePO);
        responseUtil.writeMessage(response);
    }


    /**
     * 获取课程列表
     * @param filter
     * @param response
     */
    @ApiOperation(value = "获取课程列表")
    @RequestMapping(value = "/getAllCourse",method = RequestMethod.POST)
    public void getCourseList(@RequestBody JSONObject filter, HttpServletResponse response){
        // 首页调用从Redis中获取数据
        if (filter.getString("type").equals(channel)){
            List<CourseForHomeDTO> courseListForHome = redisService.getCourseListForHome();
            responseUtil.putSuccess(courseListForHome);
            responseUtil.writeMessage(response);
        }
        // 除首页之外的其他情况
        Page result = videoCourseService.getCourseList(filter);
        responseUtil.putSuccess(result);
        responseUtil.writeMessage(response);
    }

    /**
     * 查询教师开设的所有课程
     * @param id
     * @return
     */
    public List getVideoClassByTeacherId(Long id){
        JPAQueryBase query = new JPAQuery<>();

        return null;
    }



    /**
     * 获取课程详情
     * @param filter
     * @param response
     */
    @RequestMapping(value = "/getCourseInfo",method = RequestMethod.POST)
    @ApiOperation(value = "获取课程详细信息")
    public void getCourseInfo(@RequestBody JSONObject filter, HttpServletResponse response){
        CourseDTO courseDTO = JSONObject.parseObject(filter.toJSONString(),CourseDTO.class);
        courseDTO.setId(filter.getLong("courseId"));
        VideoCoursePO courseInfo = videoCourseService.getCourseInfo(courseDTO);
        // 查询该用户是否购买课程
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("courseId",filter.getLong("courseId"));
        jsonObject.put("userId",filter.getLong("userId"));
        CourseUserDTO courseUserInfo = courseUserService.getCourseUserInfo(jsonObject);
        BeanCopyUtil.copyPropertiesIgnoreNull(courseInfo,courseDTO);
        if (null != courseUserInfo){
            courseDTO.setUserStatus(YesOrNoEnum.Y);
        }
        responseUtil.putSuccess(courseDTO);
        responseUtil.writeMessage(response);
    }

    /**
     * 用户参加的课程列表
     * @param filter
     * @param response
     */
    @RequestMapping(value = "/getCourseListByUser",method = RequestMethod.POST)
    @ApiOperation(value = "获取用户学习的课程")
    public void getCourseListByUser(@RequestBody JSONObject filter, HttpServletResponse response){
        Page result = courseUserService.getCourseUserList(filter);
        responseUtil.putSuccess(result);
        responseUtil.writeMessage(response);
    }
    @RequestMapping(value = "/shelveCourse",method = RequestMethod.POST)
    @ApiOperation(value = "下架课程")
    public void shelveCourse(@RequestBody CourseDTO courseDTO, HttpServletResponse response){
        courseDTO = videoCourseService.shelveCourse(courseDTO);
        responseUtil.putSuccess(courseDTO);
        responseUtil.writeMessage(response);
    }

    /**
     * 更新用户学习进度
     * @param contentDTO
     * @param response
     */
    @RequestMapping(value = "/updateUserCourseRate",method = RequestMethod.POST)
    @ApiOperation(value = "更新用户学习进度")
    public void updateUserCourseRate(@RequestBody ContentDTO contentDTO, HttpServletResponse response){
        CourseUserDTO courseUserDTO = courseUserService.updateUserCourseRate(contentDTO);
        responseUtil.putSuccess(courseUserDTO);
        responseUtil.writeMessage(response);
    }
}
