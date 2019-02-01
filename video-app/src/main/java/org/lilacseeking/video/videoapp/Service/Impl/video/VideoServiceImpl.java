package org.lilacseeking.video.videoapp.Service.Impl.video;

import org.lilacseeking.video.videoapp.Configuration.ConstantProperties;
import org.lilacseeking.video.videoapp.Eumns.ErrorCodeEumn;
import org.lilacseeking.video.videoapp.Exception.BusinessException;
import org.lilacseeking.video.videoapp.Service.video.VideoService;
import org.lilacseeking.video.videoapp.Utils.VideoEncodeUtil;
import org.reflections.vfs.Vfs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

/**
 * @Author： lvming
 * @Date： Created in 18:46 2019/1/30
 * @Description：
 * @Modified By：
 * @Version:
 */
@Service
public class VideoServiceImpl implements VideoService {
    // 转码工具类
    @Autowired
    private VideoEncodeUtil videoEncodeUtil;
    // 配置中心
    @Autowired
    private ConstantProperties constantProperties;

    /**
     * 将视频格式转码为MP4格式，三种格式 标清、高清、超清
     * @param file
     */
    @Override
    public void encodeVideoToMP4ThreeCommonFormat(MultipartFile file) {
        File source = new File(constantProperties.getUpload().getSourceRoute().concat(File.separator).concat(file.getOriginalFilename()));
        try {
            FileUtils.copyInputStreamToFile(file.getInputStream(), source);
        } catch (IOException e) {
            throw new BusinessException(ErrorCodeEumn.VIDEO_TRANS_FAILED.getName());
        }
        videoEncodeUtil.encodeVideoToMP4ThreeCommonFormat(source);
    }
}
