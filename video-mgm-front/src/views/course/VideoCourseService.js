
import axios from 'axios';
import {baseUrl} from '../../../config'

let base = baseUrl;

//获取列表
const getList = params => axios.post(`${base}/tradeQuery/subAccountIn`, params).then(res => res.data);

export const subAccountInQueryService = {
  base,
  getList,
}
