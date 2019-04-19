package org.lilacseeking.video.videoapp.Model.PO;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Auther: lilacseeking
 * @Date: 2018/8/24 22:00
 * @Description:好友列表
 * 用户的好友列表
 */
@Data
@Builder
@Accessors(chain = true)
@Entity
@Table(name = "friend_list")
public class FriendList extends BaseEntityPO {
    /**
     *
     */
    @Column(name = "friend_id")
    public Long friendId;

}
