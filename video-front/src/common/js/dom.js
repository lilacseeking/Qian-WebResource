// 添加class
export function addClass(el, className) {
  if (hasClass(el, className)) {
    return
  }
  let newClass = el.className.split('')
  newClass.push(className)
  el.className = newClass.join('')
}

export function hasClass(el, className) {
  let reg = new RegExp('(^|\\s)' + className + '(\\s|$)')
  return reg.test(el.className)
}

// 获取自定义属性上的值
export function getData(el, name, val) {
  const prefix = 'data-'
  name = prefix + name
  if (val) {
    return el.setAttribute(name, val)
  } else {
    return el.getAttribute(name)
  }
}

// 浏览器能力检测 (前缀判断)
let elementStyle = document.createElement('div').style

let vendor = (() => {
  let transformNames = {
    webkit: 'webkitTransform',
    Moz: 'MozTransform',
    O: 'OTransform',
    ms: 'msTransform',
    standard: 'transform'
  }

  for (let key in transformNames) {
    if (elementStyle[transformNames[key]] !== undefined) {
      return key
    }
  }

  return false
})()

export function prefixStyle(style) {
  if (vendor === false) {
    return false
  }

  if (vendor === 'standard') {
    return style
  }

  return vendor + style.charAt(0).toUpperCase() + style.substr(1)
}

// 时间戳获取年月日
export function getLocalTimer(time) {
  let date = new Date(time * 1000)
  let year = date.getFullYear()
  let month = date.getMonth() + 1
  let day = date.getDate()
  let hours = date.getHours()
  let min = date.getMinutes()
  return `${year}年${month < 10 ? '0' + month : month}月${day < 10 ? '0' + day : day}日 ${hours < 10 ? '0' + hours : hours}:${min < 10 ? '0' + min : min}`
}

// 获取当前时间
export function currentTimer() {
  let date = new Date()
  let year = date.getFullYear()
  let month = date.getMonth() + 1
  let day = date.getDate()
  let hours = date.getHours()
  let min = date.getMinutes()
  return `${year}年${month < 10 ? '0' + month : month}月${day < 10 ? '0' + day : day}日 ${hours < 10 ? '0' + hours : hours}:${min < 10 ? '0' + min : min}`
}
