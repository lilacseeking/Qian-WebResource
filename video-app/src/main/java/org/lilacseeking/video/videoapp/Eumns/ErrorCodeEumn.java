package org.lilacseeking.video.videoapp.Eumns;

/**
 * @Auther: lilacseeking
 * @Date: 2018/10/21 00:44
 * @Description:
 */
public enum  ErrorCodeEumn {

    // 参数错误
    MOBILE_NOT_NULL("1001","手机号不能为空"),
    MOBILE_CODE_ERROR("1002","验证码错误"),
    PASSWORD_NOT_CORRECT("1003","密码输入错误"),
    NEW_PASSWORD_NOT_NULL("1004","新密码不可为空"),
    // 系统错误
    UNKNOWN_ERROR("2001","未知异常"),

    // 业务错误
    MOBILE_CODE_SEND_FAILED("3001","手机验证码发送失败"),
    // 视频错误
    VIDEO_TRANS_FAILED("4001","视频格式转换失败")





    ;


    private String code;
    private String name;

    ErrorCodeEumn (String code,String name){
        this.code = code;
        this.name = name;
    }


    public static String getErrorCodeEumnCode(String code) {
        if (code == null) {
            return null;
        }
        ErrorCodeEumn[] typeEnums = ErrorCodeEumn.values();
        for (ErrorCodeEumn typeEnum : typeEnums) {
            if (typeEnum.code.equals(code)) {
                return typeEnum.name;
            }
        }
        return null;
    }

    public static ErrorCodeEumn getErrorCodeEumn(String codeName) {
        if (codeName == null) {
            return null;
        }
        ErrorCodeEumn[] typeEnums = ErrorCodeEumn.values();
        for (ErrorCodeEumn typeEnum : typeEnums) {
            if (typeEnum.name.equals(codeName)) {
                return typeEnum;
            }
        }
        return null;
    }

    public static String getErrorCodeEumnName(String codeName) {
        if (codeName == null) {
            return null;
        }
        ErrorCodeEumn[] typeEnums = ErrorCodeEumn.values();
        for (ErrorCodeEumn typeEnum : typeEnums) {
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
