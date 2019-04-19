package org.lilacseeking.video.videoapp.Model.PO;

import lombok.Data;
import lombok.experimental.Accessors;
import org.lilacseeking.video.videoapp.Eumns.TradeStatusEnum;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Auther: lilacseeking
 * @Date: 2019/3/16 21:51
 * @Description:
 */
@Data
//@Builder
@Accessors(chain = true)
@Entity
@Table(name = "course_order")
public class OrderPO extends BaseEntityPO{
    /**
     * 课程编号
     */
    @Column(name="course_id", nullable=false)
    private Long courseId;
    /**
     * 订单编号
     */
    @Column(name="order_no", nullable=false, length = 32, unique = true)
    private String orderNo;

    /**
     * 订单流水号
     */
    @Column(name="inner_trade_no")
    private String innerTradeNo;

    /**
     * 支付方Id
     */
    @Column(name="pay_id", nullable=false)
    private Long payId;

    /**
     * 收款方Id
     */
    @Column(name="seller_id", nullable=false)
    private Long sellerId;

    /**
     * 金额
     */
    @Column(name="amount", columnDefinition = "decimal(15,2) default 0.00")
    private BigDecimal amount;

    /**
     * 交易状态 默认为未支付
     */
    @Column(name="trade_status", nullable=false, length = 50)
    @Enumerated(EnumType.STRING)
    private TradeStatusEnum tradeStatus = TradeStatusEnum.NON_PAY;

    /**
     * 支付方式
     */
    @Column(name="pay_method", nullable=false,length = 32)
    private String payMethod;

}
