<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :md="6" :sm="8">
            <a-form-item label="起止日期">
              <a-range-picker v-model="rangeDate" @change="onChange" />
            </a-form-item>
          </a-col>

          <a-col :xl="4" :lg="7" :md="8" :sm="24">
            <span style="float: left; overflow: hidden" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
              <a-button type="primary" @click="getHttpRequestData" icon="reload" style="margin-left: 8px"
                >状态刷新</a-button
              >
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <!-- <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
    </div> -->

    <!-- table区域-begin -->
    <div>
      <!-- <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择
        <a style="font-weight: 600">{{ selectedRowKeys.length }}</a
        >项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div> -->
      <a-table
        ref="table"
        size="middle"
        :scroll="{ x: true }"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :expandedRowKeys="expandedRowKeys"
        class="j-table-force-nowrap"
        @expand="handleExpand"
      >
        <span slot="action" slot-scope="text, record">
          <a href="javascript:;" @click="handleEdit(record)">编辑</a>
          <a-divider type="vertical" />
          <a href="javascript:;" @click="handleprint(record)">打印</a>
        </span>
        <a-table
          slot="handleExpand"
          :columns="innerColumns"
          :dataSource="innerData"
          size="middle"
          bordered
          rowKey="id"
          :loading="loading"
          :pagination="false"
        >
        </a-table>
      </a-table>
    </div>
  </a-card>
</template>

<script>
import '@/assets/less/TableExpand.less'
import { mixinDevice } from '@/utils/mixin'
import { deleteAction, getAction, getFileAccessHttpUrl } from '@/api/manage'
import { initDictOptions, filterDictText, filterMultiDictText } from '@/components/dict/JDictSelectUtil'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import moment from 'moment'

export default {
  name: 'ContractPurchaseList',
  mixins: [JeecgListMixin, mixinDevice],
  components: {},
  data() {
    return {
      description: '采购合同表管理页面',
      rangeDate: [moment(this.date), moment(this.date)],
      namelist: [],
      // 表头
      columns: [
        {
          title: '序号',
          dataIndex: '',
          key: 'rowIndex',
          width: 60,
          align: 'center',
          customRender: function(t, r, index) {
            return parseInt(index) + 1
          }
        },
        {
          title: '合同编号',
          align: 'center',
          dataIndex: 'contract_no'
        },
        {
          title: '合同类型',
          align: 'center',
          dataIndex: 'contract_type'
        },
        {
          title: '供应商名称',
          align: 'center',
          dataIndex: 'supplier'
        },
        {
          title: '合同总金额',
          align: 'center',
          dataIndex: 'total_price'
        },
        {
          title: '合同备注',
          align: 'center',
          dataIndex: 'contract_notes'
        },
        {
          title: '业务员',
          align: 'center',
          dataIndex: 'create_by',
          customRender: text => {
            //字典值替换通用方法
            return filterMultiDictText(this.namelist, text)
          }
        },
        {
          title: '签订时间',
          align: 'center',
          dataIndex: 'signingdata',
          customRender: function(text) {
            return !text ? '' : text.length > 10 ? text.substr(0, 10) : text
          }
        },
        {
          title: '当前状态',
          align: 'center',
          dataIndex: 'current_state',
          customRender: function(t, r, index) {
            if (t === '生成合同') {
              return '合同制作'
            } else if (t === '在办') {
              return '正审批中'
            } else if (t === '退回') {
              return '领导退回'
            } else if (t === '完成') {
              return '审批完成'
            } else {
              return '状态未知'
            }
          }
        },
        {
          title: '税率',
          align: 'center',
          dataIndex: 'tax_rate'
        },
        {
          title: '操作',
          dataIndex: 'action',
          align: 'center',
          width: 147,
          scopedSlots: { customRender: 'action' }
        }
      ],
      //合同明细信息
      innerColumns: [
        {
          title: '物资编码',
          align: 'center',
          dataIndex: 'material_code'
        },
        {
          title: '物资名称',
          align: 'center',
          dataIndex: 'material_name'
        },
        {
          title: '规格型号',
          align: 'center',
          dataIndex: 'specification_model'
        },
        {
          title: '单位',
          align: 'center',
          dataIndex: 'unit'
        },
        {
          title: '单价',
          align: 'center',
          dataIndex: 'unit_price'
        },
        {
          title: '数量',
          align: 'center',
          dataIndex: 'quantity'
        },
        {
          title: '总价',
          align: 'center',
          dataIndex: 'total_price'
        },
        {
          title: '税金',
          align: 'center',
          dataIndex: 'taxes'
        },
        {
          title: '1月',
          align: 'center',
          dataIndex: 'january'
        },
        {
          title: '2月',
          align: 'center',
          dataIndex: 'february'
        },
        {
          title: '3月',
          align: 'center',
          dataIndex: 'march'
        },
        {
          title: '4月',
          align: 'center',
          dataIndex: 'april'
        },
        {
          title: '5月',
          align: 'center',
          dataIndex: 'may'
        },
        {
          title: '6月',
          align: 'center',
          dataIndex: 'june'
        },
        {
          title: '7月',
          align: 'center',
          dataIndex: 'july'
        },
        {
          title: '8月',
          align: 'center',
          dataIndex: 'august'
        },
        {
          title: '9月',
          align: 'center',
          dataIndex: 'september'
        },
        {
          title: '10月',
          align: 'center',
          dataIndex: 'october'
        },
        {
          title: '11月',
          align: 'center',
          dataIndex: 'november'
        },
        {
          title: '12月',
          align: 'center',
          dataIndex: 'december'
        },
        {
          title: '备注',
          align: 'center',
          dataIndex: 'remarks'
        }
      ],
      innerData: [],
      expandedRowKeys: [],
      url: {
        list: '/chargsearch/chargsearch/list',
        delete: '/contractpurchase/contractPurchase/delete',
        deleteBatch: '/contractpurchase/contractPurchase/deleteBatch',
        exportXlsUrl: '/contractpurchase/contractPurchase/exportXls',
        importExcelUrl: 'contractpurchase/contractPurchase/importExcel',
        findDetail: '/chargsearch/chargsearch/findDetail',
        getHttpRequestData: '/chargsearch/chargsearch/getHttpRequestData'
      },
      dictOptions: {}
    }
  },
  created() {},
  computed: {
    importExcelUrl: function() {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    }
  },
  methods: {
    initDictConfig() {
      //初始化字典 - 业务员
      initDictOptions('sys_user,realname,username').then(res => {
        if (res.success) {
          this.namelist = res.result
        }
      })
    },

    initRangeDate() {
      this.rangeDate = [moment(this.date), moment(this.date)]
    },
    onChange(date, dateString) {
      if (dateString[0].length < 2) {
        this.initRangeDate()
      }
    },
    searchQuery() {
      this.queryParam.startingTime = this.rangeDate[0].format('YYYY-MM-DD')
      this.queryParam.stopTime = this.rangeDate[1].format('YYYY-MM-DD')
      this.loadData(1)
    },
    searchReset() {
      this.initRangeDate()
      this.searchQuery()
    },
    handleExpand(expanded, record) {
      this.expandedRowKeys = []
      this.innerData = []
      if (expanded === true) {
        this.loading = true
        this.expandedRowKeys.push(record.id)
        getAction(this.url.findDetail, { contractid: record.id }).then(res => {
          if (res.success) {
            this.loading = false
            this.innerData = res.result
          }
        })
      }
    },
    //打印
    handleprint(record) {
      this.$router.push({ path: '/contract_management/chargesearch/ContractPrint', query: { data: record } })
    },
    //编辑
    handleEdit(record) {
      let statue=record.current_state
      if(statue==='生成合同'||statue==='退回'){
        this.$router.push({ path: '/contract_management/chargesearch/ContractEditTab', query: { data: record } })
      }else{
         this.$message.error('这条合同不能修改，请重新选择！')
      }
    },
    //状态刷新
    getHttpRequestData() {
      getAction(this.url.getHttpRequestData).then(res => {
        if (res.success) {
          this.loadData(1)
        }
      })
    }
  }
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>