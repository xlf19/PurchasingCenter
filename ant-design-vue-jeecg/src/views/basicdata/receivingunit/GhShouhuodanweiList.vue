<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24"> </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button @click="batchDel" type="primary" icon="delete">删除</a-button>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择
        <a style="font-weight: 600">{{ selectedRowKeys.length }}</a
        >项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>
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
        :rowSelection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange }"
        class="j-table-force-nowrap"
        @change="handleTableChange"
      >
        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>
          <a-divider type="vertical" />
          <a @click="handleDetail(record)">详情</a>
        </span>
      </a-table>
    </div>

    <gh-shouhuodanwei-modal ref="modalForm" @ok="modalFormOk"></gh-shouhuodanwei-modal>
  </a-card>
</template>

<script>
import '@/assets/less/TableExpand.less'
import { mixinDevice } from '@/utils/mixin'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import GhShouhuodanweiModal from './modules/GhShouhuodanweiModal'

export default {
  name: 'GhShouhuodanweiList',
  mixins: [JeecgListMixin, mixinDevice],
  components: {
    GhShouhuodanweiModal
  },
  data() {
    return {
      description: '收货单位管理页面',
      // 表头
      columns: [
        {
          title: '单位名称',
          align: 'center',
          dataIndex: 'shouhuodanweiname'
        },
        {
          title: '操作',
          dataIndex: 'action',
          align: 'center',
          fixed: 'right',
          width: 147,
          scopedSlots: { customRender: 'action' }
        }
      ],
      url: {
        list: '/receivingunit/ghShouhuodanwei/list',
        delete: '/receivingunit/ghShouhuodanwei/delete',
        deleteBatch: '/receivingunit/ghShouhuodanwei/deleteBatch',
        exportXlsUrl: '/receivingunit/ghShouhuodanwei/exportXls',
        importExcelUrl: 'receivingunit/ghShouhuodanwei/importExcel'
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
    initDictConfig() {}
  }
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>