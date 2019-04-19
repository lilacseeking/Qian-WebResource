package org.lilacseeking.video.service.Facade.Encode;

import com.alibaba.fastjson.JSON;
import org.lilacseeking.video.app.Common.ASycShowProcess;
import org.lilacseeking.video.app.Configuration.ConstantProperties;
import org.lilacseeking.video.app.Repository.course.VideoEncodeRepository;
import org.lilacseeking.video.app.Listener.EncodingListener;
import org.lilacseeking.video.app.Model.VO.VideoEncodeProcessVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import ws.schild.jave.*;
import java.io.File;

/**
 * @Author： lvming
 * @Date： Created in 18:08 2019/1/30
 * @Description： 视频转码
 * @Modified By：
 * @Version:
 */
@Service
public class VideoEncodeService {
    // 线程池
    @Autowired
    private ThreadPoolTaskExecutor executor;
    // 日志
    private static Logger LOGGER = LoggerFactory.getLogger(VideoEncodeService.class);
    // 配置中心
    @Autowired
    private ConstantProperties constantProperties;

    @Autowired
    private VideoEncodeService videoEncodeService;
    @Autowired
    private VideoEncodeRepository videoEncodeRepository;


    /**
     * 指定分辨率转码视频文件
     * @param videoEncodeProcessVO 转码任务信息
     * @param videoSize 视频格式信息
     */
    public void encodeVideoToMP4(VideoEncodeProcessVO videoEncodeProcessVO, VideoSize videoSize){
        File source = new File(videoEncodeProcessVO.getVideoOriginPath());
        File target = new File(videoEncodeProcessVO.getVideoPath());
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
        EncodingListener listener = new EncodingListener(videoEncodeProcessVO.getVideoOriginName());
        executor.execute(new ASycShowProcess(listener));
        // 4 转码
        Encoder encoder = new Encoder();
        try {
            encoder.encode(new MultimediaObject(source), target, encodeAttr,listener);
        } catch (EncoderException e) {
            LOGGER.error("视频转码至mp4失败，源文件路径：{}，目标文件路径：{}，错误信息：{}，音频属性：{}，转码属性：{}",source.getPath(),target.getPath(),e.getMessage(),JSON.toJSONString(video),JSON.toJSONString(encodeAttr));
        }
    }
}
