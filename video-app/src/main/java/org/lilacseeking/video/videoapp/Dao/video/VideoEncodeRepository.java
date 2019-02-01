package org.lilacseeking.video.videoapp.Dao.video;

import org.lilacseeking.video.videoapp.Dao.AbstractRepository;
import org.lilacseeking.video.videoapp.Model.PO.VideoEncodePO;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author： lvming
 * @Date： Created in 16:36 2019/1/31
 * @Description： 视频转码DAO
 * @Modified By：
 * @Version:
 */
@Repository
public class VideoEncodeRepository extends AbstractRepository<VideoEncodePO> {
    @Transactional
    public VideoEncodePO saveOrUpdate(VideoEncodePO videoEncodePO){
        return entityManager.merge(videoEncodePO);
    }
}
