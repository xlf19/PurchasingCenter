<template>
  <a-card :bordered="false">
    <!-- table区域-begin -->
    <div>
      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        class="j-table-force-nowrap"
        @change="handleTableChange"
        :expandedRowKeys="expandedRowKeys"
        @expand="handleExpand"
      >
        <template slot="ellipsisSlot" slot-scope="text">
          <j-ellipsis :value="text" :length="3"></j-ellipsis>
        </template>
        <span slot="action" slot-scope="text, record">
          <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
            <a>删除</a>
          </a-popconfirm>
        </span>
        <a-card style="margin: 0" slot="expandedRowRender" :bordered="false">
          <a-descriptions title="辅料合同元素数据" :column="8">
            <a-descriptions-item :label="item.element" :key="index" v-for="(item, index) in stelements">{{
              item.elelmentDate
            }}</a-descriptions-item>
          </a-descriptions>
        </a-card>
      </a-table>
    </div>
  </a-card>
</template>

<script>
import '@/assets/less/TableExpand.less'
import { deleteAction, getAction } from '@/api/manage'
import { mixinDevice } from '@/utils/mixin'
import DetailList from '@/components/tools/DetailList'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import { filterObj } from '@/utils/util'
import JEllipsis from '@/components/jeecg/JEllipsis'
const DetailListItem = DetailList.Item

export default {
  name: 'ContractInformationList',
  mixins: [JeecgListMixin, mixinDevice],
  components: {
    DetailList,
    DetailListItem,
    JEllipsis,
  },

  data() {
    return {
      description: '合同信息表管理页面',
      disableMixinCreated: true,
      /* 分页参数 */
      ipagination: {
        current: 1,
        pageSize: 10,
        pageSizeOptions: ['10', '20', '30'],
        showTotal: (total, range) => {
          return range[0] + '-' + range[1] + ' 共' + total + '条'
        },
        showQuickJumper: true,
        showSizeChanger: true,
        total: 0,
      },
      /* 排序参数 */
      isorter: {
        column: 'createTime',
        order: 'desc',
      },
      expandedRowKeys: [],
      //合同号
      hthone: '',
      // 表头
      columns: [
        {
          title: '合同编号',
          align: 'center',
          dataIndex: 'contractNo',
        },
        {
          title: '凭证号',
          align: 'center',
          dataIndex: 'voucherNo',
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
          title: '检斤',
          align: 'center',
          dataIndex: 'weighing',
        },

        {
          title: '合同单价',
          align: 'center',
          dataIndex: 'contractPrice',
        },
        {
          title: '点收',
          align: 'center',
          dataIndex: 'accept',
        },
        {
          title: '运费',
          align: 'center',
          dataIndex: 'shipping',
        },
        {
          title: '含税',
          align: 'center',
          dataIndex: 'taxIncluded',
          customRender: function (t, r, index) {
            if (t) {
              return '含税'
            } else {
              return '不含税'
            }
          },
        },
        {
          title: '税率',
          align: 'center',
          dataIndex: 'taxRate',
        },
        {
          title: '税金',
          align: 'center',
          dataIndex: 'taxes',
        },
        {
          title: '贷款',
          align: 'center',
          dataIndex: 'loan',
        },
        {
          title: '备注',
          align: 'center',
          dataIndex: 'remarks',
          scopedSlots: { customRender: 'ellipsisSlot' },
        },

        {
          title: '结算日期',
          align: 'center',
          dataIndex: 'settlementDate',
          customRender: function (text) {
            return !text ? '' : text.length > 10 ? text.substr(0, 10) : text
          },
        },
        {
          title: '结算状态',
          align: 'center',
          dataIndex: 'settlementIdentification',
          customRender: function (t, r, index) {
            if (t === 1) {
              return '结算'
            } else {
              return '未结算'
            }
          },
        },
        {
          title: '结算单价',
          align: 'center',
          dataIndex: 'settlemenPrice',
        },
        {
          title: '结算数量',
          align: 'center',
          dataIndex: 'settlementQuantity',
        },
        {
          title: '结算结果',
          align: 'center',
          dataIndex: 'settlementResults',
        },
        {
          title: '结算人',
          align: 'center',
          dataIndex: 'clearingHouse',
        },
        {
          title: '操作',
          dataIndex: 'action',
          align: 'center',
          width: 147,
          scopedSlots: { customRender: 'action' },
        },
      ],
      stelements: [],
      url: {
        list: '/feiliao/feiliao/list',
        lists: '/elements/contractElements/elementslist',
        delete: '/elements/contractElements/delete',
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
  
    //展开行信息
    handleExpand(expanded, record) {
      this.expandedRowKeys = []
      if (expanded === true) {
        this.expandedRowKeys.push(record.id)
        getAction(this.url.lists, { cid: record.id }).then((res) => {
          if (res.success) {
            this.stelements = res.result
          }
          if (res.code === 510) {
            this.$message.warning(res.message)
          }
          this.loading = false
        })
      }
    },
    //查询合同信息
    htlist(hth) {
      this.hthone = hth
      var params = this.getQueryParams() //查询条件
      let hths = { hth: hth }
      params = Object.assign(params, hths)
      this.loading = true
      getAction(this.url.list, params).then((res) => {
        if (res.success) {
          this.dataSource = res.result.records
          this.ipagination.total = res.result.total
        }
        if (res.code === 510) {
          this.$message.warning(res.message)
        }
        this.loading = false
      })
    },
    //分页、排序、筛选
    handleTableChange(pagination, filters, sorter) {
      //分页、排序、筛选变化时触发
      //TODO 筛选
      if (Object.keys(sorter).length > 0) {
        this.isorter.column = sorter.field
        this.isorter.order = 'ascend' == sorter.order ? 'asc' : 'desc'
      }
      this.ipagination = pagination
      let hth = this.hthone
      this.htlist(hth)
    },
    //删除
    handleDelete(id) {
      deleteAction(this.url.delete, { id: id }).then((res) => {
        if (res.success) {
          let hth = this.hthone
          this.htlist(hth)
        } else {
          that.$message.warning(res.message)
        }
      })
    },
  },
}
</script>
<style scoped>
@import '~@assets/less/common.less';
.ant-card {
  margin-left: -30px;
  margin-right: -30px;
}
</style>