package org.lilacseeking.video.service.Config;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * 使用Spring Boot ConfigurationProperties方式
 * <pre>
 * redis.cache.enabled = true
 * redis.cache.expireSeconds = 100
 * redis.cache.clusterNodes = 1,2
 * redis.cache.commandTimeout = 50
 * redis.cache.someMap.key1 = a
 * redis.cache.someMap.key2 = b
 * redis.cache.someList[0] = c
 * redis.cache.someList[1] = d
 * </pre>
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Component
@ConfigurationProperties(prefix = "redis.cache")
@EnableApolloConfig("application")
public class ApolloConfiguration {

    private int expireSeconds;
    private String clusterNodes;
    private int commandTimeout;

    private Map<String, String> someMap = Maps.newLinkedHashMap();
    private List<String> someList = Lists.newLinkedList();

    /**
     * Apollo配置中心
     * 1.github下载程序包
     * 2.修改build.bat/build.sh 中数据库连接
     * 3.修改portal中启动端口为8070
     * 4.运行build.bat/build.sh文件
     * 5.将打成包部署到服务器环境，配置应用，发布
     * 6.本地/opt/setting/添加server.properties，添加env=DEV
     * 7.SpringBoot项目配置添加-Denv=dev -Ddev.meta=http://47.95.2.117:8080
     *
     *
     */
    @PostConstruct
    private void initialize() {
        System.out.println(String.format(
                "SampleRedisConfig initialized - expireSeconds: {}, clusterNodes: {}, commandTimeout: {}, someMap: {}, someList: {}",
                expireSeconds, clusterNodes, commandTimeout, someMap, someList));
    }

    /**
     * @ApolloConfig用来自动注入Config对象
     */
    @ApolloConfig("application")
    private Config config;
    /**
     * @ApolloConfigChangeListener用来自动注册ConfigChangeListener
     */
    @ApolloConfigChangeListener("application")
    private void someOnChange(ConfigChangeEvent changeEvent) {
        changeEvent.changedKeys().forEach(key ->{
            ConfigChange change = changeEvent.getChange(key);
            System.out.println(String.format("Found change - key: %s, oldValue: %s, newValue: %s, changeType: %s", change.getPropertyName(), change.getOldValue(), change.getNewValue(), change.getChangeType()));
        });
    }
}