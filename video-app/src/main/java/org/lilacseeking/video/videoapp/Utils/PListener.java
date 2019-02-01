package org.lilacseeking.video.videoapp.Utils;

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
public class PListener implements EncoderProgressListener {

    private MultimediaInfo _info= null;
    private final List<String> _messages= new LinkedList<>();
    private final List<Integer> _progress= new LinkedList<>();

    @Override
    public void sourceInfo(MultimediaInfo info) {
        _info= info;
    }

    @Override
    public void progress(int permil) {
        _progress.add(permil);
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