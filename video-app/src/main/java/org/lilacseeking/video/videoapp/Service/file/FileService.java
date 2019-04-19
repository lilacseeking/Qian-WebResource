package org.lilacseeking.video.videoapp.Service.file;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Auther: lilacseeking
 * @Date: 2019/4/14 09:51
 * @Description:
 */
public interface FileService {

    /**
     * 上传多个文件
     * @param files
     * @return
     */
    List moreFileUpload(MultipartFile[] files);

}
