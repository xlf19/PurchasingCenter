<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" :form="form">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <!-- 第一个输入框 -->
            <a-form-item label="合同编号">
              <a-select
                v-model="contractNo"
                show-search
                allowClear
                @search="handleSearch"
                @change="handleChange"
                style="width: 200px"
              >
                <a-select-option v-for="(item, index) in contracts" :value="item.contract_no" :key="index">{{
                  item.contract_no
                }}</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <!-- 第二个输入框 -->
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="凭证号">
              <a-select v-model="voucherNo" allowClear style="width: 200px">
                <!-- 会将下拉框中的key中所对应的值给上面的:value中多对应的值 -->
                <a-select-option v-for="(item, index) in voucherNos" :value="item.voucher_no" :key="index">{{
                  item.voucher_no
                }}</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <!-- 第三个结算类型 选择框-->
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="结算状况">
              <a-select v-model="settlementIdentification" style="width: 200px">
                <a-select-option value="0">未结算</a-select-option>
                <a-select-option value="1">结算成功</a-select-option>
                <a-select-option value="2">结算失败</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>

          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left; overflow: hidden" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchList" setTimeout="200" icon="search">查询</a-button>
              <a-button type="primary" @click="accountSettle" icon="account-book" style="margin-left: 8px"
                >结算</a-button
              >
              <a-button type="primary" v-has="types" @click="deleteAccountSettle" icon="delete" style="margin-left: 8px"
                >删除</a-button
              >
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- table区域-begin rowSelection选中事件设置 -->
    <div>
      <a-table
        ref="table"
        :scroll="{ x: true }"
        size="middle"
        rowKey="id"
        :pagination="ipagination"
        :rowSelection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange }"
        :columns="columns"
        :dataSource="dataValue"
        :loading="loading"
        :expandedRowKeys="expandedRowKeys"
        @expand="handleExpand"
        class="j-table-force-nowrap"
        @change="handleTableChange"
      >
        <template slot="ellipsisSlot" slot-scope="text">
          <j-ellipsis :value="text" :length="5"></j-ellipsis>
        </template>
        <div slot="expandedRowRender" style="margin: 0">
          <a-row :gutter="24" :style="{ marginBottom: '240px' }">
            <a-descriptions title="生铁合同元素数据" :column="8">
              <detail-list-item v-for="(item, index) in element" :key="index" :term="item.element">
                <ul>
                  <li>元素数据:{{ item.element }}</li>
                  <li>{{ item.elelmentDate }}</li>
                  <li>扣除结果</li>
                  <li>{{ item.deductionResult }}</li>
                </ul>
              </detail-list-item>
            </a-descriptions>
          </a-row>
        </div>
      </a-table>
    </div>
  </a-card>
</template>

<script>
import '@/assets/less/TableExpand.less'
import { deleteAction, getAction, postAction } from '@/api/manage'
import { mixinDevice } from '@/utils/mixin'
import DetailList from '@/components/tools/DetailList'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import JEllipsis from '@/components/jeecg/JEllipsis'
import { validateDuplicateValue, randomUUID, handleStatus, filterObj } from '@/utils/util'
const DetailListItem = DetailList.Item

export default {
  name: 'shengtieAccount',
  mixins: [JeecgListMixin, mixinDevice],
  components: {
    DetailList,
    DetailListItem,
    JEllipsis,
  },
  props: {
    contrac: {
      type: String,
    },
    contracttype: {
      type: String,
    },
  },

  watch: {
    contrac(newVal, oldVal) {
      this.contractNo = newVal
      this.handleChange(newVal)
    },
  },

  data() {
    return {
      types: '', //再次结算按钮的授权标识
      settlementIdentification: '', //合同订单结算状况标识
      message: '',
      voucherNo: '', //凭证编号列表的索引值
      contractNo: this.contrac, //合同编号列表的索引值
      voucherNos: [], //接收凭证编号模糊查询的列表
      contracts: [], //接收合同编号模糊查询的列表
      dataValue: [], //填充查询到的数据
      element: [], //获取元素表的数据
      selectedRowKeys: [], //获取选中的ID
      description: '合同信息表管理页面',
      disableMixinCreated: true,
      selectionRows: [],
      form: {},
      arg: 0,
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
          title: '检斤',
          align: 'center',
          dataIndex: 'weighing',
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
          title: '结算日期',
          align: 'center',
          dataIndex: 'settlementDate',
          customRender: function (text) {
            return !text ? '' : text.length > 10 ? text.substr(0, 10) : text
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
          title: '税金',
          align: 'center',
          dataIndex: 'taxes',
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
          title: '备注',
          align: 'center',
          dataIndex: 'remarks',
        },
        {
          title: '结算状况',
          dataIndex: 'settlementIdentification',
          align: 'center',
          customRender: function (t, r, index) {
            if (t === 1) {
              return '结算成功'
            } else if (t === 0) {
              return '未结算'
            } else {
              return '结算失败'
            }
          },
        },
        {
          title: '结算备注',
          align: 'center',
          dataIndex: 'settlementNotes',
          scopedSlots: { customRender: 'ellipsisSlot' },
        },
      ],
      stelements: [],
      url: {
        deleteAccount: '/settleAccounts/settleAccounts/deleteAccountSettle',
        findContractNo: '/settleAccounts/settleAccounts/contractNos',
        findVoucherNo: '/settleAccounts/settleAccounts/voucherNos',
        findContractList: '/settleAccounts/settleAccounts/contractList',
        findContractElement: '/settleAccounts/settleAccounts/elementList',
        accountSettlement: '/settleAccounts/settleAccounts/settlements',
      },
      dictOptions: {},
    }
  },

  created() {
    //console.log(this.contracttype)
    this.againAccount()
    this.handleSearch('')
    let contractNo = this.contractNo
    if (contractNo != null && contractNo != '') {
      this.handleChange(contractNo)
    }
  },
  computed: {},
  methods: {
    initDictConfig() {},

    //高权限的人删除结算失败的合同
    deleteAccountSettle() {
      let id = this.selectedRowKeys
      if (id == '' || id == undefined || id == null) {
        this.$message.warning('请选择要删除的合同！')
      } else {
        postAction(this.url.deleteAccount, { id: id }).then((res) => {
          if (res.success) {
            this.$message.success('删除成功！')
            this.dataValue = res.result
          } else {
            this.$message.error(res.message)
          }
        })
      }

      //清空选着框中的数据
      this.selectedRowKeys = []
      this.selectionRows = []
    },
    //再一次结算
    againAccountSettle() {
      let id = this.selectedRowKeys
      if (id == '' || id == undefined || id == null) {
        this.$message.warning('请选择要结算的合同结算！')
      } else {
        postAction(this.url.accountSettlement, { id: id }).then((res) => {
          console.log(res.result)
          if (res.success) {
            this.dataValue = res.result
            res.result.forEach((element) => {
              if (element.settlementIdentification === 501) {
                this.$message.error(
                  '结算的订单中有条数据结算失败请根据返回的结算状况检查相应的合同公式的填写是否正确！'
                )
              } else if (element.settlementIdentification === 502) {
                this.$message.error('结算过程中发生了一些系统性错误，亲联系程序班！')
              } else if (element.settlementIdentification === 1) {
                this.$message.success('结算成功！')
              }
            })
            //清空选着框中的数据
            this.selectedRowKeys = []
            this.selectionRows = []
          }
          if (res.code === 500) {
            this.$message.error(res.message)
            //清空选着框中的数据
            this.selectedRowKeys = []
            this.selectionRows = []
          }
        })
      }
    },
    //按钮权限
    againAccount() {
      if (this.contracttype === '生铁') {
        this.types = 'user:iron'
        console.log(this.types)
      } else if (this.contracttype === '合金') {
        this.types = 'user:alloy'
        console.log(this.types)
      } else if (this.contracttype === '辅料') {
        this.types = 'user:subsidiary'
        console.log(this.types)
      } else if (this.contracttype === '耐材') {
        this.types = 'user:refractory'
        console.log(this.types)
      } else if (this.contracttype === '燃料') {
        this.types = 'user:fuel'
        console.log(this.types)
      } else if (this.contracttype === '精粉富粉球团') {
        this.types = 'user:jinfen'
        console.log(this.types)
      }
    },
    //合同结算
    accountSettle() {
      let id = []
      if (this.selectedRowKeys == '' || this.selectedRowKeys == undefined || this.selectedRowKeys == null) {
        this.message = '请在选择合同后在结算'
        this.$message.warning('请在选择合同后在结算')
      } else {
        this.selectionRows.forEach((item) => {
          console.log('item.id' + item.id)
          if (item.settlementIdentification > 0) {
            this.$message.warning('这批订单已经结算过，请不要重复结算！')
            //清空选着框中的数据
            this.selectedRowKeys = []
            this.selectionRows = []
          } else {
            id.push(item.id)
          }
        })
      }
      if (id != null && id != '') {
        postAction(this.url.accountSettlement, { id: id }).then((res) => {
          console.log(res.result)
          if (res.success) {
            this.dataValue = res.result //  this.ipagination.total = res.result.total
            res.result.forEach((element) => {
              if (element.settlementIdentification === 501) {
                this.$message.error(
                  '结算的订单中有条数据结算失败请根据返回的结算状况检查相应的合同公式的填写是否正确！'
                )
              } else if (element.settlementIdentification === 502) {
                this.$message.error('结算过程中发生了一些系统性错误，亲联系程序班！')
              } else if (element.settlementIdentification === 1) {
                this.$message.success('结算成功！')
              }
            })
            this.selectedRowKeys = []
            this.selectionRows = []
          }
          if (res.code === 500) {
            this.$message.warning(res.message)
            //查询完了后清空选着框中的数据
            this.selectedRowKeys = []
            this.selectionRows = []
          }
          this.loading = false
        })
      }
    },
    //展开行事件
    handleExpand(expanded, record) {
      console.log('我选中的是' + record.id)
      this.expandedRowKeys = []
      if (expanded === true) {
        this.expandedRowKeys.push(record.id)
        postAction(this.url.findContractElement, { id: record.id }).then((res) => {
          if (res.success) {
            this.element = res.result
            console.log(this.element)
          }
          if (res.code === 510) {
            this.$message.warning(res.message)
          }
          this.loading = false
        })
      }
    },
    //选中行事件
    onSelectChange(selectedRowKeys, selectionRows) {
      console.log('我是selectedRowKeys' + selectedRowKeys)

      this.selectedRowKeys = selectedRowKeys
      this.selectionRows = selectionRows
    },

    //根据合同编号和凭证号查询数据列表
    searchList() {
      let vno = this.voucherNo
      let cno = this.contractNo
      let sno = this.settlementIdentification
      // alert(sno.constructor)
      if (
        vno === '' ||
        vno === null ||
        vno === undefined ||
        cno === '' ||
        cno === null ||
        cno === undefined ||
        sno === '' ||
        sno === null ||
        sno === undefined
      ) {
        this.$message.warning('请输入合同编号,凭证号以及结算状况后再查询！')
      } else {
        let datas = {
          vno: vno,
          cno: cno,
          settlementIdentification: sno,
        }
        this.loadData(this.arg, datas)
        //查询完了后清空选着框中的数据
        this.selectedRowKeys = []
        this.selectionRows = []
      }
    },

    //合同查询的自动补全功能
    handleSearch(contractNo) {
      console.log(contractNo + '-------------------------')
      let contracttype = this.contracttype
      postAction(this.url.findContractNo, { contractNo: contractNo, contracttype: contracttype }).then((res) => {
        if (res.success) {
          if (res.result != null) {
            this.contracts = res.result
          }
          if (res.code === 510) {
            this.$message.warning(res.message)
          }
        }
      })
    },

    //自动补全的填充凭证号
    handleChange(contractNo) {
      this.contractNo = contractNo
      postAction(this.url.findVoucherNo, { contractNo1: contractNo }).then((res) => {
        if (res.success) {
          if (res.result != null) {
            this.voucherNos = res.result
          }
          if (res.code === 510) {
            this.$message.warning(res.message)
          }
        }
      })
    },

    //抽离出一个数据列表加载方法
    loadData(arg, datas) {
      if (!this.url.findContractList) {
        this.$message.error('请设置url.list属性!')
        return
      }
      //加载数据 若传入参数1则加载第一页的内容
      if (arg === 1) {
        this.ipagination.current = 1
      }
      var params = this.getQueryParams() //查询条件
      params = Object.assign(params, datas)
      this.loading = true
      getAction(this.url.findContractList, params).then((res) => {
        // console.log( res.result)
        if (res.success) {
          this.dataValue = res.result.records
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

      this.searchList()
    },
  },
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>