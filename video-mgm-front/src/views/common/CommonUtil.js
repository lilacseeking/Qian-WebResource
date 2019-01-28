/**
 * CommonUtil
 * Created by T-lih on 2017/7/24.
 */
import util from '../../common/js/util'

//格式化日期 yyyy-MM-dd
const  formatDate = date => {
    return util.formatDate.format(date,'yyyy-MM-dd');
};

//格式化日期时间 yyyy-MM-dd hh:mm:ss
const formatDateTime = date => {
    if (date) {
        return util.formatDate.format(date,'yyyy-MM-dd hh:mm:ss');
    }
    return '';
};

//格式化日期时间 yyyy-MM-dd hh:mm
const formatDateTimeByMin = date => {
    if (date) {
        return util.formatDate.format(date,'yyyy-MM-dd hh:mm');
    }
    return '';
};

//格式化枚举选项
const formatEnumOptions = (value, source) => {
    for(let item of source){
        if(value == item.value){
            return item.label;
        }
    }
    return'';
};

//格式化金钱
const formatMoney = money => {
    if(Object.is(money, undefined)) return '0.00';
    money = Number(money.toString()).toFixed(2).toString();
    if(/^\d{4,}\.\d+$/g.test(money)) {
        const reverse = money.split("").reverse().join("");
        const index = reverse.indexOf('.');
        let result = reverse.substring(0, index+1);
        const intPart = reverse.substring(index+1);
        for(let i= 0 ; i < intPart.length ; i++) {
            result = result.concat((i > 0 && Object.is(i%3, 0))?','.concat(intPart.charAt(i)):intPart.charAt(i));
        }
        return result.split("").reverse().join("");
    }
    return money;
};

//反格式化金钱
const deformatMoney = money => {
    if(Object.is(typeof money,'string')) {
        return money.replace(/,/g,'');
    }
    return money;
};

//禁用起始日期选项
const disabledStartDate = (time, endDate) => {
    //今天或者截止日期之后的时间不能选择
    if(endDate){
        return time > new Date(endDate.replace(/-/g,'/'));
    }else{
        return time > new Date();
    }
};

//禁用截止日期选项
const disabledEndDate = (time, startDate) => {
    //只能选择起止日期与今日之间的日期
    if(startDate){
        return time < new Date(startDate.replace(/-/g,'/')) || time > new Date();
    }else{
        return time > new Date();
    }
};

//字符串转千分位
const formatThousands = str => {
    if (str === '') {
        return '';
    } else {
        let pattern = /(?=((?!\b)\d{3})+$)/g;
        str = Number(str).toFixed(2);
        return str.substr(0,str.length-3).toString().replace(pattern,',')+str.substr(str.length-3,str.length);
    }
}

//千分位转数字
const deFormatThousands = num => {
    num = num.replace(/[ ]/g, ""); // 去除空格  
    num = num.replace(/,/gi,''); // 全局替换逗号
    return num >> 0;  // 去小数点
};


// 金钱格式化 三位10,000.00
const formatMoneyThree = money=>{
    if (money) {
        if(Object.is(typeof money, undefined) || money == 'undefined') return '0.00';
        let pattern = /(?=((?!\b)\d{3})+$)/g;
        let str = Number(money).toFixed(2);
        return str.substr(0,str.length-3).toString().replace(pattern,',')+str.substr(str.length-3,str.length);
    } else {
        return '0.00';
    }
};
// 期限单位
const termUnit = termUnit => {
    let unit = '';
    if (termUnit === 'Y') {
        unit = '年';
    } else if (termUnit === 'M') {
        unit = '个月';
    } else if (termUnit === 'D') {
        unit = '天';
    } else if (termUnit === 'Q') {
        unit = '季度';
    }
    return unit;
};
// 本金占用方式
const pricipalCalcBasedList = [
    {value: 'P',label: '按比率'},
    {value: 'F',label: '按固定金额'}
];

const label = (list,value) => {
    let val_ = '';
    list.forEach(val =>{
        if (val.value === value) {
            val_ = value+'-'+val.label;
            return;
        }
    });
    return val_;
};

const formatMoneySign = money =>{
    if (money) {
        if(Object.is(typeof money, undefined) || money == 'undefined') return '￥0.00';
        let pattern = /(?=((?!\b)\d{3})+$)/g;
        let sign = '￥';
        if (money < 0) {
            money = -1*money;
            sign = '-￥';
        }
        let str = Number(money).toFixed(2);
        return sign+str.substr(0,str.length-3).toString().replace(pattern,',')+str.substr(str.length-3,str.length);
    } else {
        return '￥0.00';
    }
};

const sort = prop =>{
    return function (obj1, obj2) {
        var val1 = obj1[prop];
        var val2 = obj2[prop];
        if (!isNaN(Number(val1)) && !isNaN(Number(val2))) {
            val1 = Number(val1);
            val2 = Number(val2);
        }
        if (val1 < val2) {
            return -1;
        } else if (val1 > val2) {
            return 1;
        } else {
            return 0;
        }
    }
};
const statusList = [
    {value: 'S', label: '成功'},
    {value: 'F', label: '失败'},
    {value: 'I',label: '处理中'},
    {value: 'P',label: '部分失败'},
    {value: 'N',label: '未处理'}
];

const isRepayTypeIsR12List = [
    {value: true, label: '随借随还'},
    {value: false, label: '非随借随还'}
];

const getValueByKey = (from, ...selectors) =>
  [...selectors].map(s =>
    s
      .replace(/\[([^\[\]]*)\]/g, '.$1.')
      .split('.')
      .filter(t => t !== '')
      .reduce((prev, cur) => prev && prev[cur], from)
  );

//深度拷贝
const deepClone = data => {
    var type = getType(data);
    var obj;
    if(type === 'array'){
        obj = [];
    } else if(type === 'object'){
        obj = {};
    } else {
        //不再具有下一层次
        return data;
    }
    if(type === 'array'){
        for(var i = 0, len = data.length; i < len; i++){
            obj.push(deepClone(data[i]));
        }
    } else if(type === 'object'){
        for(var key in data){
            obj[key] = deepClone(data[key]);
        }
    }
    return obj;
}

//获取对象类型
const getType = obj => {
    //tostring会返回对应不同的标签的构造函数
    var toString = Object.prototype.toString;
    var map = {
        '[object Boolean]'  : 'boolean',
        '[object Number]'   : 'number',
        '[object String]'   : 'string',
        '[object Function]' : 'function',
        '[object Array]'    : 'array',
        '[object Date]'     : 'date',
        '[object RegExp]'   : 'regExp',
        '[object Undefined]': 'undefined',
        '[object Null]'     : 'null',
        '[object Object]'   : 'object'
    };
    if(obj instanceof Element) {
        return 'element';
    }
    return map[toString.call(obj)];
}
const formatJson = (filterVal, jsonData) =>{
    return jsonData.map(v => filterVal.map(j => v[j]));
}

export default {
    formatDate,
    formatDateTime,
    formatEnumOptions,
    formatMoney,
    formatDateTimeByMin,
    deformatMoney,
    disabledStartDate,
    disabledEndDate,
    formatThousands,
    deFormatThousands,
    formatMoneyThree,
    termUnit,
    pricipalCalcBasedList,
    label,
    formatMoneySign,
    sort,
    statusList,
    getValueByKey,
    deepClone,
    isRepayTypeIsR12List,
    formatJson
}