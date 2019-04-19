package org.lilacseeking.video.videoapp.Controller.order;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import org.lilacseeking.video.videoapp.Model.DTO.CourseDTO;
import org.lilacseeking.video.videoapp.Model.DTO.OrderDTO;
import org.lilacseeking.video.videoapp.Model.DTO.UserBasicDTO;
import org.lilacseeking.video.videoapp.Service.order.OrderService;
import org.lilacseeking.video.videoapp.Utils.Page;
import org.lilacseeking.video.videoapp.Utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: lilacseeking
 * @Date: 2019/3/23 21:12
 * @Description: 订单Controller
 */
@RestController
@RequestMapping(value = "/order")
public class OrderController {

    @Autowired
    private ResponseUtil responseUtil;
    @Autowired
    private OrderService orderService;

    /**
     * 获取订单列表
     */
    @ApiOperation(value = "获取订单列表" ,notes = "获取订单列表")
    @RequestMapping(value = "/getOrderList" ,method = RequestMethod.POST)
    public void getOrderList(@RequestBody JSONObject filter, HttpServletResponse res){
        Page result = orderService.getOrderList(filter);
        responseUtil.putSuccess(result);
        responseUtil.writeMessage(res);
    }

    /**
     * 创建订单
     * @param filter
     * @param res
     */
    @ApiOperation(value = "创建订单" ,notes = "创建订单")
    @RequestMapping(value = "/createOrder" ,method = RequestMethod.POST)
    public void createOrder(@RequestBody JSONObject filter, HttpServletResponse res){
        CourseDTO courseDTO;
        UserBasicDTO userDTO;
        userDTO = JSONObject.parseObject(filter.toJSONString(),UserBasicDTO.class);
        courseDTO = JSONObject.parseObject(filter.toJSONString(),CourseDTO.class);
        courseDTO.setId(filter.getLong("courseId"));
        userDTO.setId(filter.getLong("userId"));
        OrderDTO order = orderService.createOrder(courseDTO, userDTO);
        responseUtil.putSuccess(order);
        responseUtil.writeMessage(res);
    }

    /**
     * 支付订单
     * @param orderDTO
     * @param res
     */
    @ApiOperation(value = "支付订单" ,notes = "支付订单")
    @RequestMapping(value = "/payOrder" ,method = RequestMethod.POST)
    public void payOrder(@RequestBody OrderDTO orderDTO, HttpServletResponse res){
        orderDTO = orderService.payOrder(orderDTO);
        responseUtil.putSuccess(orderDTO);
        responseUtil.writeMessage(res);
    }

    /**
     * 关闭订单
     * @param orderDTO
     * @param res
     */
    @ApiOperation(value = "关闭订单" ,notes = "关闭订单")
    @RequestMapping(value = "/closeOrder" ,method = RequestMethod.POST)
    public void closeOrder(@RequestBody OrderDTO orderDTO, HttpServletResponse res){
        orderDTO = orderService.closeOrder(orderDTO);
        responseUtil.putSuccess(orderDTO);
        responseUtil.writeMessage(res);
    }
}
