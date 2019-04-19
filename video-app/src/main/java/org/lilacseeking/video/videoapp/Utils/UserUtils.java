package org.lilacseeking.video.videoapp.Utils;

import org.lilacseeking.video.videoapp.Eumns.ErrorCodeEumn;
import org.lilacseeking.video.videoapp.Exception.BusinessException;
import org.lilacseeking.video.videoapp.Model.DTO.UserBasicInfoDTO;

/**
 * @Auther: lilacseeking
 * @Date: 2019/3/28 21:17
 * @Description: 用户工具类
 */
public class UserUtils {

    public static UserBasicInfoDTO userBasicInfoDTO = null;

    // 获取用户Session
    public static UserBasicInfoDTO getUserSession(){
        return userBasicInfoDTO;
    }

    // 登录过滤URL
    public static Boolean filterUrl(String url){
        switch (url){
            default : return false;
            case "/user/loginByPwd" :
            case "/user/mobileLogin" :
            case "/user/sendMobileCode" :
            case "/course/getCourseInfo" :
            case "/course/getAllCourse" :
            case "/content/getContentList" :
            case "/user/register" :
            case "/ppt/getPPTList" : return true;
        }
    }
}
