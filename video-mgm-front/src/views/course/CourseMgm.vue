<template>
  <section>
    <!-- 搜索栏-->
    <div class="toolbar">
      <el-form :model="courseFilter" :label-position="labelPosition" label-width="35%">
        <el-row>
          <el-col :span = "8">
            <el-form-item label="教师姓名">
              <el-input v-model="courseFilter.teacherName" size="small" placeholder="请输入" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :span = "8">
            <el-form-item label="课程分类">
              <el-input v-model="courseFilter.tags" size="small" placeholder="请输入" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :span = "8">
            <el-form-item label="课程名称">
              <el-input v-model="courseFilter.courseName" size="small" placeholder="请输入" clearable></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span = "8">
            <el-form-item class="margin-b-0" label="创建时间">
              <el-date-picker class="com-date-picker" size="mini" value-format = "yyyy-MM-dd" v-model="courseFilter.gmtCreate"
                              type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10" class="operation-bar">
            <el-button type="success" size="small" @click="getCourseListByPage" >搜   索</el-button>
            <el-button type="error" size="small" @click="resetParam" >清   空</el-button>
          </el-col>
        </el-row>
      </el-form>
    </div>

    <!--数据栏-->
    <div>
      <el-table :data="courseListByPage" fit v-loading="listLoading" style="width: 100%;" border>
        <el-table-column type="selection" align="center"></el-table-column>
        <el-table-column prop="id" label="课程编号" align="center"></el-table-column>
        <el-table-column prop="gmtCreate" label="创建日期" align="center"></el-table-column>
        <el-table-column prop="name" label="课程名称" align="center"></el-table-column>
        <el-table-column prop="isFree" label="是否免费" align="center">
          <template slot-scope="scope">
            {{ scope.row.isFree | YN }}
          </template>
        </el-table-column>
        <el-table-column prop="discount" label="折扣" align="center">
          <template slot-scope="scope">
            {{ scope.row.discount * 100}}%
          </template>
        </el-table-column>
        <el-table-column prop="price" label="价格" align="center">
          <template slot-scope="scope">
            {{ scope.row.price}}元
          </template>
        </el-table-column>
        <el-table-column prop="tags" label="分类" align="center"></el-table-column>
        <el-table-column prop="description" label="描述" align="center"></el-table-column>
        <el-table-column prop="humbnail" label="缩略图" align="center"></el-table-column>
        <el-table-column label="操作" width="200">
          <template slot-scope="scope">
            <el-button @click="undercarriage(scope.row.id)" type="warning">下 架</el-button>
            <!--<el-button @click="getCourseDetail" type="warning">详 情</el-button>-->
          </template>
        </el-table-column>
      </el-table>
      <!--工具条-->
      <el-col :span="24" class="toolbar">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="courseFilter.page"
          :page-sizes="[10, 20, 50, 100]"
          :page-size="courseFilter.rows"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total" style="float: right;">
        </el-pagination>
      </el-col>
    </div>
  </section>
</template>

<script>
  const YesOrNo = {
    Y: '收费',
    N: '免费',
  };
  import {courseService} from './CourseService'
  import commonUtil from '../common/CommonUtil'

  export default {
    data() {
      return {
        labelPosition:'right',
        listLoading:false,
        courseListByPage:[],
        total:0,
        courseFilter: {
          teacherName:'',
          courseName:'',
          tags:'',
          gmtCreate:'',
          type:'',
          page:1,
          rows:20
        },
      }
    },
    mounted() {
      this.$nextTick(()=>{
        this.getCourseListByPage();
      });
    },
    filters: {
      YN(val) {
        return YesOrNo[val];
      },
      FormatDataTime(val) {
        return commonUtil.formatDateTime(new Date(val));
      },
      FormatDate(val) {
        return commonUtil.formatDate(new Date(val));
      }
    },
    methods: {
      // 获取课程列表
      getCourseListByPage(){
        courseService.getCourseList(this.courseFilter).then(res=>{
          if(res.code== 0){
            this.total = res.content.count;
            this.courseListByPage = this.formatCourseInfo(res.content.resultList);
          }
        })
      },
      // 课程详情获取
      getCourseDetail(){

      },
      // 课程列表格式化
      formatCourseInfo(courseList){
        return courseList;
      },
      //课程下架
      undercarriage(val){
        courseService.shelveCourse({id:val}).then(res =>{
          if(res.code== 0){
           this.$message.success("课程下架成功");
            this.getCourseListByPage();
          }else {
            this.$message.error(res.content.message);
          }
        })
      },
      // 搜索条件清空
      resetParam(){

      },
      // 分页条件查询
      handleSizeChange(val) {
        this.filter.rows = val;
        this.getOrderListByPage();
      },
      handleCurrentChange(val) {
        this.filter.page = val;
        this.getOrderListByPage();
      },
    },
  }
</script>

<style scoped>

</style>
