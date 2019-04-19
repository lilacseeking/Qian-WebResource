package org.lilacseeking.video.app.Eumns;

/**
 * @Author： lvming
 * @Date： Created in 18:02 2019/1/31
 * @Description：
 * @Modified By：
 * @Version:
 */
public enum  ProcessEnum {

    // 未开始、正在进行、失败、暂停、
    PENDING("pending","待处理"),
    START("start","开始"),
    PROCESSING("processing","处理中"),
    PAUSE("pause","暂停"),
    SUCCESS("success","成功"),
    FAILURE("fail","失败");
    private String code;
    private String name;

    ProcessEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
