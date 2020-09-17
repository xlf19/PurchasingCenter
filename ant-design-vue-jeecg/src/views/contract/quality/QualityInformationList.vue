<template>
  <a-card :bordered="false">
    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">导入化验结果</a-button>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i>质检数据列表&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;已选择
        <a
          style="font-weight: 600"
        >{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>
      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        class="j-table-force-nowrap"
        @change="handleTableChange"
      >
      </a-table>
    </div>
    <!-- table区域-end -->
    <contract-list ref="ContractList"></contract-list>
  </a-card>
</template>

<script>
import '@/assets/less/TableExpand.less'
import { mixinDevice } from '@/utils/mixin'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import QualityInformationModal from './modules/QualityInformationModal'
import JDate from '@/components/jeecg/JDate.vue'
import ContractList from '../../contract/contract/ContractInformationList'

export default {
  name: 'QualityInformationList',
  mixins: [JeecgListMixin, mixinDevice],
  components: {
    JDate,
    QualityInformationModal,
    ContractList,
  },
  data() {
    return {
      description: '质检信息表管理页面',
      // 表头
      columns: [
        {
          title: '序号',
          dataIndex: '',
          key: 'rowIndex',
          width: 60,
          align: 'center',
          customRender: function (t, r, index) {
            return parseInt(index) + 1
          },
        },
        {
          title: '物资名称',
          align: 'center',
          dataIndex: 'materialName',
        },
        {
          title: '供货单位',
          align: 'center',
          dataIndex: 'supplier',
        },
        {
          title: '收货单位',
          align: 'center',
          dataIndex: 'receivingUnit',
        },
        {
          title: '取样日期',
          align: 'center',
          dataIndex: 'samplingDate',
          customRender: function (text) {
            return !text ? '' : text.length > 10 ? text.substr(0, 10) : text
          },
        },
        {
          title: '化验备注',
          align: 'center',
          dataIndex: 'laboratoryNotes',
        },
        {
          title: '派工单号',
          align: 'center',
          dataIndex: 'dispatchNumber',
        },
      ],
      url: {
        list: '/quality/qualityInformation/list',
        delete: '/quality/qualityInformation/delete',
        deleteBatch: '/quality/qualityInformation/deleteBatch',
        exportXlsUrl: '/quality/qualityInformation/exportXls',
        importExcelUrl: 'quality/qualityInformation/importExcel',
      },
      dictOptions: {},
    }
  },
  created() {},
  computed: {
    importExcelUrl: function () {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    },
  },
  methods: {
    initDictConfig() {},
  },
}
</script>
<style scoped>
@import '~@assets/less/common.less';

</style>