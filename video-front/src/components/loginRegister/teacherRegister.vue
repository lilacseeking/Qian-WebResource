<template>
  <div class="content">
    <div class="box" >
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="身份证号" prop="idCardNo">
          <el-input v-model="form.idCardNo"></el-input>
        </el-form-item>

        <el-form-item label="身份证图片">
          <!-- action="192.168.199.229:8077/file/moreFileUpload" -->

          <el-upload
            class="upload-demo"
            :action="upload_url"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            :headers = "headers"
            :limit="1"
            :on-exceed="handleExceed"
            :onSuccess="uploadSuccess_1"
            name="files"
            :file-list="fileList_1">
            <el-button size="small" type="primary">点击上传</el-button>
            <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
          </el-upload>

        </el-form-item>

        <el-form-item label="学历证书编号" prop="degreeNo">
          <el-input v-model="form.degreeNo"></el-input>
        </el-form-item>

        <el-form-item label="学历证书图片">
                      <!-- :action="upload_url" -->
           <el-upload
            class="upload-demo"
            :action="upload_url"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            :before-remove="beforeRemove"
            :headers = "headers"
            :limit="1"
            :on-exceed="handleExceed"
            :onSuccess="uploadSuccess_2"
            name="files"
            :file-list="fileList_2">
            <el-button size="small" type="primary">点击上传</el-button>
            <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
          </el-upload>

        </el-form-item>

        <el-form-item label="研究方向" prop="researchArea">
          <el-input v-model="form.researchArea"></el-input>
        </el-form-item>

        <el-form-item label="个人简介" prop="personalResume">
          <el-input v-model="form.personalResume"></el-input>
        </el-form-item>

        <el-form-item label="工作经历" prop="workExperience">
          <el-input
          type="textarea"
          v-model="form.workExperience">
           </el-input>
        </el-form-item>

      <el-form-item>
    <el-button type="primary" @click="_onSubmit">注册</el-button>
    <el-button @click="back">取消</el-button>
  </el-form-item>
</el-form>
    </div>
  </div>
</template>

<script>
  import { applyTeacher, sendFiles } from '../../util/api'
  // import tiemDown from './timeDown'

  export default {
    components: {},
    props: {
      showLogin: {
        type: Boolean,
        default: true
      }
    },
    data() {
      return {
        form: {
          idCardNo: null,
          degreeNo: null,
          researchArea: null,
          personalResume: null,
          workExperience: null
        },
        dialogImageUrl: '',
        dialogVisible: false,
        rules: {
          idCardNo: [
            {required: true, message: '请输入账户', trigger: 'blur'}
          ],
          degreeNo: [
            {required: true, message: '请输入密码', trigger: 'blur'}
          ],
          researchArea: [
            {required: true, message: '请输入密码', trigger: 'blur'}
          ],
          personalResume: [
            {required: true, message: '请输入密码', trigger: 'blur'}
          ],
          workExperience: [
            {required: true, message: '请输入密码', trigger: 'blur'}
          ]
        },
        headers: {
          token: this.$store.state.userInfo.token,
          mobile: this.$store.state.userInfo.mobile
        },
        uploadForm: new FormData(),   // 一个formdata
        upload_url: 'http://47.107.110.132:8077/file/moreFileUpload',
        fileList_1: [
          // {name: 'food.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}
        ],
        fileList_2: [],
        picPhth_1: null,
        picPhth_2: null
      }
    },
    mounted() {},
    methods: {
      //
      uploadSuccess_2 (response) {
        if (response.code === 0 && response.content.length > 0) {
          this.picPhth_2 = response.content[0]
        }
      },
      uploadSuccess_1 (response) {
        if (response.code === 0 && response.content.length > 0) {
          this.picPhth_1 = response.content[0]
        }
      },
      handleRemove(file, fileList) {
        console.log(file, fileList)
      },
      handlePreview(file) {
        console.log(file)
      },
      handleExceed(files, fileList) {
        this.$message.warning(`当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`)
      },
      beforeRemove(file, fileList) {
        return this.$confirm(`确定移除 ${file.name}？`)
      },
      // 发送文件到后台
      _sendFiles() {
        const form = new FormData()
        // form.append('files', param.file)

        sendFiles(form).then(res => {
          if (res.data.code === 0) {
            this.$Message({
              message: this.type + '审批人更新成功',
              type: 'success'
            })
          } else {
            this.$message({
              message: '上传失败',
              type: 'error'
            })
          }
          console.log('上传CDS审批人结果: ' + res.data.message)
        })
      },
      // 实现  1、校验空  2、文件上传 、  3、上传表单
      _onSubmit() {
      // 1、校验空
        this.$refs['form'].validate((valid) => {
          if (valid) {
            // 2、验证文件是否上传
            if (this.picPhth_1 && this.picPhth_2) {
              //  3、上传表单
              const params = {
                'userId': JSON.parse(localStorage.getItem('userInfo')).id,
                'idCardNo': this.form.idCardNo,
                'degreeNo': this.form.degreeNo,
                'personalResume': this.form.personalResume,
                'researchArea': this.form.researchArea,
                'workExperience': this.form.workExperience,
                'idCardFileName': this.picPhth_1,
                'degreeFileName': this.picPhth_2
              }
              applyTeacher(params).then(res => {
                const { code, content } = { ...res.data }
                if (code === 0) {
                  this.$message({
                    message: '表单提交成功',
                    type: 'success'
                  })

                  this.$router.push('/')
                } else {
                  this.$message({
                    message: content.message,
                    type: 'error'
                  })
                }
              })
            }
          } else {
            this.$message({
              message: '身份证图片需正反两张，学历证书图片需正反两张，供需需上传4张图片',
              type: 'error'
            })
            return false
          }
        })
      },
      // submitUpload(callback) {
      //   // this.$refs.upload_1.submit()
      //   // this.$refs.upload_2.submit()

      //   this.$refs.upload_3.submit()
      //   callback()
      // },
      back() {
        this.$router.go(-1)
      },
      //  用户  登录
      _applyTeacher(formPwd) {
        // 1、验证，内容是否全部输入
        this.$refs[formPwd].validate((valid) => {
          if (valid) {
            // 2、登录获取 token信息
            const params = {
              'password': this.formPwd.password,
              'username': this.formPwd.account
            }
            applyTeacher(params).then(res => {
              const {code, content} = {...res.data}
              if (code === 0) {
                // 3、将获取到的 用户信息存到 store 中， token 存到到header 中。
                this._saveF(content, true)
                // this.clickCancel()

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
    width: 1200px;
    margin: 10px auto;
    background: #ffffff;
    padding-top: 20px;
  }

  .box {
  width: 600px;
  margin: 20px 0 0 30px;
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
</style>
