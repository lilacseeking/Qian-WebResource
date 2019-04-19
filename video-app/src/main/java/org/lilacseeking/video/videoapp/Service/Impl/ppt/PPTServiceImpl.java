package org.lilacseeking.video.videoapp.Service.Impl.ppt;

import com.alibaba.fastjson.JSONObject;
import org.lilacseeking.video.videoapp.Model.DTO.PPTDTO;
import org.lilacseeking.video.videoapp.Model.PO.PPTPO;
import org.lilacseeking.video.videoapp.Repository.ppt.PPTRepository;
import org.lilacseeking.video.videoapp.Service.ppt.PPTService;
import org.lilacseeking.video.videoapp.Utils.BeanCopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: lilacseeking
 * @Date: 2019/3/24 10:15
 * @Description:
 */
@Service
public class PPTServiceImpl implements PPTService {

    @Autowired
    private PPTRepository pptRepository;
    /**
     * 获取幻灯片列表
     *
     * @return
     */
    @Override
    public List<PPTDTO> getPPTList(JSONObject filter) {
        List<PPTPO> pptList = pptRepository.getPPTList(filter);
        List result = new ArrayList<PPTDTO>();
        for (PPTPO pptPO : pptList){
            PPTDTO pptDTO = new PPTDTO();
            BeanCopyUtil.copyPropertiesIgnoreNull(pptPO,pptDTO);
            result.add(pptDTO);
        }
        return result;
    }
}
