package org.lilacseeking.video.videoapp.Model.PO;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Table;

/**
 * @Author： lvming
 * @Date： Created in 16:09 2019/1/31
 * @Description： 视频上传相关信息
 * @Modified By：
 * @Version:
 */
@Data
@Accessors(chain = true)
@Builder
@Table(name = "video_upload")
public class VideoUploadPO {

    /**
     * 操作人Id
     */
    private String operateId;
    /**
     * 操作人姓名
     */
    private String operateName;
    /**
     * 视频原名称
     */
    private String videoOriginName;
    /**
     * 称视频名
     */
    private String videoName;
    /**
     * OSS路径
     */
    private String videoOriginPath;
    /**
     * Internet 路径
     */
    private String videoPath;
    /**
     * 时长
     */
    private String videoLength;
    /**
     * 视频大小
     */
    private String videoSize;

    /**
     * 上传进度
     */
    private String uploadRate;
    /**
     * 缩略图原名称
     */
    private String thumbnailOriginName;
    /**
     * 缩略图名称
     */
    private String thumbnailName;
    /**
     * 缩略图原路径
     */
    private String thumbnailOriginPath;
    /**
     * 缩略图路径 Internet路径
     */
    private String thumbnailPath;
    /**
     * 上传状态
     */
    private String uploadStatus;
}
