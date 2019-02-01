package org.lilacseeking.video.videoapp.Model.PO;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.junit.jupiter.api.Test;
import org.lilacseeking.video.videoapp.Eumns.ProcessEnum;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 * @Author： lvming
 * @Date： Created in 16:09 2019/1/31
 * @Description： 视频转码相关信息
 * @Modified By：
 * @Version:
 */
@Data
@Accessors(chain = true)
@Builder
@Table(name = "video_encode")
public class VideoEncodePO {
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
}
