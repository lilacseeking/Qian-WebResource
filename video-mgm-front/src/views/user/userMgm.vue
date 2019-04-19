<template>
  <section>
    <!-- 搜索栏-->
    <div class="toolbar">
      <el-form :model="filter.optionParam" :label-position="labelPosition" label-width="35%">
        <el-row>
          <el-col :span="8">
            <el-form-item label="姓名">
              <el-input v-model="filter.optionParam.name" size="small" placeholder="请输入" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="用户名">
              <el-input v-model="filter.optionParam.username" size="small" placeholder="请输入" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="手机号">
              <el-input v-model="filter.optionParam.mobile" size="small" placeholder="请输入" clearable></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="邮箱">
              <el-input v-model="filter.optionParam.email" size="small" placeholder="请输入" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="创建日期">
              <template>
                <div class="block">
                  <el-date-picker
                    v-model="filter.optionParam.gmtCreate"
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
            <el-form-item label="性别">
              <el-select v-model="filter.optionParam.gender" placeholder="请选择">
                <el-option
                  v-for="item in genderList"
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
            <el-button type="success" size="small" @click="getUserListByPage">搜 索</el-button>
            <el-button type="error" size="small" @click="resetParam">清 空</el-button>
          </el-col>
        </el-row>
      </el-form>
    </div>

    <!--数据栏-->
    <div>
      <el-table :data="userListByPage" fit v-loading="listLoading" style="width: 100%;" border>
        <el-table-column type="selection" align="center"></el-table-column>
        <el-table-column prop="id" label="Id" align="center"></el-table-column>
        <el-table-column prop="name" label="姓名" align="center"></el-table-column>
        <el-table-column prop="username" label="用户名" align="center"></el-table-column>
        <el-table-column prop="gender" label="性别" align="center"></el-table-column>
        <el-table-column prop="mobile" label="手机号" align="center"></el-table-column>
        <el-table-column prop="email" label="邮箱" align="center"></el-table-column>
        <el-table-column prop="birthday" label="生日" align="center"></el-table-column>
        <el-table-column prop="gmtCreate" label="注册时间" align="center"></el-table-column>
        <!--<el-table-column label="操作">-->
          <!--<template slot-scope="scope">-->
            <!--<el-button @click="updateUserInfo" type="warning">修 改</el-button>-->
          <!--</template>-->
        <!--</el-table-column>-->
      </el-table>
      <!--工具条-->
      <el-col :span="24" class="toolbar">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="filter.page"
          :page-sizes="[10, 20, 50, 100]"
          :page-size="filter.rows"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total" style="float: right;">
        </el-pagination>
      </el-col>
    </div>
  </section>
</template>

<script>
  const gender = {
    0: '男',
    1: '女'
  };
  import {userMgmService} from "./UserMgmService";
  import commonUtil from '../common/CommonUtil'

  export default {
    // name: "userMgm",
    data() {
      return {
        total: 0,
        //数据加载图标
        listLoading: false,
        // 标签对齐方式
        labelPosition: 'right',
        genderList: [
          {
            value: '0',
            label: '男'
          },
          {
            value: '1',
            label: '女'
          }
        ],
        // 查询过滤器
        filter: {
          rows: 10,
          page: 1,
          optionParam: {
            name: '',
            username: '',
            mobile: '',
            email: '',
            gmtCreate: '',
            startDate: '',
            endDate: '',
            age: '',
            birthday: '',
            gender: '',
            achieve: ''
          },
        },
        // 用户列表
        userListByPage: []
      }
    },
    mounted() {
      this.$nextTick(() => {
        this.getUserListByPage();
      });
    },
    methods: {
      handleSizeChange(val) {
        this.filter.rows = val;
        this.getUserListByPage();
      },
      handleCurrentChange(val) {
        this.filter.page = val;
        this.getUserListByPage();
      },
      // 修改用户信息
      updateUserInfo() {
      },
      //  获取用户列表
      getUserListByPage() {
        this.listLoading = true;
        var date = this.filter.optionParam.gmtCreate;
        if (date.length > 1) {
          this.filter.optionParam.startDate = this.filter.optionParam.gmtCreate[0];
          this.filter.optionParam.endDate = this.filter.optionParam.gmtCreate[1];

        }
        userMgmService.getUserListByPage({params: this.filter}).then(res => {
          if (res.code == "0") {
            this.userListByPage = this.formatUserInfo(res.content.resultList);
            this.total = res.content.count;
          }
          else {
            this.$message.error('查询异常');
          }
          this.listLoading = false;
        })
      },
      // 格式化用户信息
      formatUserInfo(list) {
        for (let i = 0; i < list.length; i++) {
          list[i].gmtCreate = (list[i].gmtCreate == "" || list[i].gmtCreate == null) ? "" : commonUtil.formatDateTime(new Date(list[i].gmtCreate));
          list[i].birthday = (list[i].birthday == "" || list[i].birthday == null) ? "" : commonUtil.formatDate(new Date(list[i].birthday));
          if (list[i].gender.toString().trim() == '0') {
            list[i].gender = '男';
          } else {
            list[i].gender = '女';
          }
          // if(list[i].gender!="" && list[i].gender!=null){
          //   // ?'男':'女';
          //
          //
          // }
        }
        return list;
      },
      //  清空查询参数
      resetParam() {
        this.filter.optionParam = {}
      }
    },
    watch: {}
  }
</script>

<style scoped>
</style>
