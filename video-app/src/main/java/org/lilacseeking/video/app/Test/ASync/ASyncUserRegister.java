package org.lilacseeking.video.app.Test.ASync;


import com.alibaba.fastjson.JSON;
import org.lilacseeking.video.infrastructure.Model.DTO.ContentDTO;
import org.lilacseeking.video.app.Model.DTO.CourseAndContentDTO;
import org.lilacseeking.video.infrastructure.Model.DTO.CourseDTO;
import org.lilacseeking.video.app.Model.DTO.RegisterDTO;
import org.lilacseeking.video.app.Test.UserTest;

/**
 * @Auther: lilacseeking
 * @Date: 2018/10/20 22:53
 * @Description:
 */
public class ASyncUserRegister implements Runnable {

    @Override
    public void run() {
        new UserTest().register();
    }

//    public static void main(String[] args) {
//
//        // 课程信息赋值
//        CourseDTO courseDTO = new CourseDTO();
//        courseDTO.setName("张老师带你学Java");
//        courseDTO.setDescription("由浅入深，从深入浅。");
//        courseDTO.setDiscount(0.9);
//        courseDTO.setTags("后端技术");
//        courseDTO.setTeacher(1L);
//        // 章节信息赋值
//        ContentDTO contentDTO = new ContentDTO();
//        contentDTO.setCourseId(1L);
//        contentDTO.setCourceName("张老师带你学Java");
//        contentDTO.setChapterId("第一章");
//        contentDTO.setChapterNane("绪论");
//        contentDTO.setChapterDescription("Java是世界上最好的编程语言。");
//        contentDTO.setIsFree("Y");
//        contentDTO.setUrl("http://lilacseeking.oss-cn-beijing.aliyuncs.com/0c7c7c51-6fe2-4526-9ba9-afcd5a2d9217.avi");
//        contentDTO.setTime("20:35");
//        contentDTO.setViews("5478");
//        contentDTO.setFileName("安装编译环境");
//
//        ArrayList<ContentDTO> contentDTOArrayList = new ArrayList<ContentDTO>();
//        contentDTOArrayList.add(contentDTO);
//        contentDTOArrayList.add(contentDTO);
//        contentDTOArrayList.add(contentDTO);
//        CourseAndContentDTO courseAndContentDTO = new CourseAndContentDTO();
//        courseAndContentDTO.setCourseDTO(courseDTO);
//        courseAndContentDTO.setContentDTOList(contentDTOArrayList);
//        System.out.println(JSON.toJSONString(courseAndContentDTO));
//    }
}
