package org.lilacseeking.video.infrastructure.Model.DTO;

import lombok.Data;
import lombok.experimental.Accessors;
import org.lilacseeking.video.infrastructure.enums.YesOrNoEnum;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Auther: lilacseeking
 * @Date: 2018/10/18 22:43
 * @Description:
 */

@Data
@Accessors(chain = true)
public class CourseDTO implements Serializable {
    /**
     * 课程编号
     */
    private Long id;
    /**
     * 创建日期
     */
    private Date gmtCreate;
    /**
     * 创建人
     */
    private Integer creater;
    /**
     * 课程名称
     */
    public String name;

    /**
     * 主讲人
     */
    public Long teacher;

    /**
     * 课程描述
     */
    public String description;

    /**
     *  课程标签
     */
    public String tags;

    /**
     * 该课程是否免费
     */
    public YesOrNoEnum isFree;

    /**
     * 该课程的折扣
     */
    public BigDecimal discount;

    /**
     * 课程价格
     */
    private BigDecimal price;

    /**
     * 课程缩略图
     */
    private String thumbnail;
    /**
     * 当前用户是否拥有该课程
     */
    private YesOrNoEnum userStatus;
}
