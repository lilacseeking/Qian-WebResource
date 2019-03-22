package org.lilacseeking.video.videoapp.Model.DTO;

import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: lilacseeking
 * @Date: 2018/10/18 22:43
 * @Description:
 */

@Data
public class CourseDTO implements Serializable {

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
    public Boolean isFree = true;

    /**
     * 该课程的折扣
     */
    public Double discount;
}
