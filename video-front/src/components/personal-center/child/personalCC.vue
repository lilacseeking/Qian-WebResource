<template>
    <div >
        <div style="border-bottom:1px solid #f2f2f2;padding: 10px;margin-bottom: 18px;line-height: 36px;"> <span > 我的订单</span>  </div>
   <div class="courseBody" >

       <el-row style="padding: 10px;">  <span > 订单列表</span></el-row>
       <!-- 订单列表 -->
       <el-row >

           <template>
    <el-table
      :data="myOrderList.resultList "
      :header-cell-style="{background:'rgba(242,242,242,1)'}"
      style="width: 100%">
      <el-table-column
        prop="courseName"
        label="课程名称"
        width="80">
      </el-table-column>
      <el-table-column
        prop="orderNo"
        label="订单编号"
        width="80">
        <template slot-scope="scope">
          <p :title="scope.row.orderNo" > {{ scope.row.orderNo }} </p>
        </template>
      </el-table-column>
      <el-table-column
        prop="amount"
        width="80"
        label="价格">
        <template slot-scope="scope">
          <span > {{ scope.row.amount}} 元</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="payName"
        width="120"
        label="支付方">
      </el-table-column>
      <!-- <el-table-column
        prop="sellerId "
        label="收款方">
      </el-table-column> -->
      <el-table-column
        prop="gmtCreate"
        width="120"
        label="创建日期">
        <template slot-scope="scope">
        {{  scope.row.gmtCreate | dealDate }}
      </template>
      </el-table-column>
      <el-table-column
        prop="payMethod"
        width="100"
        label="支付方式">
        <template slot-scope="scope">
        {{  scope.row.payMethod | payM }}
      </template>
      </el-table-column>

      <el-table-column
        prop="tradeStatus"
        width="100"
        label="交易状态">
        <template slot-scope="scope">
          <span class="point" :class=" scope.row.tradeStatus === 'NON_PAY' ? 'nopay' :'ispay' "></span><span style="display:inline-block;"> {{ scope.row.tradeStatus | status  }}</span>
        </template>
      </el-table-column>

      <el-table-column
        label="操作">
        <template slot-scope="scope">
        
        <div v-if="scope.row.tradeStatus === 'PAY_CLOSE'"> 关闭订单</div>
        <div v-else-if="scope.row.tradeStatus === 'NON_PAY'">
          <el-button size="mini" type="primary" @click="_payOder(scope.row.orderNo)" >支付订单 </el-button>
          <el-button size="mini" @click="_closeOrder(scope.row.orderNo)">关闭订单 </el-button>
        </div>
        <div v-else> 完成订单</div>
      </template>
      </el-table-column>


    </el-table>
  </template>

       </el-row>

   </div>
    </div>
</template>

<script>

import { getOrderList, payOrder, closeOrder } from '../../../util/api'
import {mapState} from 'vuex'
var moment = require('moment')

const status = {
  NON_PAY: '未支付',
  PAY_SUCCESS: '支付成功',
  PAY_ING: '正在支付',
  PAY_FAILED: '支付失败',
  PAY_CLOSE: '支付关闭'
}
const payM = {
  BALANCE_PAY: '余额支付'
}
// const payOp = {
//   BALANCE_PAY: '支付订单',
//   BALANCE_PAY: '关闭订单'
// }
export default {
  components: {},
  data: function() {
    return {
      tableData: [
        {
          name: '张珊',
          num: '123',
          price: '45',
          date: '2016-05-02',
          status: '交易成功'
        },
        {
          name: '张珊2',
          num: '123',
          price: '45',
          date: '2016-05-02',
          status: '交易成功'
        },
        {
          name: '张珊3',
          num: '123',
          price: '45',
          date: '2016-05-02',
          status: '交易成功'
        }
      ],
      myOrderList: {}
    }
  },
  filters: {
    status (val) {
      return status[val]
    },
    dealDate (val) {
      return moment(val).format('YYYY-MM-DD')
    },
    payM (val) {
      return payM[val]
    }
  },
  computed: {
    ...mapState({
      isLogin: 'isLogin',
      userInfo: 'userInfo'
    })
  },
  mounted() {
    this.orderList()
  },
  methods: {
    // 支付订单
    _payOder(orderNo) {
      const params = {
        'orderNo': orderNo
      }

      this.$confirm('此操作将支付订单, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        payOrder(params).then(res => {
          const { code } = {...res.data}
          if (code === 0) {
            this.orderList()
            this.$message({
              type: 'success',
              message: '支付成功!'
            })
          }
        })
      })
    },
    // 关闭订单
    _closeOrder(orderNo) {
      const params = {
        'orderNo': orderNo
      }

      this.$confirm('此操作将关闭订单, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        closeOrder(params).then(res => {
          const { code } = {...res.data}
          if (code === 0) {
            this.orderList()
            this.$message({
              type: 'success',
              message: '删除成功!'
            })
          }
        })
      })
    },
    handleClick(row) {
      console.log(row)
    },
    orderList () {
      const params = {
        'page': 1,
        'rows': 20,
        // 'startDate': '2018-01-05',
        // 'endDate': '2020-02-05',
        // 'courseId': 5,
        'payId': this.userInfo.id
      }

      getOrderList(params).then(res => {
        const {code, content} = {...res.data}
        if (code === 0) {
          this.myOrderList = content
        }
      })
    }
  }
}
</script>

<style scoped>
.item{
    width: 100%;
    height: 300px;
    box-shadow: 1px 6px 7px #f2f2f2;
}
.item > div {
    display: inline-block;
    text-align: center;
    vertical-align: middle;
    margin-right: 20px;
}
.item  img {
    width: 100%;
}
.point{
  display: inline-block;
    width: 6px;
    height: 6px;
    border-radius: 50%;
    margin-right: 4px;
}
.ispay {
 background: green;
}
.nopay {
  background: #7c7373
}
</style>

