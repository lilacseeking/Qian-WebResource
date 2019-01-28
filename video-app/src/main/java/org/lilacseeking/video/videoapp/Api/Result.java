package org.lilacseeking.video.videoapp.Api;
import java.io.Serializable;
import java.util.List;

/**
 * 类BaseResult.java的实现描述：通用的执行结果
 *
 * @author lilacseeking 2018-7-25 14:00:15
 * 工具类应包含的信息
 * 返回数据 data T
 * 错误信息 errorParams List
 * 成功标记 success Boolean
 * 操作结果代码 code Srting
 * 错误代码 errCode String
 * 操作消息 message String
 * 动态错误码 dynamicCode String
 * 动态信息 dynamicMessage
 */
public class Result<T> implements Serializable {
    private static final long serialVersionUID = -2368446516546812379L;

    /**
     * 请求唯一ID
     */
    private String requestId;

    /**
     * result data
     */
    private T data;

    /**
     * 国际化以后前端需要根据code 和 这个param里的动态信息来构成错误信息
     *
     * @see The Instance {0} is out of date,the endDate is {1} !
     *      ["atx1","2015-01-01"]
     */
    private List<String> errorParams;

    /**
     * 是否是幂等返回
     */
    private Boolean tokenConflicted;

    /**
     * 成功标记
     */
    private boolean success;
    /**
     * 操作结果代码 如:LX_CREATE_ORDER_FAILED ,笼统的错误代码，由于openapi已经在使用，这里的错误码不能轻易改动
     */
    private String code;

    /**
     * 详细的错误code ,add 的，后续接入的 如果要细化错误code 展示，就使用这个。如：国际化翻译的错误code
     */
    private String errCode;

    /**
     * 操作消息
     */
    private String message;

    /**
     * pop调用中的可能使用的动态错误码
     */
    private String dynamicCode;

    /**
     * pop调用中可能使用的动态信息
     */
    private String dynamicMessage;

    public Result() {
    }

    public Result(T t, String requestId) {
        setSuccess(true);
        setData(t);
        setRequestId(requestId);
    }

    public Result(T t) {
        setSuccess(true);
        setData(t);
    }

    public Result(boolean isSuccess, T t, String requestId) {
        setSuccess(isSuccess);
        setData(t);
        setRequestId(requestId);
    }

    public Result(boolean isSuccess, String requestId) {
        setSuccess(isSuccess);
        setRequestId(requestId);
    }

    public Result(String code, String message, String requestId) {
        setSuccess(false);
        setCode(code);
        setMessage(message);
        setRequestId(requestId);
    }

    public Result(String code, String message) {
        setSuccess(false);
        setCode(code);
        setMessage(message);
    }

    public Result(boolean isSuccess, String code, String message, T t) {
        setSuccess(isSuccess);
        setCode(code);
        setMessage(message);
        setData(t);
    }

    public Result(boolean isSuccess, String code, String message) {
        setSuccess(isSuccess);
        setCode(code);
        setMessage(message);
    }

    public Result(boolean isSuccess, String message, T t) {
        setSuccess(isSuccess);
        setMessage(message);
        setData(t);
    }

    /**
     * @return Result<T>
     */
    public static <T> Result<T> create() {
        return new Result<T>();
    }

    /**
     * 新增快速创建Result的方法,不用谢我,请叫我雷锋... ^.^ -Shaka create Result<T> Object for
     * successful cases.
     *
     * @param data result data
     * @return Result<T>
     */
    public static <T> Result<T> create(T data) {
        Result<T> result = Result.create();
        result.setSuccess(true);
        result.setData(data);
        return result;
    }

    /**
     * 接口调用成功时也需要code和message的场景
     *
     * @param data
     * @param code
     * @param message
     * @return
     */
    public static <T> Result<T> create(T data, String code, String message) {
        Result<T> result = Result.create();
        result.setSuccess(true);
        result.setData(data);
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    /**
     * 接口调用成功时也需要code和message的场景，添加动态code 和 动态message信息
     *
     * @param data
     * @param code
     * @param message
     * @param requestId
     * @param dynamicCode
     * @param dynamicMessage
     * @return
     */
    public static <T> Result<T> create(T data, String code, String message, String requestId, String dynamicCode,
                                       String dynamicMessage) {
        Result<T> result = Result.create();
        result.setSuccess(true);
        result.setData(data);
        result.setCode(code);
        result.setMessage(message);
        result.setRequestId(requestId);
        result.setDynamicCode(dynamicCode);
        result.setDynamicMessage(dynamicMessage);
        return result;
    }

    /**
     * 接口调用成功时也需要code和message的场景
     *
     * @param data
     * @param code
     * @param message
     * @param requestId
     * @return
     */
    public static <T> Result<T> create(T data, String code, String message, String requestId) {
        Result<T> result = Result.create();
        result.setSuccess(true);
        result.setData(data);
        result.setCode(code);
        result.setMessage(message);
        result.setRequestId(requestId);
        return result;
    }

    /**
     * create Result<T> Object for unsuccessful cases.
     *
     * @param code result code
     * @param message result code
     * @return Result<T>
     */
    public static <T> Result<T> create(String code, String message) {
        Result<T> result = Result.create();
        result.setSuccess(false);
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    /**
     * create Result<T> Object for unsuccessful cases.
     *
     * @param code result code
     * @param message result code
     * @return Result<T>
     */
    public static <T> Result<T> create(String code, String message, String requestId) {
        Result<T> result = Result.create();
        result.setSuccess(false);
        result.setCode(code);
        result.setMessage(message);
        result.setRequestId(requestId);
        return result;
    }

    /**
     * create Result<T> Object for unsuccessful cases.
     *
     * @param code result code
     * @param message result code
     * @return Result<T>
     */
    public static <T> Result<T> create(String code, String message, String requestId, String dynamicMessage) {
        Result<T> result = Result.create();
        result.setSuccess(false);
        result.setCode(code);
        result.setMessage(message);
        result.setRequestId(requestId);
        result.setDynamicMessage(dynamicMessage);
        return result;
    }

    /**
     * @return the success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * @param success the success to set
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public List<String> getErrorParams() {
        return errorParams;
    }

    public void setErrorParams(List<String> errorParams) {
        this.errorParams = errorParams;
    }

    public Boolean getTokenConflicted() {
        return tokenConflicted;
    }

    public void setTokenConflicted(Boolean tokenConflicted) {
        this.tokenConflicted = tokenConflicted;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getDynamicCode() {
        return dynamicCode;
    }

    public void setDynamicCode(String dynamicCode) {
        this.dynamicCode = dynamicCode;
    }

    public String getDynamicMessage() {
        return dynamicMessage;
    }

    public void setDynamicMessage(String dynamicMessage) {
        this.dynamicMessage = dynamicMessage;
    }

    @Override
    public String toString() {
        return "Result [requestId=" + requestId + ", data=" + data + ", errorParams=" + errorParams
                + ", tokenConflicted=" + tokenConflicted + ", success=" + success + ", code=" + code + ", errCode="
                + errCode + ", message=" + message + ", dynamicCode=" + dynamicCode + ", dynamicMessage="
                + dynamicMessage + "]";
    }

}