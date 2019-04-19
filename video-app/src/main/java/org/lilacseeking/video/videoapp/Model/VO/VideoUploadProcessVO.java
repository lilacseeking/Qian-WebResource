package org.lilacseeking.video.videoapp.Model.VO;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

/**
 * @Author： lvming
 * @Date： Created in 16:24 2019/2/13
 * @Description： 视频上传进度条共享对象
 * @Modified By：
 * @Version:
 */
@Data
@Accessors(chain = true)
@Builder
public class VideoUploadProcessVO implements Serializable {
    /**
     * 创建时间
     */
    @Builder.Default
    public Date gmtCreate = new Date();
    /**
     * 创建人
     */
    @Builder.Default
    public Long creater = 1L;
    /**
     * 完成时间
     */
    public Date gmtFinish;
    /**
     * 用时 单位ms
     */
    public Long consumingTime;
    /**
     * 视频原名称
     */
    public String videoOriginName;
    /**
     * 视频名称（OSS 中存储的key）
     */
    public String videoName;
    /**
     * OSS路径
     */
    public String videoOriginPath;
    /**
     * Internet 路径
     */
    public String videoPath;
    /**
     * 时长
     */
    public String videoLength;
    /**
     * 视频大小
     */
    @Builder.Default
    public Long totalVideoSize;
    /**
     * 已上传大小
     */
    @Builder.Default
    public Long uploadVideoSize = 0L;
    /**
     * 上传进度
     */
    public Integer uploadRate ;
    /**
     * 上传状态
     */
    public String uploadStatus;

    public Integer getUploadRate(){
        if (null == totalVideoSize || null == uploadVideoSize || totalVideoSize == 0){
            return 0;
        }
        this.uploadRate = BigDecimal.valueOf(uploadVideoSize).divide(BigDecimal.valueOf(totalVideoSize),2, RoundingMode.DOWN).multiply(new BigDecimal("100")).intValue();
        return uploadRate;
    }
}
