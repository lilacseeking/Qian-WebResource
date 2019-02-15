package org.lilacseeking.video.videoapp.Service.Impl.video;

import org.lilacseeking.video.videoapp.Configuration.ConstantProperties;
import org.lilacseeking.video.videoapp.Dao.video.VideoUploadRepository;
import org.lilacseeking.video.videoapp.Eumns.ErrorCodeEumn;
import org.lilacseeking.video.videoapp.Eumns.ProcessEnum;
import org.lilacseeking.video.videoapp.Exception.BusinessException;
import org.lilacseeking.video.videoapp.Model.PO.VideoUploadPO;
import org.lilacseeking.video.videoapp.Model.Factory.FileUploadSetFactory;
import org.lilacseeking.video.videoapp.Model.Factory.VideoEncodeSetFactory;
import org.lilacseeking.video.videoapp.Model.VO.VideoEncodeProcessVO;
import org.lilacseeking.video.videoapp.Model.VO.VideoUploadProcessVO;
import org.lilacseeking.video.videoapp.Service.RedisService;
import org.lilacseeking.video.videoapp.Service.video.VideoService;
import org.lilacseeking.video.videoapp.Utils.BeanCopyUtil;
import org.lilacseeking.video.videoapp.Utils.OSSUtil;
import org.lilacseeking.video.videoapp.Utils.VideoEncodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import ws.schild.jave.VideoSize;

/**
 * @Author： lvming
 * @Date： Created in 18:46 2019/1/30
 * @Description：
 * @Modified By：
 * @Version:
 */
@Service
public class VideoServiceImpl implements VideoService {
    // 转码工具类
    @Autowired
    private VideoEncodeService videoEncodeService;
    // 配置中心
    @Autowired
    private ConstantProperties constantProperties;
    // Redis服务类
    @Autowired
    private RedisService redisService;
    // 保存文件上传结果
    @Autowired
    private VideoUploadRepository videoUploadRepository;
    // 线程池
    @Autowired
    private ThreadPoolTaskExecutor executor;


    /**
     * 将视频格式转码为MP4格式，三种格式 标清、高清、超清
     * @param file
     */
    @Override
    public void encodeVideoToMP4ThreeCommonFormat(MultipartFile file) {
        // 1. 构造转码源文件
        File source = new File(constantProperties.getUpload().getSourceRoute().concat(File.separator).concat(file.getOriginalFilename()));
        try {
            FileUtils.copyInputStreamToFile(file.getInputStream(), source);
        } catch (IOException e) {
            throw new BusinessException(ErrorCodeEumn.VIDEO_TRANS_FAILED.getName());
        }

        List<File> fileList = encodeVideoToMP4ThreeCommonFormat(source);
        // 上传文件
//        uploadFileList(fileList);
    }

    /**
     * 上传文件
     *
     * @param fileList
     */
    @Override
    public void uploadFileList(List<File> fileList) {
        // 1 添加上传任务
        for (File file : fileList){
            String objectName = constantProperties.getUpload().getVideoRoute() + redisService.getUuidCode();
            // 1.1 构造视频上传进度VO
            VideoUploadProcessVO videoUploadProcessVO = VideoUploadProcessVO.builder()
                    .videoOriginName(file.getName()).videoName(objectName)
                    .videoOriginPath(file.getPath()).videoPath("OSS地址").videoLength("视频时长").totalVideoSize(file.length())
                    .uploadVideoSize(0L).uploadStatus(ProcessEnum.PENDING.name()).build();
            // 1.2 存入上传集合
            FileUploadSetFactory.putUploadMap(objectName,videoUploadProcessVO);
            // 1.3 调用工具方法上传
            videoUploadProcessVO = OSSUtil.multipartUpload(videoUploadProcessVO);
            // 1.4 调用工具创建下载地址
            URL fileAccessUrl = OSSUtil.createFileAccessUrl(videoUploadProcessVO.getVideoName());
            videoUploadProcessVO.setVideoPath(fileAccessUrl.getPath());
            // 1.4 保存上传结果
            saveFileUpload(videoUploadProcessVO);
        }
    }

    /**
     * 保存上传任务到数据库
     * @param videoUploadProcessVO
     */
    public void saveFileUpload(VideoUploadProcessVO videoUploadProcessVO){
        VideoUploadPO videoUploadPO = VideoUploadPO.builder().build();
        BeanCopyUtil.copyPropertiesIgnoreNull(videoUploadProcessVO,videoUploadPO);
        videoUploadRepository.saveOrUpdate(videoUploadPO);
    }

    /**
     * 转码视频文件为超清，高清、标清、分辨率为1920*1080、1280*720、720*480
     * @param source 源文件
     */
    public List<File> encodeVideoToMP4ThreeCommonFormat(File source){

        // 转码生成文件
        List<File> fileList = new ArrayList<>();
        // 目标文件路径
        String encodeTargetRoute = constantProperties.getUpload().getEncodeTargetRoute();
        String FHDRoute = constantProperties.getEncode().getFHD();
        String HDRoute = constantProperties.getEncode().getHD();
        String SDRoute = constantProperties.getEncode().getSD();
        // 目标文件名称
        String sourceFileName = source.getName().split("\\.")[0];
        // 循环三次 分别构造出超清转码，高清转码，标清转码三个转码任务
        for (int i = 0; i < 3; i++){
            // 转码为超清(分辨率)
            VideoSize videoSize = null;
            String resolutionRoute = "";
            if (i==0){
                videoSize = new VideoSize(1920, 1080);
                resolutionRoute = FHDRoute;
            }else if(i==1){
                videoSize = new VideoSize(1280, 720);
                resolutionRoute = HDRoute;
            }else {
                videoSize = new VideoSize(720, 480);
                resolutionRoute = SDRoute;
            }
            File target = new File(encodeTargetRoute.concat(File.separator).concat(sourceFileName).concat(File.separator).concat(resolutionRoute).concat(File.separator).concat(sourceFileName).concat(".mp4"));
            // 2. 构造转码任务,插入转码队列
            VideoEncodeProcessVO videoEncodeProcessVO = new VideoEncodeProcessVO();
            videoEncodeProcessVO.setGmtCreate(new Date()).setGmtFinish(null).setConsumingTime(0L).setOperateId(1L).setOperateName("admin")
                    .setVideoOriginName(target.getName()).setVideoName(redisService.getUuidCode()).setVideoOriginPath(source.getPath())
                    .setVideoPath(target.getPath()).setVideoLength(null).setResolution(resolutionRoute).setVideoSize(target.length()).setEncodeStatus(ProcessEnum.START).setEncodeRate(0);
            VideoEncodeSetFactory.putEncodeObject(videoEncodeProcessVO.getVideoName(),videoEncodeProcessVO);

            // 3. 保存任务至数据库？
            // 4. 任务处理-开始转码
//            executor.execute(new ASyncVideoEncode(source,target,videoSize,videoEncodeUtil,videoEncodeRepository));
            videoEncodeService.encodeVideoToMP4(videoEncodeProcessVO,videoSize);
            // 5. 任务完成后保存至数据库
            // 6. 上传任务开始
            fileList.add(target);
        }
        return fileList;
    }
}
