package org.lilacseeking.video.videoapp.Repository.ppt;

import com.alibaba.fastjson.JSONObject;
import com.querydsl.jpa.impl.JPAQuery;
import org.apache.commons.lang.StringUtils;
import org.lilacseeking.video.videoapp.Eumns.YesOrNoEnum;
import org.lilacseeking.video.videoapp.Model.PO.PPTPO;
import org.lilacseeking.video.videoapp.Model.PO.QPPTPO;
import org.lilacseeking.video.videoapp.Repository.AbstractRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * @Auther: lilacseeking
 * @Date: 2019/3/24 10:24
 * @Description:
 */
@Repository
public class PPTRepository extends AbstractRepository {

    private QPPTPO qpptpo = QPPTPO.pPTPO;

    /**
     * 获取PPT列表
     * @param filter
     * @return
     */
    public List<PPTPO> getPPTList(JSONObject filter){
        String isUse = filter.getString("isUse");
        YesOrNoEnum isUseEnum = StringUtils.isBlank(isUse) ? null : YesOrNoEnum.valueOf(isUse);
        JPAQuery query = new JPAQuery<>(entityManager).from(qpptpo);
        query.where(qpptpo.achieve.eq(0));
        if (null != isUseEnum){
            query.where(qpptpo.isUse.eq(isUseEnum));
        }
        query.orderBy(qpptpo.gmtCreate.desc());
        return (List<PPTPO>)query.fetch();
    }
}
