package org.lilacseeking.video.videoapp.Model.PO;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.lilacseeking.video.videoapp.Eumns.YesOrNoEnum;

import javax.persistence.*;

/**
 * @Auther: lilacseeking  未验证
 * @Date: 2018/8/23 23:04
 * @Description:幻灯片表
 * 首页显示的轮播图设置
 */
@Data
//@Builder
@Accessors(chain = true)
@Entity
@Table(name = "ppt")
public class PPTPO extends BaseEntityPO {
    /**
     * 图片名称
     */
    @Column(name = "name")
    public String name;

    /**
     * 图片描述
     */
    @Column(name = "description")
    public String description;

    /**
     * 是否启用
     */
    @Column(name = "is_use")
    @Enumerated(EnumType.STRING)
    public YesOrNoEnum isUse;

    /**
     * 图片地址
     */
    @Column(name = "url")
    public String url;

}
