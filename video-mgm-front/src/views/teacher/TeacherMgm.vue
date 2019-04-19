<template>
  <section>
    <!-- 搜索栏-->
    <div class="toolbar">
      <el-form :model="teacherFilter" :label-position="labelPosition" label-width="35%">
        <el-row>
          <el-col :span="8">
            <el-form-item label="姓名">
              <el-input v-model="teacherFilter.name" size="small" placeholder="请输入" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="手机号">
              <el-input v-model="teacherFilter.mobile" size="small" placeholder="请输入" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="邮箱">
              <el-input v-model="teacherFilter.email" size="small" placeholder="请输入" clearable></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="身份证号">
              <el-input v-model="teacherFilter.idCardNo" size="small" placeholder="请输入" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="学位证号">
              <el-input v-model="teacherFilter.degreeNo" size="small" placeholder="请输入" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="研究方向">
              <el-input v-model="teacherFilter.researchArea" size="small" placeholder="请输入" clearable></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="创建日期">
              <template>
                <div class="block">
                  <el-date-picker
                    v-model="teacherFilter.dateList"
                    type="daterange"
                    format="yyyy-MM-dd"
                    value-format="yyyy-MM-dd"
                    range-separator="至"
                    start-placeholder="开始日期"
                    end-placeholder="结束日期">
                  </el-date-picker>
                </div>
              </template>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="审核状态">
              <el-select v-model="teacherFilter.verifyStatus" placeholder="请选择">
                <el-option
                  v-for="item in auditStatusList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10" class="operation-bar">
            <el-button type="success" size="small" @click="getTeacherListByPage">搜 索</el-button>
            <el-button type="error" size="small" @click="resetParam">清 空</el-button>
          </el-col>
        </el-row>
      </el-form>
    </div>

    <!--数据栏-->
    <div>
      <el-table :data="teacherListByPage" fit v-loading="listLoading" style="width: 100%;" border>
        <el-table-column prop="teacherId" label="Id" align="center" width="60px"></el-table-column>
        <el-table-column prop="teacherName" label="姓名" align="center" width="80px"></el-table-column>
        <el-table-column prop="username" label="用户名" align="center"></el-table-column>
        <el-table-column prop="teacherMobile" label="手机号" align="center" width="110px"></el-table-column>
        <el-table-column prop="email" label="邮箱" align="center"></el-table-column>
        <el-table-column prop="gmtCreate" label="注册时间" align="center">
          <template slot-scope="scope">
            {{ scope.row.gmtCreate | FormatDate }}
          </template>
        </el-table-column>
        <el-table-column prop="researchArea" label="研究方向" align="center"></el-table-column>
        <el-table-column prop="idCardNo" label="身份证号" align="center"
                         show-overflow-tooltip></el-table-column>
        <el-table-column prop="degreeNo" label="学历证号" align="center"
                         show-overflow-tooltip></el-table-column>
        <el-table-column prop="verifyStatus" label="状态" align="center" width="80px">
          <template slot-scope="scope">
            {{ scope.row.verifyStatus | YN }}
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button v-if="scope.row.verifyStatus != 'NULL'" @click="getTeacherDetail" type="warning" size="mini">查看详情</el-button>
            <el-button v-if="scope.row.verifyStatus == 'NULL'" @click="audit('pass',scope.row.teacherId)" type="warning" size="mini">通过</el-button>
            <el-button v-if="scope.row.verifyStatus == 'NULL'" @click="audit('reject',scope.row.teacherId)" type="warning" size="mini">拒绝</el-button>
          </template>
        </el-table-column>
      </el-table>
      <!--工具条-->
      <el-col :span="24" class="toolbar">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="teacherFilter.page"
          :page-sizes="[10, 20, 50, 100]"
          :page-size="teacherFilter.rows"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total" style="float: right;">
        </el-pagination>
      </el-col>
    </div>
  </section>
</template>

<script>
  const YesOrNo = {
    Y: '成功',
    N: '失败',
    NULL:'未审核'
  };
  import {teacherService} from './TeacherService'
  import commonUtil from '../common/CommonUtil'

  export default {
    data() {
      return {
        labelPosition: 'right',
        listLoading: false,
        total: 0,
        teacherListByPage: [],
        teacherFilter: {
          page: 1,
          rows: 20,
          dateList:'',
          name:'',
          mobile:'',
          email:'',
          username:'',
          verifyStatus:'',
          researchArea:'',
          idCardNo:'',
          degreeNo:'',
          startDate:'',
          endDate:''
        },
        teacherAudit:{
          teacherId:'',
          verifyStatus:''
        },
        auditStatusList:[
          {
            value:'Y',
            label:'成功'
          },
          {
            value:'N',
            label:'失败'
          },
          {
            value:'NULL',
            label:'未审核'
          }
        ],
      }
    },
    mounted() {
      this.$nextTick(() => {
        this.getTeacherListByPage();
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
      // 获取教师列表
      getTeacherListByPage() {
        var date = this.teacherFilter.dateList;
        if (date.length > 1) {
          this.teacherFilter.startDate = this.teacherFilter.dateList[0];
          this.teacherFilter.endDate = this.teacherFilter.dateList[1];
        }
        teacherService.getTeacherListByPage(this.teacherFilter).then(res => {
          if (res.code == 0) {
            this.total = res.content.count;
            this.teacherListByPage = this.formatTeacherInfo(res.content.resultList);
          }
        })
      },
      formatTeacherInfo(teacherList) {
        for (var i = 0; i < teacherList.size; i++) {
          var teacher = teacherList.get(i);
          teacher['verifyStatus'] = teacher['verifyStatus'].equals("Y") ? "通过" : "失败"
        }
        return teacherList;
      },
      //审核
      audit(status,id) {
        debugger;
        this.teacherAudit.teacherId = id;
        if (status === "pass"){
          this.teacherAudit.verifyStatus = 'Y';
        } else if (status === "reject") {
          this.teacherAudit.verifyStatus = 'N';
        }
        teacherService.auditTeacher(this.teacherAudit).then(res => {
          if (res.code == 0) {
            this.$message.success('审核成功');
            this.getTeacherListByPage();
          }
        })

      },
      // 搜索条件清空
      resetParam() {

      },
      // 获取课程详情
      getTeacherDetail() {

      },
      // 分页条件查询
      handleSizeChange(val) {
        this.filter.rows = val;
        this.getTeacherListByPage();
      },
      handleCurrentChange(val) {
        this.filter.page = val;
        this.getTeacherListByPage();
      },
    },
  }
</script>

<style scoped>

</style>
