package org.lilacseeking.video.videoapp.Model.PO;

import lombok.Data;
import lombok.experimental.Accessors;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @Auther: lilacseeking
 * @Date: 2018/8/23 22:29
 * @Description:视频课程表
 * 此表为视频课程表，显示课程的详细信息
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "video_class")
public class VideoCoursePO extends BaseEntityPO {
    /**
     * 课程名称
     */
    @Column(name = "name",nullable = false)
    public String name;

    /**
     * 主讲人
     */
    @Column(name = "teacher", nullable = false)
    public Long teacher;

    /**
     * 课程描述
     */
    @Column(name = "description")
    public String description;

    /**
     *  课程标签
     */
    @Column(name = "tags")
    public String tags;

    /**
     * 该课程是否免费
     */
    @Column(name = "is_free", nullable = false)
    public Boolean isFree = true;
    /**
     * 课程价格
     */
    @Column(columnDefinition = "decimal(15,2) default 0.00")
    public BigDecimal price;

    /**
     * 该课程的折扣
     */
    @Column(name = "discount",nullable = false, precision = 2, scale = 1)
    public Double discount = 0.0;
    /**
     * 课程的缩略图
     */
    @Column(name = "thumbnail", nullable = false)
    public String thumbnail;
}
