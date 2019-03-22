//package org.lilacseeking.video.videoapp;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.lilacseeking.video.videoapp.Test.ASync.ASyncUserRegister;
//import org.lilacseeking.video.videoapp.Utils.EncodeUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.math.BigDecimal;
//import java.math.RoundingMode;
//
////@RunWith(SpringRunner.class)
////@SpringBootTest(classes = VideoAppApplication.class)
//public class VideoAppApplicationTests {
//
//    // 线程池
//    @Autowired
//    private ThreadPoolTaskExecutor executor;
//
//    @Test
//    public void contextLoads() {
//        executor.execute(new ASyncUserRegister());
//    }
//
//    @Test
//    public void testEncodeVideo2(){
//        try {
//            new EncodeUtil().testEncodeVideo2();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void test(){
//        System.out.println(BigDecimal.valueOf(10).divide(BigDecimal.valueOf(100),2, RoundingMode.DOWN).multiply(new BigDecimal("100")).intValue());
//    }
//
//
//}
//
