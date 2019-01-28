<template>
  <section>
    <div class="toolbar">
      <p>基本信息</p>
      <div>
        <el-form class="operation-container" :label-position="labelPosition" :model="course" label-width="35%">
          <el-row>
            <el-col :span="8">
              <el-form-item label="课程名称">
                <el-input v-model="course.basicInfo.name" size="small" clearable placeholder="请输入"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="封面图片">
                <el-upload
                  class="upload-demo"
                  ref="upload"
                  action="https://jsonplaceholder.typicode.com/posts/"
                  :on-preview="handlePreview"
                  :on-remove="handleRemove"
                  :file-list="fileList"
                  :auto-upload="false">
                  <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
                  <!--<el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到服务器</el-button>-->
                  <span slot="tip" class="el-upload__tip" style="margin-left: 10px" v-model="picturePath" placeholder="仅支持jpg/png格式">{{pictureName}}</span>
                </el-upload>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="课程描述">
                <el-input v-model="course.basicInfo.description" size="small" clearable placeholder="请输入"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="是否免费">
                <el-select v-model="course.basicInfo.wasFree" clearable filterable placeholder="请选择" size="small">
                  <el-option
                    v-for="item in wasInfoList"
                    :key="item.label"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="课程价格">
                <el-input v-model="course.basicInfo.price" size="small" clearable placeholder="请输入"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="课程折扣">
                <el-input v-model="course.basicInfo.discount" size="small" clearable placeholder="请输入"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="课程简介">
                <el-input
                  v-model="course.basicInfo.summary"
                  style="width: 350%"
                  size="small"
                  clearable
                  placeholder="请输入"
                  type="textarea"
                  :rows="3"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
    </div>
    <div class="toolbar">
      <p>章节信息</p>
      <el-form class="operation-container" :label-position="labelPosition" :model="chapterInfo" label-width="35%">
        <el-row>
          <el-col :span="8">
            <el-form-item label="章序号">
              <el-input v-model="chapterInfo.chapterId" size="small" clearable placeholder="请输入"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="章名称">
              <el-input v-model="chapterInfo.chapterName" size="small" clearable placeholder="请输入"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="节序号">
              <el-input v-model="chapterInfo.partId" size="small" clearable placeholder="请输入"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="节名称">
              <el-input v-model="chapterInfo.partName" size="small" clearable placeholder="请输入"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="视频Id">
              <el-input v-model="chapterInfo.videoId" size="small" clearable placeholder="请输入"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="课程视频">
              <el-upload
                class="upload-demo"
                ref="upload"
                action="https://jsonplaceholder.typicode.com/posts/"
                :on-preview="handlePreview"
                :on-remove="handleRemove"
                :file-list="fileList"
                :auto-upload="false">
                <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
                <!--<el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到服务器</el-button>-->
                <span slot="tip" class="el-upload__tip" style="margin-left: 10px" v-model="picturePath" >{{videoName}}</span>
              </el-upload>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item>
              <el-button type="primary" icon="el-icon-search" size="small" @click="search">确定添加</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <div>
      <el-form class="operation-container" :label-position="labelPosition" :model="param" label-width="35%">
        <el-row>
          <el-col :span="8">
            <el-form-item>
              <el-button type="primary" icon="el-icon-search" size="small" @click="search">确定提交</el-button>
              <el-button @click="" size="small">取消上传</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
  </section>
</template>

<script>
    export default {
        name: "VideoCourse",
        data() {
          return {
            // 是否免费
            wasInfoList:[{label: "免费", value: 0},{label: "收费", value: 1}],
            // 缩略图
            pictureName:'仅支持png/jpg格式',
            picturePath:'',
            // 视频
            videoName:'仅支持mp4/flv格式',
            labelPosition:"right",
            // 课程信息保存
            course:{
              basicInfo:{
                name:'',
                teacherName:'',
                description:'',
                tags:[],
                wasFree:'',
                price:'',
                discount:'',
                courseLogo:'',
                summary:''
              },
              chapterList:[]
            },
            chapterInfo:{
              chapterId:'',
              chapterName:'',
              partId:'',
              partName:'',
              videoId:'',
              videoUrl:''
            },
          }
        },
        mounted() {
          // this.$nextTick(()=>{
          //   // this.getList();
          // });
        },
        methods: {
          // 上传文件相关
          submitUpload() {
            this.$refs.upload.submit();
          },
          handleRemove(file, fileList) {
            console.log(file, fileList);
          },
          handlePreview(file) {
            console.log(file);
          }
        },
        watch: {
        }
    }
</script>

<style scoped>

</style>
