package org.lilacseeking.video.core.Order.Service.Impl;

import com.alibaba.fastjson.JSONObject;
import org.lilacseeking.video.core.Course.Model.VideoCoursePO;
import org.lilacseeking.video.core.Course.Service.CourseUserService;
import org.lilacseeking.video.core.Course.Service.VideoCourseService;
import org.lilacseeking.video.core.Order.Model.OrderPO;
import org.lilacseeking.video.core.Order.Repository.OrderRepository;
import org.lilacseeking.video.core.Order.Service.OrderService;
import org.lilacseeking.video.core.User.Model.UserPO;
import org.lilacseeking.video.core.User.Service.UserService;
import org.lilacseeking.video.infrastructure.Model.DTO.CourseDTO;
import org.lilacseeking.video.infrastructure.Model.DTO.CourseUserDTO;
import org.lilacseeking.video.infrastructure.Model.DTO.OrderDTO;
import org.lilacseeking.video.infrastructure.Model.DTO.UserBasicDTO;
import org.lilacseeking.video.infrastructure.enums.ErrorCodeEumn;
import org.lilacseeking.video.infrastructure.enums.TradeStatusEnum;
import org.lilacseeking.video.infrastructure.enums.YesOrNoEnum;
import org.lilacseeking.video.infrastructure.utils.BeanCopyUtil;
import org.lilacseeking.video.infrastructure.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import java.math.BigDecimal;

/**
 * @Auther: lilacseeking
 * @Date: 2019/3/23 21:45
 * @Description:
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
//    @Autowired
//    private RedisService redisService;
    @Autowired
    private UserService userService;
    @Autowired
    private VideoCourseService videoCourseService;
    @Autowired
    private CourseUserService courseUserService;
    /**
     * 获取订单列表
     *
     * @param filter
     * @return
     */
    @Override
    public Page getOrderList(JSONObject filter) {
        Integer page = filter.getInteger("page");
        Integer rows = filter.getInteger("rows");
        Assert.isTrue(page!=null && rows!=null,ErrorCodeEumn.PAGE_INFO_FORMAT_WRONG.getName());
        return orderRepository.getOrderList(filter,new Page(page,rows));
    }

    /**
     * 创建订单
     *
     * @param courseDTO
     * @param userDTO
     * @return
     */
    @Override
    public OrderDTO createOrder(CourseDTO courseDTO, UserBasicDTO userDTO) {
        // 获取用户信息
        UserPO userInfo = userService.getUserInfo(userDTO);
        // 获取课程信息
        VideoCoursePO courseInfo = videoCourseService.getCourseInfo(courseDTO);
        // 参数校验
        Assert.isTrue(null!=userInfo,ErrorCodeEumn.USER_ID_NOT_NULL.getName());
        Assert.isTrue(null!=courseInfo,ErrorCodeEumn.COURSE_ID_NOT_NULL.getName());
        // 用户信息，课程信息
        OrderPO orderPO = new OrderPO();
        orderPO.setAmount(courseInfo.getPrice().multiply(courseInfo.getDiscount()));
        orderPO.setPayId(userInfo.getId());
        // TODO 工程结构修改
//        orderPO.setInnerTradeNo(redisService.getUuidCode().replace("-",""));
        orderPO.setPayMethod("BALANCE_PAY");
        orderPO.setSellerId(1L);
        orderPO.setCourseId(courseInfo.getId());
//        orderPO.setOrderNo(redisService.getUuidCode().replace("-",""));
        OrderPO result = orderRepository.saveOrUpdate(orderPO);
        OrderDTO orderDTO = new OrderDTO();
        BeanCopyUtil.copyPropertiesIgnoreNull(result,orderDTO);
        return orderDTO;
    }

    /**
     * 支付订单
     *
     * @param orderDTO
     * @return
     */
    @Override
    public OrderDTO payOrder(OrderDTO orderDTO) {
        OrderPO orderPO = new OrderPO();
        BeanCopyUtil.copyPropertiesIgnoreNull(orderDTO,orderPO);
        orderPO = orderRepository.getOrderPOByOrderNo(orderDTO);
        Assert.isTrue(null != orderPO,ErrorCodeEumn.ORDER_NO_NOT_NULL.getName());
        orderPO.setTradeStatus(TradeStatusEnum.PAY_SUCCESS);
        orderPO = orderRepository.saveOrUpdate(orderPO);
        // 保存课程用户关系
        CourseUserDTO courseUserDTO = new CourseUserDTO();
        courseUserDTO = courseUserDTO.setUserId(orderPO.getPayId()).setCourseId(orderPO.getCourseId()).setCompletion(BigDecimal.ZERO)
        .setIsFinish(YesOrNoEnum.N);
        courseUserService.addCourseUser(courseUserDTO);
        BeanCopyUtil.copyPropertiesIgnoreNull(orderPO,orderDTO);
        return orderDTO;
    }

    /**
     * 关闭订单
     *
     * @param orderDTO
     * @return
     */
    @Override
    public OrderDTO closeOrder(OrderDTO orderDTO) {
        OrderPO orderPO = new OrderPO();
        BeanCopyUtil.copyPropertiesIgnoreNull(orderDTO,orderPO);
        orderPO = orderRepository.getOrderPOByOrderNo(orderDTO);
        Assert.isTrue(null != orderPO,ErrorCodeEumn.ORDER_NO_NOT_NULL.getName());
        orderPO.setTradeStatus(TradeStatusEnum.PAY_CLOSE);
        orderPO = orderRepository.saveOrUpdate(orderPO);
        BeanCopyUtil.copyPropertiesIgnoreNull(orderPO,orderDTO);
        return orderDTO;
    }

    /**
     * 根据订单号获取订单
     *
     * @param orderDTO
     * @return
     */
    @Override
    public OrderPO getOrderPOByOrderNo(OrderDTO orderDTO) {
        return orderRepository.getOrderPOByOrderNo(orderDTO);
    }
}
