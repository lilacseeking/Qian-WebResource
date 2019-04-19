package org.lilacseeking.video.videoapp.Repository.course;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import org.lilacseeking.video.videoapp.Model.PO.CourseUserPO;
import org.lilacseeking.video.videoapp.Model.PO.QCourseUserPO;
import org.lilacseeking.video.videoapp.Model.PO.QVideoCoursePO;
import org.lilacseeking.video.videoapp.Repository.AbstractRepository;
import org.lilacseeking.video.videoapp.Utils.Page;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: lilacseeking
 * @Date: 2019/3/24 14:50
 * @Description:
 */
@Repository
public class CourseUserRepository extends AbstractRepository<CourseUserPO> {

    QCourseUserPO qCourseUserPO = QCourseUserPO.courseUserPO;

    QVideoCoursePO qVideoCoursePO = QVideoCoursePO.videoCoursePO;

    /**
     * 新增或修改
     * @param courseUserPO
     * @return
     */
    @Transactional
    public CourseUserPO saveOrUpdate(CourseUserPO courseUserPO){
        return entityManager.merge(courseUserPO);
    }

    /**
     * 获取用户-课程关系
     * @param userId
     * @param courseId
     * @return
     */
    public CourseUserPO getCourseUserInfo(Long userId,Long courseId){
        JPAQuery query = new JPAQuery<>(entityManager).from(qCourseUserPO);
        query.where(qCourseUserPO.userId.eq(userId)).where(qCourseUserPO.courseId.eq(courseId)).where(qCourseUserPO.achieve.eq(0));
        return (CourseUserPO)query.fetchFirst();
    }

    /**
     * 分页获取课程-用户关系
     * @param filter
     * @param page
     * @return
     */
    public Page getCourseUserList(JSONObject filter,Page page){
        Long userId = filter.getLong("userId");
        Long courseId = filter.getLong("courseId");
        JPAQuery query = new JPAQuery<>(entityManager).from(qCourseUserPO,qVideoCoursePO);
        query.select(qCourseUserPO,qVideoCoursePO.name,qVideoCoursePO.thumbnail);
        query.where(qCourseUserPO.achieve.eq(0));
        query.where(qCourseUserPO.courseId.eq(qVideoCoursePO.id));
        if (null != userId){
            query.where(qCourseUserPO.userId.eq(userId));
        }
        if (null != courseId){
            query.where(qCourseUserPO.courseId.eq(courseId));
        }
        query.limit(page.getRows());
        query.offset(page.getFirstResult());
        query.orderBy(qCourseUserPO.gmtCreate.desc());
        int count = (int)query.fetchCount();
        page.setCount(count);
        List<Tuple> result = query.fetch();
        List<JSONObject> resultList = new ArrayList<>();
        for (Tuple tuple : result){
            JSONObject courseUser = new JSONObject();
            CourseUserPO courseUserPO = tuple.get(qCourseUserPO);
            String courseName = tuple.get(qVideoCoursePO.name);
            String thumbnail = tuple.get(qVideoCoursePO.thumbnail);
            courseUser.putAll(JSONObject.parseObject(JSON.toJSONString(courseUserPO)));
            courseUser.put("courseName",courseName);
            courseUser.put("thumbnail",thumbnail);
            resultList.add(courseUser);
        }
        page.setResultList(resultList);
        return page;
    }
}
