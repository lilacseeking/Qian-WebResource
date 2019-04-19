package org.lilacseeking.video.videoapp.Controller.course;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.lilacseeking.video.videoapp.Eumns.YesOrNoEnum;
import org.lilacseeking.video.videoapp.Model.DTO.ContentDTO;
import org.lilacseeking.video.videoapp.Model.DTO.CourseDTO;
import org.lilacseeking.video.videoapp.Model.DTO.CourseUserDTO;
import org.lilacseeking.video.videoapp.Model.PO.VideoContentPO;
import org.lilacseeking.video.videoapp.Service.course.CourseUserService;
import org.lilacseeking.video.videoapp.Service.course.VideoContentService;
import org.lilacseeking.video.videoapp.Utils.BeanCopyUtil;
import org.lilacseeking.video.videoapp.Utils.ResponseUtil;
import org.lilacseeking.video.videoapp.Utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Auther: lilacseeking
 * @Date: 2019/3/23 00:44
 * @Description: 章节相关信息
 */
@RequestMapping(value = "/content")
@Api(value = "/content", tags = {"课程章节"})
@RestController
public class ContentController {
    // HTTP工具类
    @Autowired
    private ResponseUtil responseUtil;
    // 章节服务
    @Autowired
    private VideoContentService videoContentService;
    // 课程用户关系服务
    @Autowired
    private CourseUserService courseUserService;

    @RequestMapping(value = "/addVideoContent",method = RequestMethod.POST)
    @ApiOperation(value = "创建章节")
    public void addVideoContent(@RequestBody ContentDTO contentDTO, HttpServletResponse response){
        VideoContentPO videoContentPO = new VideoContentPO();
        BeanCopyUtil.copyPropertiesIgnoreNull(contentDTO,videoContentPO);
        VideoContentPO videoContent = videoContentService.addVideoContent(videoContentPO);
        responseUtil.putSuccess(videoContent);
        responseUtil.writeMessage(response);
    }

    /**
     * 章节列表
     * @param courseDTO
     * @param response
     */
    @RequestMapping(value = "/getContentList",method = RequestMethod.POST)
    @ApiOperation(value = "查询课程列表")
    public void getContentList(@RequestBody CourseDTO courseDTO, HttpServletResponse response){
        // 查询课程列表
        List<ContentDTO> contentList = videoContentService.getContentList(courseDTO);
        // 查询当前用户是否拥有该课程
        CourseUserDTO courseUserInfo = null;
        if (null != UserUtils.getUserSession()){
            JSONObject userCourseInfo = new JSONObject();
            userCourseInfo.put("userId",UserUtils.getUserSession().getId());
            userCourseInfo.put("courseId",courseDTO.getId());
            courseUserInfo = courseUserService.getCourseUserInfo(userCourseInfo);
        }
        // 若当前用户已购买课程，修改章节购买状态
        if (null != courseUserInfo){
            for (int i = 0; i<contentList.size();i++) {
                ContentDTO contentDTO = contentList.get(i);
                contentDTO.setIsFree(YesOrNoEnum.Y);
                contentList.set(i,contentDTO);
            }
        }
        responseUtil.putSuccess(contentList);
        responseUtil.writeMessage(response);
    }
}
