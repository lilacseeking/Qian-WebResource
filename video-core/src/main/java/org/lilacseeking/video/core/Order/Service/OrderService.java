package org.lilacseeking.video.core.Order.Service;

import com.alibaba.fastjson.JSONObject;
import org.lilacseeking.video.core.Order.Model.OrderPO;
import org.lilacseeking.video.infrastructure.Model.DTO.CourseDTO;
import org.lilacseeking.video.infrastructure.Model.DTO.OrderDTO;
import org.lilacseeking.video.infrastructure.Model.DTO.UserBasicDTO;
import org.lilacseeking.video.infrastructure.utils.Page;


/**
 * @Auther: lilacseeking
 * @Date: 2019/3/23 21:44
 * @Description:
 */
public interface OrderService {
    /**
     * 获取订单列表
     * @return
     */
    Page getOrderList(JSONObject filter);

    /**
     * 创建订单
     * @return
     */
    OrderDTO createOrder(CourseDTO courseDTO, UserBasicDTO userDTO);

    /**
     * 支付订单
     * @return
     */
    OrderDTO payOrder(OrderDTO orderDTO);

    /**
     * 关闭订单
     * @return
     */
    OrderDTO closeOrder(OrderDTO orderDTO);

    /**
     * 根据订单号获取订单
     * @param orderDTO
     * @return
     */
    OrderPO getOrderPOByOrderNo(OrderDTO orderDTO);
}
