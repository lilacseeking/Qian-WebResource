package org.lilacseeking.video.core.Course.Service.Impl;


import org.lilacseeking.video.core.Course.Model.VideoContentPO;
import org.lilacseeking.video.core.Course.Repository.VideoContentRepository;
import org.lilacseeking.video.core.Course.Service.VideoContentService;
import org.lilacseeking.video.infrastructure.Model.DTO.ContentDTO;
import org.lilacseeking.video.infrastructure.Model.DTO.CourseDTO;
import org.lilacseeking.video.infrastructure.enums.ErrorCodeEumn;
import org.lilacseeking.video.infrastructure.utils.BeanCopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * 新增课程章节信息列表
     *
     * @param videoContentPOList
     * @return
     */
    @Override
    public List<VideoContentPO> addVideoContentList(List<ContentDTO> videoContentPOList) {
        // 校验参数
        Assert.isTrue(videoContentPOList != null && videoContentPOList.size() > 0,ErrorCodeEumn.CONTENT_LIST_NOT_NULL.getName());
        List videoContentPOS = new ArrayList<VideoContentPO>();
        for (ContentDTO contentDTO: videoContentPOList){
            VideoContentPO target = new VideoContentPO();
            BeanCopyUtil.copyPropertiesIgnoreNull(contentDTO,target);
            videoContentPOS.add(target);
        }
        return videoContentRepository.saveVideoContentList(videoContentPOS);
    }

    /**
     * 获取课程列表
     *
     * @param courseDTO
     * @return
     */
    @Override
    public List<ContentDTO> getContentList(CourseDTO courseDTO) {
        List<VideoContentPO> contentList = videoContentRepository.getContentList(courseDTO);
        List resultList = new ArrayList<ContentDTO>();
        for (VideoContentPO videoContentPO : contentList){
            ContentDTO contentDTO = new ContentDTO();
            BeanCopyUtil.copyPropertiesIgnoreNull(videoContentPO,contentDTO);
            resultList.add(contentDTO);
        }
        return resultList;
    }
}
