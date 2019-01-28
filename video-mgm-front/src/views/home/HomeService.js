/**
 * Created by T-lih on 2017/7/5.
 */
import axios from 'axios';
import {baseUrl} from '../../config'

let base = baseUrl;
let envDefault_ = true;

if (baseUrl.indexOf('pay-mgm') > 0) {
  envDefault_ = false;
  base = baseUrl.replace(/pay-mgm/, '');

}
const envDefault = envDefault_;
const getMeunList = params => {
  return axios.post(`${base}menu/pay-mgm`, params).then(res => res.data);
}
const getSystemStatus = params => {
  return axios.get(`${baseUrl}/noauthority/getSystemStatus`).then(res => res.data);
}
export const homeService = {
  getMeunList: getMeunList,
  envDefault: envDefault,
  getSystemStatus: getSystemStatus,

}
