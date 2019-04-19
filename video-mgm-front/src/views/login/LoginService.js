/**
 * Created by T-lih on 2017/7/3.
 */
import axios from 'axios';
import {baseUrl} from '../../config'

let base = baseUrl;
let envDefault_ = true;
// //从授权登录
// if (baseUrl.indexOf('pay-mgm') > 0 ) {
//     base = baseUrl.replace(/pay-mgm/,'yudian_authz');
//     envDefault_ = false;
// }

const envDefault = envDefault_;
const sendSmsCode = params =>{return axios.post(`${base}/user/sendMobileCode`,params).then(res =>res.data);}
const loginToGetToken = params => {return axios.post(`${base}/user/mobileLogin`,params).then(res => res.data);}
const loginToGetTokenByPwd = params => {return axios.post(`${base}/user/loginByPwd`,params).then(res => res.data);}

export const loginService = {
    sendSmsCode,
    loginToGetToken,
    loginToGetTokenByPwd,
    envDefault: envDefault,
}
