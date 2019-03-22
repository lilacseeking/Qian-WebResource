package org.lilacseeking.video.videoapp.Model.PO;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
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
@Entity
public class VideoUploadPO extends BaseEntityPO{

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
    private Long videoSize;

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

    public VideoUploadPO(){

    }

    public VideoUploadPO(String operateId, String operateName, String videoOriginName, String videoName, String videoOriginPath, String videoPath, String videoLength, Long videoSize, String uploadRate, String thumbnailOriginName, String thumbnailName, String thumbnailOriginPath, String thumbnailPath, String uploadStatus) {
        this.operateId = operateId;
        this.operateName = operateName;
        this.videoOriginName = videoOriginName;
        this.videoName = videoName;
        this.videoOriginPath = videoOriginPath;
        this.videoPath = videoPath;
        this.videoLength = videoLength;
        this.videoSize = videoSize;
        this.uploadRate = uploadRate;
        this.thumbnailOriginName = thumbnailOriginName;
        this.thumbnailName = thumbnailName;
        this.thumbnailOriginPath = thumbnailOriginPath;
        this.thumbnailPath = thumbnailPath;
        this.uploadStatus = uploadStatus;
    }
}
