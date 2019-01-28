/**
 * @Author gedonghua
 * @Date 2018/10/19 16:33
 */
import axios from 'axios';
import {baseUrl} from '../../config'

let base = baseUrl;

//获取列表
const hello = params => axios.post(`${base}/first/hello`, params).then(res => res.data);

export const helloWorldService = {
  base,
  hello,
}
