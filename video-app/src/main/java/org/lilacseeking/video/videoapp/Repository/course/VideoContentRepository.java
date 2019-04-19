package org.lilacseeking.video.videoapp.Repository.course;

import com.querydsl.jpa.impl.JPAQuery;
import org.lilacseeking.video.videoapp.Repository.AbstractRepository;
import org.lilacseeking.video.videoapp.Model.DTO.CourseDTO;
import org.lilacseeking.video.videoapp.Model.PO.QVideoContentPO;
import org.lilacseeking.video.videoapp.Model.PO.VideoContentPO;
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

    // 视频课程
    private QVideoContentPO qVideoContentPO = QVideoContentPO.videoContentPO;

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

    /**
     * 获取课程目录列表
     * @param courseDTO
     * @return
     */
    public List<VideoContentPO> getContentList(CourseDTO courseDTO){

        JPAQuery query = new JPAQuery<>(entityManager).from(qVideoContentPO);
        query.where(qVideoContentPO.courseId.eq(courseDTO.getId()));
        query.where(qVideoContentPO.achieve.eq(0));
        query.orderBy(qVideoContentPO.gmtCreate.desc());
        return ( List<VideoContentPO>)query.fetch();
    }
}
