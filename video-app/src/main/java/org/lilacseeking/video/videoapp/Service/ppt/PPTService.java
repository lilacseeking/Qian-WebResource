package org.lilacseeking.video.videoapp.Service.ppt;

import com.alibaba.fastjson.JSONObject;
import org.lilacseeking.video.videoapp.Model.DTO.PPTDTO;
import java.util.List;

/**
 * @Auther: lilacseeking
 * @Date: 2019/3/24 10:15
 * @Description:
 */
public interface PPTService {

    /**
     * 获取幻灯片列表
     * @return
     */
    List<PPTDTO> getPPTList(JSONObject filter);
}
