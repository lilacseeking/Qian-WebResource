package org.lilacseeking.video.videoapp.Dao.video;


import com.alibaba.fastjson.JSONObject;
import com.querydsl.core.Query;
import com.querydsl.core.support.QueryBase;
import com.querydsl.jpa.impl.JPAQuery;
import jodd.util.StringUtil;
import org.lilacseeking.video.videoapp.Dao.AbstractRepository;
import org.lilacseeking.video.videoapp.Model.PO.QVideoCoursePO;
import org.lilacseeking.video.videoapp.Model.PO.VideoCoursePO;
import org.lilacseeking.video.videoapp.Utils.Page;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @Auther: lilacseeking
 * @Date: 2018/10/18 22:50
 * @Description:课程持久化类
 */
@Repository
public class VideoCourseRepository extends AbstractRepository<VideoCoursePO> {

    QVideoCoursePO qVideoCoursePO = QVideoCoursePO.videoCoursePO;
    /**
     * 新增或修改
     * @param videoCoursePO
     * @return
     */
    @Transactional
    public VideoCoursePO saveOrUpdate(VideoCoursePO videoCoursePO){
        return entityManager.merge(videoCoursePO);
    }

    /**
     * 分页查询、条件查询列表
     * @return
     */
    public Page getCourseList(JSONObject filter,Page page){

        // 条件参数赋值
        String name = filter.getString("name");
        String tags = filter.getString("tags");
        JPAQuery query = new JPAQuery<>(entityManager).from(qVideoCoursePO);
        // 条件查询
        if (StringUtil.isNotBlank(name)){
            query.where(qVideoCoursePO.name.like("%" + name + "%"));
        }
        if (StringUtil.isNotBlank(tags)){
            query.where(qVideoCoursePO.tags.like("%" + tags + "%"));
        }
        long count = query.fetchCount();
        query.limit(page.getRows());
        query.offset(page.getCurrentPage());
        List result = query.fetch();
        page.setCount((int) count);
        page.setResultList(result);
        return page;
    }
}
