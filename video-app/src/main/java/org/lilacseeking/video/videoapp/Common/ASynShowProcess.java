package org.lilacseeking.video.videoapp.Common;

import org.lilacseeking.video.videoapp.Dao.video.VideoEncodeRepository;
import org.lilacseeking.video.videoapp.Model.PO.VideoEncodePO;
import org.lilacseeking.video.videoapp.Utils.PListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author： lvming
 * @Date： Created in 11:57 2019/1/30
 * @Description： 进度显示
 * @Modified By：
 * @Version:
 */
public class ASynShowProcess implements Runnable{
    private static Logger LOGGER = LoggerFactory.getLogger(ASynShowProcess.class);
    // 转码监听
    private PListener pListener;
    // 转码监听结束标志
    private Boolean endListenStatus = false;
    // 视频转码结果持久化
    private VideoEncodeRepository videoEncodeRepository;

    public ASynShowProcess(PListener pListener){
        this.pListener = pListener;
    }

    public ASynShowProcess(PListener pListener,Boolean endListenStatus,VideoEncodeRepository videoEncodeRepository){
        this.pListener = pListener;
        this.endListenStatus = endListenStatus;
        this.videoEncodeRepository = videoEncodeRepository;
    }

    @Override
    public void run() {
        List process = pListener.getProgress();
        Integer encodeRate = (Integer) process.get(process.size() - 1) / 10;
        while (!Thread.interrupted() && null !=Thread.currentThread()){
            process = pListener.getProgress();
            LOGGER.info("视频转码进度显示：进度：{}%,info:{},message:{},process:{}",process.size()==0 ? 0 : (Integer)process.get(process.size() - 1)/10,
                    pListener.getInfo(),pListener.getMessages(),pListener.getProgress());
            if (100 == encodeRate){
                VideoEncodePO videoencodePO = VideoEncodePO.builder().operateId(null).
                        operateName(null).videoOriginName(null).videoName(null).
                        videoOriginPath(null).videoPath(null).videoLength(null).
                        videoSize(null).encodeStatus(null).encodeRate(encodeRate).build();
                videoEncodeRepository.saveOrUpdate(videoencodePO);
                break;
            }
            try {
                TimeUnit.SECONDS.sleep( 1 );
            } catch (InterruptedException e) {
                if (endListenStatus.equals(Boolean.TRUE)){
                    break;
                }
            }
        }
    }
}
