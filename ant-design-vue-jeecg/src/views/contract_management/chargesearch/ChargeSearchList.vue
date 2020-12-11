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
        @change="handleTableChange"
        @expand="handleExpand"
      >
        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>
          <a-divider type="vertical" />
          <a @click="handleDetail(record)">打印</a>
        </span>
        <a-table
          slot="expandedRowRender"
          :columns="innerColumns"
          size="middle"
          :scroll="{ x: true }"
          bordered
          rowKey="id"
          :loading="loading"
          :dataSource="innerData"
          :pagination="false"
        >
        </a-table>
      </a-table>
    </div>

    <contract-purchase-modal ref="modalForm" @ok="modalFormOk"></contract-purchase-modal>
  </a-card>
</template>

<script>
import '@/assets/less/TableExpand.less'
import { mixinDevice } from '@/utils/mixin'
import { deleteAction, getAction, downFile, getFileAccessHttpUrl } from '@/api/manage'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import ContractPurchaseModal from './modules/ContractPurchaseModal'
import moment from 'moment'

export default {
  name: 'ContractPurchaseList',
  mixins: [JeecgListMixin, mixinDevice],
  components: {
    ContractPurchaseModal
  },
  data() {
    return {
      description: '采购合同表管理页面',
      rangeDate: [moment(this.date), moment(this.date)],
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
          dataIndex: 'create_by'
        },
        {
          title: '签订时间',
          align: 'center',
          dataIndex: 'signing_time',
          customRender: function(text) {
            return !text ? '' : text.length > 10 ? text.substr(0, 10) : text
          }
        },
        {
          title: '当前状态',
          align: 'center'
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
          dataIndex: 'materialCode'
        },
        {
          title: '物资名称',
          align: 'center',
          dataIndex: 'materialName'
        },
        {
          title: '规格型号',
          align: 'center',
          dataIndex: 'specificationModel'
        },
        {
          title: '单位',
          align: 'center',
          dataIndex: 'unit'
        },
        {
          title: '单价',
          align: 'center',
          dataIndex: 'unitPrice'
        },
        {
          title: '数量',
          align: 'center',
          dataIndex: 'quantity'
        },
        {
          title: '总价',
          align: 'center',
          dataIndex: 'totalPrice'
        },
        {
          title: '税金',
          align: 'center',
          dataIndex: 'taxes'
        },
        {
          title: '1月',
          align: 'center'
        },
        {
          title: '2月',
          align: 'center'
        },
        {
          title: '3月',
          align: 'center'
        },
        {
          title: '4月',
          align: 'center'
        },
        {
          title: '5月',
          align: 'center'
        },
        {
          title: '6月',
          align: 'center'
        },
        {
          title: '7月',
          align: 'center'
        },
        {
          title: '8月',
          align: 'center'
        },
        {
          title: '9月',
          align: 'center'
        },
        {
          title: '10月',
          align: 'center'
        },
        {
          title: '11月',
          align: 'center'
        },
        {
          title: '12月',
          align: 'center'
        },
        {
          title: '备注',
          align: 'center'
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
        findDetail: '/chargsearch/chargsearch/findDetail'
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
    initDictConfig() {},
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
            this.innerData = res.result.records
          }
        })
      }
    }
  }
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>