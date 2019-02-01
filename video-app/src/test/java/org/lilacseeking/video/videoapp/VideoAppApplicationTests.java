package org.lilacseeking.video.videoapp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.lilacseeking.video.videoapp.Test.ASync.ASyncUserRegister;
import org.lilacseeking.video.videoapp.Utils.EncodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.concurrent.Executor;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = VideoAppApplication.class)
public class VideoAppApplicationTests {

    // 线程池
    @Autowired
    private Executor executor;

    @Test
    public void contextLoads() {
        executor.execute(new ASyncUserRegister());
    }

    @Test
    public void testEncodeVideo2(){
        try {
            new EncodeUtil().testEncodeVideo2();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

