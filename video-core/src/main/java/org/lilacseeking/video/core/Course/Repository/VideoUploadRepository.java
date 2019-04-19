package org.lilacseeking.video.core.Course.Repository;

import org.lilacseeking.video.core.Upload.Model.VideoUploadPO;
import org.lilacseeking.video.infrastructure.Repository.AbstractRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author： lvming
 * @Date： Created in 16:36 2019/1/31
 * @Description： 视频上传DAO
 * @Modified By：
 * @Version:
 */
@Repository
public class VideoUploadRepository extends AbstractRepository<VideoUploadPO> {

    @Transactional
    public VideoUploadPO saveOrUpdate(VideoUploadPO videoUploadPO){
        return entityManager.merge(videoUploadPO);
    }

}
