package org.lilacseeking.video.service;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @Auther: lilacseeking
 * @Date: 2019/4/20 22:42
 * @Description:
 */
@SpringBootApplication
@EnableApolloConfig
@EnableDubbo
@EnableAsync
public class VideoServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(VideoServiceApplication.class, args);
    }
}
