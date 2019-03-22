package org.lilacseeking.video.videoapp.Model.PO;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
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
@Table(name = "user")
public class OrderPO extends BaseEntityPO{
    /**
     * 订单编号
     */
    @Column(name="course_id", nullable=false, length = 20, unique = true)
    private String courseId;
    /**
     * 订单编号
     */
    @Column(name="order_no", nullable=false, length = 20, unique = true)
    private String orderNo;

    /**
     * 订单流水号
     */
    @Column(name="inner_trade_no")
    private String innerTradeNo;

    /**
     * 支付方Id
     */
    @Column(name="pay_d", nullable=false, length = 50)
    private Long payId;

    /**
     * 收款方Id
     */
    @Column(name="seller_id")
    private Long sellerId;

    /**
     * 金额
     */
    @Column(name="amount", length = 20)
    private String amount;

    /**
     * 交易状态
     */
    @Column(name="trade_status", nullable=false, length = 50,unique = true)
    private String tradeStatus;

    /**
     * 支付方式
     */
    @Column(name="pay_method", nullable=false, unique = true,length = 11)
    private String payMethod;

}
