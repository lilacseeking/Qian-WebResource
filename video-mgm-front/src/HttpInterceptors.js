import axios from 'axios'
import Vue from 'vue'
import VueRouter from 'vue-router'

// 超时时间
axios.defaults.timeout = 2000000;
axios.interceptors.request.use(
    config => {
        //config.headers['Content-Type'] = 'application/json;charset=UTF-8';
        var tokenData = JSON.parse(sessionStorage.getItem('user'));
        if (tokenData != null) {
            config.headers["token"] = tokenData.token;//;tokenData.username;
            config.headers["mobile"] = tokenData.mobile;
            config.headers["Content-Type"] = "Application/json";
        } else {
        }
        return config
    },
    error => {
        new Vue().$message({message: '加载超时',type: 'error'});
        return Promise.reject(error)
    }
);
axios.interceptors.response.use(
    data => {
        return data
    },
    error => {
        if (error.response) {
            switch (error.response.status) {
                case 401:
                    if (error.response.request.responseURL.indexOf("FineReport") == -1) {
                        sessionStorage.removeItem('user');
                        window.location.href = 'index.html#/login'
                    }
                    break;
                case 403:
                    sessionStorage.removeItem('user');
                    new Vue().$message({message: '非法访问',type: 'warning'});
                    window.location.href = 'index.html#/login';
                    break;
            }
        } else {
            new Vue().$message({message: '请求超时，请检查网络',type: 'warning'});
        }
        return Promise.reject(error)
    }
);

export default axios
