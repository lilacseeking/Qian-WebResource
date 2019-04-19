
import axios from 'axios';
import {baseUrl} from '../../config'

let base = baseUrl;

// 获取列表
const getCourseList = params => axios.post(`${base}/course/getAllCourse`, params).then(res => res.data);
// 上传课程
const saveCourseAndContentList = params => axios.post(`${base}/course/addVideoClass`, params).then(res => res.data);
// 下架课程
const shelveCourse = params => axios.post(`${base}/course/shelveCourse`, params).then(res => res.data);

export const courseService = {
  base,
  getCourseList,
  saveCourseAndContentList,
  shelveCourse
};
