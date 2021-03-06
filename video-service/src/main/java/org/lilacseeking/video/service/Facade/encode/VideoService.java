package org.lilacseeking.video.service.Facade.encode;

import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.util.List;

/**
 * @Author： lvming
 * @Date： Created in 18:45 2019/1/30
 * @Description：
 * @Modified By：
 * @Version:
 */
public interface VideoService {

    /**
     * 将视频格式转码为MP4格式，三种格式 标清、高清、超清
     * @param file
     */
    void encodeVideoToMP4ThreeCommonFormat(MultipartFile file);

    /**
     * 上传文件(列表上传)
     * @param fileList
     */
    void uploadFileList(List<File> fileList);
}
