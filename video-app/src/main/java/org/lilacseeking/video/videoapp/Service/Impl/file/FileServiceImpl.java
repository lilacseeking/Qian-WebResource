package org.lilacseeking.video.videoapp.Service.Impl.file;

import org.lilacseeking.video.videoapp.Common.AsyncPutObjectUpload;
import org.lilacseeking.video.videoapp.Service.RedisService;
import org.lilacseeking.video.videoapp.Service.file.FileService;
import org.lilacseeking.video.videoapp.Utils.OSSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: lilacseeking
 * @Date: 2019/4/14 10:02
 * @Description:
 */
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private ThreadPoolTaskExecutor executor;

    /**
     * 上传多个文件
     *
     * @param files
     * @return
     */
    @Override
    public List moreFileUpload(MultipartFile[] files) {
        List fileUrlList = new ArrayList();
        for (MultipartFile file : files) {
            try {
                String key = redisService.getUuidCode() + "." + OSSUtil.getExtensionName(file.getOriginalFilename());
                executor.submit(new AsyncPutObjectUpload(file.getInputStream(), key));
                fileUrlList.add("http://www.lilacseeking.com/" + key );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileUrlList;
    }
}
