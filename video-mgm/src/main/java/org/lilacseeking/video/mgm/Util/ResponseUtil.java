package org.lilacseeking.video.mgm.Util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.TreeMap;

/**
 * 处理结果通知辅助类
 * {
 * "code" : 0, 0成功,其它CODE失败
 * "msg" : "",
 * "content" : "" or {} or []
 * }
 *
 * @author tangshd
 */
@Component
public class ResponseUtil {
    private static final Logger logger = LoggerFactory.getLogger(ResponseUtil.class);
    private static final ThreadLocal<LinkedHashMap<String, Object>> TL_MESSAGE_MAP = new ThreadLocal<LinkedHashMap<String, Object>>();
    private static final ThreadLocal<TreeMap<String, Object>> TL_CONTENT_MAP = new ThreadLocal<TreeMap<String, Object>>();

    private String defalutSuccess;
    private String defalutException;

    @PostConstruct
    public void initDefault() {
        this.defalutSuccess = "success";
        this.defalutException = "error";
    }

    /**
     * 返回 output.content thread local Map
     *
     * @return
     */
    private TreeMap<String, Object> getContentMap() {

        TreeMap<String, Object> ss = TL_CONTENT_MAP.get();
        if (ss == null) {
            ss = new TreeMap<>();
            TL_CONTENT_MAP.set(ss);
        }
        return ss;
    }

    /**
     * 返回output thread local map
     *
     * @return
     */
    private LinkedHashMap<String, Object> getMessageMap() {
        LinkedHashMap<String, Object> lhm = TL_MESSAGE_MAP.get();
        if (null == lhm) {
            lhm = new LinkedHashMap<>();
            TL_MESSAGE_MAP.set(lhm);
        }
        return lhm;
    }

    /**
     * 向{code:0,msg:"",content: {}}中的content添加返回值
     */
    public ResponseUtil addContent(String key, Object value) {
        TreeMap<String, Object> ss = getContentMap();
        ss.put(key, value);
        return this;
    }

    /**
     * 清理{code:0,msg:"",content: {}}中的content返回值
     *
     * @return
     */
    public ResponseUtil clearContent() {
        TreeMap<String, Object> ss = getContentMap();
        ss.clear();
        return this;
    }

    /**
     * 组装返回客户断的消息体
     *
     * @param code
     * @param messg
     * @param content
     */
    public void put(int code, String messg, Object content) {
        try {
            LinkedHashMap<String, Object> resultMap = getMessageMap();
            resultMap.put("code", code);
            resultMap.put("msg", messg);
            resultMap.put("content", content);
        } catch (Throwable th) {
            logger.warn("异常信息为: ", th);
        }
    }
    /**
     * 组装返回客户断的消息体
     *
     * @param code
     * @param message
     * @param content
     */
    public void put(String code, String message, Object content) {
        try {
            LinkedHashMap<String, Object> resultMap = getMessageMap();
            resultMap.put("code", code);
            resultMap.put("errorMsg", message);
            resultMap.put("content", content);
        } catch (Throwable th) {
            logger.warn("异常信息为: ", th);
        }
    }
    /**
     * 默认成功信息
     */
    public void putSuccess() {
        Object content = "";
        TreeMap<String, Object> treeMap = getContentMap();
        if (null != treeMap && treeMap.size() > 0) {
            content = treeMap;
        }
        put(0, defalutSuccess, content);
    }

    /**
     * 成功信息
     *
     * @param content 内容
     */
    public void putSuccess(Object content) {
        put(0, defalutSuccess, content);
    }

    /**
     * 成功信息
     *
     * @param msg     描述
     * @param content 内容
     */
    public void putSuccess(String msg, Object content) {
        put(0, msg, content);
    }

    /**
     * 默认失败
     */
    public void putError() {
        Object content = "";
        TreeMap<String, Object> treeMap = getContentMap();
        if (null != treeMap && treeMap.size() > 0) {
            content = treeMap;
        }
        put(1, defalutException, content);
    }

    /**
     * 失败信息
     *
     * @param content 内容
     */
    public void putError(Object content) {
        put(1, defalutException, content);
    }

    /**
     * 失败信息
     *
     * @param msg     描述
     * @param content 内容
     */
    public void putError(String msg, Object content) {
        putError(1, msg, content);
    }

    /**
     * 返回
     * @param errorCode 错误代码
     * @param errorMsg 错误信息
     */
    public void putErrorStr(String errorId,String errorCode, String errorMsg) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("errorId",errorId);
        jsonObject.put("message",errorMsg);
        putError(1, errorCode, jsonObject);
    }

    /**
     * 失败信息
     *
     * @param code    代码
     * @param msg     描述
     * @param content 内容
     */
    public void putError(Integer code, String msg, Object content) {
        put(code, msg, content);
    }

    /**
     * 获得向客户端输出的处理结果信息
     *
     * @return
     */
    public String getMessage(SerializerFeature... features) {
        LinkedHashMap<String, Object> resultMap = getMessageMap();
        return (null != resultMap && resultMap.size() > 0) ? JSON.toJSONString(resultMap, features) : null;
    }

    public String getMessage() {
        LinkedHashMap<String, Object> resultMap = getMessageMap();
        return (null != resultMap && resultMap.size() > 0) ? JSON.toJSONString(resultMap) : null;
    }


    public Object getJSON() {
        LinkedHashMap<String, Object> resultMap = getMessageMap();
        return (null != resultMap && resultMap.size() > 0) ? JSON.toJSON(resultMap) : null;
    }

    /**
     * 向客户端写入处理结果, 默认输出json String
     *
     */
    public void writeMessage(HttpServletResponse response) {
        writeMessage(response, false);
    }

    /**
     * 向客户端写入处理结果, 输出json Object
     *
     */
    public void writeMessage(HttpServletResponse response, boolean isWriteJsonObject ) {
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
            // 设置ContentType
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            // 避免乱码
            response.setCharacterEncoding("UTF-8");
            //客户端不用CACHE
            response.setHeader("Cache-Control", "no-cache, must-revalidate");
            //输出到客户端
            pw.print( isWriteJsonObject? getJSON() : getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(pw);
            //清理本线程的THREADLOCAL变量
            TL_CONTENT_MAP.remove();
            TL_MESSAGE_MAP.remove();
        }
    }
}