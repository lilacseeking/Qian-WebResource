package org.lilacseeking.video.videoapp.Model.PO;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Auther: lilacseeking 未验证
 * @Date: 2018/8/23 22:54
 * @Description:课程用户表
 * 此表为视频和用户之间的中间表，表示用户学习的课程以及学习情况。
 */
@Data
@Builder
@Accessors(chain = true)
@Entity
@Table(name = "course_user")
public class CourseUserPO extends BaseEntityPO {

    /**
     * 目录Id
     */
    @Column(name = "contents_id")
    public String contentsId;

    /**
     * 用户Id
     */
    @Column(name = "user_id")
    public String userId;

    /**
     * 是否完成
     */
    @Column(name = "is_finish")
    public String isFinish;

    /**
     * 完成度
     */
    @Column(name = "completion")
    public String completion;

}
