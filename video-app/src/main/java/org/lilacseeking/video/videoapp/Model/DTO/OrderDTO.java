package org.lilacseeking.video.videoapp.Model.DTO;

import lombok.Data;
import lombok.experimental.Accessors;
import org.lilacseeking.video.videoapp.Eumns.TradeStatusEnum;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Auther: lilacseeking
 * @Date: 2019/3/23 21:20
 * @Description: 订单DTO
 */
@Data
@Accessors(chain = true)
public class OrderDTO {

    /**
     * 订单Id
     */
    private Long id;
    /**
     * 创建日期
     */
    private Date gmtCreate;
    /**
     * 课程编号
     */
    private Long courseId;
    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 订单流水号
     */
    private String innerTradeNo;

    /**
     * 支付方Id
     */
    private Long payId;

    /**
     * 收款方Id
     */
    private Long sellerId;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 交易状态
     */
    private TradeStatusEnum tradeStatus;

    /**
     * 支付方式
     */
    private String payMethod;
}
