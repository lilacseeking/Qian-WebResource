<template>
  <section>
    <div class="toolbar">
      <p>基本信息</p>
      <div>
        <el-form class="operation-container" :label-position="labelPosition" :model="course.courseDTO" label-width="35%">
          <el-row>
            <el-col :span="8">
              <el-form-item label="课程名称">
                <el-input v-model="course.courseDTO.name" size="small" clearable placeholder="请输入"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="封面图片">
                <el-upload
                  class="upload-demo"
                  :action="upload_url"
                  :on-remove="handleRemove"
                  :onSuccess="uploadSuccess_1"
                  :on-exceed="handleExceed"
                  :file-list="thumbnailList"
                  :headers = "headers"
                  :limit="1"
                  name="files">
                  <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
                  <span slot="tip" class="el-upload__tip" style="margin-left: 10px" v-model="picturePath" placeholder="仅支持jpg/png格式">{{pictureName}}</span>
                </el-upload>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="课程描述">
                <el-input v-model="course.courseDTO.description" size="small" clearable placeholder="请输入"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="是否免费">
                <el-select v-model="course.courseDTO.isFree" clearable filterable placeholder="请选择" size="small">
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
                <el-input v-model="course.courseDTO.price" size="small" clearable placeholder="请输入"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="课程折扣">
                <el-input v-model="course.courseDTO.discount" size="small" clearable placeholder="请输入"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="课程分类">
                <el-select v-model="course.courseDTO.tags" clearable filterable placeholder="请选择" size="small">
                  <el-option
                    v-for="item in tagsList"
                    :key="item.label"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="课程简介">
                <el-input
                  v-model="course.courseDTO.summary"
                  style="width: 100%"
                  size="small"
                  clearable
                  placeholder="请输入"
                  type="textarea"
                  :rows="2"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item>
                <el-button type="primary" icon="el-icon-search" size="small" @click="addContent">添加章节</el-button>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
    </div>
    <div class="toolbar" v-for="(item,index) in course.contentDTOList" :key="index">
      <p>章节信息</p>
      <el-form class="operation-container" :label-position="labelPosition" :model="item" label-width="35%">
        <el-row>
          <el-col :span="8">
            <el-form-item label="节序号">
              <el-input v-model="item.chapterId" size="small" clearable placeholder="请输入" :value="index"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="节名称">
              <el-input v-model="item.chapterName" size="small" clearable placeholder="请输入"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="节描述">
              <el-input v-model="item.chapterDescription" size="small" clearable placeholder="请输入"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="是否免费">
              <el-select v-model="item.isFree" placeholder="请选择">
                <el-option
                  v-for="item in isFreeList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="课程视频">
              <el-upload
                class="upload-demo"
                :action="upload_url"
                :on-preview="handlePreview"
                :on-remove="handleRemove"
                :headers = "headers"
                :limit="1"
                :on-exceed="handleExceed"
                :onSuccess="((response,file,fileList)=>uploadSuccess_1(response,file,fileList,index))"
                name="files"
                :file-list="fileList[index]">
                <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
                <span slot="tip" class="el-upload__tip" style="margin-left: 10px" v-model="picturePath" >{{videoName}}</span>
              </el-upload>
            </el-form-item>
          </el-col>
        </el-row>

        <!--<el-row>-->
          <!--<el-col :span="8">-->
            <!--<el-form-item>-->
              <!--<el-button type="primary" icon="el-icon-search" size="small" @click="addCourseAndContentList">确定添加</el-button>-->
            <!--</el-form-item>-->
          <!--</el-col>-->
        <!--</el-row>-->
      </el-form>
    </div>
    <div>
      <el-form class="operation-container" :label-position="labelPosition" label-width="35%">
        <el-row>
          <el-col :span="8">
            <el-form-item>
              <el-button type="primary" icon="el-icon-search" size="small" @click="addCourseAndContentList">确定提交</el-button>
              <el-button @click="" size="small">取消上传</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
  </section>
</template>

<script>

  import {courseService} from './CourseService'

    export default {
        name: "VideoCourse",
        data() {
          return {
            tagsList:[{label: "后端技术", value: '后端技术'},{label: "前端技术", value: '前端技术'},{label: "中间件技术", value: '中间件技术'},{label: "常用框架", value: '常用框架'}],
            // 视频地址索引
            videoIndex:'',
            headers: {
              token: JSON.parse(sessionStorage.getItem('user')).token,
              mobile: JSON.parse(sessionStorage.getItem('user')).mobile
            },
            picPath_1:null,
            upload_url: 'http://47.107.110.132:8077/file/moreFileUpload',
            // 文件上传列表
            fileList:[[]],
            // 缩略图列表
            thumbnailList:[],
            // 是否免费
            wasInfoList:[{label: "免费", value: 'Y'},{label: "收费", value: 'N'}],
            // 缩略图
            pictureName:'仅支持png/jpg格式',
            picturePath:'',
            // 视频
            videoName:'仅支持mp4/flv格式',
            labelPosition:"right",
            // 课程信息保存
            course:{
              courseDTO:{
                name:'',
                description:'',
                tags:'',
                isFree:'N',
                price:'',
                discount:'',
                thumbnail:'',
                summary:''
              },
              contentDTOList:[
              ]
            },
            chapterInfo:{
              chapterId:'',
              chapterName:'',
              chapterDescription:'',
              isFree:'',
              url:'',
              fileName:''
            },
            isFreeList:[
              {
                value:'Y',
                label:'免费'
              },
              {
                value:'N',
                label:'收费'
              }
            ]
          }
        },
        mounted() {
          this.$nextTick(()=>{
            this.course.contentDTOList.push({
              chapterId:'1',
              chapterName:'',
              chapterDescription:'',
              isFree:'Y',
              url:'',
              fileName:''
            });
          });
        },
        methods: {
          handleExceed(files, fileList) {
            this.$message.warning(`当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`)
          },
          beforeRemove(file, fileList) {
            return this.$confirm(`确定移除 ${file.name}？`)
          },
          uploadSuccess_1 (response,file,fileList,index) {
            if (response.code === 0 && response.content.length > 0) {
              if (null == index){
                this.course.courseDTO.thumbnail = response.content[0];
              }else{
                this.course.contentDTOList[index].url = response.content[0];
              }
            }
          },
          // 上传文件相关
          submitUpload() {
            this.$refs.upload.submit();
          },
          //添加章节
          addContent(){
            this.fileList.push([]);
            this.course.contentDTOList.push({
              chapterId:this.course.contentDTOList.length + 1,
              chapterName:'',
              chapterDescription:'',
              isFree:'N',
              url:'',
              fileName:''
            });
          },
          handleRemove(file, fileList) {
            console.log(file, fileList);
          },
          handlePreview(file) {
            console.log(file);
          },
          addCourseAndContentList(){
            // 第一步：添加上传任务，同步返回文件上传后的URL
            // 第二部：将URL赋值到各章节目录
            // 第三部：添加课程
            courseService.saveCourseAndContentList(this.course).then(res => {
              if (res.code == 0){
                this.$message.success("创建成功");
              }else{
                this.$message.error("创建失败");
              }
            })
          }
        },
        watch: {
        }
    }
</script>

<style scoped>

</style>
