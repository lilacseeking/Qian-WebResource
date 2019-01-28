package org.lilacseeking.video.videoapp.Service.video;


import org.lilacseeking.video.videoapp.Model.PO.VideoCoursePO;

/**
 * @Auther: lilacseeking
 * @Date: 2018/10/18 22:46
 * @Description:课程相关接口
 */
public interface VideoCourseService {

    /**
     * 新增课程
     * @param videoCoursePO
     * @return
     */
    VideoCoursePO addVideoClass(VideoCoursePO videoCoursePO);
}
