package org.lilacseeking.video.videoapp.Service.Impl.course;

import com.alibaba.fastjson.JSONObject;
import org.lilacseeking.video.videoapp.Eumns.ErrorCodeEumn;
import org.lilacseeking.video.videoapp.Eumns.YesOrNoEnum;
import org.lilacseeking.video.videoapp.Model.DTO.ContentDTO;
import org.lilacseeking.video.videoapp.Model.DTO.CourseDTO;
import org.lilacseeking.video.videoapp.Model.DTO.CourseUserDTO;
import org.lilacseeking.video.videoapp.Model.PO.CourseUserPO;
import org.lilacseeking.video.videoapp.Model.PO.VideoCoursePO;
import org.lilacseeking.video.videoapp.Repository.course.CourseUserRepository;
import org.lilacseeking.video.videoapp.Service.course.CourseUserService;
import org.lilacseeking.video.videoapp.Service.course.VideoContentService;
import org.lilacseeking.video.videoapp.Utils.BeanCopyUtil;
import org.lilacseeking.video.videoapp.Utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.support.Assert;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static java.math.BigDecimal.ROUND_UP;

/**
 * @Auther: lilacseeking
 * @Date: 2019/3/24 15:27
 * @Description:
 */
@Service
public class CourseUserServiceImpl implements CourseUserService {

    @Autowired
    private CourseUserRepository courseUserRepository;

    @Autowired
    private VideoContentService videoContentService;
    /**
     * 新增用户课程关系
     *
     * @param courseUserDTO
     * @return
     */
    @Override
    public CourseUserDTO addCourseUser(CourseUserDTO courseUserDTO) {

        CourseUserPO courseUserPO = new CourseUserPO();
        BeanCopyUtil.copyPropertiesIgnoreNull(courseUserDTO,courseUserPO);
        courseUserPO = courseUserRepository.saveOrUpdate(courseUserPO);
        BeanCopyUtil.copyPropertiesIgnoreNull(courseUserPO,courseUserDTO);
        return courseUserDTO;
    }

    /**
     * 查询用户课程关系
     *
     * @param filter
     * @return
     */
    @Override
    public CourseUserDTO getCourseUserInfo(JSONObject filter) {
        Long userId = filter.getLong("userId");
        Long courseId = filter.getLong("courseId");
        Assert.isTrue(userId!=null && courseId!=null,ErrorCodeEumn.COURSE_ID_AND_USER_ID_NOT_NULL.getName());
        CourseUserPO courseUserInfo = courseUserRepository.getCourseUserInfo(userId, courseId);
        if (null == courseUserInfo){
            return null;
        }
        CourseUserDTO courseUserDTO = new CourseUserDTO();
        BeanCopyUtil.copyPropertiesIgnoreNull(courseUserInfo,courseUserDTO);
        return courseUserDTO;
    }

    /**
     * 查询用户课程列表
     *
     * @param filter
     * @return
     */
    @Override
    public Page getCourseUserList(JSONObject filter) {
        Integer page = filter.getInteger("page");
        Integer rows = filter.getInteger("rows");
        Assert.isTrue(page!=null&& rows!=null,ErrorCodeEumn.PAGE_INFO_FORMAT_WRONG.getName());
        return courseUserRepository.getCourseUserList(filter,new Page(page,rows));
    }

    /**
     * 更新用户课程关系
     * @param courseUserDTO
     * @return
     */
    @Override
    public CourseUserPO updateCourseUserPO(CourseUserDTO courseUserDTO){
        CourseUserPO courseUserInfo = courseUserRepository.getCourseUserInfo(courseUserDTO.getUserId(), courseUserDTO.getCourseId());
        BeanCopyUtil.copyPropertiesIgnoreNull(courseUserDTO,courseUserInfo);
        return courseUserRepository.saveOrUpdate(courseUserInfo);
    }

    /**
     * 更新用户学习进度
     *
     * @param contentDTO
     * @return
     */
    @Override
    public CourseUserDTO updateUserCourseRate(ContentDTO contentDTO) {
        // 获取课程章节列表
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setId(contentDTO.getCourseId());
        List<ContentDTO> contentList = videoContentService.getContentList(courseDTO);
        BigDecimal playRate = BigDecimal.ZERO;
        playRate = new BigDecimal((float)contentDTO.getChapterId() / contentList.size()).setScale(2, RoundingMode.DOWN);
        JSONObject filter = new JSONObject();
        filter.put("userId",contentDTO.getUserId());
        filter.put("courseId",contentDTO.getCourseId());
        CourseUserDTO courseUserInfo = getCourseUserInfo(filter);
        courseUserInfo.setCompletion(playRate);
        if (playRate.compareTo(BigDecimal.ONE) == 0){
            courseUserInfo.setIsFinish(YesOrNoEnum.Y);
        }
        CourseUserPO courseUserPO = updateCourseUserPO(courseUserInfo);
        CourseUserDTO courseUserDTO = new CourseUserDTO();
        BeanCopyUtil.copyPropertiesIgnoreNull(courseUserPO,courseUserDTO);
        // 使用当前章节计算学习进度
        // 更新用户课程信息
        // 最后一节将课程学习进度置为完成
        return courseUserDTO;
    }
}
