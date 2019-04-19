<template>
  <div class="container">
    <div class="top">
      <div class="header">
        <img alt="" class="logo" src="../../assets/LOGO-01.png" />
      </div>
      <div class="desc">后台管理系统</div>
    </div>
    <div class="main">
      <el-tabs class="custom-tab" v-model="activeName">
        <el-tab-pane disabled label="" name="1"></el-tab-pane>
        <el-tab-pane disabled label="" name="2"></el-tab-pane>
        <el-tab-pane label="手机号登录" name="first">
        <el-form class="custom-form" label-position="left" label-width="70px" :model="loginUser" ref="mobilePhoneForm" :rules="rules">

          <el-form-item prop="mobile">
            <el-input @keyup.enter.native="loginByMobilePhone('mobilePhoneForm')" prefix-icon="el-icon-mobile-phone" v-model.trim="loginUser.mobile" auto-complete="off" placeholder="请输入手机号" clearable></el-input>
          </el-form-item>

          <el-form-item class="sendCodeBtn" prop="verifyCode">
            <el-input @keyup.enter.native="loginByMobilePhone('mobilePhoneForm')" prefix-icon="el-icon-message" v-model.trim="loginUser.verifyCode" style="width: 73%" placeholder="请输入验证码"></el-input>
            <el-button @click.prevent="getMobileCode" auto-complete="off" v-show="showTime" size="small">获取验证码</el-button>
            <el-button type="primary" auto-complete="off" v-show="!showTime">{{timeText}}</el-button>
          </el-form-item>
          <el-form-item>
            <el-checkbox v-model="autoLogin">自动登录</el-checkbox>
            <!--<a class="forgot">忘记密码</a>-->
          </el-form-item>
          <el-form-item>
            <el-button type="primary"  @click="loginByMobilePhone('mobilePhoneForm')" :loading="MobilePhoneLoginLoading" :disabled="disSubmit" style="width:100%;">登录</el-button>
            <!-- <el-button @click="handleReset">重置</el-button> -->
          </el-form-item>

          <!--<el-form-item>-->
            <!--<el-button @click="envDefaultClick" v-if="envDefault" style="width:100%;">开发环境直接登录</el-button>-->
          <!--</el-form-item>-->

        </el-form>
        </el-tab-pane>
        <el-tab-pane label="账户密码登录" name="second">
        <el-form class="custom-form" label-position="left" label-width="70px" :model="loginUser" ref="usernameForm" :rules="rules">

          <el-form-item prop="mobile">
            <el-input @keyup.enter.native="loginByUsername('usernameForm')" v-model.trim="loginUser.mobile" auto-complete="off" placeholder="请输入手机号" clearable>
              <i slot="prefix" class="el-input__icon svg-account"></i>
            </el-input>
          </el-form-item>

          <el-form-item prop="password">
            <el-input @keyup.enter.native="loginByUsername('usernameForm')" type="password" v-model.trim="loginUser.password" style="width: 100%" placeholder="请输入密码" clearable>
              <i slot="prefix" class="el-input__icon svg-password"></i>
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-checkbox v-model="autoLogin">自动登录</el-checkbox>
            <a class="forgot" @click="forgot">忘记密码</a>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" :loading="usernameLoginLoading" @click="loginByUsername('usernameForm')" :disabled="!loginUser.mobile || !loginUser.password" style="width:100%;">登录</el-button>
            <!-- <el-button @click="handleReset">重置</el-button> -->
          </el-form-item>

          <!--<el-form-item>-->
            <!--<el-button @click="envDefaultClick" v-if="envDefault" style="width:100%;">开发环境直接登录</el-button>-->
          <!--</el-form-item>-->

        </el-form>
        </el-tab-pane>


      </el-tabs>
    </div>
    <div class="footer">
      <div class="copyright">
        <!--<span v-text="footerTxt"></span>-->
      </div>
    </div>
  </div>
</template>

<script>
  import { loginService } from './LoginService';
  export default {
    data() {
      return {
          usernameLoginLoading: false,
          MobilePhoneLoginLoading: false,
          disSubmit: true,
          showTime: true,
          envDefault:true,
          autoLogin: true,
          activeName: 'first',
          loginUser:{
            mobile: '',
            verifyCode: '',
            userName: '',
            password: '',
            origin:'ADMIN'
          },
          timeText: '',
          time: 60,
          rules: {
            mobile: [
              { required: true, message: '请输入手机号', trigger: 'blur' },
              { min: 11, max: 11, message: '手机号不存在', trigger: 'blur' }
            ],
            verifyCode: [
              { required: true, message: '请输入验证码', trigger: 'blur' },
              { min: 4, max: 6, message: '验证码错误', trigger: 'blur' }
            ],
            mobile:[
              {required: true, message: '请输入手机号', trigger: 'blur'},
            ],
            password:[
              {required: true, message: '请输入密码', trigger: 'blur'},
            ]
          },
      };
    },
    methods: {
      init() {
        this.envDefault = loginService.envDefault;
        this.autoLog();
      },
      // 发送验证码
      getMobileCode() {
          if (!/^[0-9]{11}$/.test(this.loginUser.mobile)){
            this.$message({
              showClose: true,
              message: '手机号输入错误',
              type: 'warning'
            });
            return;
          }
          this.sendSmsCode();
          this.countDown();
      },
      sendSmsCode() {
        loginService.sendSmsCode({mobile:this.loginUser.mobile,origin:"ADMIN"}).then(res =>{
          if (Object.is(res.code,0)) {
            this.$set(this.loginUser,'mobile',res.msg);
            this.$message({
              showClose: true,
              message: '短信已发送您的手机!',
              type: 'success'
            });
          } else {
            this.$message({
              showClose: true,
              message: '短信发送失败，请稍后再试！',
              type: 'warning'
            });
          }
        });
      },
      countDown() {
        this.disSubmit = false;
        this.showTime = !this.showTime;
        let n = this.time;
        this.timeText = n+'秒';
        let temp = setInterval(()=>{
          n--;
          this.timeText = n+'秒';
          if (n == 0) {
            this.showTime = !this.showTime;
            clearInterval(temp);
          }
        },1000);
      },
      //用户名密码登录
      loginByUsername(formName){
          this.$refs[formName].validate((valid) => {
              if (valid) {
                  this.usernameLoginLoading = true;
                  loginService.loginToGetTokenByPwd(this.loginUser).then(res => {
                    if (res.code==0){
                      this.usernameLoginLoading = false;
                      sessionStorage.setItem('user', '{"mobile":"' + res.content.mobile + '","token":"' + res.content.token + '"}');
                      this.setCookie(res.content.mobile, res.content.token);
                      this.$router.push({ path: '/userMgm' });
                    } else{
                      this.$message({
                                message: res.content,
                                type: 'error'
                            });
                    }
                      // this.usernameLoginLoading = false;
                      // const {access_token, error, error_description} = data;
                      // if (access_token) {
                      //     sessionStorage.setItem('user', '{"mobile":"' + mobile + '","token":"' + access_token + '"}');
                      //     this.setCookie(mobile, access_token);
                      //     this.$router.push({path: '/'});
                      //
                      // } else if(error) {
                      //     this.$message({
                      //         message: error_description,
                      //         type: 'error'
                      //     });
                      // }
                  });
              }
          });
      },
      //手机号登录
      loginByMobilePhone(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.MobilePhoneLoginLoading = true;
              // const {username, mobile, code} = this.loginUser;
              // const loginParams = 'username=' + username + '&mobile=' + mobile + '&code=' + code + '&client_id=accounts&client_secret=accounts&grant_type=mobile_code&scope=read write';
              loginService.loginToGetToken(this.loginUser).then(res => {
                  this.MobilePhoneLoginLoading = false;
                  // const {access_token, error, error_description} = data;
                  // if (access_token) {
                  //     sessionStorage.setItem('user', '{"username":"' + username + '","token":"' + access_token + '"}');
                  //     this.setCookie(username, access_token);
                  //     this.$router.push({path: '/'});
                  //
                  // } else if(error) {
                  //     this.$message({
                  //         message: error_description,
                  //         type: 'error'
                  //     });
                  // }
                // 判断回应、填充session、页面跳转
                if (res.code=="0"){
                  sessionStorage.setItem('user', '{"mobile":"' + res.content.mobile + '","token":"' + res.content.token + '"}');
                  this.setCookie(res.content.mobile, res.content.token);
                  this.$router.push({path: '/'});
                } else{
                  this.$message.error(res.content);
                }
              });
          }
        });
      },
      handleReset() {
        this.$refs.loginUser.resetFields();
      },
      envDefaultClick() {
        this.loginUser.mobile = '18803838075';
        let access_token = this.rundomStr(32);
        sessionStorage.setItem('user', '{"mobile":"' + this.loginUser.mobile + '","token":"' + access_token + '"}');
        this.setCookie(this.loginUser.mobile, access_token);
        this.$router.push({ path: '/' });
        this.$message({
          showClose: true,
          message: '您已经登录成功',
          type: 'success'
        });
      },
      rundomStr(len) {
        let s= '';
        let randomchar = function(){
          var n= Math.floor(Math.random()*62);
          if(n<10) return n; //1-10
          if(n<36) return String.fromCharCode(n+55); //A-Z
          return String.fromCharCode(n+61); //a-z
        }
        while(s.length< len) s+= randomchar();
        return s;
      },
      //自动登录
      autoLog() {
        if (this.autoLogin) {
          let user = this.$cookieStore.getCookie('user');
          if (user) {
            let userObj = JSON.parse(user);
            sessionStorage.setItem('user', '{"mobile":"' + userObj.mobile + '","token":"' + userObj.token + '"}');
            this.$router.push({ path: '/userMgm' });
            this.$message({
              showClose: true,
              message: '自动登录成功',
              type: 'success'
            });
          }
        }
      },
      forgot() {
        this.$alert('请联系开发人员', '忘记密码');
      },
      setCookie(mobile, token) {
        if (this.autoLogin) {
          this.$cookieStore.setCookie('user', JSON.stringify({mobile: mobile, token: token}), 60*60*12);
        }
      }
    },
    mounted() {
      this.$nextTick(
          this.init()
      );
    },
  }
</script>

<style lang="scss" scoped>
  .custom-tab {
    .el-tabs__nav-wrap::after {
      z-index: -1 !important;
    }
  }
  .container {
    background: #f0f2f5;
    background-image: url('../../assets/mohubg.jpg');
    width: 100%;
    min-height: 100%;
    background-repeat: no-repeat;
    background-position: center;
    background-size: 100%;
    padding: 110px 0 144px 0;
    position: relative;
  }
  .svg-account {
      background: transparent;
      background-image: url('../../assets/account.svg');
      background-repeat: no-repeat;
      background-position: center;
      background-size: 100%;
      color: #b4bccc;
      vertical-align: middle;
      margin-left: 5px;
      width: 15px;
      display: inline-block;
      &_login {
        font-size: 20px;
      }
  }
  .svg-password {
      background: transparent;
      background-image: url('../../assets/password.svg');
      background-repeat: no-repeat;
      background-position: center;
      background-size: 100%;
      color: #b4bccc;
      vertical-align: middle;
      margin-left: 5px;
      width: 15px;
      display: inline-block;
      &_login {
        font-size: 20px;
      }
  }
  .forgot {
    float: right;
  }
  .top {
    text-align: center;
  }
  .header {
    height: 44px;
    line-height: 44px;
    a {
      text-decoration: none;
    }
  }
  .logo {
    height: 60px;
    vertical-align: top;
    margin-right: 16px;
  }
  .desc {
    font-size: 16px;
    color: rgba(0, 0, 0, 0.45);
    margin-top: 20px;
    margin-bottom: 40px;
    font-weight: bold;
  }

  .footer {
    position: absolute;
    width: 100%;
    bottom: 0;
  }
  .main {
    width: 368px;
    margin: 0 auto;
  }
  .footer {
  padding: 0 16px;
  margin: 48px 0 24px 0;
  text-align: center;

  .links {
    margin-bottom: 8px;

    a {
      color: rgba(0, 0, 0, 0.45);
      transition: all .3s;

      &:not(:last-child) {
        margin-right: 40px;
      }
    }
  }

  .copyright {
    color: rgba(0, 0, 0, 0.45);
    font-size: 14px;
  }
}
  a {
    color: #1890ff;
    background-color: transparent;
    text-decoration: none;
    outline: none;
    cursor: pointer;
    -webkit-transition: color .3s;
    -o-transition: color .3s;
    transition: color .3s;
    -webkit-text-decoration-skip: objects;
  }

  a:focus {
    text-decoration: underline;
    -webkit-text-decoration-skip: ink;
    text-decoration-skip: ink;
  }

  a:hover {
    color: #40a9ff;
  }

  a:active {
    color: #096dd9;
  }

  a:active, a:hover {
    outline: 0;
    text-decoration: none;
  }

  a[disabled] {
    color: rgba(0, 0, 0, 0.25);
    cursor: not-allowed;
    pointer-events: none;
  }
  // .login-container {
  //   /*box-shadow: 0 0px 8px 0 rgba(0, 0, 0, 0.06), 0 1px 0px 0 rgba(0, 0, 0, 0.02);*/
  //   -webkit-border-radius: 5px;
  //   border-radius: 5px;
  //   -moz-border-radius: 5px;
  //   background-clip: padding-box;
  //   margin: 180px auto;
  //   width: 350px;
  //   padding: 35px 35px 15px 35px;
  //   background: #fff;
  //   border: 1px solid #eaeaea;
  //   box-shadow: 0 0 25px #cac6c6;
  // .title {
  //   margin: 0px auto 40px auto;
  //   text-align: center;
  //   color: #505458;
  // }
  // .remember {
  //   margin: 0px 0px 35px 0px;
  // }
  // }
</style>
