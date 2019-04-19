/**
 * Created by lilacseeking on 2018/5/15.
 */
var env = process.env.NODE_ENV
let httpRes = '';
if (env === 'development') {
     // httpRes = 'http://47.107.110.132:8077';
     httpRes = 'http://localhost:8077';
}  else if (env === 'uat') {
    httpRes = 'http://47.107.110.132:8077';
} else if (env === 'uprod') {
  httpRes = 'http://47.107.110.132:8077';
}else if (env === 'production'){
    httpRes = 'http://47.107.110.132:8077';
} else {
    httpRes = '';
}
export const baseUrl = httpRes;
