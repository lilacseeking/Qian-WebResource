package org.lilacseeking.video.app;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableApolloConfig
@EnableAsync
@EnableDubbo
@EnableRedisHttpSession
public class VideoAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(VideoAppApplication.class, args);
    }

}

