package org.lilacseeking.video.infrastructure.Model.DTO;

import lombok.Data;
import lombok.experimental.Accessors;
import org.lilacseeking.video.infrastructure.enums.YesOrNoEnum;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Auther: lilacseeking
 * @Date: 2019/3/24 15:30
 * @Description:
 */
@Data
@Accessors(chain = true)
public class CourseUserDTO {
    /**
     * 学习Id
     */
    private Long id;
    /**
     * 创建日期
     */
    private Date gmtCreate;
    /**
     * 课程Id
     */
    private Long courseId;

    /**
     * 目录Id
     */
    public String contentsId;

    /**
     * 用户Id
     */
    public Long userId;

    /**
     * 是否完成
     */
    public YesOrNoEnum isFinish;

    /**
     * 完成度
     */
    public BigDecimal completion;

}
