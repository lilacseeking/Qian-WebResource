package org.lilacseeking.video.videoapp.Service.Impl.video;

import com.alibaba.fastjson.JSONObject;
import org.lilacseeking.video.videoapp.Dao.video.VideoCourseRepository;
import org.lilacseeking.video.videoapp.Eumns.ErrorCodeEumn;
import org.lilacseeking.video.videoapp.Model.PO.VideoCoursePO;
import org.lilacseeking.video.videoapp.Service.video.VideoCourseService;
import org.lilacseeking.video.videoapp.Utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.support.Assert;

/**
 * @Auther: lilacseeking
 * @Date: 2018/10/18 22:48
 * @Description:课程相关接口实现类
 */
@Service
public class VideoCourseServiceImpl implements VideoCourseService {

    @Autowired
    private VideoCourseRepository videoCourseRepository;

    /**
     * 保存课程信息
     * @param videoCoursePO
     * @return
     */
    public VideoCoursePO addVideoClass(VideoCoursePO videoCoursePO){
        return videoCourseRepository.saveOrUpdate(videoCoursePO);
    }

    /**
     * 获取课程列表
     *
     * @param filter
     * @return
     */
    @Override
    public Page getCourseList(JSONObject filter) {
        Integer page = filter.getInteger("page");
        Integer rows = filter.getInteger("rows");
        Assert.isTrue(page!=null && rows!=null,ErrorCodeEumn.PAGE_INFO_FORMAT_WRONG.getName());
        return videoCourseRepository.getCourseList(filter,new Page(page,rows));
    }
}
