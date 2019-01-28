package org.lilacseeking.video.videoapp.Service.video;

import org.lilacseeking.video.videoapp.Model.PO.VideoContentPO;

/**
 * @Auther: lilacseeking
 * @Date: 2018/12/17 21:05
 * @Description:
 */
public interface VideoContentService {

    /**
     * 新增课程章节信息
     * @param videoContentPO 章节信息
     * @return 章节信息
     */
    VideoContentPO addVideoContent(VideoContentPO videoContentPO);
}
