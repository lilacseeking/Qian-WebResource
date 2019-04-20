package org.lilacseeking.video.core.User.Repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPAQueryBase;
import com.querydsl.jpa.impl.JPAQuery;
import org.apache.commons.lang3.StringUtils;
import org.lilacseeking.video.core.User.Model.QTeacherPO;
import org.lilacseeking.video.core.User.Model.QUserPO;
import org.lilacseeking.video.core.User.Model.TeacherPO;
import org.lilacseeking.video.core.User.Model.UserPO;
import org.lilacseeking.video.infrastructure.Model.DTO.TeacherDTO;
import org.lilacseeking.video.infrastructure.Repository.AbstractRepository;
import org.lilacseeking.video.infrastructure.enums.YesOrNoEnum;
import org.lilacseeking.video.infrastructure.utils.BeanCopyUtil;
import org.lilacseeking.video.infrastructure.utils.Page;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: lilacseeking
 * @Date: 2019/3/30 12:09
 * @Description:
 */
@Repository
public class TeacherRepository extends AbstractRepository<TeacherPO> {

    QTeacherPO qTeacherPO = QTeacherPO.teacherPO;
    QUserPO qUserPO = QUserPO.userPO;

    /**
     * 根据教师Id获取教师信息
     * @param teacherId
     * @return
     */
    public TeacherPO getTeacherByTeacherId(Long teacherId){
        return entityManager.find(TeacherPO.class,teacherId);
    }

    /**
     * 保存
     * @param teacherPO
     * @return
     */
    @Transactional
    public TeacherPO saveOrUpdate(TeacherPO teacherPO){
        Long userId = teacherPO.getUserId();
        JPAQueryBase query = new JPAQuery<>(entityManager).from(qTeacherPO);
        query.where(qTeacherPO.userId.eq(userId));
        query.where(qTeacherPO.achieve.eq(0));
        TeacherPO temp = (TeacherPO)query.fetchFirst();
        if (null != temp){
            BeanCopyUtil.copyPropertiesIgnoreNull(teacherPO,temp);
            return entityManager.merge(temp);
        }
        return entityManager.merge(teacherPO);
    }

    /**
     * 获取教师列表
     * @param filter
     * @param page
     * @return
     */
    public Page getTeacherList(JSONObject filter, Page page){

        // 查询条件
        String email = filter.getString("email");
        String mobile = filter.getString("mobile");
        String userName = filter.getString("userName");
        String teacherName = filter.getString("teacherName");
        String startDate = filter.getString("startDate");
        String endDate = filter.getString("endDate");
        String idCardNo = filter.getString("idCardNo");
        String researchArea = filter.getString("researchArea");
        String verifyStatus = filter.getString("verifyStatus");
        YesOrNoEnum verifyStatusEnum = null;
        verifyStatusEnum = StringUtils.isNotBlank(verifyStatus)?YesOrNoEnum.valueOf(verifyStatus):null;
        JPAQuery query = new JPAQuery<>(entityManager).from(qTeacherPO, qUserPO);
        query.select(qTeacherPO,qUserPO);
        query.where(qTeacherPO.userId.eq(qUserPO.id));
        query.where(qTeacherPO.achieve.eq(0));
        if (StringUtils.isNotBlank(email)){
            query.where(qUserPO.email.eq(email));
        }
        if (StringUtils.isNotBlank(mobile)){
            query.where(qUserPO.mobile.eq(mobile));
        }
        if (StringUtils.isNotBlank(userName)){
            query.where(qUserPO.username.eq(userName));
        }
        if (StringUtils.isNotBlank(teacherName)){
            query.where(qUserPO.name.like("%" + teacherName + "%"));
        }
        if (StringUtils.isNotBlank(idCardNo)){
            query.where(qTeacherPO.idCardNo.eq(idCardNo));
        }
        if (StringUtils.isNotBlank(researchArea)){
            query.where(qTeacherPO.researchArea.eq(researchArea));
        }
        if (null!=verifyStatusEnum){
            query.where(qTeacherPO.verifyStatus.eq(verifyStatusEnum));
        }
//        if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)){
//            try {
//                query.where(qTeacherPO.gmtCreate.between(new SimpleDateFormat("yyyy-MM-dd").parse(startDate)),);
//            } catch (ParseException e) {
//                throw new BusinessException(ErrorCodeEumn.DATE_TIME_WRONG.getName());
//            }
//        }
        query.limit(page.getRows());
        query.offset(page.getFirstResult());
        int count = (int)query.fetchCount();
        List<JSONObject> resultList = new ArrayList<>();
        List<Tuple> tupleList = query.fetch();
        for (Tuple tuple : tupleList) {
            JSONObject result = new JSONObject();
            TeacherPO teacherPO = tuple.get(qTeacherPO);
            UserPO userPO = tuple.get(qUserPO);
            TeacherDTO teacherDTO = new TeacherDTO();
            BeanCopyUtil.copyPropertiesIgnoreNull(teacherPO,teacherDTO);
            teacherDTO.setTeacherId(teacherPO.getId());
            teacherDTO.setTeacherName(userPO.getName());
            teacherDTO.setEmail(userPO.getEmail());
            teacherDTO.setUsername(userPO.getUsername());
            teacherDTO.setTeacherMobile(userPO.getMobile());
            result.putAll(JSONObject.parseObject(JSON.toJSONString(teacherDTO)));
            resultList.add(result);
        }
        page.setCount(count);
        page.setResultList(resultList);
        return page;
    }
}
