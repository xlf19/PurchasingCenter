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
        @expand="handleExpand"
      >
        <span slot="action" slot-scope="text, record">
          <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
            <a>删除</a>
          </a-popconfirm>
        </span>
        <a-card style="margin: 0" slot="expandedRowRender" :bordered="false">
          <detail-list title="生铁合同元素数据">
            <detail-list-item
              :term="item.element"
              v-for="(item,index) in stelements"
              :key="index"
            >{{item.elelmentDate}}</detail-list-item>
          </detail-list>
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
const DetailListItem = DetailList.Item

export default {
  name: 'ContractInformationList',
  mixins: [JeecgListMixin, mixinDevice],
  components: {
    DetailList,
    DetailListItem,
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
          title: '物资编码',
          align: 'center',
          dataIndex: 'materialCode',
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
          title: '备注',
          align: 'center',
          dataIndex: 'remarks',
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
          title: '合同单价',
          align: 'center',
          dataIndex: 'contractPrice',
        },
        {
          title: '运费',
          align: 'center',
          dataIndex: 'shipping',
        },
        {
          title: '点收',
          align: 'center',
          dataIndex: 'accept',
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
          title: '过磅日期',
          align: 'center',
          dataIndex: 'weighingDate',
          customRender: function (text) {
            return !text ? '' : text.length > 10 ? text.substr(0, 10) : text
          },
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
        list: '/naicai/naicai/list',
        lists: '/shengtie/shengtie/elementslist',
        delete: '/shengtie/shengtie/delete',
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
      getAction(this.url.lists, { cid: record.id }).then((res) => {
        if (res.success) {
          this.stelements = res.result
        }
        if (res.code === 510) {
          this.$message.warning(res.message)
        }
        this.loading = false
      })
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
          this.loadData()
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