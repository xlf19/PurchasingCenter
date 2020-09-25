<template>
  <a-card :bordered="false" :class="{ abcdefg: true }">
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery" :form="form">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="合同编号">
              <a-select v-model="queryParam.contractNo" show-search allowClear @search="findHt" @change="findOne">
                <a-select-option
                  placeholder="请选择合同号"
                  :value="item"
                  v-for="(item, index) in contractNos"
                  :key="index"
                  >{{ item }}</a-select-option
                >
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="凭证号">
              <a-select v-model="queryParam.voucherNo" allowClear>
                <a-select-option
                  placeholder="请选择凭证号"
                  :value="item"
                  v-for="(item, index) in voucherNos"
                  :key="index"
                  >{{ item }}</a-select-option
                >
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left; overflow: hidden" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchHtbh" icon="search" ghost>查询</a-button>
              <a-button v-print="'#printContent'" type="primary" style="margin-left: 8px" ghost>打印</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>

    <section ref="print" id="printContent" class="abcdefg">
      <div style="text-align: center">
        <p style="font-size: 24px; font-weight: 800">废钢自动结算单</p>
      </div>
      <a-row :gutter="24" style="text-align: left">
        <a-col :span="6"></a-col>
        <a-col :span="8">
          <span>执行:{{ queryParam.contractNo }} 合同标准</span>
        </a-col>
        <a-col :span="9">
          <span>供货单位:{{ supplier }}</span>
        </a-col>
      </a-row>
      <a-row :gutter="24" style="text-align: left; margin-top: 10px">
        <a-col :span="6"></a-col>
        <a-col :span="8">
          <span>收货单位:{{ receivingUnit }}</span>
        </a-col>
        <a-col :span="9">
          <span>凭证号:汇--{{ queryParam.voucherNo }}</span>
        </a-col>
      </a-row>
      <!--签字-->
      <div style="margin-top: 10px">
        <a-table
          ref="table"
          size="middle"
          bordered
          style="color: black"
          rowKey="id"
          :columns="columns"
          :dataSource="dataSource"
          :pagination="false"
          :loading="loading"
        ></a-table>
      </div>
      <div>
        <a-row :gutter="24" style="text-align: center; margin-top: 10px">
          <a-col :span="24">
            <span style="font-size: 18px">检斤:{{ weighing }}</span>
          </a-col>
        </a-row>
        <a-row :gutter="24" style="text-align: center; margin-top: 10px">
          <a-col :span="24">
            <span style="font-size: 18px">总计：贷款:￥{{ loan }}+税金:￥{{ taxes }}=结算金额：￥{{ zprice }}</span>
          </a-col>
        </a-row>
      </div>
      <div>
        <a-row :gutter="24" style="text-align: left; margin-top: 10px">
          <a-col :span="6"></a-col>
          <a-col :span="9">
            <span>结算人：{{ clearingHouse }}</span>
          </a-col>
          <a-col :span="9">
            <span>结算日期：{{ settlementDate }}</span>
          </a-col>
        </a-row>
      </div>
    </section>
  </a-card>
  <!--</page-layout>-->
</template>
<script>
import ACol from 'ant-design-vue/es/grid/Col'
import ARow from 'ant-design-vue/es/grid/Row'
import ATextarea from 'ant-design-vue/es/input/TextArea'
import { getAction, postAction } from '@/api/manage'
import { validateDuplicateValue, randomUUID, handleStatus, filterObj } from '@/utils/util'
import { mixinDevice } from '@/utils/mixin'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'

export default {
  mixins: [JeecgListMixin, mixinDevice],
  components: {
    ATextarea,
    ARow,
    ACol,
  },
  name: 'shentieprint',
  props: {
    contrac: {
      type: String,
    },
  },
  watch: {
    contrac(newVal, oldVal) {
      this.queryParam.contractNo = newVal
      this.queryParam.voucherNo = null
      this.findHt(newVal)
      this.findOne(newVal)
    },
  },
  data() {
    return {
      description: '生铁打印单页面',
      disableMixinCreated: true,
      form: this.$form.createForm(this),
      //   /* 查询条件-请不要在queryParam中声明非字符串值的属性 */
      queryParam: {
        contractNo: this.contrac,
      },
      /* 数据源 */
      dataSource: [],
      //合同号
      contractNos: [],
      //凭证号
      voucherNos: [],
      //供货单位
      supplier: '',
      //收货单位
      receivingUnit: '',
      //结算日期
      settlementDate: '',
      //结算人
      clearingHouse: '',
      //贷款
      loan: 0,
      //税金
      taxes: 0,
      //检斤
      weighing: 0,
      //总金额
      zprice: 0,
      // 表头
      columns: [
        {
          title: '入库日期',
          align: 'center',
          dataIndex: 'settlementDate',
          customRender: function (text) {
            return !text ? '' : text.length > 10 ? text.substr(0, 10) : text
          },
        },
        {
          title: '检斤',
          align: 'center',
          dataIndex: 'settlementQuantity',
        },
        {
          title: '备注',
          align: 'center',
          dataIndex: 'remarks',
        },
        {
          title: '贷款',
          align: 'center',
          dataIndex: 'loan',
        },
        {
          title: '税率',
          align: 'center',
          dataIndex: 'taxRate',
        },
        {
          title: '税金',
          align: 'center',
          dataIndex: 'settlementTaxes',
        },
        {
          title: '结算金额',
          align: 'center',
          dataIndex: 'settlementResults',
        },
      ],
      url: {
        list: '/feigang/feigang/selectfgdy',
        selectHtbh: '/contract/contractInformation/selectHtbh',
        selectHtpzh: '/contract/contractInformation/selectHtpzh',
      },
      dictOptions: {},
    }
  },
  created() {
    let hth = this.queryParam.contractNo
    this.findHt(hth)
    if (hth != '' && hth != null) {
      this.findOne(hth)
    }
  },
  //   mounted() {
  //     this.tableAddTotalRow(this.columns, this.dataSource)
  //   },
  methods: {
    //获取合同号
    findHt(hth) {
      getAction(this.url.selectHtbh, { htbh: hth, httype: '废钢' }).then((res) => {
        if (res.success) {
          this.contractNos = res.result
        }
        if (res.code === 510) {
          this.$message.warning(res.message)
        }
        this.loading = false
      })
    },
    findOne(hth) {
      getAction(this.url.selectHtpzh, { htbh: hth, httype: '废钢' }).then((res) => {
        if (res.success) {
          this.voucherNos = res.result
        }
        if (res.code === 510) {
          this.$message.warning(res.message)
        }
        this.loading = false
      })
    },
    searchHtbh() {
      let contractNo = this.queryParam.contractNo
      let voucherNo = this.queryParam.voucherNo
      if (contractNo === '' || contractNo === null) {
        this.$message.warning('请选择合同号')
        return
      }
      if (voucherNo === '' || voucherNo === null || voucherNo === undefined) {
        this.$message.warning('请选择凭证号')
        return
      }
      var params = this.getQueryParams() //查询条件
      this.loading = true
      getAction(this.url.list, params).then((res) => {
        if (res.success) {
          if (res.result.records.length > 0) {
            this.supplier = res.result.records[0].supplier
            this.receivingUnit = res.result.records[0].receivingUnit
            this.settlementDate = res.result.records[0].settlementDate
            this.clearingHouse = res.result.records[0].clearingHouse
            this.dataSource = res.result.records
            this.ipagination.total = res.result.total
            this.loan = this.dataSource.reduce(function (preValue, cont) {
              return preValue + cont.loan
            }, 0)
            this.taxes = this.dataSource.reduce(function (preValue, cont) {
              return preValue + cont.settlementTaxes
            }, 0)
            this.weighing = this.dataSource.reduce(function (preValue, cont) {
              return preValue + cont.settlementQuantity
            }, 0)
            this.zprice = this.loan + this.taxes
            this.tableAddTotalRow(this.columns, this.dataSource)
          }
        }
        if (res.code === 510) {
          this.$message.warning(res.message)
        }

        this.loading = false
      })
    },
    /** 表格增加合计行 */
    tableAddTotalRow(columns, dataSource) {
      let numKey = 'settlementDate'
      let totalRow = { [numKey]: '合计' }
      columns.forEach((column) => {
        let { key, dataIndex } = column
        if (![key, dataIndex].includes(numKey)) {
          let total = 0
          dataSource.forEach((data) => {
            total += /^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$/.test(data[dataIndex])
              ? Number.parseFloat(data[dataIndex])
              : Number.NaN
          })
          if (Number.isNaN(total)) {
            total = '-'
          }
          console.log([dataIndex])
          totalRow[dataIndex] = total
        }
      })
      dataSource.push(totalRow)
    },
  },
}
</script>
<style scoped>
/*update_begin author:scott date:20191203 for:打印机打印的字体模糊问题 */
* {
  color: #000000 !important;
  -webkit-tap-highlight-color: #000000 !important;
}
/*update_end author:scott date:20191203 for:打印机打印的字体模糊问题 */

.abcdefg .ant-card-body {
  margin-left: 0%;
  margin-right: 0%;
  margin-bottom: 1%;
  border: 0px solid black;
  min-width: 1600px;
  color: #000000 !important;
}
.explain {
  text-align: left;
  margin-left: 50px;
  color: #000000 !important;
}
.explain .ant-input,
.sign .ant-input {
  font-weight: bolder;
  text-align: center;
  border-left-width: 0px !important;
  border-top-width: 0px !important;
  border-right-width: 0px !important;
}
.explain div {
  margin-bottom: 10px;
}
/* you can make up upload button and sample style by using stylesheets */
.ant-upload-select-picture-card i {
  font-size: 32px;
  color: #999;
}

.ant-upload-select-picture-card .ant-upload-text {
  margin-top: 8px;
  color: #666;
}
</style>