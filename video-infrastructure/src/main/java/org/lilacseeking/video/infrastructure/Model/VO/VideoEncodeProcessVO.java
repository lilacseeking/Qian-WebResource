package org.lilacseeking.video.infrastructure.Model.VO;

import lombok.Data;
import lombok.experimental.Accessors;
import org.lilacseeking.video.infrastructure.enums.ProcessEnum;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author： lvming
 * @Date： Created in 15:24 2019/2/15
 * @Description：
 * @Modified By：
 * @Version:
 */
@Data
@Accessors(chain = true)
public class VideoEncodeProcessVO implements Serializable {
    /**
     * 创建时间
     */
    public Date gmtCreate = new Date();
    /**
     * 完成时间
     */
    public Date gmtFinish;
    /**
     * 用时 单位ms
     */
    public Long consumingTime;
    /**
     * 操作人Id
     */
    private Long operateId;
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
    private ProcessEnum encodeStatus;
    /**
     * 转码进度
     */
    private Integer encodeRate;
}
