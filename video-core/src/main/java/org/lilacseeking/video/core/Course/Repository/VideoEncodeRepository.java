package org.lilacseeking.video.core.Course.Repository;

import org.lilacseeking.video.core.Encode.Model.VideoEncodePO;
import org.lilacseeking.video.infrastructure.Repository.AbstractRepository;
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
