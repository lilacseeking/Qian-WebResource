package org.lilacseeking.video.videoapp.Model.DTO;

import lombok.Data;
import lombok.experimental.Accessors;
import org.lilacseeking.video.videoapp.Eumns.YesOrNoEnum;

import java.util.Date;

/**
 * @Auther: lilacseeking
 * @Date: 2019/3/24 10:17
 * @Description:
 */
@Data
@Accessors(chain = true)
public class PPTDTO {

    /**
     * id
     */
    private Long id;
    /**
     * 创建日期
     */
    private Date gmtCreate;

    /**
     * 图片名称
     */
    public String name;

    /**
     * 图片描述
     */
    public String description;

    /**
     * 是否启用
     */
    public YesOrNoEnum isUse;

    /**
     * 图片地址
     */
    public String url;
}
