package org.lilacseeking.video.videoapp.Model.PO;

import lombok.Data;
import lombok.experimental.Accessors;
import org.lilacseeking.video.videoapp.Eumns.YesOrNoEnum;
import javax.persistence.*;

/**
 * @Auther: lilacseeking 未验证
 * @Date: 2018/8/23 22:40
 * @Description:课程目录表
 * 此表为视频课程表，显示课程的详细信息
 */
@Entity
@Data
@Accessors(chain = true)
@Table(name = "video_content")
public class VideoContentPO extends BaseEntityPO {

    /**
     * 课程Id
     */
    @Column(name = "course_id")
    public Long courseId;
    /**
     * 课程名称
     */
    @Column(name = "course_name")
    public String courseName;

    /**
     * 章节名称
     */
    @Column(name = "chapter_name")
    public String chapterName;

    /**
     * 章节编号
     */
    @Column(name = "chapter_id")
    public Integer chapterId;

    /**
     * 章节描述
     */
    @Column(name = "chapter_description")
    public String chapterDescription;

    /**
     * 是否免费
     */
    @Column(name = "is_free")
    @Enumerated(EnumType.STRING)
    public YesOrNoEnum isFree;

    /**
     * 视频时长
     */
    @Column(name = "time")
    public String time;

    /**
     * 视频地址
     */
    @Column(name = "url")
    public String url;

    /**
     * 视频文件名称
     */
    @Column(name = "file_name")
    public String fileName;

    /**
     * 播放次数
     */
    @Column(name = "views")
    public String views;

}
