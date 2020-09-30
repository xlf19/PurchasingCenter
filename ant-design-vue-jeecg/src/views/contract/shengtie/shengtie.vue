<template>
  <a-card :bordered="false">
    <!-- table区域-begin -->
    <div>
      <!-- 查询区域 -->
      <div class="table-page-search-wrapper">
        <a-form layout="inline" @keyup.enter.native="searchQuery" :form="form">
          <a-row :gutter="24">
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="合同编号">
                <a-select
                  v-decorator="['contractNo', validatorRules.contractNo]"
                  show-search
                  allowClear
                  @search="htSearch"
                  @change="findOne"
                >
                  <a-select-option
                    placeholder="请选择合同号"
                    :value="item.HeTongBianHao"
                    v-for="(item, index) in contractNos"
                    :key="index"
                    >{{ item.HeTongBianHao }}</a-select-option
                  >
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="凭证号">
                <a-input placeholder="请输入凭证号" v-model="voucherNo" disabled></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="收货单位">
                <a-select show-search allowClear v-decorator="['receivingUnit', validatorRules.receivingUnit]">
                  <a-select-option
                    placeholder="请选择收货单位"
                    :value="item.ShouHuoDanWeiName"
                    v-for="(item, index) in receivingUnits"
                    :key="index"
                    >{{ item.ShouHuoDanWeiName }}</a-select-option
                  >
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="物资名称">
                <a-input placeholder="请输入物资名称" v-model="materialName" disabled></a-input>
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="24">
            <a-col :xl="6" :lg="8" :md="10" :sm="24">
              <a-form-item label="起止日期">
                <a-range-picker :trigger-change="true" v-decorator="['rangeDate', validatorRules.rangeDate]" />
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="供货单位">
                <a-input placeholder="请输入供货单位" v-model="supplier" disabled></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <span style="float: left; overflow: hidden" class="table-page-search-submitButtons">
                <a-button type="primary" @click="searchList" icon="search">质检数据</a-button>
              </span>
            </a-col>
          </a-row>
        </a-form>
      </div>
      <!-- 操作按钮区域 -->
      <div class="table-operator">
        <a-button @click="htAdd" type="primary" icon="plus">导入化验结果</a-button>
      </div>
      <!-- table区域-begin -->
      <div>
        <div class="ant-alert ant-alert-info" style="margin-bottom: 16px">
          <i class="anticon anticon-info-circle ant-alert-icon"></i
          >质检数据列表&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;已选择
          <a style="font-weight: 600">{{ selectedRowKeys.length }}</a
          >项
          <a style="margin-left: 24px" @click="onClearSelected">清空</a>
        </div>
        <a-table
          ref="table"
          size="middle"
          bordered
          rowKey="派工单号"
          :columns="columns"
          :dataSource="dataSource"
          :pagination="ipagination"
          :loading="loading"
          :rowSelection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange }"
          class="j-table-force-nowrap"
          @change="handleTableChange"
        ></a-table>
      </div>
      <!-- table区域-end -->
      <contract-list ref="ContractList"></contract-list>
    </div>
  </a-card>
</template>

<script>
import '@/assets/less/TableExpand.less'
import { getAction, postAction } from '@/api/manage'
import { validateDuplicateValue, randomUUID, handleStatus, filterObj } from '@/utils/util'
import { mixinDevice } from '@/utils/mixin'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import JDate from '@/components/jeecg/JDate.vue'
import ContractList from '../shengtie/ContractInformationList'
import moment from 'moment'

export default {
  name: 'shengtie',
  mixins: [JeecgListMixin, mixinDevice],
  components: {
    JDate,
    ContractList,
  },
  data() {
    return {
      description: '生铁结算管理页面',
      disableMixinCreated: true,
      form: this.$form.createForm(this),
      /* 查询条件-请不要在queryParam中声明非字符串值的属性 */
      queryParam: {},
      /* 数据源 */
      dataSource: [],
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
      /* table选中keys*/
      selectedRowKeys: [],
      /* table选中records*/
      selectionRows: [],
      //凭证号
      voucherNo: 1,
      //合同号
      contractNos: [],
      arg: 0,
      //收货单位
      receivingUnits: [],
      //供货单位
      supplier: '',
      //物资名称
      materialName: '',
      //取样日期
      rangeDate: '',
      startTime: '',
      endTime: '',
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
          title: '供货单位',
          align: 'center',
          dataIndex: '供货单位',
        },
        {
          title: '收货单位',
          align: 'center',
          dataIndex: '使用单位',
        },
        {
          title: '物资名称',
          align: 'center',
          dataIndex: '名称',
        },
        {
          title: '取样日期',
          align: 'center',
          dataIndex: '取样日期',
          customRender: function (text) {
            return !text ? '' : text.length > 10 ? text.substr(0, 10) : text
          },
        },
        {
          title: '检验量',
          align: 'center',
          dataIndex: '检验量',
        },
        {
          title: 'Si',
          align: 'center',
          dataIndex: 'Si',
        },
        {
          title: 'Mn',
          align: 'center',
          dataIndex: 'Mn',
        },
        {
          title: 'S',
          align: 'center',
          dataIndex: 'S',
        },
        {
          title: 'P',
          align: 'center',
          dataIndex: 'P',
        },
        {
          title: '化验备注',
          align: 'center',
          dataIndex: '化验备注',
        },
        {
          title: '派工单号',
          align: 'center',
          dataIndex: '派工单号',
        },
      ],

      validatorRules: {
        contractNo: { rules: [{ required: true, message: '请选择合同号!' }] },
        receivingUnit: { rules: [{ required: true, message: '请选择收货单位!' }] },
        rangeDate: {
          rules: [{ required: true, message: '请输入取样日期!' }],
          initialValue: [moment(this.date), moment(this.date)],
        },
      },

      url: {
        list: '/zhijianxingxi/zhijianxingxi/listzjxx',
        findpzh: '/contract/contractInformation/findpzh',
        findHt: '/hetong/hetong/findListHt',
        findshdw: '/hetong/hetong/findshdw',
        findOne: '/hetong/hetong/findOne',
        htadd: '/shengtie/shengtie/htadd',
      },

      dictOptions: {},
    }
  },
  created() {
    this.findpzh()
    this.findHt('')
    this.findshdw()
  },
  computed: {
    importExcelUrl: function () {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    },
  },
  methods: {
    initDictConfig() {},

    //获取凭证号
    findpzh() {
      getAction(this.url.findpzh).then((res) => {
        if (res.success) {
          if (res.result != null) {
            this.voucherNo = res.result
          }
        }
        if (res.code === 510) {
          this.$message.warning(res.message)
        }
        this.loading = false
      })
    },

    //获取合同号
    findHt(hth) {
      getAction(this.url.findHt, { hth: hth }).then((res) => {
        if (res.success) {
          this.contractNos = res.result
        }
        if (res.code === 510) {
          this.$message.warning(res.message)
        }
        this.loading = false
      })
    },

    //获取合同号
    htSearch(value) {
      this.findHt(value)
    },

    //获取收货单位
    findshdw(hth) {
      getAction(this.url.findshdw).then((res) => {
        if (res.success) {
          this.receivingUnits = res.result
        }
        if (res.code === 510) {
          this.$message.warning(res.message)
        }
        this.loading = false
      })
    },

    //获取供货单位、物资名称
    findOne(hth) {
      getAction(this.url.findOne, { hth: hth }).then((res) => {
        if (res.success) {
          if (res.result != null && res.result != '') {
            this.supplier = res.result.DanWeiName
            this.materialName = res.result.WZName
          }
        }
        if (res.code === 510) {
          this.$message.warning(res.message)
        }
        this.loading = false
      })    
      if (hth != null && hth != '' && hth != undefined) {
        this.finlist(hth)
      }
      this.$emit('gethtbh', hth)
    },

    //合同信息
    finlist(hth) {
      this.$refs.ContractList.htlist(hth)
    },

    //导入质检数据
    htAdd() {
      let htxx = JSON.stringify(this.selectionRows)
      //凭证号
      let pzh = this.voucherNo
      let length = this.selectedRowKeys.length
      if (length === 0) {
        this.$message.warning('请选择数据。')
        return
      }
      this.form.validateFields((err, values) => {
        if (!err) {
          let shdw = values.receivingUnit
          let htbhs = values.contractNo
          postAction(this.url.htadd, { htxx: htxx, htbhs: htbhs, pzh: pzh, shdw: shdw }).then((res) => {
            if (res.success) {
              this.$message.success(res.message)
              this.$refs.ContractList.htlist(htbhs)
              this.selectedRowKeys = []
              this.selectionRows = []
              let datas = {
                startTime: this.startTime,
                endTime: this.endTime,
                supplier: this.supplier,
              }
              this.loadData(this.arg, datas)
            }
            if (res.code === 510) {
              this.$message.warning(res.message)
            }
            this.loading = false
          })
        }
      })
    },

    //查询质检数据
    searchList() {
      this.form.validateFields((err, values) => {
        if (!err) {
          this.startTime = moment(values.rangeDate[0]).format('YYYY-MM-DD')
          this.endTime = moment(values.rangeDate[1]).format('YYYY-MM-DD')
          let shdw = this.supplier
          let datas = {
            startTime: this.startTime,
            endTime: this.endTime,
            supplier: shdw,
          }
          this.loadData(this.arg, datas)
        }
      })
    },

    loadData(arg, datas) {
      if (!this.url.list) {
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

    //获取选中行数据
    onSelectChange(selectedRowKeys, selectionRows) {
      this.selectedRowKeys = selectedRowKeys
      this.selectionRows = selectionRows
    },

    //分页、排序、筛选
    handleTableChange(pagination, filters, sorter) {
      this.form.validateFields((err, values) => {
        //分页、排序、筛选变化时触发
        //TODO 筛选
        if (Object.keys(sorter).length > 0) {
          this.isorter.column = sorter.field
          this.isorter.order = 'ascend' == sorter.order ? 'asc' : 'desc'
        }
        this.ipagination = pagination
        if (!err) {
          this.startTime = moment(values.rangeDate[0]).format('YYYY-MM-DD')
          this.endTime = moment(values.rangeDate[1]).format('YYYY-MM-DD')
          let shdw = this.supplier
          let datas = {
            startTime: this.startTime,
            endTime: this.endTime,
            supplier: shdw,
          }
          this.loadData(this.arg, datas)
        }
      })
    },
  },
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>