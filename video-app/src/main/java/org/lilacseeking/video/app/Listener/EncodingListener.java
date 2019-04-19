package org.lilacseeking.video.app.Listener;

import org.lilacseeking.video.app.Model.Factory.VideoEncodeSetFactory;
import org.lilacseeking.video.app.Model.VO.VideoEncodeProcessVO;
import ws.schild.jave.EncoderProgressListener;
import ws.schild.jave.MultimediaInfo;

import java.util.LinkedList;
import java.util.List;

/**
 * @Author： lvming
 * @Date： Created in 14:05 2019/1/30
 * @Description：
 * @Modified By：
 * @Version:
 */
public class EncodingListener implements EncoderProgressListener {

    private MultimediaInfo _info= null;
    private final List<String> _messages= new LinkedList<>();
    private final List<Integer> _progress= new LinkedList<>();
    /**
     * 文件名
     */
    private String fileName;

    public EncodingListener(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void sourceInfo(MultimediaInfo info) {
        _info= info;
    }

    // 更新转码进度
    @Override
    public void progress(int permil) {
        _progress.add(permil);
        // 获取当前转码任务文件
        VideoEncodeProcessVO videoEncodeProcessVO = (VideoEncodeProcessVO)VideoEncodeSetFactory.getEncodeObject(fileName);
        VideoEncodeSetFactory.putEncodeObject(fileName,videoEncodeProcessVO);
        // 计算转换进度
        Integer encodeRate = permil / 100;
        videoEncodeProcessVO.setEncodeRate(encodeRate);
    }

    @Override
    public void message(String message) {
        _messages.add(message);
    }

    /**
     * @return the _info
     */
    public MultimediaInfo getInfo() {
        return _info;
    }

    /**
     * @return the _messages
     */
    public List<String> getMessages() {
        return _messages;
    }

    /**
     * @return the _progress
     */
    public List<Integer> getProgress() {
        return _progress;
    }

}