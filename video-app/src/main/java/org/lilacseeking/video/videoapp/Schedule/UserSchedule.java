package org.lilacseeking.video.videoapp.Schedule;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.springframework.stereotype.Service;

@Service
@JobHandler(value = "insertUser")
public class UserSchedule extends IJobHandler {
    @Override
    public ReturnT<String> execute(String strings){
        for (int i = 0; i < 10; i++){
            System.out.println("成功创建第" + i + "个用户");
        }
        return ReturnT.SUCCESS;
    }
}
