<template>
  <div class="content">
    <div class="box">
      <template>
        <h3> 用户注册 </h3>
        <el-form ref="formRegister" :rules="rules" :model="formRegister" label-width="80px">
          <el-form-item label="用户名 " prop='username '>
            <el-input v-model="formRegister.username"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop='password'>
            <el-input v-model="formRegister.password" type="password"></el-input>
          </el-form-item>
          <el-form-item label="确认密码" prop='confirmPassword'>
            <el-input v-model="confirmPassword" type="password"></el-input>
          </el-form-item>
          <el-form-item label="手机号" prop='mobile'>
            <el-input v-model="formRegister.mobile"></el-input>
          </el-form-item>
          <el-form-item label="姓名" prop='name'>
            <el-input v-model="formRegister.name"></el-input>
          </el-form-item>
          <el-form-item label="性别" prop='gender'>
            <el-select v-model="formRegister.gender" placeholder="请选择">
              <el-option
                v-for="item in genderList"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="邮箱" prop='email'>
            <el-input v-model="formRegister.email"></el-input>
          </el-form-item>
          <el-form-item label="生日" prop='birthday'>
            <el-date-picker
              v-model="formRegister.birthday"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="选择日期">
            </el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-button @click="clickCancel">取消</el-button>
            <el-button type="primary" @click="_register('formRegister')">注册</el-button>
          </el-form-item>
        </el-form>

      </template>
    </div>
  </div>
</template>

<script>

  import {register} from '../../util/api'

  export default {
    props: {
      showLogin: {
        type: Boolean,
        default: true
      }
    },
    data() {
      return {
        activeName2: 'first',
        formRegister: {
          'email': '',
          'mobile': '',
          'password': '',
          'username': '',
          'name': '',
          'gender': 0, // 0 男  1 女
          'birthday': ''
        },
        confirmPassword: '',
        formMessa: {
          phone: '',
          password: ''
        },
        genderList: [
          {label: '男', value: 0},
          {label: '女', value: 1}
          ],
        rules: {
          account: [
            {required: true, message: '请输入活动名称', trigger: 'blur'}
          ],
          password: [
            {required: true, message: '请输入活动名称', trigger: 'blur'}
          ]
        }
      }
    },
    mounted() {
    },
    methods: {
      clickCancel() {
        // this.showLogin = false
        this.$emit('Cancel', this.showLogin)
      },
      onSubmit() {
        console.log('submit!')
      },
      handleClick(tab, event) {
        console.log(tab, event)
      },
      //  用户  登录
      _register(formRegister) {
        // 1、验证，内容是否全部输入
        this.$refs[formRegister].validate((valid) => {
          if (valid) {
            // 2、登录获取 token信息
            const params = this.formRegister
            register(params).then(res => {
              const {code, content} = {...res.data}
              if (code === 0) {
                // 3、将获取到的 用户信息存到 store 中， token 存到到header 中。

                this.$store.commit('setUserInfo', content)
                this.$store.commit('setIsLogin', true)

                this.clickCancel()

                this.$message({
                  message: '登录成功',
                  type: 'success'
                })
              } else if (code === 1003) {
                this.$alert(content.message, '错误类型', {
                  confirmButtonText: '确定',
                  callback: action => {
                    this.$message({
                      type: 'info',
                      message: `action: ${action}`
                    })
                  }
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
    margin: 20px auto;
    /* width: 30%; */
    /* height: 40%; */
    background-color: #f5f6f7;
    padding: 10px 20px 0 0;
    box-shadow: 0px 14px 58PX #856e6e;
  }

  .el-tabs__nav-scroll {
    text-align: center;
  }

  .el-tabs__nav {
    float: none;
    display: inline-block;
  }
</style>
