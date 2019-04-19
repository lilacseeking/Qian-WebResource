package org.lilacseeking.video.app.Eumns;

/**
 * @Auther: lilacseeking
 * @Date: 2018/10/21 00:44
 * @Description:
 */
public enum  ErrorCodeEumn {

    //
    HAVE_NOT_LOGIN_IN("0003","未登录"),
    HAVE_NOT_REGISTER("0004","未注册"),
    // 参数错误
    MOBILE_NOT_NULL("1001","手机号不能为空"),
    MOBILE_CODE_ERROR("1002","验证码错误"),
    PASSWORD_NOT_CORRECT("1003","密码输入错误"),
    NEW_PASSWORD_NOT_NULL("1004","新密码不可为空"),
    PAGE_INFO_FORMAT_WRONG("1005","分页信息输入错误"),
    MOBILE_EMAIL_USERNAME_NOT_NULL_ALL("1006","手机号邮箱用户名不能同时为空"),
    USER_ID_NOT_NULL("1007","用户ID不能为空"),
    TEACHER_ID_NOT_NULL("1008","教师ID不能为空"),
    VERIFY_STATUS_NOT_NULL("1009","审核状态不能为空"),
    DATE_TIME_WRONG("1010","日期时间格式错误"),
    USER_NOT_EXIST("1011","用户不存在"),
    USER_NOT_TEACHER_OR_ADMIN("1012","用户不存在"),
    // 系统错误
    UNKNOWN_ERROR("2001","未知异常"),

    // 业务错误
    MOBILE_CODE_SEND_FAILED("3001","手机验证码发送失败"),
    // 视频错误
    VIDEO_TRANS_FAILED("4001","视频格式转换失败"),
    // 视频上传
    VIDEO_UPLOAD_FAIL("5001","视频上传失败"),
    VIDEO_UPLOAD_RATE_FAIL("5002","视频上传进度获取失败"),
    // 课程相关
    COURSE_NOT_NULL("6001","课程不允许为空"),
    COURSE_ID_NOT_NULL("6002","课程ID不允许为空"),
    CONTENT_LIST_NOT_NULL("6003","章节列表不允许为空"),
    COURSE_ID_AND_USER_ID_NOT_NULL("6004","用户Id和课程Id不允许为空"),
    COURSE_THUMBNAIL_UPLOAD_FAIL("6005","课程缩略图上传失败"),
    // 订单相关
    ORDER_NO_NOT_NULL("7001","章节列表不允许为空");


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
