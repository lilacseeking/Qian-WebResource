//package org.lilacseeking.video.app.Controller.file;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.lilacseeking.video.app.Service.ResponseUtil;
//import org.lilacseeking.video.core.Upload.Service.FileService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//import javax.servlet.http.HttpServletResponse;
//import java.util.List;
//
///**
// * @Auther: lilacseeking
// * @Date: 2019/4/14 09:28
// * @Description:
// */
//@RestController
//@RequestMapping(value = "/file")
//@Api(value = "文件")
//public class FileController {
//    @Autowired
//    FileService fileService;
//
//    @Autowired
//    private ResponseUtil responseUtil;
//
//    @RequestMapping(value = "/moreFileUpload", method = RequestMethod.POST)
//    @ApiOperation(value = "多文件上传")
//    public void moreFileUpload(MultipartFile[] files, HttpServletResponse response) {
//        List list = fileService.moreFileUpload(files);
//        responseUtil.putSuccess(list);
//        responseUtil.writeMessage(response);
//    }
//}