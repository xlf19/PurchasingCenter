<template>
  <a-card :bordered="false">
    <!-- table区域-begin -->
    <div>
      <!-- 查询区域 -->
      <div class="table-page-search-wrapper">
        <a-form layout="inline" @keyup.enter.native="searchQuery" :form="form">
          <a-row :gutter="24">
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="物资类别">
                <a-select
                  v-decorator="['material_type', validatorRules.material_type]"
                  @change="listcolumn"
                >
                  <a-select-option value="精粉">精粉</a-select-option>
                  <a-select-option value="球团">球团</a-select-option>
                  <a-select-option value="富粉">富粉</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
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
                    v-for="(item,index) in contractNos"
                    :key="index"
                  >{{item.HeTongBianHao}}</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="收货单位">
                <a-select
                  show-search
                  allowClear
                  v-decorator="['receivingUnit', validatorRules.receivingUnit]"
                >
                  <a-select-option
                    placeholder="请选择收货单位"
                    :value="item.ShouHuoDanWeiName"
                    v-for="(item,index) in receivingUnits"
                    :key="index"
                  >{{item.ShouHuoDanWeiName}}</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="凭证号">
                <a-input placeholder="请输入凭证号" v-model="voucherNo" disabled></a-input>
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="24">
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="供货商">
                <a-input placeholder="请输入供货商" v-model="supplier" disabled></a-input>
              </a-form-item>
            </a-col>

            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="物资名称">
                <a-input placeholder="请输入物资名称" v-model="materialName" disabled></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
                <a-button type="primary" @click="searchList" icon="search">加权平均</a-button>
              </span>
            </a-col>
          </a-row>
        </a-form>
      </div>
      <!-- 操作按钮区域 -->
      <div class="table-operator">
        <a-button @click="htAdd" type="primary" icon="plus">导入加权均值</a-button>
        <a-button @click="htAddOne" type="primary" icon="plus">手工添加结算单</a-button>
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
        ></a-table>
      </div>
      <!-- table区域-end -->
      <!-- 操作按钮区域 -->
      <contract-list ref="ContractList"></contract-list>
    </div>
    <jia-quan-modal ref="modalForm" @ok="modalFormOk" :gudw="supplier"></jia-quan-modal>
  </a-card>
</template>

<script>
import '@/assets/less/TableExpand.less'
import { getAction, postAction } from '@/api/manage'
import { validateDuplicateValue, randomUUID, handleStatus, filterObj } from '@/utils/util'
import { initDictOptions, filterDictText, filterMultiDictText } from '@/components/dict/JDictSelectUtil'
import { mixinDevice } from '@/utils/mixin'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import JDate from '@/components/jeecg/JDate.vue'
import moment from 'moment'
import JiaQuanModal from './modules/JiaQuanModal'
import ContractList from '../jingfen/ContractInformationList'

export default {
  name: 'jingfen',
  mixins: [JeecgListMixin, mixinDevice],
  components: {
    JDate,
    ContractList,
    JiaQuanModal,
  },
  data() {
    return {
      description: '精粉结算管理页面',
      disableMixinCreated: true,
      //字典数组缓存-物资类别
      materialtypes: [],
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
      htbhs: '',
      arg: 0,
      //收货单位
      receivingUnits: [],
      //供货单位
      supplier: '',
      //物资名称
      materialName: '',
      // 表头
      settingColumns: [
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
          dataIndex: 'supplier',
        },
        {
          title: '收货单位',
          align: 'center',
          dataIndex: 'receivingunit',
        },
        {
          title: '物资名称',
          align: 'center',
          dataIndex: 'material_name',
        },
        {
          title: '取样日期',
          align: 'center',
          dataIndex: 'riqi',
          customRender: function (text) {
            return !text ? '' : text.length > 10 ? text.substr(0, 10) : text
          },
        },
        {
          title: '检验量',
          align: 'center',
          dataIndex: 'weighing',
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
          title: 'FEO',
          align: 'center',
          dataIndex: 'feo',
        },
        {
          title: 'H2O',
          align: 'center',
          dataIndex: 'h2o',
        },
        {
          title: 'Al2O3',
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
          title: 'TFE',
          align: 'center',
          dataIndex: 'tfe',
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
          title: '[As]',
          align: 'center',
          dataIndex: 'as1',
        },
        {
          title: 'CaO',
          align: 'center',
          dataIndex: 'cao',
        },
        {
          title: 'K2O',
          align: 'center',
          dataIndex: 'k2o',
        },
        {
          title: 'SiO2',
          align: 'center',
          dataIndex: 'sio2',
        },
        {
          title: 'Zn',
          align: 'center',
          dataIndex: 'zn',
        },
        {
          title: '抗压',
          align: 'center',
          dataIndex: 'compressive',
        },
        {
          title: '[粒度<5mm]',
          align: 'center',
          dataIndex: 'granularity',
        },
      ],

      columns: [],

      columnstwo: [
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
          dataIndex: 'supplier',
        },
        {
          title: '收货单位',
          align: 'center',
          dataIndex: 'receivingunit',
        },
        {
          title: '物资名称',
          align: 'center',
          dataIndex: 'material_name',
        },
        {
          title: '取样日期',
          align: 'center',
          dataIndex: 'riqi',
          customRender: function (text) {
            return !text ? '' : text.length > 10 ? text.substr(0, 10) : text
          },
        },
        {
          title: '检验量',
          align: 'center',
          dataIndex: 'weighing',
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
          title: 'FEO',
          align: 'center',
          dataIndex: 'feo',
        },
        {
          title: 'H2O',
          align: 'center',
          dataIndex: 'h2o',
        },
        {
          title: 'Al2O3',
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
          title: 'TFE',
          align: 'center',
          dataIndex: 'tfe',
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
          title: '[As]',
          align: 'center',
          dataIndex: 'as1',
        },
        {
          title: 'CaO',
          align: 'center',
          dataIndex: 'cao',
        },
        {
          title: 'K2O',
          align: 'center',
          dataIndex: 'k2o',
        },
        {
          title: 'SiO2',
          align: 'center',
          dataIndex: 'sio2',
        },
        {
          title: 'Zn',
          align: 'center',
          dataIndex: 'zn',
        },
      ],

      validatorRules: {
        contractNo: { rules: [{ required: true, message: '请选择合同号!' }] },
        material_type: { rules: [{ required: true, message: '请选择物资类别!' }] },
        receivingUnit: { rules: [{ required: true, message: '请选择收货单位!' }] },
      },

      url: {
        list: '/qualitydate/qualityDate/listjf',
        findpzh: '/contract/contractInformation/findpzh',
        findHt: '/hetong/hetong/findListHt',
        findshdw: '/hetong/hetong/findshdw',
        findOne: '/hetong/hetong/findOne',
        htadd: '/jingfen/jingfen/htadd',
      },

      dictOptions: {},
    }
  },
  created() {
    this.columns = this.columnstwo
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
    
    //表头更换
    listcolumn(type) {
      if (type === '球团') {
        this.columns = this.settingColumns
      } else {
        this.columns = this.columnstwo
      }
    },

    //手动添加结算单
    htAddOne() {
      this.form.validateFields((err, values) => {
        if (!err) {
          let hth = values.contractNo
          let voucherNo = this.voucherNo
          let receiving = values.receivingUnit
          this.$refs.ContractList.addsgjsd(hth, voucherNo, receiving)
        }
      })
    },

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

    //合同编号改变发生
    findOne(hth) {
      this.finghuandwzmc(hth)
      this.finlist(hth)
      this.htbhs = hth
      let datas = {
        htbh: hth,
      }
      this.loadData(this.arg, datas)
      this.$emit('gethtbh', hth)
    },

    //获取供货单位、物资名称
    finghuandwzmc(hth) {
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
    },

    //合同信息
    finlist(hth) {
      this.$refs.ContractList.htlist(hth)
    },

    //导入加权均值
    htAdd() {
      let length = this.selectedRowKeys.length
      if (length === 0) {
        this.$message.warning('请选择数据。')
        return
      }
      let htxx = JSON.stringify(this.selectionRows)
      //凭证号
      let pzh = this.voucherNo
      this.form.validateFields((err, values) => {
        if (!err) {
          let shdw = values.receivingUnit
          let htbhs = values.contractNo
          postAction(this.url.htadd, { htxx: htxx, htbhs: htbhs, pzh: pzh, shdw: shdw }).then((res) => {
            if (res.success) {
              this.$message.success(res.message)
              this.$refs.ContractList.htlist(htbhs)
            }
            if (res.code === 510) {
              this.$message.warning(res.message)
            }
            this.loading = false
          })
        }
      })
    },

    //加权平均
    searchList() {
      this.form.validateFields((err, values) => {
        if (!err) {
          this.htbhs = values.contractNo
          let wzmc = values.material_type
          let htbh = values.contractNo
          this.$refs.modalForm.add(wzmc, htbh)
          this.$refs.modalForm.title = '加权平均'
          this.$refs.modalForm.disableSubmit = false
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
      postAction(this.url.list, params).then((res) => {
        if (res.success) {
          console.log(res)
          this.dataSource = res.result.records
          this.ipagination.total = res.result.total
        }
        if (res.code === 510) {
          this.$message.warning(res.message)
        }
        this.loading = false
      })
    },

    modalFormOk() {
      // 新增/修改 成功时，重载列表
      let htbh = {
        htbh: this.htbhs,
      }
      this.loadData(0, htbh)
    },

    //获取选中行数据
    onSelectChange(selectedRowKeys, selectionRows) {
      this.selectedRowKeys = selectedRowKeys
      this.selectionRows = selectionRows
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
      let shdw = this.supplier
      let htbh = {
        htbh: this.htbhs,
      }
      this.loadData(this.arg, htbh)
    },
  },
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>