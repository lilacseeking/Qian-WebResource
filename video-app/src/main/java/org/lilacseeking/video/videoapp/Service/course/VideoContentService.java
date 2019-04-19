package org.lilacseeking.video.videoapp.Service.course;

import org.lilacseeking.video.videoapp.Model.DTO.ContentDTO;
import org.lilacseeking.video.videoapp.Model.DTO.CourseDTO;
import org.lilacseeking.video.videoapp.Model.PO.VideoContentPO;

import java.util.List;

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

    /**
     * 新增课程章节信息列表
     * @param contentDTOList
     * @return
     */
    List<VideoContentPO> addVideoContentList(List<ContentDTO> contentDTOList);

    /**
     * 获取章节列表
     * @param courseDTO
     * @return
     */
    List<ContentDTO> getContentList(CourseDTO courseDTO);
}
