<template>
  <a-spin :spinning="confirmLoading">
    <a-card :bordered="false">
      <!-- 查询区域 -->
      <div class="table-page-search-wrapper">
        <a-form layout="inline" @keyup.enter.native="searchQuery" :form="form">
          <a-row :gutter="24">
            <a-col :xl="6" :lg="8" :md="10" :sm="24">
              <a-form-item label="起止日期">
                <a-range-picker :trigger-change="true" v-decorator="['rangeDate', validatorRules.rangeDate]" />
              </a-form-item>
            </a-col>
            <a-col :xl="5" :lg="7" :md="8" :sm="24">
              <a-form-item label="收货单位">
                <a-input placeholder="请输入收货单位" v-model="supplier" disabled></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="4" :lg="7" :md="8" :sm="24">
              <span style="float: left; overflow: hidden" class="table-page-search-submitButtons">
                <a-button type="primary" @click="searchList()" icon="search">查询质检数据</a-button>
              </span>
            </a-col>
            <a-col :xl="5" :lg="7" :md="8" :sm="24">
              <a-form-item label="方式">
                <a-select v-model="fangshi">
                  <a-select-option value="可选">可选</a-select-option>
                  <a-select-option value="按天">按天</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :xl="4" :lg="7" :md="8" :sm="24">
              <span style="float: left; overflow: hidden" class="table-page-search-submitButtons">
                <a-button type="primary" @click="addjflist()" icon="search">加权平均</a-button>
              </span>
            </a-col>
          </a-row>
        </a-form>
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
    </a-card>
  </a-spin>
</template>

<script>
import pick from 'lodash.pick'
import JDate from '@/components/jeecg/JDate.vue'
import { filterObj, validateDuplicateValue, randomUUID, handleStatus } from '@/utils/util'
import '@/assets/less/TableExpand.less'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import moment from 'moment'
import { httpAction, postAction } from '@/api/manage'

export default {
  name: 'ContractFormulaForm',
  mixins: [JeecgListMixin],
  components: {
    JDate,
  },
  data() {
    return {
      disableMixinCreated: true,
      form: this.$form.createForm(this),
      confirmLoading: false,
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

      model: {},
      wzmc: '',
      arg: 0,
      //供货单位
      supplier: '',
      //取样日期
      rangeDate: '',
      startTime: '',
      endTime: '',
      //方式
      fangshi: '可选',
      //合同编号
      htbh: '',

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
          title: '供货单位',
          align: 'center',
          dataIndex: '供货单位'
        },
        {
          title: '收货单位',
          align: 'center',
          dataIndex: '使用单位'
        },
        {
          title: '物资名称',
          align: 'center',
          dataIndex: '名称'
        },
        {
          title: '取样日期',
          align: 'center',
          dataIndex: '取样日期',
          customRender: function(text) {
            return !text ? '' : text.length > 10 ? text.substr(0, 10) : text
          }
        },
        {
          title: '检验量',
          align: 'center',
          dataIndex: '检验量'
        },
        {
          title: 'Si',
          align: 'center',
          dataIndex: 'Si'
        },
        {
          title: 'Mn',
          align: 'center',
          dataIndex: 'Mn'
        },
        {
          title: 'S',
          align: 'center',
          dataIndex: 'S'
        },
        {
          title: 'P',
          align: 'center',
          dataIndex: 'P'
        },
        {
          title: '化验备注',
          align: 'center',
          dataIndex: '化验备注',
          scopedSlots: {
            customRender: 'ellipsisSlot'
          }
        },
        {
          title: '派工单号',
          align: 'center',
          dataIndex: '派工单号'
        }
      ],

      validatorRules: {
        rangeDate: {
          rules: [{ required: true, message: '请输入取样日期!' }],
          initialValue: [moment(this.date), moment(this.date)],
        },
      },
      url: {
        listModel: '/zhijianxingxi/zhijianxingxi/listzjst',
        addlistst: '/shengtie/shengtie/addlistst',
      },
    }
  },
  computed: {},

  methods: {
    guhw(contractNo,shdw) {
      this.supplier = shdw
      this.htbh = contractNo
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
      if (!this.url.listModel) {
        this.$message.error('请设置url.list属性!')
        return
      }
      //加载数据 若传入参数1则加载第一页的内容
      if (arg === 1) {
        this.ipagination.current = 1
      }
      var params = this.getQueryParams() //查询条件
      params = Object.assign(params, datas)
      console.log(params)
      this.loading = true
      httpAction(this.url.listModel, params, 'post').then((res) => {
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

    //获取选中行数据
    onSelectChange(selectedRowKeys, selectionRows) {
      this.selectedRowKeys = selectedRowKeys
      this.selectionRows = selectionRows
    },

    //添加质检数据
    addjflist() {
      if ((this.fangshi = '可选')) {
        let length = this.selectedRowKeys.length
        if (length === 0) {
          this.$message.warning('请选择数据。')
          return
        }
      }
      const that = this
      let jfjaxx = JSON.stringify(this.selectionRows)
      let fangshi = this.fangshi
      let htbh = this.htbh
      this.form.validateFields((err, values) => {
        if (!err) {
          that.confirmLoading = true
          let startTime = moment(values.rangeDate[0]).format('YYYY-MM-DD')
          let endTime = moment(values.rangeDate[1]).format('YYYY-MM-DD')
          let shdw = this.supplier
          postAction(this.url.addlistst, {
            jfjaxx: jfjaxx,
            startTime: startTime,
            endTime: endTime,
            shdw: shdw,
            fangshi: fangshi,
            htbh: htbh,
          })
            .then((res) => {
              if (res.success) {
                that.$message.success(res.message)
                that.$emit('ok',htbh)
              }
              if (res.code === 510) {
                that.$message.warning(res.message)
              }
            })
            .finally(() => {
              that.confirmLoading = false
            })
        }
      })
    },
  },
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>