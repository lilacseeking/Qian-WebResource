package org.lilacseeking.video.videoapp.Service.Impl.course;

import com.alibaba.fastjson.JSONObject;
import org.lilacseeking.video.videoapp.Eumns.YesOrNoEnum;
import org.lilacseeking.video.videoapp.Model.DTO.ContentDTO;
import org.lilacseeking.video.videoapp.Model.DTO.CourseUserDTO;
import org.lilacseeking.video.videoapp.Model.PO.CourseUserPO;
import org.lilacseeking.video.videoapp.Repository.course.VideoCourseRepository;
import org.lilacseeking.video.videoapp.Eumns.ErrorCodeEumn;
import org.lilacseeking.video.videoapp.Model.DTO.CourseDTO;
import org.lilacseeking.video.videoapp.Model.PO.VideoCoursePO;
import org.lilacseeking.video.videoapp.Service.course.CourseUserService;
import org.lilacseeking.video.videoapp.Service.course.VideoContentService;
import org.lilacseeking.video.videoapp.Service.course.VideoCourseService;
import org.lilacseeking.video.videoapp.Utils.BeanCopyUtil;
import org.lilacseeking.video.videoapp.Utils.Page;
import org.lilacseeking.video.videoapp.Utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.support.Assert;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Auther: lilacseeking
 * @Date: 2018/10/18 22:48
 * @Description:课程相关接口实现类
 */
@Service
public class VideoCourseServiceImpl implements VideoCourseService {

    @Autowired
    private VideoCourseRepository videoCourseRepository;

    @Autowired
    private VideoContentService videoContentService;

    @Autowired
    private CourseUserService courseUserService;

    /**
     * 保存课程信息
     * @param videoCoursePO
     * @return
     */
    public VideoCoursePO addVideoClass(VideoCoursePO videoCoursePO){
        // 当前用户信息赋值
        videoCoursePO.setTeacher(UserUtils.getUserSession().getId());
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

    /**
     * 获取课程详情
     *
     * @param courseDTO
     * @return
     */
    @Override
    public VideoCoursePO getCourseInfo(CourseDTO courseDTO) {
        Assert.isTrue(null != courseDTO.getId(),ErrorCodeEumn.COURSE_ID_NOT_NULL.getName());
        return videoCourseRepository.getCourseInfo(courseDTO.getId());
    }

    /**
     * 下架课程
     *
     * @param courseDTO
     * @return
     */
    @Override
    public CourseDTO shelveCourse(CourseDTO courseDTO) {
        VideoCoursePO videoCoursePO = new VideoCoursePO();
        BeanCopyUtil.copyPropertiesIgnoreNull(courseDTO,videoCoursePO);
        videoCoursePO = videoCourseRepository.getCourseInfo(courseDTO.getId());
        Assert.isTrue(null!= videoCoursePO.getId(),ErrorCodeEumn.COURSE_ID_NOT_NULL.getName());
        videoCoursePO.setAchieve(1);
        videoCoursePO = videoCourseRepository.saveOrUpdate(videoCoursePO);
        BeanCopyUtil.copyPropertiesIgnoreNull(videoCoursePO,courseDTO);
        return courseDTO;
    }

}
