package org.lilacseeking.video.core.Course.Model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.lilacseeking.video.infrastructure.Model.PO.BaseEntityPO;
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
