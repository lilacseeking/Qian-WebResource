package org.lilacseeking.video.videoapp.Common;

import com.alibaba.fastjson.JSON;
import org.lilacseeking.video.videoapp.Dao.video.VideoEncodeRepository;
import org.lilacseeking.video.videoapp.Eumns.ProcessEnum;
import org.lilacseeking.video.videoapp.Model.PO.VideoEncodePO;
import org.lilacseeking.video.videoapp.Utils.VideoEncodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ws.schild.jave.EncoderException;
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
public class ASynaVideoEncode implements Runnable{
    // 日志
    private static Logger LOGGER = LoggerFactory.getLogger(ASynaVideoEncode.class);
    // 视频转码
    private VideoEncodeUtil videoEncodeUtil;
    // 视频大小
    private VideoSize videoSize;
    // 源文件
    private File source;
    // 目标文件
    private File target;
    // 视频转码结果持久化
    private VideoEncodeRepository videoEncodeRepository;


    public ASynaVideoEncode(File source,File target ,VideoSize videoSize,VideoEncodeUtil videoEncodeUtil,VideoEncodeRepository videoEncodeRepository){
        this.source = source;
        this.target = target;
        this.videoSize = videoSize;
        this.videoEncodeUtil = videoEncodeUtil;
        this.videoEncodeRepository = videoEncodeRepository;
    }
    @Override
    public void run() {
        LOGGER.info("视频转码异步处理开始：source:{}，target:{}，videoSize:{},videoEncodeUtil:{}"
                , JSON.toJSONString(source),JSON.toJSONString(target),JSON.toJSONString(videoSize),JSON.toJSONString(videoEncodeUtil));
        MultimediaObject multimediaObject = new MultimediaObject(source);
        VideoEncodePO videoencodePO = null;
        try {
            videoencodePO = VideoEncodePO.builder().operateId(null).
                    operateName(null).videoOriginName(source.getName()).videoName(target.getName()).
                    videoOriginPath(source.getPath()).videoPath(target.getPath()).videoLength(multimediaObject.getInfo().getDuration()).
                    videoSize(target.getFreeSpace()).encodeStatus(ProcessEnum.START).encodeRate(0).build();
        } catch (EncoderException e) {
            e.printStackTrace();
        }
        videoEncodeRepository.saveOrUpdate(videoencodePO);
        videoEncodeUtil.encodeVideoToMP4(multimediaObject,target,videoSize);
    }
}
