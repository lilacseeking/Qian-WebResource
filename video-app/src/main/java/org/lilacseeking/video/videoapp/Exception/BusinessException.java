package org.lilacseeking.video.videoapp.Exception;

/**
 * @Auther: lilacseeking
 * @Date: 2018/10/21 00:42
 * @Description:
 */
public class BusinessException extends RuntimeException {

    private int code;

    private Object content;

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }


    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }


    public int getCode() {
        return code;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
