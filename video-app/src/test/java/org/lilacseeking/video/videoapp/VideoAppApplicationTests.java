package org.lilacseeking.video.videoapp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.lilacseeking.video.videoapp.Test.UserTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VideoAppApplicationTests {

    @Test
    public void contextLoads() {
        new UserTest().register();
    }

}

