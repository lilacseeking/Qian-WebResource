import request from './http'

export function getAllCourse(data) {
  return request({
    url: '/course/getAllCourse',
    method: 'post',
    data
  })
}

export function login(data) {
  return request({
    url: '/user/loginByPwd',
    method: 'post',
    data
  })
}

export function loginByMes(data) {
  return request({
    url: '/user/mobileLogin',
    method: 'post',
    data
  })
}

export function sendMobile(data) {
  return request({
    url: '/user/sendMobileCode',
    method: 'post',
    data
  })
}

export function getPPTList(data) {
  return request({
    url: '/ppt/getPPTList',
    method: 'post',
    data
  })
}

export function loginOut(data) {
  return request({
    url: '/user/loginOut',
    method: 'post',
    data
  })
}
export function getCourseList(data) {
  return request({
    url: '/content/getContentList',
    method: 'post',
    data
  })
}

export function getCourseListByUser(data) {
  return request({
    url: '/course/getCourseListByUser',
    method: 'post',
    data
  })
}

export function getOrderList(data) {
  return request({
    url: '/order/getOrderList',
    method: 'post',
    data
  })
}
export function register(data) {
  return request({
    url: '/user/register',
    method: 'post',
    data
  })
}

export function sendFiles(form) {
  return request({
    url: '/file/moreFileUpload',
    method: 'post',
    data: form
  })
}

export function applyTeacher(data) {
  return request({
    url: '/teacher/applyTeacher',
    method: 'post',
    data
  })
}

export function getCourseInfo(data) {
  return request({
    url: '/course/getCourseInfo',
    method: 'post',
    data
  })
}
export function createOrder(data) {
  return request({
    url: '/order/createOrder',
    method: 'post',
    data
  })
}
export function payOrder(data) {
  return request({
    url: '/order/payOrder',
    method: 'post',
    data
  })
}
export function closeOrder(data) {
  return request({
    url: '/order/closeOrder',
    method: 'post',
    data
  })
}
// 用户学习进度更新
export function updateUserCourseRate(data) {
  return request({
    url: '/course/updateUserCourseRate',
    method: 'post',
    data
  })
}
