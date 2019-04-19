package org.lilacseeking.video.app.Lock;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeUnit;

/**
 * @Author： lvming
 * @Date： Created in 17:43 2019/2/13
 * @Description： 分布式锁
 * @Modified By：
 * @Version:
 */
@Component
public class DistributedLockService {

    @Autowired
    private RedissonClient redissonClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(DistributedLockService.class);

    /**
     * 获得分布式锁
     * @param uniqueStr
     * @return
     */
    public Boolean acquireLock(String uniqueStr) {
        try {
            String key = uniqueStr;
            RLock lock = redissonClient.getLock(key);
            lock.lock(60, TimeUnit.SECONDS);
            LOGGER.info("锁获取成功，key:{}", uniqueStr);
            return  true;
        } catch (Exception e) {
            LOGGER.error("锁获取失败，key:{},异常原因:{}",uniqueStr ,e);
            return false;
        }
    }

    /**
     * 释放分布式锁
     * @param uniqueStr
     * @return
     */
    public Boolean releaseLock(String uniqueStr) {
        try {
            String key = uniqueStr;
            RLock lock = redissonClient.getLock(key);
            if (lock!= null && lock.isLocked()) {
                lock.unlock();
            }
            LOGGER.info("锁释放成功，key:{}", uniqueStr);
            return  true;
        } catch (Exception e) {
            LOGGER.error("锁释放失败，key:{},异常原因:{}",uniqueStr ,e);
            return false;
        }
    }
}

