//package org.lilacseeking.video.videoapp.Utils;
//
//import com.alibaba.fastjson.JSON;
//import org.junit.jupiter.api.Test;
//import org.lilacseeking.video.videoapp.Common.ASycShowProcess;
//import org.lilacseeking.video.videoapp.Listener.EncodingListener;
//import org.lilacseeking.video.videoapp.Service.RedisService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//import org.springframework.stereotype.Service;
//import ws.schild.jave.*;
//import java.io.File;
//import java.util.concurrent.TimeUnit;
//import static org.junit.jupiter.api.Assertions.*;
//
///**
// * @Author： lvming
// * @Date： Created in 14:06 2019/1/29
// * @Description： JAVE 工具类
// * @Modified By：
// * @Version:
// */
//@Service
//public class EncodeUtil{
//    // 线程池
//    @Autowired
//    private ThreadPoolTaskExecutor executor;
//    @Autowired
//    private RedisService redisService;
//    // 日志
//    private static Logger LOGGER = LoggerFactory.getLogger(EncodeUtil.class);
//    /**
//     * 音频转码至mp3
//     * @return
//     */
//    public void AudioTranscode(){
//        // 1 定义源文件和目标文件
//        File source = new File("C:\\Users\\Administrator\\Desktop\\20190127_013146.m4a");
//        File target = new File("C:\\Users\\Administrator\\Desktop\\20190127_013146.mp3");
//        if (target.exists())
//        {
//            target.delete();
//        }
//        // 2 定义音频属性
//        AudioAttributes audio = new AudioAttributes();
//        // 编码器名称，为空则复制文件
//        audio.setCodec("libmp3lame");
//        // 编码过程的比特率值，为空选择默认值
//        audio.setBitRate(128000);
//        // 编码过程的通道值，1=mono，2=stereo
//        audio.setChannels(2);
//        // 编码过程的采样值，为空则选择默认值
//        audio.setSamplingRate(44100);
//        // 3 转码属性
//        EncodingAttributes encodeAttr = new EncodingAttributes();
//        //已编码的目标文件格式
//        encodeAttr.setFormat("mp3");
//        // 音频流编码属性
//        encodeAttr.setAudioAttributes(audio);
//        // 4 转码
//        //Encode
//        Encoder encoder = new Encoder();
//        try {
//            encoder.encode(new MultimediaObject(source), target, encodeAttr);
//        } catch (EncoderException e) {
//            LOGGER.error("音频转码至mp3失败，源文件路径：{}，目标文件路径：{}，错误信息：{}，音频属性：{}，转码属性：{}",source.getPath(),target.getPath(),e.getMessage(),JSON.toJSONString(audio),JSON.toJSONString(encodeAttr));
//        }
//    }
//
//    public void VideoTranscode(){
//        // 1 定义源文件和目标文件
//        File source = new File("C:\\Users\\Administrator\\Desktop\\公司的视频.mp4");
//        File target = new File("C:\\Users\\Administrator\\Desktop\\公司的视频.3gp");
//        if (target.exists())
//        {
//            target.delete();
//        }
////        VideoSize totalVideoSize = new VideoSize(3840, 2160);
//        // 2 定义视频属性
//        VideoAttributes video = new VideoAttributes();
////        video.setCodec("mpeg4");
////        video.setBitRate(160000);
////        video.setFrameRate(15);
////        video.setSize(totalVideoSize);
//        // 2 定义音频属性
//        AudioAttributes audio = new AudioAttributes();
//        // 编码器名称，为空则复制文件
////        audio.setCodec("libfaac");
//        // 编码过程的比特率值，为空选择默认值
////        audio.setBitRate(128000);
//        // 编码过程的通道值，1=mono，2=stereo
////        audio.setChannels(2);
//        // 编码过程的采样值，为空则选择默认值
////        audio.setSamplingRate(44100);
//        // 3 转码属性
//        EncodingAttributes encodeAttr = new EncodingAttributes();
//        //已编码的目标文件格式
//        encodeAttr.setFormat("3gp");
//        // 视频流编码属性
//        encodeAttr.setVideoAttributes(video);
//        // 音频流编码属性
////        encodeAttr.setAudioAttributes(audio);
//        // 4 设置进度监听
//        EncodingListener listener = new EncodingListener();
//        executor.execute(new ASycShowProcess(listener));
//        // 4 转码
//        //Encode
//        Encoder encoder = new Encoder();
//        try {
//            encoder.encode(new MultimediaObject(source), target, encodeAttr,listener);
//        } catch (EncoderException e) {
//            LOGGER.error("视频转码至mp4失败，源文件路径：{}，目标文件路径：{}，错误信息：{}，音频属性：{}，转码属性：{}",source.getPath(),target.getPath(),e.getMessage(),JSON.toJSONString(video),JSON.toJSONString(encodeAttr));
//        }
//    }
//
//    /**
//     * 获取视频长度
//     */
//    public void getVideoSize(){
//        // 1 定义源文件和目标文件
//        File source = new File("C:\\Users\\Administrator\\Desktop\\上传用户新头像-注意事项.wmv");
//        Encoder encoder =  new  Encoder();
//        String[] videoDecoders = null;
//        try {
//            videoDecoders = encoder.getVideoDecoders();
//            System.out.println(JSON.toJSONString(videoDecoders));
//        } catch (EncoderException e) {
//            LOGGER.error("获取视频长度异常，输入参数：{},输出参数：{}", JSON.toJSONString(encoder),JSON.toJSONString(videoDecoders));
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 测试类MultimediaObject的getInfo方法。
//     * @throws java.lang.Exception
//     */
//    public void testGetInfo() throws Exception {
//        System.out.println("getInfo");
//        File file = new File("C:\\Users\\Administrator\\Desktop\\上传用户新头像-注意事项.wmv");
//        MultimediaObject instance = new MultimediaObject(file);
//        MultimediaInfo result = instance.getInfo();
//        assertEquals("asf", result.getFormat());
//        assertEquals(1201330, result.getDuration());
////        assertNull(result.getAudio());
//        assertEquals("wmv3 (Main) (WMV3 / 0x33564D57)", result.getVideo().getDecoder());
//        assertEquals(1280, result.getVideo().getSize().getWidth());
//        assertEquals(800, result.getVideo().getSize().getHeight());
//        assertEquals(219, result.getVideo().getBitRate());
//        assertEquals(15.0f, result.getVideo().getFrameRate());
//
//    }
//
//
//    /**
//     * 获取文件
//     */
//    public void testGetFile() {
//        System.out.println("getFile");
//        File file = new File("C:\\\\Users\\\\Administrator\\\\Desktop\\\\上传用户新头像-注意事项.wmv\"");
//        MultimediaObject instance = new MultimediaObject(file);
//        File expResult = file;
//        File result = instance.getFile();
//        assertEquals(expResult, result);
//    }
//
//    /**
//     * 获取音频解码器列表
//     * @throws java.lang.Exception
//     */
//    public void testGetAudioDecoders() throws Exception {
//        System.out.println("getAudioDecoders");
//        Encoder instance = new Encoder();
//        String[] result = instance.getAudioDecoders();
//        LOGGER.info("音频解码器列表：{}",JSON.toJSONString(result));
//        assertTrue(result != null && result.length >0, "No audio decoders found");
//    }
//
//    /**
//     * 获取音频编码器列表
//     * @throws java.lang.Exception
//     */
//    public void testGetAudioEncoders() throws Exception {
//        System.out.println("getAudioEncoders");
//        Encoder instance = new Encoder();
//        String[] result = instance.getAudioEncoders();
//        LOGGER.info("音频编码器列表：{}",JSON.toJSONString(result));
//        assertTrue(result != null && result.length >0, "No audio encoders found");
//    }
//
//    /**
//     * 获取视频解码器列表
//     * @throws java.lang.Exception
//     */
//    public void testGetVideoDecoders() throws Exception {
//        System.out.println("getVideoDecoders");
//        Encoder instance = new Encoder();
//        String[] result = instance.getVideoDecoders();
//        LOGGER.info("视频解码器列表：{}",JSON.toJSONString(result));
//        assertTrue(result != null && result.length >0, "No video decoders found");
//    }
//
//    /**
//     * 获取视频编码器列表
//     * @throws java.lang.Exception
//     */
//    public void testGetVideoEncoders() throws Exception {
//        System.out.println("getVideoEncoders");
//        Encoder instance = new Encoder();
//        String[] result = instance.getVideoEncoders();
//        LOGGER.info("视频编码器列表：{}",JSON.toJSONString(result));
//        assertTrue(result != null && result.length >0, "No video enecoders found");
//    }
//
//    /**
//     * 获取ffmpeg发行版支持的文件格式列表
//     * @throws java.lang.Exception
//     */
//    public void testGetSupportedEncodingFormats() throws Exception {
//        System.out.println("getSupportedEncodingFormats");
//        Encoder instance = new Encoder();
//        String[] result = instance.getSupportedEncodingFormats();
//        LOGGER.info("ffmpeg发行版支持的文件格式列表：{}",JSON.toJSONString(result));
//        assertTrue(result != null && result.length >0, "No supported encoding formats found");
//    }
//
//    /**
//     * 获得支持的解码格式列表
//     * @throws java.lang.Exception
//     */
//    public void testGetSupportedDecodingFormats() throws Exception {
//        System.out.println("getSupportedDecodingFormats");
//        Encoder instance = new Encoder();
//        String[] result = instance.getSupportedDecodingFormats();
//        LOGGER.info("支持的解码格式列表：{}",JSON.toJSONString(result));
//        assertTrue(result != null && result.length >0, "No supported decoding formats found");
//    }
//
//    /**
//     * 测试转码
//     * @throws java.lang.Exception
//     */
//    public void testEncodeVideo1() throws Exception {
//        System.out.println("encode");
//
//        File source = new File("C:\\Users\\Administrator\\Desktop\\公司的视频.mp4");
//        File target = new File("C:\\Users\\Administrator\\Desktop\\公司的视频.avi");
//        if (target.exists())
//        {
//            target.delete();
//        }
//        AudioAttributes audio = new AudioAttributes();
////        audio.setCodec("libfaac");
////        audio.setBitRate(192000);
////        audio.setSamplingRate(8000);
////        audio.setChannels(2);
//        VideoAttributes video = new VideoAttributes();
////        video.setCodec("mpeg4");
////        video.setBitRate(31876000);
////        video.setFrameRate(15);
//        video.setSize(new VideoSize(1920,1080));
//        EncodingAttributes attrs = new EncodingAttributes();
//        attrs.setFormat("avi");
////        attrs.setAudioAttributes(audio);
//        attrs.setVideoAttributes(video);
//        Encoder encoder = new Encoder();
//        encoder.encode(new MultimediaObject(source), target, attrs);
//        assertTrue( target.exists(), "Output file missing");
//    }
//
//    /**
//     * 测试转码（进度显示）
//     * @throws java.lang.Exception
//     */
//    public void testEncodeVideo2() throws Exception {
//        System.out.println("encode");
//        File source = new File("C:\\Users\\Administrator\\Desktop\\公司的视频.mp4");
//        File target = new File("C:\\Users\\Administrator\\Desktop\\公司的视频.avi");
//        if (target.exists())
//        {
//            target.delete();
//        }
//        AudioAttributes audio = new AudioAttributes();
////        audio.setCodec("libfaac");
////        audio.setBitRate(128000);
////        audio.setSamplingRate(44100);
////        audio.setChannels(2);
//        VideoAttributes video = new VideoAttributes();
////        video.setCodec("mpeg4");
////        video.setBitRate(160000);
////        video.setFrameRate(15);
//        video.setSize(new VideoSize(3840, 2160));
//        EncodingAttributes attrs = new EncodingAttributes();
//        attrs.setFormat("avi");
//        attrs.setAudioAttributes(audio);
//        attrs.setVideoAttributes(video);
//        Encoder encoder = new Encoder();
//        EncodingListener listener = new EncodingListener();
//        executor.execute(new ASycShowProcess(listener));
//        encoder.encode(new MultimediaObject(source), target, attrs, listener);
//        assertNotNull(listener.getInfo());
//        assertTrue( target.exists(), "Output file missing");
//    }
//
//    /**
//     * Test of encode method, of class Encoder.
//     * @throws java.lang.Exception
//     */
//    public void testEncodeVideo3() throws Exception {
//        System.out.println("encode");
//        File source = new File("C:\\Users\\Administrator\\Desktop\\Redis-String案例.AVI");
//        File target = new File("C:\\Users\\Administrator\\Desktop\\Redis-String案例.3pg");
//        if (target.exists())
//        {
//            target.delete();
//        }
//        AudioAttributes audio = new AudioAttributes();
//        audio.setCodec("libfaac");
//        audio.setBitRate(128000);
//        audio.setSamplingRate(44100);
//        audio.setChannels(2);
//        VideoAttributes video = new VideoAttributes();
//        video.setCodec("mpeg4");
//        video.setBitRate(160000);
//        video.setFrameRate(15);
//        video.setSize(new VideoSize(176, 144));
//        EncodingAttributes attrs = new EncodingAttributes();
//        attrs.setFormat("3gp");
//        attrs.setAudioAttributes(audio);
//        attrs.setVideoAttributes(video);
//        Encoder encoder = new Encoder();
//        EncodingListener listener = new EncodingListener();
//        // TODO 多线程显示进度信息
//        executor.execute(new ASycShowProcess(listener));
//        String errorMessage= "Exit code of ffmpeg encoding run is 1";
//        boolean exceptionThrown= false;
//        try
//        {
//            encoder.encode(new MultimediaObject(source), target, attrs, listener);
//        }
//        catch (EncoderException ex)
//        {
//            assertEquals(ex.getMessage(), errorMessage, "Not expected error message");
//            exceptionThrown= true;
//        }
//        assertTrue( exceptionThrown, "No exception occured");
//    }
//
//    /**
//     * Test of encode method, of class Encoder.
//     * @throws java.lang.Exception
//     */
//    @Test
//    public void testEncodeVideo4() throws Exception {
//        System.out.println("encode");
//        File source = new File("C:\\Users\\Administrator\\Desktop\\Redis-String案例.AVI");
//        File target = new File("C:\\Users\\Administrator\\Desktop\\Redis-String案例.3pg");
//        if (target.exists())
//        {
//            target.delete();
//        }
//        AudioAttributes audio = new AudioAttributes();
//        audio.setCodec("adpcm_ms");
//        audio.setBitRate(128000);
//        audio.setSamplingRate(44100);
//        audio.setChannels(2);
//        VideoAttributes video = new VideoAttributes();
//        video.setCodec("mpeg4");
//        video.setBitRate(160000);
//        video.setFrameRate(15);
//        video.setSize(new VideoSize(176, 144));
//        EncodingAttributes attrs = new EncodingAttributes();
//        attrs.setFormat("3gp");
//        attrs.setAudioAttributes(audio);
//        attrs.setVideoAttributes(video);
//        Encoder encoder = new Encoder();
//        EncodingListener listener = new EncodingListener();
//        // TODO 多线程显示进度信息
//        executor.execute(new ASycShowProcess(listener));
//        boolean exceptionThrown= false;
//        String errorMessage= "Exit code of ffmpeg encoding run is 1";
//        try
//        {
//            encoder.encode(new MultimediaObject(source), target, attrs, listener);
//        }
//        catch (EncoderException ex)
//        {
//            assertEquals(ex.getMessage(), errorMessage, "Not expected error message");
//            exceptionThrown= true;
//        }
//        assertTrue( exceptionThrown, "No exception occured");
//    }
//
//    /**
//     * Test of encode method, of class Encoder.
//     * @throws java.lang.Exception
//     */
//    @Test
//    public void testEncodeVideo5() throws Exception {
//        System.out.println("encode");
//        File source = new File("C:\\Users\\Administrator\\Desktop\\Redis-String案例.AVI");
//        File target = new File("C:\\Users\\Administrator\\Desktop\\Redis-String案例.flv");
//        if (target.exists())
//        {
//            target.delete();
//        }
//        AudioAttributes audio = new AudioAttributes();
//        audio.setCodec("libmp3lame");
//        audio.setBitRate(64000);
//        audio.setChannels(1);
//        audio.setSamplingRate(22050);
//        VideoAttributes video = new VideoAttributes();
//        video.setCodec("flv");
//        video.setBitRate(160000);
//        video.setFrameRate(15);
//        video.setSize(new VideoSize(400, 300));
//        EncodingAttributes attrs = new EncodingAttributes();
//        attrs.setFormat("flv");
//        attrs.setAudioAttributes(audio);
//        attrs.setVideoAttributes(video);
//        Encoder encoder = new Encoder();
//        EncodingListener listener = new EncodingListener();
//        // TODO 多线程显示进度信息
//        executor.execute(new ASycShowProcess(listener));
//        encoder.encode(new MultimediaObject(source), target, attrs, listener);
//        assertNotNull(listener.getInfo());
//        assertTrue(target.exists(), "Output file missing");
//    }
//
//    /**
//     * Test of encode method, of class Encoder.
//     * @throws java.lang.Exception
//     */
//    @Test
//    public void testEncodeAudio09() throws Exception {
//        System.out.println("encode");
//        File source = new File("C:\\Users\\Administrator\\Desktop\\金南玲-逆流成河.mp3");
//        if (source.exists())
//        {
//            File target = new File("C:\\Users\\Administrator\\Desktop\\金南玲-逆流成河.wav");
//            if (target.exists())
//            {
//                target.delete();
//            }
//
//            //Set Audio Attributes
//            AudioAttributes audio = new AudioAttributes();
//            audio.setCodec("pcm_s16le");
//            audio.setChannels(2);
//            audio.setSamplingRate(44100);
//
//            //Set encoding attributes
//            EncodingAttributes attributes = new EncodingAttributes();
//            attributes.setFormat("wav");
//            attributes.setAudioAttributes(audio);
//            Encoder encoder = new Encoder();
//            EncodingListener listener = new EncodingListener();
//            executor.execute(new ASycShowProcess(listener));
//            encoder.encode(new MultimediaObject(source), target, attributes, listener);
//            assertNotNull(listener.getInfo());
//            assertTrue(target.exists(), "Output file missing");
//        }
//    }
//
//
//    /**
//     * Test of encode method, of class Encoder.
//     * @throws java.lang.Exception
//     */
//    @Test
//    public void testEncodeVideo10() throws Exception {
//        System.out.println("encode");
//        File source = new File("src/test/resources/private/test10.mpg");
//        if (source.exists())
//        {
//            File target = new File("target/testoutput/testEncodeVideo10.mp4");
//            if (target.exists())
//            {
//                target.delete();
//            }
//
//            AudioAttributes audio = new AudioAttributes();
//            audio.setCodec("eac3");
//            audio.setBitRate(97000);
//            audio.setSamplingRate(48000);
//            audio.setChannels(2);
//            VideoAttributes video = new VideoAttributes();
//            video.setCodec("mpeg4");
//            video.setBitRate(1500000);
//            video.setFrameRate(30);
//            video.setSize(new VideoSize(320, 240));
//            EncodingAttributes attrs = new EncodingAttributes();
//            attrs.setFormat("mp4");
//            attrs.setVideoAttributes(video);
//            attrs.setAudioAttributes(audio);
//            Encoder encoder = new Encoder();
//            EncodingListener listener = new EncodingListener();
//            encoder.encode(new MultimediaObject(source), target, attrs, listener);
//            assertNotNull(listener.getInfo());
//            assertTrue(target.exists(), "Output file missing");
//        }
//    }
//
//
//
//    /**
//     * 测试停止转码-Stream意外关闭
//     * @throws java.lang.Exception
//     */
//    @Test
//    public void testAbortEncoder() throws Exception {
//        System.out.println("encode");
//        File source = new File("C:\\Users\\Administrator\\Desktop\\上传用户新头像-注意事项.wmv");
//        File target = new File("C:\\Users\\Administrator\\Desktop\\上传用户新头像-注意事项.mp3");
//        if (target.exists())
//        {
//            target.delete();
//        }
//        Encoder encoder = new Encoder();
//        EncodingListener listener = new EncodingListener();
//        executor.execute(new ASycShowProcess(listener));
//        String message= null;
//        String compareTo= "Specified sample rate";
//        AudioAttributes audio = new AudioAttributes();
//        audio.setCodec("libmp3lame");
//        audio.setBitRate(128000);
//        audio.setChannels(2);
//        audio.setSamplingRate(44100);
//        EncodingAttributes attrs = new EncodingAttributes();
//        attrs.setFormat("mp3");
//        attrs.setAudioAttributes(audio);
//
//        Runnable task = () -> {
//            try
//            {
//                encoder.encode(new MultimediaObject(source), target, attrs, listener);
//                assertTrue(target.exists(), "Output file missing");
//            }
//            catch (EncoderException ex)
//            {
//                throw new AssertionError("Unexpected exception in encoder", ex);
//            }
//        };
//
//        Thread thread = new Thread(task);
//        thread.start();
//        TimeUnit.MILLISECONDS.sleep(100);
//        encoder.abortEncoding();
//    }
//
//
//
//
//
//
//
//
//
//}
