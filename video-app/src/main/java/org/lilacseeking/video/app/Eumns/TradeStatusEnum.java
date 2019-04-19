package org.lilacseeking.video.app.Eumns;

/**
 * @Auther: lilacseeking
 * @Date: 2019/3/23 23:04
 * @Description: 订单状态枚举值
 */
public enum TradeStatusEnum {
    NON_PAY("未支付"),
    PAY_SUCCESS("支付成功"),
    PAY_ING("正在支付"),
    PAY_FAILED("支付失败"),
    PAY_CLOSE("支付关闭");
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    TradeStatusEnum(String name) {
        this.name = name;
    }
}
