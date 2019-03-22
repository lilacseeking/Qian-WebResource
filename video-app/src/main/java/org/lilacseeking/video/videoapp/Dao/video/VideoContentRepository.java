package org.lilacseeking.video.videoapp.Dao.video;

import org.lilacseeking.video.videoapp.Dao.AbstractRepository;
import org.lilacseeking.video.videoapp.Model.PO.VideoContentPO;
import org.lilacseeking.video.videoapp.Model.PO.VideoCoursePO;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: lilacseeking
 * @Date: 2018/12/17 21:33
 * @Description:
 */
@Repository
public class VideoContentRepository extends AbstractRepository<VideoContentPO> {


    /**
     * 保存课程目录信息
     * @return
     */
    @Transactional
    public VideoContentPO saveOrUpdate(VideoContentPO videoContentPO){
        return entityManager.merge(videoContentPO);
    }

    /**
     * 保存课程目录信息列表
     * @param videoContentPOList
     * @return
     */
    @Transactional
    public List<VideoContentPO> saveVideoContentList(List<VideoContentPO> videoContentPOList){
        List result = new ArrayList<VideoContentPO>();
        for(VideoContentPO videoContentPO : videoContentPOList){
            result.add(saveOrUpdate(videoContentPO));
        }
        return result;
    }
}
