package org.lilacseeking.video.videoapp.Service.Impl.video;

import org.lilacseeking.video.videoapp.Dao.video.VideoContentRepository;
import org.lilacseeking.video.videoapp.Model.PO.VideoContentPO;
import org.lilacseeking.video.videoapp.Service.video.VideoContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: lilacseeking
 * @Date: 2018/12/17 21:31
 * @Description:
 */
@Service
public class VideoContentServiceImpl implements VideoContentService {

    @Autowired
    private VideoContentRepository videoContentRepository;

    /**
     * 新增课程章节信息
     *
     * @param videoContentPO 章节信息
     * @return 章节信息
     */
    @Override
    public VideoContentPO addVideoContent(VideoContentPO videoContentPO) {
        return videoContentRepository.saveOrUpdate(videoContentPO);
    }
}
