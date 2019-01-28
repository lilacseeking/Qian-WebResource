/**
 * 面向服务端的Common
 * Created by T-lih on 2017/7/24.
 */
import axios from 'axios';
import {baseUrl} from '../../config'

// 金融条件
const getEnvParamList = params => {return axios.get(`${baseUrl}/dropDown/getEnvParamList`).then(res => res.data);}
//偿还方式和周期
const getReplayTypeAndTermUnit = params =>{return axios.get(`${baseUrl}/dropDown/getReplayTypeAndTermUnit`).then(res => res.data);}
// 首付模型
const getDpParamList = params =>{return axios.get(`${baseUrl}/dropDown/getDpParamList`).then(res => res.data);}
// 费用模型
const getFreeModeList = params => {return axios.get(`${baseUrl}/dropDown/getFreeModeList`).then(res => res.data);}
// 保证金模型
const getSdParamList = params => {return axios.get(`${baseUrl}/dropDown/getSdParamList`).then(res => res.data);}
// 进件渠道 放款主体 放款账号
const getInitParam = params => {return axios.get(`${baseUrl}/funds/getInitParam`).then(res => res.data)}

const initParam = () => {
    let finaCoopCompNoList = [];
    let loanMainlList = [];
    let loanAccountList = [];
    let loanAccountInfo = {};

    getInitParam().then(res =>{
        if (res.code === 0) {
            if (res.content) {
                let {loanMain,coopCompany} = res.content;
                // 进件渠道
                finaCoopCompNoList.length = 0;
                Object.keys(coopCompany).forEach(key =>{
                    finaCoopCompNoList.push({value: key,label: coopCompany[key]});
                });

                loanMainlList.length = 0;
                loanAccountList.length = 0;
                // 放款主体和放款账号
                Object.keys(loanMain).forEach(val => {
                    let resObj = JSON.parse(loanMain[val]); // 字符串转对象
                    let mainName = resObj['PtpLoanMainParam']['mainName'];
                    let accountInfo = Array.from(resObj['PtpAccountInfo']);
                    let loanAccountInfoArr = [];
                    Object.keys(accountInfo).forEach(key =>{
                        let accountId = accountInfo[key]['accountId'];// accountNo
                        let appAccount = accountInfo[key]['appAccount'];// accountName
                        let accountNo = accountInfo[key]['accountNo'];
                        loanAccountList.push({value: accountId,label: appAccount,});
                        loanAccountInfoArr.push({value: accountId,label: appAccount, accountNo: accountNo});
                    });
                    loanAccountInfo[val] = loanAccountInfoArr;
                    loanMainlList.push({value: val,label: mainName});// 放款主体
                })
            }
        }
    });
    return {finaCoopCompNoList:finaCoopCompNoList,loanMainlList:loanMainlList,loanAccountList:loanAccountList,loanAccountInfo:loanAccountInfo}
}
export const commonService = {
    getEnvParamList: getEnvParamList,
    getReplayTypeAndTermUnit: getReplayTypeAndTermUnit,
    getDpParamList: getDpParamList,
    getFreeModeList: getFreeModeList,
    getSdParamList: getSdParamList,
    getInitParam: getInitParam,
    initParam:initParam
}
