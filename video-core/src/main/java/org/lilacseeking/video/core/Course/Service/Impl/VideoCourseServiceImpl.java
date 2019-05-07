package org.lilacseeking.video.core.Course.Service.Impl;

import com.alibaba.fastjson.JSONObject;
import org.lilacseeking.video.core.Course.Model.VideoCoursePO;
import org.lilacseeking.video.core.Course.Repository.VideoCourseRepository;
import org.lilacseeking.video.core.Course.Service.CourseUserService;
import org.lilacseeking.video.core.Course.Service.VideoContentService;
import org.lilacseeking.video.core.Course.Service.VideoCourseService;
import org.lilacseeking.video.infrastructure.Model.DTO.CourseDTO;
import org.lilacseeking.video.infrastructure.enums.ErrorCodeEumn;
import org.lilacseeking.video.infrastructure.utils.BeanCopyUtil;
import org.lilacseeking.video.infrastructure.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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
        // TODO 工程改造
//        videoCoursePO.setTeacher(UserUtils.getUserSession().getId());
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
