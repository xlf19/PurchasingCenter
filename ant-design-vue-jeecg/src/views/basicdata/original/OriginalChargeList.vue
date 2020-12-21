<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="5" :lg="7" :md="8" :sm="24">
            <a-form-item label="供应商名称">
              <a-input placeholder="请输入供应商名称" v-model="queryParam.companyName"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="5" :lg="7" :md="8" :sm="24">
            <a-form-item label="联系人">
              <a-input placeholder="请输入联系人" v-model="queryParam.contactPerson"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="5" :lg="7" :md="8" :sm="24">
            <a-form-item label="供应商编码">
              <a-input placeholder="请输入供应商编码" v-model="queryParam.supplierCode"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="5" :lg="7" :md="8" :sm="24">
            <a-form-item label="使用状态">
              <a-select v-model="queryParam.useStatus" placeholder="请选择使用状态" allowClear>
                <a-select-option value="">全部</a-select-option>
                <a-select-option value="1">使用中</a-select-option>
                <a-select-option value="2">已禁用</a-select-option>
              </a-select>
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
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button @click="batchDel" type="primary" icon="delete">删除</a-button>
      <a-button @click="usestatus(1)" type="primary" icon="check">设为可用</a-button>
      <a-button @click="usestatus(2)" type="primary" icon="close">设为不可用</a-button>
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
        <template slot="ellipsisSlot" slot-scope="text">
          <j-ellipsis :value="text" :length="6"></j-ellipsis>
        </template>
        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>
          <a-divider type="vertical" />
          <a @click="handleDetail(record)">详情</a>
        </span>
        <!-- 状态值 -->
        <span slot="status" slot-scope="text, record">
          <a-button class="using_btn" v-if="record.useStatus == '1'">使用中</a-button>
          <a-button class="ban_btn" v-if="record.useStatus == '2'">已禁用</a-button>
        </span>
      </a-table>
    </div>

    <original-charge-modal ref="modalForm" @ok="modalFormOk"></original-charge-modal>
  </a-card>
</template>

<script>
import '@/assets/less/TableExpand.less'
import { mixinDevice } from '@/utils/mixin'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import OriginalChargeModal from './modules/OriginalChargeModal'
import { deleteAction, getAction, downFile, getFileAccessHttpUrl } from '@/api/manage'
import { initDictOptions, filterMultiDictText } from '@/components/dict/JDictSelectUtil'
import JEllipsis from '@/components/jeecg/JEllipsis'
export default {
  name: 'OriginalChargeList',
  mixins: [JeecgListMixin, mixinDevice],
  components: {
    OriginalChargeModal,
    JEllipsis
  },
  data() {
    return {
      description: '原炉料供应商管理页面',
      provinciallist: [],
      // 表头
      columns: [
        {
          title: '供应商编码',
          align: 'center',
          dataIndex: 'supplierCode'
        },
        {
          title: '供应商名称',
          align: 'center',
          dataIndex: 'companyName'
        },
        {
          title: '公司地址',
          align: 'center',
          dataIndex: 'unitAddress'
        },
        {
          title: '银行名称',
          align: 'center',
          dataIndex: 'bankName'
        },
        {
          title: '银行账号',
          align: 'center',
          dataIndex: 'bankAccount'
        },
        {
          title: '信用代码',
          align: 'center',
          dataIndex: 'creditCode'
        },
        {
          title: '使用状态',
          align: 'center',
          dataIndex: 'useStatus',
          scopedSlots: {
            customRender: 'status'
          }
        },
        {
          title: '邮政编码',
          align: 'center',
          dataIndex: 'postalCode'
        },
        {
          title: '法人代表',
          align: 'center',
          dataIndex: 'legalRepresentative'
        },
        {
          title: '联系人',
          align: 'center',
          dataIndex: 'contactPerson'
        },
        {
          title: '联系电话',
          align: 'center',
          dataIndex: 'contactNumber'
        },
        {
          title: '传真电话',
          align: 'center',
          dataIndex: 'contactFax'
        },
        {
          title: '电子邮箱',
          align: 'center',
          dataIndex: 'email'
        },
        {
          title: '公司类别',
          align: 'center',
          dataIndex: 'companyCategory',
          customRender: function(t, r, index) {
            if (t === 1) {
              return '供方单位'
            } else if (t === 2) {
              return '需方单位'
            }
          }
        },
        {
          title: '所在省级',
          align: 'center',
          dataIndex: 'provincial',
          customRender: text => {
            //字典值替换通用方法
            return filterMultiDictText(this.provinciallist, text)
          }
        },
        {
          title: '注册资金',
          align: 'center',
          dataIndex: 'registeredCapital',
          scopedSlots: { customRender: 'ellipsisSlot' }
        },
        {
          title: '经营范围',
          align: 'center',
          dataIndex: 'businessScope',
          scopedSlots: { customRender: 'ellipsisSlot' }
        },
        {
          title: '企业性质',
          align: 'center',
          dataIndex: 'enterpriseNature',
          customRender: function(t, r, index) {
            if (t === 1) {
              return '国企'
            } else if (t === 2) {
              return '民营'
            }
          }
        },
        {
          title: '供方状态',
          align: 'center',
          dataIndex: 'supplierStatus',
          customRender: function(t, r, index) {
            if (t === 1) {
              return '正常'
            } else if (t === 2) {
              return '异常'
            }
          }
        },
        {
          title: '临时供应商',
          align: 'center',
          dataIndex: 'temporarySupplier',
          customRender: function(t, r, index) {
            if (t === 1) {
              return '是'
            } else if (t === 2) {
              return '否'
            }
          }
        },
        {
          title: '内部供应商',
          align: 'center',
          dataIndex: 'internalSupplier',
          customRender: function(t, r, index) {
            if (t === 1) {
              return '是'
            } else if (t === 2) {
              return '否'
            }
          }
        },
        {
          title: '注册日期',
          align: 'center',
          dataIndex: 'establishmentDate',
          customRender: function(text) {
            return !text ? '' : text.length > 10 ? text.substr(0, 10) : text
          }
        },
        {
          title: '准入日期',
          align: 'center',
          dataIndex: 'accessDate',
          customRender: function(text) {
            return !text ? '' : text.length > 10 ? text.substr(0, 10) : text
          }
        },
        {
          title: '名称首字母',
          align: 'center',
          dataIndex: 'nameInitials'
        },
        {
          title: '录入人',
          align: 'center',
          dataIndex: 'createBy'
        },
        {
          title: '录入时间',
          align: 'center',
          dataIndex: 'createTime',
          customRender: function(text) {
            return !text ? '' : text.length > 10 ? text.substr(0, 10) : text
          }
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
        list: '/original/originalCharge/list',
        delete: '/original/originalCharge/delete',
        deleteBatch: '/original/originalCharge/deleteBatch',
        exportXlsUrl: '/original/originalCharge/exportXls',
        importExcelUrl: 'original/originalCharge/importExcel',
        usestatus: 'original/originalCharge/usestatus'
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
      //初始化字典 - 所在省级
      initDictOptions('G_ShengBianMa,ShengName,ShengCode').then(res => {
        if (res.success) {
          this.provinciallist = res.result
        }
      })
    },
    usestatus: function(status) {
      if (!this.url.usestatus) {
        this.$message.error('请设置url.usestatus属性!')
        return
      }
      if (this.selectedRowKeys.length <= 0) {
        this.$message.warning('请选择一条记录！')
        return
      } else {
        var ids = ''
        for (var a = 0; a < this.selectedRowKeys.length; a++) {
          ids += this.selectedRowKeys[a] + ','
        }
        var that = this
        that.loading = true
        deleteAction(that.url.usestatus, { ids: ids, status: status })
          .then(res => {
            if (res.success) {
              that.$message.success(res.message)
              that.loadData()
              that.onClearSelected()
            } else {
              that.$message.warning(res.message)
            }
          })
          .finally(() => {
            that.loading = false
          })
      }
    }
  }
}
</script>
<style scoped>
@import '~@assets/less/common.less';
  .using_btn{
    background-color: #00BB00;
    color: #f0f0f0;
  }
  .ban_btn{
    background-color: #cccccc;
    color: white;
  }
</style>