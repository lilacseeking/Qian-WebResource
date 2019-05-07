//package org.lilacseeking.video.app.Controller.video;
//
//import com.alibaba.fastjson.JSON;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.lilacseeking.video.infrastructure.Model.Factory.FileUploadSetFactory;
//import org.lilacseeking.video.infrastructure.Model.Factory.VideoEncodeSetFactory;
//import org.lilacseeking.video.infrastructure.Model.VO.VideoEncodeProcessVO;
//import org.lilacseeking.video.infrastructure.Model.VO.VideoUploadProcessVO;
//import org.lilacseeking.video.infrastructure.enums.ErrorCodeEumn;
//import org.lilacseeking.video.infrastructure.Exception.BusinessException;
//import org.lilacseeking.video.app.Service.ResponseUtil;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.*;
//
///**
// * @Auther: lilacseeking
// * @Date: 2018/10/18 22:41
// * @Description:
// */
//@RestController
//@RequestMapping(value = "/video")
//@Api(value = "视频")
//public class VideoController {
//
//
//    @Autowired
//    private ResponseUtil responseUtil;
//
//// TODO 工程结构修改
////    @Autowired
////    private VideoService videoService;
//
//    private static Logger LOGGER = LoggerFactory.getLogger(VideoController.class);
//
//    /**
//     * 上传图片
//     *
//     * @param thumbnail 缩略图
//     * @param response
//     */
//    @RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
//    @ApiOperation(value = "上传图片")
//    public void uploadImage(MultipartFile thumbnail, HttpServletResponse response) {
//        try {
//            // TODO 工程结构修改
////            UploadUtils.uploadImage(thumbnail.getInputStream());
//
//        } catch (Exception e) {
//            throw new BusinessException(ErrorCodeEumn.COURSE_THUMBNAIL_UPLOAD_FAIL.getName());
//        }
//        responseUtil.putSuccess();
//        responseUtil.writeMessage(response);
//    }
//
//
//    /**
//     * 课程文件上传
//     *
//     * @param classVideo
//     * @param classThumbnail
//     * @param response
//     */
//    @RequestMapping(value = "/uploadVideoClass", method = RequestMethod.POST)
//    @ApiOperation(value = "上传视频")
//    public void uploadVideoClass(MultipartFile classVideo, MultipartFile classThumbnail, HttpServletResponse response) {
//
//        try {
//            // TODO 工程结构修改
////            UploadUtils.uploadFileStream(classVideo.getInputStream());
////            UploadUtils.uploadFileStream(classThumbnail.getInputStream());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        responseUtil.putSuccess();
//        responseUtil.writeMessage(response);
//    }
//
//    /**
//     * 视频转码至MP4格式
//     *
//     * @param file
//     * @param response
//     */
//    @RequestMapping(value = "/encodeVideoToMP4ThreeCommonFormat", method = RequestMethod.POST)
//    @ApiOperation(value = "视频转码")
//    public void encodeVideoToMP4ThreeCommonFormat(MultipartFile file, HttpServletResponse response) {
//        // TODO 工程结构修改
////        videoService.encodeVideoToMP4ThreeCommonFormat(file);
//        responseUtil.putSuccess();
//        responseUtil.writeMessage(response);
//    }
//
//    /**
//     * 查看文件上传、转码进度
//     */
//    @RequestMapping(value = "/getFileTaskProcess", method = RequestMethod.POST)
//    @ApiOperation(value = "查看文件上传、转码进度")
//    public void getFileTaskProcess(String str, HttpServletResponse response) {
//
//        while (true) {
//            // 获取所有转码文件进度，打印日志
//            Set<String> encodeSets = VideoEncodeSetFactory.getEncodeMap().keySet();
//            Iterator<String> encodeIterator = encodeSets.iterator();
//            while (encodeIterator.hasNext()) {
//                VideoEncodeProcessVO videoEncodeProcessVO = (VideoEncodeProcessVO) VideoEncodeSetFactory.getEncodeObject(encodeIterator.next());
//                LOGGER.info("视频转码进度,视频名称：{}，转码进度：{}%，转码任务:{}", encodeIterator.next(), videoEncodeProcessVO == null ? "暂无正在转码的视频" : videoEncodeProcessVO.getEncodeRate(), JSON.toJSON(VideoEncodeSetFactory.getEncodeMap()));
//            }
//            // 获取所有转码文件进度，打印日志
//            Set<String> uploadSets = FileUploadSetFactory.getUploadMap().keySet();
//            Iterator<String> uploadIterators = uploadSets.iterator();
//            while (uploadIterators.hasNext()) {
//                VideoUploadProcessVO videoUploadProcessVO = (VideoUploadProcessVO) FileUploadSetFactory.getUploadObject(uploadIterators.next());
//                LOGGER.info("视频上传进度,视频名称：{}，上传进度：{}%，上传任务:{}", uploadIterators.next(), videoUploadProcessVO == null ? "暂无正在上传的视频" : videoUploadProcessVO.getUploadRate(), JSON.toJSON(VideoEncodeSetFactory.getEncodeMap()));
//            }
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }
//
//
//
//
//}
