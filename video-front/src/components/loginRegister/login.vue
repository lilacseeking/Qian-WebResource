<template>
  <div class="content">
    <div class="box" >
      <template>
        <el-tabs v-model="activeName2" @tab-click="handleClick" class="tabLines">
          <el-tab-pane disabled label="" name=""></el-tab-pane>
          <el-tab-pane disabled label="" name=""></el-tab-pane>
          <el-tab-pane disabled label="" name=""></el-tab-pane>
          <el-tab-pane label="密码登录" name="first">
              <el-form  ref="formPwd" :rules="rules" :model="formPwd" label-width="80px" style="width: 400px;margin: 0 auto;">

                  <el-form-item label="账户" prop='account'>
                    <el-input v-model="formPwd.account"></el-input>
                  </el-form-item>

                  <el-form-item label="密码" prop="password" >
                    <el-input v-model="formPwd.password" type="password"></el-input>
                  </el-form-item>

                  <el-form-item >
                    <el-button type="primary" @click="_login('formPwd')" style="margin-left: 38px;margin-right:10px;">登录</el-button>
                    <el-button @click="clickCancel">取消</el-button>
                  </el-form-item>

            </el-form>

          </el-tab-pane>
          <el-tab-pane label="短信登录" name="second">

            <el-form ref="formMessa" :model="formMessa" :rules="rules" label-width="80px">
              <el-form-item label="电话号码">
                <el-input v-model="formMessa.mobile"></el-input>
              </el-form-item>
              <el-form-item label="验证码">
                <el-col :span="12">
                <el-input v-model="formMessa.num"></el-input>
                </el-col>
                <el-col :span="12" style="padding-left:20px;">
                  <el-button  v-if="messBtn" @click="_sendMobile"> 获取验证码 </el-button>
                  <template v-else>
                 <tiemDown :seconds="60" :type="'sec'" @end="messBtn = true"> </tiemDown>
                  </template>
                </el-col>
              </el-form-item>
              <el-form-item  >
                <el-button type="primary" @click="onSubmit('formMessa')" style="margin-left: 38px;margin-right:10px;">登录</el-button>
                <el-button @click="clickCancel">取消</el-button>
              </el-form-item>
            </el-form>
          </el-tab-pane>
        </el-tabs>
      </template>
    </div>
  </div>
</template>

<script>
  import {login, loginByMes, sendMobile} from '../../util/api'
  import tiemDown from './timeDown'

  export default {
    components: {tiemDown},
    props: {
      showLogin: {
        type: Boolean,
        default: true
      }
    },
    data() {
      return {
        messBtn: true,
        labelPosition: 'left',
        activeName2: 'first',
        formPwd: {
          account: '',
          password: ''
        },
        formMessa: {
          mobile: '',
          password: ''
        },
        rules: {
          account: [
            {required: true, message: '请输入账户', trigger: 'blur'}
          ],
          password: [
            {required: true, message: '请输入密码', trigger: 'blur'}
          ]
        }
      }
    },
    mounted() {
    },
    methods: {
      // 获取注册码
      _sendMobile() {
        //  必须输入手机号
        const reg = /^1[34578]\d{9}$/
        if (reg.test(this.formMessa.mobile)) {
          this.messBtn = false
          const params = {
            'mobile': this.formMessa.mobile,
            'username': this.formMessa.mobile
          }

          sendMobile(params).then(res => {
            const { code, content } = {...res.data}
            if (code === 0) {
              console.log('yanzhengma ', content)
            } else {
              this.$message({
                message: res.data.content.message,
                type: 'error'
              })
              this.messBtn = true
            }
          })
        } else {
          this.$message({
            message: ' 手机号码有误，请重新填写',
            type: 'warning'
          })
        }
      },
      // 短信登录
      _loginByMes() {
        const params = {
          'mobile': this.formMessa.mobile,
          'verifyCode': this.formMessa.num
        }

        if (this.formMessa.num.length > 0) {
          loginByMes(params).then(res => {
            const { code, content } = { ...res.data }
            if (code === 0) {
              this._saveF(content, true)
              console.log('content', content)
            } else {
              this.$message({
                message: res.data.content.message,
                type: 'error'
              })
            }
          })
        } else {
          this.$message({
            message: '请填写验证码',
            type: 'warning'
          })
        }
      },
      // save login data
      _saveF(content, isLogin) {
        this.$store.commit('setUserInfo', content)
        this.$store.commit('setIsLogin', isLogin)

        if (window.localStorage) {
          let storage = window.localStorage
          storage.setItem('userInfo', JSON.stringify(content))
          storage.setItem('isLogin', isLogin)
        } else {
          console.logt('浏览器支持localStorage')
        }
        this.clickCancel()
      },
      clickCancel() {
        // this.showLogin = false
        this.$emit('Cancel', this.showLogin)
      },
      onSubmit(formMessa) {
        // 1、验证，内容是否全部输入
        this.$refs[formMessa].validate((valid) => {
          if (valid) {
            const params = {
              'mobile': this.formMessa.mobile,
              'verifyCode': this.formMessa.num
            }
            loginByMes(params).then(res => {
              const { code, content } = { ...res.data }
              if (code === 0) {
                // 3、将获取到的 用户信息存到 store 中， token 存到到header 中。
                this._saveF(content, true)
                // this.clickCancel()

                this.$message({
                  message: '登录成功',
                  type: 'success'
                })
              } else {
                this.$alert(res.data.content.message, '错误类型', {
                  confirmButtonText: '确定'
                })
              }
            })
          } else {
            return false
          }
        })
        console.log('submit!')
      },
      handleClick(tab, event) {
        console.log(tab, event)
      },
      //  用户  登录
      _login(formPwd) {
        // 1、验证，内容是否全部输入
        this.$refs[formPwd].validate((valid) => {
          if (valid) {
            // 2、登录获取 token信息
            const params = {
              'password': this.formPwd.password,
              'username': this.formPwd.account
            }
            login(params).then(res => {
              const {code, content} = {...res.data}
              if (code === 0) {
                // 3、将获取到的 用户信息存到 store 中， token 存到到header 中。
                this._saveF(content, true)
                // this.clickCancel()

                this.$message({
                  message: '登录成功',
                  type: 'success'
                })
              } else {
                this.$alert(res.data.content.message, '错误类型', {
                  confirmButtonText: '确定'
                })
              }
            })
              .catch(function (error) {
                console.log(error)
              })
          } else {
            console.log('error submit!!')
            return false
          }
        })
      }
    }
  }
</script>

<style scoped>
  .content {
    position: fixed;
    width: 100%;
    height: 100%;
    left: 0px;
    top: 0px;
  }

  .box {
    position: relative;
    width: 400px;
    background-color: #ffffff;
    margin: 200px auto;
    background-color: #f5f6f7;
    padding:10px 100px 0 100px;
    box-shadow: 0px 14px 58PX #856e6e;
  }

  .el-tabs__nav-scroll {
    text-align: center;
  }

  .el-tabs__nav {
    float: none;
    display: inline-block;
  }
  .el-table .cell{
    white-space:pre-line;
  }
  .tabLines::after {
    content: "";
    position: absolute;
    left: 0;
    top: 64px;
    width: 100px;
    height: 2px;
    background-color: #E4E7ED;
    z-index: 1;
  }
  .tabLines::before {
    content: "";
    position: absolute;
    right: 0;
        top: 64px;
    width: 100px;
    height: 2px;
    background-color: #E4E7ED;
    z-index: 1;
  }
</style>
