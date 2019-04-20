package org.lilacseeking.video.service.Common;

import com.alibaba.fastjson.JSON;
import org.lilacseeking.video.core.Course.Repository.VideoEncodeRepository;
import org.lilacseeking.video.service.Facade.encode.VideoEncodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.VideoSize;
import java.io.File;

/**
 * @Author： lvming
 * @Date： Created in 18:24 2019/1/30
 * @Description： 视频转码
 * @Modified By：
 * @Version:
 */
public class ASyncVideoEncode implements Runnable{
    // 日志
    private static Logger LOGGER = LoggerFactory.getLogger(ASyncVideoEncode.class);
    // 视频转码
    private VideoEncodeService videoEncodeService;
    // 视频大小
    private VideoSize videoSize;
    // 源文件
    private File source;
    // 目标文件
    private File target;
    // TODO 工程就够修改
    // 视频转码结果持久化
//    private VideoEncodeRepository videoEncodeRepository;


    public ASyncVideoEncode(File source, File target , VideoSize videoSize, VideoEncodeService videoEncodeService, VideoEncodeRepository videoEncodeRepository){
        this.source = source;
        this.target = target;
        this.videoSize = videoSize;
        this.videoEncodeService = videoEncodeService;
        // TODO 工程就够修改
//        this.videoEncodeRepository = videoEncodeRepository;
    }
    @Override
    public void run() {
        LOGGER.info("视频转码异步处理开始：source:{}，target:{}，totalVideoSize:{},videoEncodeUtil:{}"
                , JSON.toJSONString(source),JSON.toJSONString(target),JSON.toJSONString(videoSize),JSON.toJSONString(videoEncodeService));
        MultimediaObject multimediaObject = new MultimediaObject(source);
//        VideoEncodePO videoencodePO = null;
//        try {
//            videoencodePO = VideoEncodePO.builder().operateId(null).
//                    operateName(null).videoOriginName(source.getName()).videoName(target.getName()).
//                    videoOriginPath(source.getPath()).videoPath(target.getPath()).videoLength(multimediaObject.getInfo().getDuration()).
//                    videoSize(target.getFreeSpace()).encodeStatus(ProcessEnum.START).encodeRate(0).build();
//        } catch (EncoderException e) {
//            e.printStackTrace();
//        }
//        videoEncodeRepository.saveOrUpdate(videoencodePO);
//        videoEncodeService.encodeVideoToMP4(multimediaObject,target,videoSize);
    }
}
