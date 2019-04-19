package org.lilacseeking.video.app.Common;

import org.lilacseeking.video.app.Repository.course.VideoEncodeRepository;
import org.lilacseeking.video.app.Listener.EncodingListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @Author： lvming
 * @Date： Created in 11:57 2019/1/30
 * @Description： 进度显示
 * @Modified By：
 * @Version:
 */
public class ASycShowProcess implements Runnable{
    private static Logger LOGGER = LoggerFactory.getLogger(ASycShowProcess.class);
    // 转码监听
    private EncodingListener pListener;
    // 转码监听结束标志
    private Boolean endListenStatus = false;
    // 视频转码结果持久化
    private VideoEncodeRepository videoEncodeRepository;

    public ASycShowProcess(EncodingListener pListener){
        this.pListener = pListener;
    }

    public ASycShowProcess(EncodingListener pListener, Boolean endListenStatus, VideoEncodeRepository videoEncodeRepository){
        this.pListener = pListener;
        this.endListenStatus = endListenStatus;
        this.videoEncodeRepository = videoEncodeRepository;
    }

    @Override
    public void run() {

        while (!Thread.interrupted() && null !=Thread.currentThread()){
            List process = pListener.getProgress();
            Integer encodeRate = process.size() == 0 ? 0 : (Integer) process.get(process.size() - 1) / 10;

////            LOGGER.info("视频转码进度显示：进度：{}%,info:{},message:{},process:{}",encodeRate,
////                    pListener.getInfo(),pListener.getMessages(),pListener.getProgress());
            if (100 == encodeRate){
//                VideoEncodePO videoencodePO = VideoEncodePO.builder().operateId(null).
//                        operateName(null).videoOriginName(null).videoName(null).
//                        videoOriginPath(null).videoPath(null).videoLength(null).
//                        videoSize(null).encodeStatus(null).encodeRate(encodeRate).build();
                endListenStatus = true;
//                videoEncodeRepository.saveOrUpdate(videoencodePO);
                LOGGER.info("转码成功：进度：{}%",encodeRate);
                break;
            }
        }
    }
}
