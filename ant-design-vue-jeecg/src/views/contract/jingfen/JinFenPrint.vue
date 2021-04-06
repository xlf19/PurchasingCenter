<template>
  <a-card :bordered="false" :class="{ abcdefg: true }">
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery" :form="form">
        <a-row :gutter="24">
          <a-col :xl="4" :lg="7" :md="8" :sm="24">
            <a-form-item label="结算依据" placeholder="请选择结算依据">
              <a-select v-model="settlementyj" @change="findcolumns">
                <a-select-option value="外检">外检</a-select-option>
                <a-select-option value="内检">内检</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :xl="4" :lg="7" :md="8" :sm="24">
            <a-form-item label="物资类别">
              <a-select v-model="contracttype" placeholder="请选择物资类别">
                <a-select-option value="精矿粉">精矿粉</a-select-option>
                <a-select-option value="球团">球团</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :xl="5" :lg="7" :md="8" :sm="24">
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
          <a-col :xl="5" :lg="7" :md="8" :sm="24">
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
        <p style="font-size: 24px; font-weight: 800">精矿粉&nbsp;&nbsp;&nbsp;结算单</p>
      </div>
      <a-row :gutter="24" style="text-align: center">
        <a-col :span="6">
          <span>执行:{{ queryParam.contractNo }} 合同标准</span>
        </a-col>
        <a-col :span="6">
          <span>供货单位:{{ supplier }}</span>
        </a-col>
        <a-col :span="6">
          <span>收货单位:{{ receivingUnit }}</span>
        </a-col>
        <a-col :span="6"
          ><span>凭证号:汇--{{ queryParam.voucherNo }}</span></a-col
        >
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
            <span style="font-size: 18px">货款:￥{{ loan }}+税金：￥{{ taxes }}=结算金额：￥{{ zprice }}</span>
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
import * as math from 'mathjs'
export default {
  mixins: [JeecgListMixin, mixinDevice],
  components: {
    ATextarea,
    ARow,
    ACol
  },
  name: 'shentieprint',
  props: {
    contrac: {
      type: String
    }
  },
  watch: {
    contrac(newVal, oldVal) {
      this.queryParam.contractNo = newVal
      this.queryParam.voucherNo = null
      this.findHt(newVal)
      this.findOne(newVal)
    }
  },
  data() {
    return {
      description: '生铁打印单页面',
      disableMixinCreated: true,
      form: this.$form.createForm(this),
      //   /* 查询条件-请不要在queryParam中声明非字符串值的属性 */
      queryParam: {
        contractNo: this.contrac
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
      //结算依据
      settlementyj: '外检',
      //物资类别
      contracttype: '精矿粉',
      //货款
      loan: 0,
      //税金
      taxes: 0,
      //总金额
      zprice: 0,
      //行数
      numberhs: 0,
      // 表头
      wjcolumns: [
        {
          title: '入库日期',
          align: 'center',
          dataIndex: 'rkDate',
          customRender: function(text) {
            return !text ? '' : text.length > 10 ? text.substr(0, 10) : text
          }
        },
        {
          title: '检斤',
          align: 'center',
          dataIndex: 'weighing'
        },
        {
          title: '含税单价',
          align: 'center',
          dataIndex: 'contractPrice'
        },
        {
          title: '不含税单价',
          align: 'center',
          dataIndex: 'settlemenPrice'
        },
        {
          title: '货款',
          align: 'center',
          dataIndex: 'loan'
        },
        {
          title: '税率',
          align: 'center',
          dataIndex: 'taxRate'
        },
        {
          title: '税金',
          align: 'center',
          dataIndex: 'taxes'
        }
      ],

      columns: [
        {
          title: '入库日期',
          align: 'center',
          dataIndex: 'rkDate',
          customRender: function(text) {
            return !text ? '' : text.length > 10 ? text.substr(0, 10) : text
          }
        },
        {
          title: '检斤',
          align: 'center',
          dataIndex: 'weighing'
        },
        {
          title: '含税单价',
          align: 'center',
          dataIndex: 'contractPrice'
        },
        {
          title: '不含税单价',
          align: 'center',
          dataIndex: 'settlemenPrice'
        },
        {
          title: '货款',
          align: 'center',
          dataIndex: 'loan'
        },
        {
          title: '税率',
          align: 'center',
          dataIndex: 'taxRate'
        },
        {
          title: '税金',
          align: 'center',
          dataIndex: 'taxes'
        }
      ],

      njcolumns: [
        {
          title: '入库日期',
          align: 'center',
          dataIndex: 'rkDate',
          customRender: function(text) {
            return !text ? '' : text.length > 10 ? text.substr(0, 10) : text
          }
        },
        {
          title: '检斤',
          align: 'center',
          dataIndex: 'weighing'
        },
        {
          title: '扣猛',
          align: 'center',
          dataIndex: 'KMnO'
        },
        {
          title: '扣水',
          align: 'center',
          dataIndex: 'KH2O'
        },
        {
          title: '扣钾',
          align: 'center',
          dataIndex: 'KK2O'
        },
        {
          title: '扣锌',
          align: 'center',
          dataIndex: 'KZn'
        },
        // {
        //   title: '品位价',
        //   align: 'center',
        // },
        {
          title: '扣硫',
          align: 'center',
          dataIndex: 'KS'
        },
        {
          title: '扣铝',
          align: 'center',
          dataIndex: 'KAL2O3'
        },
        {
          title: '扣硅',
          align: 'center',
          dataIndex: 'KSIO2'
        },
        {
          title: '扣磷',
          align: 'center',
          dataIndex: 'KP'
        },
        {
          title: '扣钛',
          align: 'center',
          dataIndex: 'KTiO2'
        },
        {
          title: '结算单价',
          align: 'center',
          dataIndex: 'settlemenPrice'
        },
        {
          title: '结算数量',
          align: 'center',
          dataIndex: 'settlementQuantity'
        },
        {
          title: '货款',
          align: 'center',
          dataIndex: 'loan'
        },
        {
          title: '税率',
          align: 'center',
          dataIndex: 'taxRate'
        },
        {
          title: '税金',
          align: 'center',
          dataIndex: 'taxes'
        }
      ],

      url: {
        list: '/jingfen/jingfen/selectjfdy',
        selectHtbh: '/contract/contractInformation/selectHtbh',
        selectHtpzh: '/contract/contractInformation/selectHtpzh'
      },
      dictOptions: {}
    }
  },
  created() {
    let hth = this.queryParam.contractNo
    this.findHt(hth)
    if (hth != '' && hth != null) {
      this.findOne(hth)
    }
  },

  methods: {
    //结算依据
    findcolumns(type) {
      if (type === '外检') {
        this.columns = this.wjcolumns
      } else {
        this.columns = this.njcolumns
      }
    },
    //获取合同号
    findHt(hth) {
      getAction(this.url.selectHtbh, { htbh: hth, httype: '精粉富粉球团' }).then(res => {
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
      getAction(this.url.selectHtpzh, { htbh: hth, httype: '精粉富粉球团' }).then(res => {
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
      getAction(this.url.list, params).then(res => {
        if (res.success) {
          if (res.result.records.length > 0) {
            this.numberhs = res.result.records.length
            this.supplier = res.result.records[0].supplier
            this.receivingUnit = res.result.records[0].receivingUnit
            this.settlementDate = res.result.records[0].settlementDate
            this.clearingHouse = res.result.records[0].clearingHouse
            this.dataSource = res.result.records
            this.ipagination.total = res.result.total
            this.tableAddTotalRow(this.columns, this.dataSource)
          }
        }
        if (res.code === 510) {
          this.$message.warning(res.message)
        }

        this.loading = false
      })
    },
    printFn(value) {
      const precision = 14
      return Number(math.format(value, precision))
    },
    /** 表格增加合计行 */
    tableAddTotalRow(columns, dataSource) {
      let numberhs = this.numberhs
      let numKey = 'weighingDate'
      let totalRow = { [numKey]: '合计' }
      columns.forEach(column => {
        let { key, dataIndex } = column
        if (![key, dataIndex].includes(numKey)) {
          let total = 0
          dataSource.forEach(data => {
            if (data[dataIndex] === null || data[dataIndex] === '' || dataIndex === undefined) {
              data[dataIndex] = 0
            }
            total = this.printFn(
              total +
                (/^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$/.test(data[dataIndex])
                  ? Number.parseFloat(data[dataIndex])
                  : Number.NaN)
            )
          })
          if (Number.isNaN(total)) {
            total = '-'
          }
          totalRow[dataIndex] = total
        }
      })
      let taxrate = this.printFn(totalRow['taxRate'] / numberhs)
      let contractPrice = this.printFn(totalRow['contractPrice'] / numberhs)
      let settlemenPrice = this.printFn(totalRow['settlemenPrice'] / numberhs)
      let KMnO = this.printFn(totalRow['KMnO'] / numberhs)
      let KH2O = this.printFn(totalRow['KH2O'] / numberhs)
      let KK2O = this.printFn(totalRow['KK2O'] / numberhs)
      let KZn = this.printFn(totalRow['KZn'] / numberhs)
      let KS = this.printFn(totalRow['KS'] / numberhs)
      let KAL2O3 = this.printFn(totalRow['KAL2O3'] / numberhs)
      let KSIO2 = this.printFn(totalRow['KSIO2'] / numberhs)
      let KP = this.printFn(totalRow['KP'] / numberhs)
      let KTiO2 = this.printFn(totalRow['KTiO2'] / numberhs)
      this.taxes = totalRow['taxes']
      if (totalRow['loan'] === '-') {
        this.loan = 0
      } else {
        this.loan = totalRow['loan']
      }
      this.taxes = parseFloat(this.taxes.toFixed(2))
      this.loan = parseFloat(this.loan.toFixed(2))
      this.zprice = this.printFn(this.taxes + this.loan)
      this.zprice = parseFloat(this.zprice.toFixed(2))

      totalRow['contractPrice'] ='-'
      totalRow['settlemenPrice'] ='-'
      totalRow['KMnO'] = '-'
      totalRow['KH2O'] = '-'
      totalRow['KK2O'] = '-'
      totalRow['KZn'] = '-'
      totalRow['KS'] = '-'
      totalRow['KAL2O3'] ='-'
      totalRow['KSIO2'] ='-'
      totalRow['KP'] ='-'
      totalRow['KTiO2'] ='-'
      totalRow['taxRate'] = taxrate
      dataSource.push(totalRow)
    }
  }
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