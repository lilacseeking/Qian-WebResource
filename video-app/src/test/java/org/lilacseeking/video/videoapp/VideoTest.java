package org.lilacseeking.video.videoapp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.lilacseeking.video.videoapp.Utils.EncodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author： lvming
 * @Date： Created in 14:29 2019/1/30
 * @Description：
 * @Modified By：
 * @Version:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class VideoTest {
    @Autowired
    EncodeUtil encodeUtil;

    @Test
    public void VideoTranscode(){
        encodeUtil.VideoTranscode();
    }
    @Test
    public void testEncodeVideo1(){
        try {
            encodeUtil.testEncodeVideo1();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testEncodeVideo2(){
        try {
            encodeUtil.testEncodeVideo2();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testEncodeVideo3(){
        try {
            encodeUtil.testEncodeVideo3();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testEncodeVideo4(){
        try {
            encodeUtil.testEncodeVideo4();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testEncodeVideo5(){
        try {
            encodeUtil.testEncodeVideo5();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testEncodeAudio09(){
        try {
            encodeUtil.testEncodeAudio09();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAbortEncoder(){
        try {
            encodeUtil.testAbortEncoder();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
