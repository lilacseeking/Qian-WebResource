package org.lilacseeking.video.videoapp.Model.DTO;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: lilacseeking
 * @Date: 2019/3/18 23:03
 * @Description: 课程和对应章节信息
 */
@Data
@Accessors(chain = true)
public class CourseAndContentDTO implements Serializable {

    /**
     * 课程DTO
     */
    private CourseDTO courseDTO;

    /**
     * 章节信息
     */
    private List<ContentDTO> contentDTOList;

}
