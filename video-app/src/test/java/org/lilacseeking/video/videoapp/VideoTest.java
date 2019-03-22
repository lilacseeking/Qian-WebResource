//package org.lilacseeking.video.videoapp;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.lilacseeking.video.videoapp.Utils.EncodeUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
///**
// * @Author： lvming
// * @Date： Created in 14:29 2019/1/30
// * @Description：
// * @Modified By：
// * @Version:
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class VideoTest {
//    @Autowired
//    EncodeUtil encodeUtil;
//
//    @Test
//    public void VideoTranscode(){
//        encodeUtil.VideoTranscode();
//    }
//    @Test
//    public void testEncodeVideo1(){
//        try {
//            encodeUtil.testEncodeVideo1();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void testEncodeVideo2(){
//        try {
//            encodeUtil.testEncodeVideo2();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void testEncodeVideo3(){
//        try {
//            encodeUtil.testEncodeVideo3();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void testEncodeVideo4(){
//        try {
//            encodeUtil.testEncodeVideo4();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void testEncodeVideo5(){
//        try {
//            encodeUtil.testEncodeVideo5();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void testEncodeAudio09(){
//        try {
//            encodeUtil.testEncodeAudio09();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void testAbortEncoder(){
//        try {
//            encodeUtil.testAbortEncoder();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void testEncryptData(){
//
//        String innerEncryptTest1 = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDK5e3/33rVJfX1epNe0feBkC1Qql3jrJHeYrzs3Hhil46GkOvPtgmGRyxkt85rarE5d5OAKJOdfvpkjmS9N+O9CIdJUX9taQPtkzlAoQMkbbaLPPhfxblBnx3Nah0hwDp5LCCgOEu/+eS3HXjAMafHalzE8mzpcM6qMXloRclJIQIDAQAB";
//        String innerEncryptTest2 = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDK5e3/33rVJfX1epNe0feBkC1Qql3jrJHeYrzs3Hhil46GkOvPtgmGRyxkt85rarE5d5OAKJOdfvpkjmS9N+O9CIdJUX9taQPtkzlAoQMkbbaLPPhfxblBnx3Nah0hwDp5LCCgOEu/+eS3HXjAMafHalzE8mzpcM6qMXloRclJIQIDAQAB";
//
//        String testEncryptTest1 = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJlA+4inufx20awuCgwkwnfzitMiH9/t/3U1KV5cQKglOBNr1CUbZ3ep9kupVNBKEJHqJv7dI3ND/43Jd1ag+I/DOpXkRp5zK8GF/EStm0VZBI9uJV9V0YpziPkxde+KXWYP1B96otnKjQmoVpTrYz3ipobIPXlqrtX6hyFvW8dbAgMBAAECgYAGSb1IWSFvc2yc9PKiluuHwm1ixlaqeHhv1bOR1JUukQBhA56buykeptGdPyBkv3U4l3PsJySqORMGPUrxOf+YzjazWGsCn7EpfGzvNujmgt0KfsKcnbV9eg3Ivcd3zPLQYW+VuVWHcnnMw8IhipIZdGvIHqFlr9YtuoFz/35awQJBAMqgsdr6CXXqr5wUkvhpQwH2KkdRH9pNFxQ909SVRfvsbKxDuwrO50Jxu85yz6FTJfgGTACDkbe12KIwy04wP7sCQQDBnvp+LJcEUc8GhWJS1wzuFx6gG2x7mjwB+FbQh2b4V+eP7ohWAP/DrhVwISXabKDEtnSfyXz0Y8DTgyEC9QzhAkAPRtBB7yn5bukwfkiaW+1GX5XObKpXHgrIZuTqmRp+pNOIpU6bwP+kfcioBvXqdsddDLR8MW8yB46+o6kgSvjzAkAZeD1BTcJL2isek3s8pHdZ9oabzx+1HrPYGn/pDHa6Im4em+UpUxZR+lttlZ6yHeoEFnA++LVMPLcH288C4ZNhAkEAhnSnYuq+d1HlrC5va9Adf+pVtc2hfQeceY9Wwvfhd+uXj9MNcnUT3lF70vNwR+bYu2XfruWloTzbeM1HprNwvA==";
//        String testEncryptTest2 = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCLXqfecYxLmGqiDPCqm5TAMUwYFZ1CX8/Eb5XCRIloejvsQnVCggn4a6PgeDFNao8k339DJcHj1tf/CzpaYHRtPftBcF/qUqEP+08QuQ9iSqkdbEAYf0yT1TxLr+XvJZziKb+NngllxwnoEkgMQ45ReCQzzGvmAFhoLucuu7+hHwIDAQAB";
//        System.out.println("私钥对比结果" + innerEncryptTest1.equals(testEncryptTest1));
//        System.out.println("公钥对比结果" + innerEncryptTest2.equals(testEncryptTest2));
//
//    }
//}
