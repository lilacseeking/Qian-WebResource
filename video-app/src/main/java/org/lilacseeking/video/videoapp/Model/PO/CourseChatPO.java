package org.lilacseeking.video.videoapp.Model.PO;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Auther: lilacseeking
 * @Date: 2018/8/24 22:03
 * @Description:课程讨论表
 * 课程的讨论区
 */
@Data
@Builder
@Accessors(chain = true)
@Entity
@Table(name = "class_chat")
public class CourseChatPO extends BaseEntityPO {

    /**
     * 用户Id
     */
    @Column(name = "user_id",nullable = false)
    public String userId;

    /**
     * 讨论内容
     */
    @Column(name = "text")
    public String text;
}
