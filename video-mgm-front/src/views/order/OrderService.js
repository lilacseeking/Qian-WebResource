import {baseUrl} from '../../config'
import axios from 'axios'

let base = baseUrl;
const getOrderListByPage = params => axios.post(`${base}/order/getOrderList`,params).then(res => res.data);

export const orderService = {
  base,
  getOrderListByPage
};
