package org.lilacseeking.video.videoapp.Model.PO;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.junit.jupiter.api.Test;
import org.lilacseeking.video.videoapp.Eumns.ProcessEnum;

import javax.persistence.*;

/**
 * @Author： lvming
 * @Date： Created in 16:09 2019/1/31
 * @Description： 视频转码相关信息
 * @Modified By：
 * @Version:
 */
@Data
@Accessors(chain = true)
//@Builder
@Table(name = "video_encode")
@Entity
public class VideoEncodePO extends BaseEntityPO{
    /**
     * 操作人Id
     */
    @Column(name="operate_id", nullable=false, length = 64)
    private Long operateId;
    /**
     * 操作人姓名
     */
    @Column(name="operate_name", nullable=false, length = 64)
    private String operateName;
    /**
     * 视频原名称
     */
    private String videoOriginName;
    /**
     * 视频名称
     */
    private String videoName;
    /**
     * 视频原路径
     */
    private String videoOriginPath;
    /**
     * 视频路径
     */
    private String videoPath;
    /**
     * 时长
     */
    private Long videoLength;
    /**
     * 分辨率
     */
    private String resolution;
    /**
     * 视频大小
     */
    private Long videoSize;
    /**
     * 转码状态
     */
    @Enumerated(EnumType.STRING)
    private ProcessEnum encodeStatus;
    /**
     * 转码进度
     */
    private Integer encodeRate;

    public VideoEncodePO(Long operateId, String operateName, String videoOriginName, String videoName, String videoOriginPath, String videoPath, Long videoLength, Long videoSize,String resolution, ProcessEnum encodeStatus, Integer encodeRate) {
        this.operateId = operateId;
        this.operateName = operateName;
        this.videoOriginName = videoOriginName;
        this.videoName = videoName;
        this.videoOriginPath = videoOriginPath;
        this.videoPath = videoPath;
        this.videoLength = videoLength;
        this.resolution = resolution;
        this.videoSize = videoSize;
        this.encodeStatus = encodeStatus;
        this.encodeRate = encodeRate;
    }

    public VideoEncodePO() {
    }
}
