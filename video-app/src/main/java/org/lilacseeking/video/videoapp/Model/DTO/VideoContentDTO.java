package org.lilacseeking.video.videoapp.Model.DTO;

import lombok.Data;

/**
 * @Auther: lilacseeking
 * @Date: 2018/12/17 21:38
 * @Description:
 */
@Data
public class VideoContentDTO {

    /**
     * 课程Id
     */
    public Long courseId;

    /**
     * 课程名称
     */
    public String courceName;

    /**
     * 章节名称
     */
    public String chapterNane;

    /**
     * 章节编号
     */
    public String chapterId;

    /**
     * 章节描述
     */
    public String chapterDescription;

    /**
     * 是否免费
     */
    public String isFree;

    /**
     * 视频时长
     */
    public String time;

    /**
     * 视频地址
     */
    public String url;

    /**
     * 视频文件名称
     */
    public String fileName;

    /**
     * 播放次数
     */
    public String views;
}
