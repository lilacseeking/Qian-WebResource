package org.lilacseeking.video.videoapp.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: lilacseeking
 * @Date: 2018/11/11 12:03
 * @Description: 常量配置类
 */
@Configuration
@EnableConfigurationProperties(ConstantProperties.class)
public class ConstantConfig{

    @Autowired
    private ConstantProperties constantProperties;

    public ConstantConfig (){

    }
    public void init(){

    }
}
