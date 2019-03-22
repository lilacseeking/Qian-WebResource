package org.lilacseeking.video.videoapp.Controller.video;

import com.alibaba.fastjson.JSON;
import com.querydsl.jpa.JPAQueryBase;
import com.querydsl.jpa.impl.JPAQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.lilacseeking.video.videoapp.Model.DTO.VideoContentDTO;
import org.lilacseeking.video.videoapp.Model.DTO.VideoCourseDTO;
import org.lilacseeking.video.videoapp.Model.Factory.FileUploadSetFactory;
import org.lilacseeking.video.videoapp.Model.Factory.VideoEncodeSetFactory;
import org.lilacseeking.video.videoapp.Model.PO.VideoContentPO;
import org.lilacseeking.video.videoapp.Model.PO.VideoCoursePO;
import org.lilacseeking.video.videoapp.Model.VO.VideoEncodeProcessVO;
import org.lilacseeking.video.videoapp.Model.VO.VideoUploadProcessVO;
import org.lilacseeking.video.videoapp.Service.video.VideoContentService;
import org.lilacseeking.video.videoapp.Service.video.VideoCourseService;
import org.lilacseeking.video.videoapp.Service.video.VideoService;
import org.lilacseeking.video.videoapp.Utils.BeanCopyUtil;
import org.lilacseeking.video.videoapp.Utils.ResponseUtil;
import org.lilacseeking.video.videoapp.Utils.UploadUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.FileEncodingApplicationListener;
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

    /**
     * 创建课程
     * @param videoCourseDTO
     * @param response
     */
    @RequestMapping(value = "/addVideoClass",method = RequestMethod.POST)
    @ApiOperation(value = "创建课程")
    public void AddVideoClass(@RequestBody VideoCourseDTO videoCourseDTO, HttpServletResponse response){
        VideoCoursePO videoCoursePO = VideoCoursePO.builder().build();
        videoCoursePO = videoCourseService.addVideoClass(videoCoursePO);
        responseUtil.putSuccess(videoCoursePO);
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
    public void addVideoContent(@RequestBody VideoContentDTO videoContentDTO, HttpServletResponse response){
        VideoContentPO videoContentPO = VideoContentPO.builder().build();
        BeanCopyUtil.copyPropertiesIgnoreNull(videoContentDTO,videoContentPO);
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
