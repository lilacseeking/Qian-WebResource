package org.lilacseeking.video.videoapp.Test;

import com.alibaba.fastjson.JSON;
import groovy.util.logging.Slf4j;
import org.lilacseeking.video.videoapp.Model.DTO.RegisterDTO;
import org.lilacseeking.video.videoapp.Utils.HttpClientUtil;
import org.lilacseeking.video.videoapp.Utils.RandomValueUtil;
import org.lilacseeking.video.videoapp.Utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.text.SimpleDateFormat;

/**
 * @Auther: lilacseeking
 * @Date: 2018/10/20 15:05
 * @Description:
 */
@Slf4j
public class UserTest {

    //日志系统
    public final Logger logger = LoggerFactory.getLogger(UserTest.class);

    public void register(){
//        {
//                "email": "xianjin@gmail.com",
//                "mobile": "18803830004",
//                "password": "123456",
//                "username": "程序大牛",
//                "name":"张秋月",
//                "age":"29",
//                "gender":"0",
//                "birthday":"1993-11-06"
//        }
        //随机生成测试数据
        String randomBirthday = RandomValueUtil.getRandomBirthday("1980-01-01", "2010-01-01");
        RegisterDTO registerDTO = new RegisterDTO();
        try {
            registerDTO.setAge(StringUtil.getAge(new SimpleDateFormat("yyyy-MM-dd").parse(randomBirthday)));//由出生日期获取年龄
        } catch (Exception e) {
            e.printStackTrace();
        }
        registerDTO.setBirthday(randomBirthday);
        registerDTO.setEmail(RandomValueUtil.getEmail(8,12));
        registerDTO.setGender(Math.random()>0.5?1:0);//随机生成性别
        registerDTO.setMobile(RandomValueUtil.getTelephone());//随机生成手机号码
        registerDTO.setPassword("123456");
        registerDTO.setUsername(RandomValueUtil.getRandomUserName((int)(2 + Math.random()*3)));
        registerDTO.setName(RandomValueUtil.getChineseName());
        logger.info("用户注册信息：{}",JSON.toJSONString(registerDTO));
        try {
            String s = HttpClientUtil.sendHttpPost("http://localhost:8077/common/register", JSON.toJSONString(registerDTO));
            logger.info("调用结果：{}",s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
