package org.lilacseeking.video.videoapp.Controller.video;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.querydsl.jpa.JPAQueryBase;
import com.querydsl.jpa.impl.JPAQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.lilacseeking.video.videoapp.Eumns.ErrorCodeEumn;
import org.lilacseeking.video.videoapp.Model.DTO.CourseAndContentDTO;
import org.lilacseeking.video.videoapp.Model.DTO.CourseForHomeDTO;
import org.lilacseeking.video.videoapp.Model.Factory.FileUploadSetFactory;
import org.lilacseeking.video.videoapp.Model.Factory.VideoEncodeSetFactory;
import org.lilacseeking.video.videoapp.Model.PO.VideoContentPO;
import org.lilacseeking.video.videoapp.Model.PO.VideoCoursePO;
import org.lilacseeking.video.videoapp.Model.VO.VideoEncodeProcessVO;
import org.lilacseeking.video.videoapp.Model.VO.VideoUploadProcessVO;
import org.lilacseeking.video.videoapp.Service.RedisService;
import org.lilacseeking.video.videoapp.Service.video.VideoContentService;
import org.lilacseeking.video.videoapp.Service.video.VideoCourseService;
import org.lilacseeking.video.videoapp.Service.video.VideoService;
import org.lilacseeking.video.videoapp.Utils.BeanCopyUtil;
import org.lilacseeking.video.videoapp.Utils.Page;
import org.lilacseeking.video.videoapp.Utils.ResponseUtil;
import org.lilacseeking.video.videoapp.Utils.UploadUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.FileEncodingApplicationListener;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @Auther: lilacseeking
 * @Date: 2018/10/18 22:41
 * @Description:
 */
@RestController
@RequestMapping(value = "/video")
@Api(value = "课程")
public class VideoController {

    @Autowired
    private VideoCourseService videoCourseService;

    @Autowired
    private ResponseUtil responseUtil;

    @Autowired
    private VideoContentService videoContentService;

    @Autowired
    private VideoService videoService;
    private static Logger LOGGER = LoggerFactory.getLogger(VideoController.class);

    @Autowired
    private RedisService redisService;

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
//        List<ContentDTO> contentDTOList = courseAndContentDTO.getContentDTOList().stream().map(ContentDTO::setCourseId(videoCoursePO.getId())).collect(Collectors.toList());
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
    @RequestMapping(value = "/getCourseList",method = RequestMethod.POST)
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

    @RequestMapping(value = "/addVideoContent",method = RequestMethod.POST)
    @ApiOperation(value = "创建课程")
    public void addVideoContent(@RequestBody ContentDTO contentDTO, HttpServletResponse response){
        VideoContentPO videoContentPO = new VideoContentPO();
        BeanCopyUtil.copyPropertiesIgnoreNull(contentDTO,videoContentPO);
        VideoContentPO videoContent = videoContentService.addVideoContent(videoContentPO);
        responseUtil.putSuccess(videoContent);
        responseUtil.writeMessage(response);
    }

    /**
     * 课程文件上传
     * @param classVideo
     * @param classThumbnail
     * @param response
     */
    @RequestMapping(value = "/uploadVideoClass",method = RequestMethod.POST)
    @ApiOperation(value = "上传视频")
    public void uploadVideoClass(MultipartFile classVideo,MultipartFile classThumbnail,HttpServletResponse response){

        try {
            UploadUtils.uploadFileStream(classVideo.getInputStream());
            UploadUtils.uploadFileStream(classThumbnail.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        responseUtil.putSuccess();
        responseUtil.writeMessage(response);
    }

    /**
     * 视频转码至MP4格式
     * @param file
     * @param response
     */
    @RequestMapping(value = "/encodeVideoToMP4ThreeCommonFormat",method = RequestMethod.POST)
    @ApiOperation(value = "视频转码")
    public void encodeVideoToMP4ThreeCommonFormat(MultipartFile file,HttpServletResponse response){
        videoService.encodeVideoToMP4ThreeCommonFormat(file);
        responseUtil.putSuccess();
        responseUtil.writeMessage(response);
    }

    /**
     * 查看文件上传、转码进度
     */
    @RequestMapping(value = "/getFileTaskProcess",method = RequestMethod.POST)
    @ApiOperation(value = "查看文件上传、转码进度")
    public void getFileTaskProcess(String str,HttpServletResponse response){

        while (true){
            // 获取所有转码文件进度，打印日志
            Set<String> encodeSets = VideoEncodeSetFactory.getEncodeMap().keySet();
            Iterator<String> encodeIterator=encodeSets.iterator();
            while (encodeIterator.hasNext()){
                VideoEncodeProcessVO videoEncodeProcessVO = (VideoEncodeProcessVO) VideoEncodeSetFactory.getEncodeObject(encodeIterator.next());
                LOGGER.info("视频转码进度,视频名称：{}，转码进度：{}%，转码任务:{}",encodeIterator.next(),videoEncodeProcessVO==null?"暂无正在转码的视频":videoEncodeProcessVO.getEncodeRate(), JSON.toJSON(VideoEncodeSetFactory.getEncodeMap()));
            }
            // 获取所有转码文件进度，打印日志
            Set<String> uploadSets = FileUploadSetFactory.getUploadMap().keySet();
            Iterator<String> uploadIterators=uploadSets.iterator();
            while (uploadIterators.hasNext()){
                VideoUploadProcessVO videoUploadProcessVO = (VideoUploadProcessVO) FileUploadSetFactory.getUploadObject(uploadIterators.next());
                LOGGER.info("视频上传进度,视频名称：{}，上传进度：{}%，上传任务:{}",uploadIterators.next(),videoUploadProcessVO==null?"暂无正在上传的视频":videoUploadProcessVO.getUploadRate(), JSON.toJSON(VideoEncodeSetFactory.getEncodeMap()));
            }
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }




}
