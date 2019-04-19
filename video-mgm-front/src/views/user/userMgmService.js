import {baseUrl} from '../../config'
import axios from 'axios'

let base = baseUrl;
const getUserListByPage = params => axios.post(`${base}/user/list`,params).then(res => res.data);

export const userMgmService = {
  base,
  getUserListByPage
};
