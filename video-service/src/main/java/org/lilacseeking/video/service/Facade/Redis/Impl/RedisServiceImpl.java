package org.lilacseeking.video.service.Facade.Redis.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.lilacseeking.video.app.Model.DTO.CourseForHomeDTO;
import org.lilacseeking.video.app.Model.DTO.LoginDTO;
import org.lilacseeking.video.app.Model.DTO.UserBasicInfoDTO;
import org.lilacseeking.video.app.Service.RedisService;
import org.redisson.api.RBucket;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: lilacseeking
 * @Date: 2018/10/30 20:53
 * @Description:
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedissonClient redissonClient;

    // 保存验证码
    public Boolean saveSmsCode(String mobile,String code){
        RMap<String, String> map = redissonClient.getMap(QIAN_MANAGE_SMS_CODE + "-" + mobile);
        map.fastPut(mobile, code);
        // 开发环境验证码有效时间为1天
        map.expire(1440, TimeUnit.MINUTES);
        return true;
    }

    // 查询验证码
    public String getSmsCode(String mobile){
        RMap<String, String> map = redissonClient.getMap(QIAN_MANAGE_SMS_CODE + "-" + mobile);
        return map.get(mobile);
    }

    /**
     * 验证码置为失效
     * @param mobile
     */
    public void invalid(String mobile){
        RMap<String, String> map = redissonClient.getMap(QIAN_MANAGE_SMS_CODE + "-" + mobile);
        map.remove(mobile);
    }

    /**
     * 保存登录token
     * @param userBasicInfoDTO
     * @return
     */
    public UserBasicInfoDTO saveUserToken(UserBasicInfoDTO userBasicInfoDTO){
        userBasicInfoDTO.setToken(UUID.randomUUID().toString().replace("-","").toLowerCase());
        RMap<String, String> map = redissonClient.getMap(QIAN_MANAGE_USER_INFO);
        map.fastPut(userBasicInfoDTO.getMobile(), JSON.toJSONString(userBasicInfoDTO));
        return userBasicInfoDTO;
    }

    /**
     * 获取token
     * @param userBasicInfoDTO
     * @return
     */
    public UserBasicInfoDTO getUserToken(UserBasicInfoDTO userBasicInfoDTO){
        // 获取Redis中保存的token
        RMap<String, String> map = redissonClient.getMap(QIAN_MANAGE_USER_INFO);
        String result = map.get(userBasicInfoDTO.getMobile());
        return JSONObject.parseObject(result,UserBasicInfoDTO.class);
    }

    /**
     * 移除用户token
     * @param loginDTO
     * @return
     */
    @Override
    public String removeUserToken(LoginDTO loginDTO){
        // 获取Redis中保存的token
        RMap<String, String> map = redissonClient.getMap(QIAN_MANAGE_USER_INFO);
        return map.remove(loginDTO.getMobile());
    }
    /**
     * 获取唯一字符编号
     * @return
     */
    @Override
    public String getUuidCode() {
        return UUID.randomUUID().toString();
    }
     /**
     * 获取首页课程列表
     * @return
     */
    @Override
    public List<CourseForHomeDTO> getCourseListForHome() {
        RBucket<String> bucket = redissonClient.getBucket(QIAN_HOME_COURSE_LIST);
        String result = bucket.get();
        return JSONObject.parseArray(result,CourseForHomeDTO.class);
    }
}
