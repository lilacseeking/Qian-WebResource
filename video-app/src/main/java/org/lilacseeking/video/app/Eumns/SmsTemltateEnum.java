package org.lilacseeking.video.app.Eumns;

/**
 * @Auther: lilacseeking
 * @Date: 2018/11/6 23:45
 * @Description:
 */
public enum SmsTemltateEnum {
    RESET_PWD("SMS_149400110","重置密码"),
    USER_LOGIN("SMS_149405100","用户登录"),
    USER_REGISTER("SMS_149400104","注册验证");

    private String code;
    private String name;

    SmsTemltateEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getSmsTemltateEnumCode(String code) {
        if (code == null) {
            return null;
        }
        SmsTemltateEnum[] typeEnums = SmsTemltateEnum.values();
        for (SmsTemltateEnum typeEnum : typeEnums) {
            if (typeEnum.code.equals(code)) {
                return typeEnum.name;
            }
        }
        return null;
    }

    public static SmsTemltateEnum getSmsTemltateEnum(String codeName) {
        if (codeName == null) {
            return null;
        }
        SmsTemltateEnum[] typeEnums = SmsTemltateEnum.values();
        for (SmsTemltateEnum typeEnum : typeEnums) {
            if (typeEnum.name.equals(codeName)) {
                return typeEnum;
            }
        }
        return null;
    }

    public static String getSmsTemltateEnumName(String codeName) {
        if (codeName == null) {
            return null;
        }
        SmsTemltateEnum[] typeEnums = SmsTemltateEnum.values();
        for (SmsTemltateEnum typeEnum : typeEnums) {
            if (typeEnum.name.equals(codeName)) {
                return typeEnum.name;
            }
        }
        return null;
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
