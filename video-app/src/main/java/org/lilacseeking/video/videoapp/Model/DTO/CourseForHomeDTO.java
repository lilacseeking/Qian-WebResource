package org.lilacseeking.video.videoapp.Model.DTO;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: lilacseeking
 * @Date: 2019/3/20 21:14
 * @Description:
 */
@Data
public class CourseForHomeDTO implements Serializable {

    /**
     * 菜单
     */
    private String menu;

    /**
     * 课程列表
     */
    private List<CourseDTO> courseDTOList;

}
