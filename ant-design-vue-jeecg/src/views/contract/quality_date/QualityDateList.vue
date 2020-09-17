<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24"></a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('加权平均')">导出</a-button>
      <a-upload
        name="file"
        :showUploadList="false"
        :multiple="false"
        :headers="tokenHeader"
        :action="importExcelUrl"
        @change="handleImportExcel"
      >
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel">
            <a-icon type="delete" />删除
          </a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px">
          批量操作
          <a-icon type="down" />
        </a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择
        <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>
      <a-table
        ref="table"
        size="middle"
        :scroll="{x:true}"
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
        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无图片</span>
          <img
            v-else
            :src="getImgView(text)"
            height="25px"
            alt
            style="max-width:80px;font-size: 12px;font-style: italic;"
          />
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无文件</span>
          <a-button
            v-else
            :ghost="true"
            type="primary"
            icon="download"
            size="small"
            @click="downloadFile(text)"
          >下载</a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">
              更多
              <a-icon type="down" />
            </a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a @click="handleDetail(record)">详情</a>
              </a-menu-item>
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>
      </a-table>
    </div>

    <quality-date-modal ref="modalForm" @ok="modalFormOk"></quality-date-modal>
  </a-card>
</template>

<script>
import '@/assets/less/TableExpand.less'
import { mixinDevice } from '@/utils/mixin'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import QualityDateModal from './modules/QualityDateModal'

export default {
  name: 'QualityDateList',
  mixins: [JeecgListMixin, mixinDevice],
  components: {
    QualityDateModal,
  },
  data() {
    return {
      description: '加权平均管理页面',
      // 表头
      columns: [
        {
          title: '#',
          dataIndex: '',
          key: 'rowIndex',
          width: 60,
          align: 'center',
          customRender: function (t, r, index) {
            return parseInt(index) + 1
          },
        },
        {
          title: '取样日期',
          align: 'center',
          dataIndex: 'qyDate',
          customRender: function (text) {
            return !text ? '' : text.length > 10 ? text.substr(0, 10) : text
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
          title: '需方单位',
          align: 'center',
          dataIndex: 'receivingunit',
        },
        {
          title: '检验量',
          align: 'center',
          dataIndex: 'weighing',
        },
        {
          title: 'TFE',
          align: 'center',
          dataIndex: 'tfe',
        },
        {
          title: 'SIO2',
          align: 'center',
          dataIndex: 'sio2',
        },
        {
          title: 'S',
          align: 'center',
          dataIndex: 'ss',
        },
        {
          title: 'P',
          align: 'center',
          dataIndex: 'pp',
        },
        {
          title: 'AL2O3',
          align: 'center',
          dataIndex: 'al2o3',
        },
        {
          title: 'MnO',
          align: 'center',
          dataIndex: 'mno',
        },
        {
          title: 'TiO2',
          align: 'center',
          dataIndex: 'tio2',
        },
        {
          title: 'CAO',
          align: 'center',
          dataIndex: 'cao',
        },
        {
          title: 'Cr',
          align: 'center',
          dataIndex: 'cr',
        },
        {
          title: 'Cu',
          align: 'center',
          dataIndex: 'cu',
        },
        {
          title: 'FEO',
          align: 'center',
          dataIndex: 'feo',
        },
        {
          title: 'MGO',
          align: 'center',
          dataIndex: 'mgo',
        },
        {
          title: 'Pb',
          align: 'center',
          dataIndex: 'pb',
        },
        {
          title: 'As',
          align: 'center',
          dataIndex: 'as1',
        },
        {
          title: 'Zn',
          align: 'center',
          dataIndex: 'zn',
        },
        {
          title: 'K2O',
          align: 'center',
          dataIndex: 'k2o',
        },
        {
          title: 'H2O',
          align: 'center',
          dataIndex: 'h2o',
        },
        {
          title: '含粉',
          align: 'center',
          dataIndex: 'containsPowder',
        },
        {
          title: '抗压',
          align: 'center',
          dataIndex: 'compressive',
        },
        {
          title: '粒度',
          align: 'center',
          dataIndex: 'granularity',
        },
        {
          title: 'md',
          align: 'center',
          dataIndex: 'md',
        },
        {
          title: '凭证号标识',
          align: 'center',
          dataIndex: 'voucherId',
        },
        {
          title: '操作',
          dataIndex: 'action',
          align: 'center',
          fixed: 'right',
          width: 147,
          scopedSlots: { customRender: 'action' },
        },
      ],
      url: {
        list: '/quality_date/qualityDate/list',
        delete: '/quality_date/qualityDate/delete',
        deleteBatch: '/quality_date/qualityDate/deleteBatch',
        exportXlsUrl: '/quality_date/qualityDate/exportXls',
        importExcelUrl: 'quality_date/qualityDate/importExcel',
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