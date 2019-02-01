package org.lilacseeking.video.videoapp.Utils;

import com.alibaba.fastjson.JSON;
import lombok.experimental.Accessors;
import org.lilacseeking.video.videoapp.Common.ASynShowProcess;
import org.lilacseeking.video.videoapp.Common.ASynaVideoEncode;
import org.lilacseeking.video.videoapp.Configuration.ConstantProperties;
import org.lilacseeking.video.videoapp.Dao.video.VideoEncodeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ws.schild.jave.*;
import java.io.File;
import java.util.concurrent.Executor;

/**
 * @Author： lvming
 * @Date： Created in 18:08 2019/1/30
 * @Description： 视频转码
 * @Modified By：
 * @Version:
 */
@Service
public class VideoEncodeUtil {
    // 线程池
    @Autowired
    private Executor executor;
    // 日志
    private static Logger LOGGER = LoggerFactory.getLogger(VideoEncodeUtil.class);
    // 配置中心
    @Autowired
    private ConstantProperties constantProperties;

    @Autowired
    private VideoEncodeUtil videoEncodeUtil;
    @Autowired
    private VideoEncodeRepository videoEncodeRepository;


    /**
     * 转码视频文件为超清，高清、标清、分辨率为1920*1080、1280*720、720*480
     * @param source 源文件
     */
    public void encodeVideoToMP4ThreeCommonFormat(File source){
        // 目标文件路径
        String encodeTargetRoute = constantProperties.getUpload().getEncodeTargetRoute();
        String FHDRoute = constantProperties.getEncode().getFHD();
        String HDRoute = constantProperties.getEncode().getHD();
        String SDRoute = constantProperties.getEncode().getSD();
        // 目标文件名称
        String sourceFileName = source.getName().split("\\.")[0];
        // 转码为超清
        VideoSize FHD = new VideoSize(1920, 1080);
        File FHDTarget = new File(encodeTargetRoute.concat(File.separator).concat(sourceFileName).concat(File.separator).concat(FHDRoute).concat(File.separator).concat(sourceFileName).concat(".mp4"));
        executor.execute(new ASynaVideoEncode(source,FHDTarget,FHD,videoEncodeUtil,videoEncodeRepository));
        // 转码为高清
        VideoSize HD = new VideoSize(1280, 720);
        File HDTarget = new File(encodeTargetRoute.concat(File.separator).concat(sourceFileName).concat(File.separator).concat(HDRoute).concat(File.separator).concat(sourceFileName).concat(".mp4"));
        executor.execute(new ASynaVideoEncode(source,HDTarget,HD,videoEncodeUtil,videoEncodeRepository));
        // 转码为标清
        VideoSize SD = new VideoSize(720, 480);
        File SDTarget = new File(encodeTargetRoute.concat(File.separator).concat(sourceFileName).concat(File.separator).concat(SDRoute).concat(File.separator).concat(sourceFileName).concat(".mp4"));
        executor.execute(new ASynaVideoEncode(source,SDTarget,SD,videoEncodeUtil,videoEncodeRepository));
    }

    /**
     * 指定分辨率转码视频文件
     * @param source
     * @param target
     * @param videoSize
     */
    public void encodeVideoToMP4(MultimediaObject source,File target ,VideoSize videoSize){
        if (target.exists())
        {
            target.delete();
        }
        // 2 定义视频属性
        VideoAttributes video = new VideoAttributes();
        video.setSize(videoSize);
        // 2 定义音频属性
        AudioAttributes audio = new AudioAttributes();
        // 3 转码属性
        EncodingAttributes encodeAttr = new EncodingAttributes();
        //已编码的目标文件格式
        encodeAttr.setFormat("mp4");
        // 视频流编码属性
        encodeAttr.setVideoAttributes(video);
        // 音频流编码属性
        encodeAttr.setAudioAttributes(audio);
        // 4 设置进度监听
        PListener listener = new PListener();
        executor.execute(new ASynShowProcess(listener));
        // 4 转码
        Encoder encoder = new Encoder();
        try {
            encoder.encode(source, target, encodeAttr,listener);
        } catch (EncoderException e) {
            LOGGER.error("视频转码至mp4失败，源文件路径：{}，目标文件路径：{}，错误信息：{}，音频属性：{}，转码属性：{}",source.getFile().getPath(),target.getPath(),e.getMessage(),JSON.toJSONString(video),JSON.toJSONString(encodeAttr));
        }
    }
}
