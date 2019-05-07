package org.lilacseeking.video.service.DubboServiceImpl;

import org.lilacseeking.video.api.IUploadService;
import org.apache.dubbo.config.annotation.Service;

/**
 * @Auther: lilacseeking
 * @Date: 2019/4/29 10:21
 * @Description:
 */
@Service(version = "1.0.0")
public class UploadServiceImpl implements IUploadService {
    /**
     * Dubbo测试服务
     */
    @Override
    public void sayHelloWorld() {
        System.out.println("Hello World!");
    }
}
