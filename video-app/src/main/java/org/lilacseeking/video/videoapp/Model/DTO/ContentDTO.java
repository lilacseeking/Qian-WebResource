package org.lilacseeking.video.videoapp.Model.DTO;

import lombok.Data;
import lombok.experimental.Accessors;
import org.lilacseeking.video.videoapp.Eumns.YesOrNoEnum;

import java.io.Serializable;

/**
 * @Auther: lilacseeking
 * @Date: 2018/12/17 21:38
 * @Description:
 */
@Data
@Accessors(chain = true)
public class ContentDTO implements Serializable {

    /**
     * 用户Id
     */
    public Long userId;

    /**
     * 课程Id
     */
    public Long courseId;

    /**
     * 课程名称
     */
    public String courseName;

    /**
     * 章节名称
     */
    public String chapterName;

    /**
     * 章节编号
     */
    public Integer chapterId;

    /**
     * 章节描述
     */
    public String chapterDescription;

    /**
     * 是否免费
     */
    public YesOrNoEnum isFree;

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
