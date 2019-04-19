import {baseUrl} from '../../config'
import axios from 'axios'

let base = baseUrl;
// 获取教师列表
const getTeacherListByPage = params => axios.post(`${base}/teacher/getTeacherList`,params).then(res => res.data);
// 教师审核
const auditTeacher = params => axios.post(`${base}/teacher/auditTeacher`,params).then(res => res.data);
// 申请成为教师
const applyTeacher = params => axios.post(`${base}/teacher/applyTeacher`,params).then(res => res.data);

export const teacherService = {
  base,
  getTeacherListByPage,
  auditTeacher,
  applyTeacher
};
