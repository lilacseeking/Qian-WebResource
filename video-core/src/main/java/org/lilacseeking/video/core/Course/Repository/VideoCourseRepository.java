package org.lilacseeking.video.core.Course.Repository;


import com.alibaba.fastjson.JSONObject;
import com.querydsl.jpa.impl.JPAQuery;
import org.apache.commons.lang3.StringUtils;
import org.lilacseeking.video.core.Course.Model.QVideoCoursePO;
import org.lilacseeking.video.core.Course.Model.VideoCoursePO;
import org.lilacseeking.video.infrastructure.Repository.AbstractRepository;
import org.lilacseeking.video.infrastructure.utils.Page;
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
    public Page getCourseList(JSONObject filter, Page page){

        // 条件参数赋值
        String name = filter.getString("name");
        String tags = filter.getString("tags");
        JPAQuery query = new JPAQuery<>(entityManager).from(qVideoCoursePO);
        // 查询未下架课程
        query.where(qVideoCoursePO.achieve.eq(0));
        // 条件查询
        if (StringUtils.isNotBlank(name)){
            query.where(qVideoCoursePO.name.like( '%'+ name + '%'));
        }
        if (StringUtils.isNotBlank(tags)){
            query.where(qVideoCoursePO.tags.like('%' + tags + '%'));
        }
        long count = query.fetchCount();
        query.limit(page.getRows());
        query.offset(page.getFirstResult());
        query.orderBy(qVideoCoursePO.gmtCreate.desc());
        List result = query.fetch();
        page.setCount((int) count);
        page.setResultList(result);
        return page;
    }

    /**
     * 获取课程信息
     * @param id
     * @return
     */
    public VideoCoursePO getCourseInfo(Long id){
        JPAQuery query = new JPAQuery<>(entityManager).from(qVideoCoursePO);
        query.where(qVideoCoursePO.id.eq(id));
        query.where(qVideoCoursePO.achieve.eq(0));
        return (VideoCoursePO)query.fetchFirst();
    }
}
