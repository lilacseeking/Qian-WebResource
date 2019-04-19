<template>
  <section>
    <!-- 搜索栏-->
    <div class="toolbar">
      <el-form :model="orderFilter" :label-position="labelPosition" label-width="35%">
        <el-row>
          <el-col :span="8">
            <el-form-item label="支付方姓名">
              <el-input v-model="orderFilter.payName" size="small" placeholder="请输入" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="课程名称">
              <el-input v-model="orderFilter.courseName" size="small" placeholder="请输入" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="订单编号">
              <el-input v-model="orderFilter.orderNo" size="small" placeholder="请输入" clearable></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="内部流水号">
              <el-input v-model="orderFilter.innerTradeNo" size="small" placeholder="请输入" clearable></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="支付方式">
              <el-select v-model="orderFilter.payMethod" placeholder="请选择">
                <el-option
                  v-for="item in payMethodList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="交易状态">
                <el-select v-model="orderFilter.tradeStatus" placeholder="请选择">
                  <el-option
                    v-for="item in tradeStatusList"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>

          <el-col :span="8">
            <el-form-item label="创建日期">
              <template>
                <div class="block">
                  <el-date-picker
                    v-model="orderFilter.dateList"
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
        </el-row>
        <el-row>
          <el-col :span="10" class="operation-bar">
            <el-button type="success" size="small" @click="getOrderListByPage">搜 索</el-button>
            <el-button type="error" size="small" @click="resetParam">清 空</el-button>
          </el-col>
        </el-row>
      </el-form>
    </div>

    <!--数据栏-->
    <div>
      <el-table :data="orderListByPage" fit v-loading="listLoading" style="width: 100%;" border>
        <el-table-column prop="id" label="订单Id" align="center"></el-table-column>
        <el-table-column prop="orderNo" label="订单编号" align="center"></el-table-column>
        <el-table-column prop="gmtCreate" label="创建时间" align="center">
          <template slot-scope="scope">
            {{ scope.row.gmtCreate | FormatDataTime }}
          </template>
        </el-table-column>
        <el-table-column prop="innerTradeNo" label="内部流水号" align="center"></el-table-column>
        <el-table-column prop="courseName" label="课程名称" align="center"></el-table-column>
        <el-table-column prop="payName" label="支付方姓名" align="center"></el-table-column>
        <el-table-column prop="amount" label="支付金额" align="center">
          <template slot-scope="scope">
            {{ scope.row.amount }}元
          </template>
        </el-table-column>
        <el-table-column prop="payMethod" label="支付方式" align="center">
          <template slot-scope="scope">
            {{ scope.row.payMethod | FormatPayMethod }}
          </template>
        </el-table-column>
        <el-table-column prop="tradeStatus" label="交易状态" align="center">
          <template slot-scope="scope">
            {{ scope.row.tradeStatus | FormatTradeStatus }}
          </template>
        </el-table-column>
      </el-table>
      <!--工具条-->
      <el-col :span="24" class="toolbar">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="orderFilter.page"
          :page-sizes="[10, 20, 50, 100]"
          :page-size="orderFilter.rows"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total" style="float: right;">
        </el-pagination>
      </el-col>
    </div>
  </section>
</template>

<script>
  const TradeStatus = {
    NON_PAY: '未支付',
    PAY_SUCCESS: '支付成功',
    PAY_ING: '正在支付',
    PAY_FAILED: '支付失败',
    PAY_CLOSE: '支付关闭',
  };
  const PayMethod = {
    BALANCE_PAY: '余额支付',
  };
  import {orderService} from './OrderService'
  import commonUtil from '../common/CommonUtil'

  export default {
    data() {
      return {
        labelPosition: 'right',
        listLoading: false,
        orderListByPage: [],
        total: 0,
        tradeStatusList: [{
          value: 'NON_PAY',
          label: '未支付'
        },{
          value: 'PAY_SUCCESS',
          label: '支付成功'
        },{
          value: 'PAY_CLOSE',
          label: '支付关闭'
        }],
        payMethodList:[
          {
            value:'BALANCE_PAY',
            label:'余额支付'
          }
        ],
        // 订单搜索条件
        orderFilter: {
          payName: '',
          sellerName: '',
          courseName: '',
          orderNo: '',
          innerTradeNo: '',
          tradeStatus: '',
          payMethod: '',
          startDate: '',
          endDate:'',
          dateList:'',
          page: 1,
          rows: 20
        },
      }
    },
    mounted() {
      this.$nextTick(() => {
        this.getOrderListByPage();
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
      },
      FormatTradeStatus(val){
        return TradeStatus[val];
      },
      FormatPayMethod(val){
        return PayMethod[val];
      }
    },
    methods: {
      // 获取课程列表
      getOrderListByPage() {
        var date = this.orderFilter.dateList;
        if (date.length > 1) {
          this.orderFilter.startDate = this.orderFilter.dateList[0];
          this.orderFilter.endDate = this.orderFilter.dateList[1];

        }
        orderService.getOrderListByPage(this.orderFilter).then(res => {
          if (res.code == 0) {
            this.total = res.content.count;
            this.orderListByPage = this.formatOrderInfo(res.content.resultList);
          }
        })
      },
      // 课程列表格式化
      formatOrderInfo(orderList) {
        return orderList;
      },
      // 搜索条件清空
      resetParam() {

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
