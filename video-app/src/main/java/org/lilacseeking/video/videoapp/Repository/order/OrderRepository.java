package org.lilacseeking.video.videoapp.Repository.order;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import org.apache.commons.lang.StringUtils;
import org.lilacseeking.video.videoapp.Model.DTO.OrderDTO;
import org.lilacseeking.video.videoapp.Model.PO.*;
import org.lilacseeking.video.videoapp.Repository.AbstractRepository;
import org.lilacseeking.video.videoapp.Eumns.TradeStatusEnum;
import org.lilacseeking.video.videoapp.Utils.Page;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: lilacseeking
 * @Date: 2019/3/23 21:51
 * @Description:
 */
@Repository
public class OrderRepository extends AbstractRepository<OrderPO> {

    private QOrderPO qOrderPO = QOrderPO.orderPO;
    private QUserPO qUserPO = QUserPO.userPO;
    private QVideoCoursePO qVideoCoursePO = QVideoCoursePO.videoCoursePO;
    /**
     * 保存
     * @param orderPO
     * @return
     */
    @Transactional
    public OrderPO saveOrUpdate(OrderPO orderPO){
        return entityManager.merge(orderPO);
    }

    /**
     * 获取订单列表
     * @param filter
     * @param page
     * @return
     */
    public Page getOrderList(JSONObject filter, Page page){
        // 参数封装
        Long id = filter.getLong("id");
        Long courseId = filter.getLong("courseId");
        Long payId = filter.getLong("payId");
        String orderNo = filter.getString("orderNo");
        String innerTradeNo = filter.getString("innerTradeNo");
        String tradeStatus = filter.getString("tradeStatus");
        String payMethod = filter.getString("payMethod");
        String payName = filter.getString("payName");
        String courseName = filter.getString("courseName");
        TradeStatusEnum tradeStatusEnum = null;
        tradeStatusEnum = StringUtils.isBlank(tradeStatus)? null : TradeStatusEnum.valueOf(tradeStatus);

        JPAQuery query = new JPAQuery<>(entityManager).from(qUserPO,qVideoCoursePO,qOrderPO);
        query.select(qUserPO,qVideoCoursePO,qOrderPO);
        query.where(qOrderPO.courseId.eq(qVideoCoursePO.id));
        query.where(qOrderPO.achieve.eq(0));
        query.where(qOrderPO.payId.eq(qUserPO.id));
        //条件查询
        if (null != id){
            query.where(qOrderPO.id.eq(id));
        }
        if (null != courseId){
            query.where(qOrderPO.courseId.eq(courseId));
        }
        if (null != payId){
            query.where(qOrderPO.payId.eq(payId));
        }
        if (null !=tradeStatusEnum){
            query.where(qOrderPO.tradeStatus.eq(tradeStatusEnum));
        }
        if (StringUtils.isNotBlank(orderNo)){
            query.where(qOrderPO.orderNo.eq(orderNo));
        }
        if (StringUtils.isNotBlank(innerTradeNo)){
            query.where(qOrderPO.innerTradeNo.eq(innerTradeNo));
        }
        if (StringUtils.isNotBlank(payMethod)){
            query.where(qOrderPO.payMethod.eq(payMethod));
        }
        if (StringUtils.isNotBlank(payName)){
            query.where(qUserPO.name.like("%" + payName + "%"));
            query.where(qUserPO.id.eq(qOrderPO.payId));
        }
        if (StringUtils.isNotBlank(courseName)){
            query.where(qVideoCoursePO.name.like("%" + courseName + "%"));
            query.where(qOrderPO.courseId.eq(qVideoCoursePO.id));
        }
        query.limit(page.getRows());
        query.offset(page.getFirstResult());
        query.orderBy(qOrderPO.gmtCreate.desc());
        int count = (int)query.fetchCount();
        List<JSONObject> resultList = new ArrayList<>();
        List<Tuple> tupleList = query.fetch();
        for (Tuple tuple : tupleList){
            JSONObject result = new JSONObject();
            OrderPO orderPO = tuple.get(qOrderPO);
            UserPO userPO = tuple.get(qUserPO);
            VideoCoursePO videoCoursePO = tuple.get(qVideoCoursePO);
            result.putAll(JSONObject.parseObject(JSON.toJSONString(orderPO)));
            result.put("courseName",videoCoursePO.getName());
            result.put("payName",userPO.getName());
            resultList.add(result);
        }
        // 分页查询
        page.setResultList(resultList);
        page.setCount(count);
        return page;
    }

    /**
     * 获取根据Id订单
     * @param orderPO
     * @return
     */
    public OrderPO getOrderPO(OrderPO orderPO){
        JPAQuery query = new JPAQuery<>(entityManager).from(qOrderPO);
        query.where(qOrderPO.id.eq(orderPO.getId()));
        query.where(qOrderPO.achieve.eq(0));
        return (OrderPO)query.fetchFirst();
    }

    /**
     * 根据订单号获取订单
     * @param orderDTO
     * @return
     */
    public OrderPO getOrderPOByOrderNo(OrderDTO orderDTO){
        JPAQuery query = new JPAQuery<>(entityManager).from(qOrderPO);
        query.where(qOrderPO.orderNo.eq(orderDTO.getOrderNo()));
        query.where(qOrderPO.achieve.eq(0));
        return (OrderPO)query.fetchFirst();
    }

}
