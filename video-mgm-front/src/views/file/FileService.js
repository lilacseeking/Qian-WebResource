import axios from 'axios';
import {baseUrl} from '../../config'

// 上传视频
const applyTeacher = params => axios.post(`${base}/teacher/applyTeacher`,params).then(res => res.data);

// 上传图片
const applyTeacher = params => axios.post(`${base}/teacher/applyTeacher`,params).then(res => res.data);

export const fileService = {
  getMeunList: getMeunList,
  envDefault: envDefault,
  getSystemStatus: getSystemStatus,

}
