<template>
  <a-card :bordered="false">
    <!-- table区域-begin -->
    <div>
      <!-- 查询区域 -->
      <div class="table-page-search-wrapper">
        <a-form layout="inline" @keyup.enter.native="searchQuery" :form="form">
          <a-row :gutter="24">
            <a-col :xl="6" :lg="8" :md="8" :sm="24">
              <a-form-item label="合同来源">
                <a-select v-model="hetongly" @change="findhtly">
                  <a-select-option value="物资合同">物资合同</a-select-option>
                  <a-select-option value="原炉料合同">原炉料合同</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="8" :md="8" :sm="24">
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
            <a-col :xl="6" :lg="8" :md="8" :sm="24">
              <a-form-item label="凭证号">
                <a-input placeholder="请输入凭证号" v-model="voucherNo" disabled></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="8" :md="8" :sm="24">
              <a-form-item label="供货单位">
                <a-input placeholder="请输入供货单位" v-model="supplier"></a-input>
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="24">
            <a-col :xl="6" :lg="8" :md="8" :sm="24">
              <a-form-item label="物资名称">
                <a-select v-model="materialName" @change="finwzcode">
                  <a-select-option
                    placeholder="请选择物资名称"
                    :value="item.WZName"
                    v-for="(item,index) in materialNamelist"
                    :key="index"
                  >{{item.WZName}}</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>

            <a-col :xl="6" :lg="8" :md="8" :sm="24">
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

            <a-col :xl="6" :lg="8" :md="10" :sm="24">
              <a-form-item label="起止日期">
                <a-range-picker
                  :trigger-change="true"
                  v-decorator="['rangeDate', validatorRules.rangeDate]"
                />
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="8" :md="8" :sm="24">
              <a-form-item label="物资大类">
                <a-select v-model="wztype" @change="finwztype">
                  <a-select-option value="合金">合金</a-select-option>
                  <a-select-option value="辅料">辅料</a-select-option>
                  <a-select-option value="燃料">燃料</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="24">
            <a-col :xl="6" :lg="8" :md="8" :sm="24">
              <a-form-item label="筛上">
                <a-select v-model="wzshang">
                  <a-select-option value="物资合同">物资合同</a-select-option>
                  <a-select-option value="原炉料合同">原炉料合同</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="8" :md="8" :sm="24">
              <a-form-item label="筛下">
                <a-select v-model="wzxia">
                  <a-select-option value="物资合同">物资合同</a-select-option>
                  <a-select-option value="原炉料合同">原炉料合同</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="8" :md="8" :sm="24">
              <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
                <a-button type="primary" @click="searchList" icon="search">查询质检数据</a-button>
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
      <contract-list ref="ContractList"></contract-list>
    </div>
  </a-card>
</template>

<script>
import '@/assets/less/TableExpand.less'
import { getAction, postAction } from '@/api/manage'
import { filterObj } from '@/utils/util'
import { validateDuplicateValue, randomUUID, handleStatus } from '@/utils/util'
import { mixinDevice } from '@/utils/mixin'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import JDate from '@/components/jeecg/JDate.vue'
import ContractList from '../fuliao/ContractInformationList'
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
      //合同来源
      hetongly: '',
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
      materialNamelist: [],
      //物资编码
      wzcode: '',
      //物资大类
      wztype: '',
      //筛上
      wzshang: '',
      //筛下
      wzxia: '',
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
          dataIndex: '物资名称',
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
          dataIndex: 'JJ',
        },
        {
          title: 'LD1',
          align: 'center',
          dataIndex: 'LD1',
        },
        {
          title: 'LD2',
          align: 'center',
          dataIndex: 'LD2',
        },
        {
          title: 'CaO',
          align: 'center',
          dataIndex: 'CaO',
        },
        {
          title: 'MgO',
          align: 'center',
          dataIndex: 'MgO',
        },
        {
          title: 'SiO2',
          align: 'center',
          dataIndex: 'SiO2',
        },
        {
          title: 'P',
          align: 'center',
          dataIndex: 'P',
        },
        {
          title: 'S',
          align: 'center',
          dataIndex: 'S',
        },
        {
          title: 'CaF2',
          align: 'center',
          dataIndex: 'CaF2',
        },
        {
          title: 'Al2O3',
          align: 'center',
          dataIndex: 'Al2O3',
        },
        {
          title: 'TFe',
          align: 'center',
          dataIndex: 'TFe',
        },
        {
          title: 'TMn',
          align: 'center',
          dataIndex: 'TMn',
        },
        {
          title: 'H2O',
          align: 'center',
          dataIndex: 'H2O',
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
        list: '/zhijianxingxi/zhijianxingxi/listzjfl',
        findpzh: '/contract/contractInformation/findpzh',
        findHt: '/hetong/hetong/findListHt',
        findHtwz: '/hetong/hetong/findListHtwz',
        findshdw: '/hetong/hetong/findshdw',
        findyllht: '/hetong/hetong/findyllhthj',
        findwzht: '/hetong/hetong/findwzhthj',
        htadd: '/feiliao/feiliao/htadd',
        finwzname: '/hetong/hetong/finwzname',
        finhtyllxxhj: '/hetong/hetong/findyllhthj',
        finhtwzxxhj: '/hetong/hetong/findwzhthj',
        findhtwznamehj: '/hetong/hetong/findhtncwzname',
        findhtyllnamehj: '/hetong/hetong/findhtncyllname',
      },
      dictOptions: {},
    }
  },
  created() {
    this.findpzh()
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

    //获取收货单位
    findshdw() {
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

    //获取合同号
    findHt(hth, urlht) {
      getAction(urlht, { hth: hth }).then((res) => {
        if (res.success) {
          this.contractNos = res.result
        }
        if (res.code === 510) {
          this.$message.warning(res.message)
        }
        this.loading = false
      })
    },

    //合同来源获取合同号
    findhtly(ly) {
      let urlht = ''
      if (ly === '物资合同') {
        urlht = this.url.findHtwz
      } else {
        urlht = this.url.findHt
      }
      this.findHt('', urlht)
    },

    //获取合同号
    htSearch(value) {
      let ly = this.hetongly
      let urlht = ''
      if (ly === '物资合同') {
        urlht = this.url.findHtwz
      } else {
        urlht = this.url.findHt
      }
      this.findHt(value, urlht)
    },

    //获取供货单位
    findOne(hth) {
      let httpye = this.hetongly
      this.htbh = hth
      let urlhtdw = ''
      let urlhtmc = ''
      if (httpye === '物资合同') {
        urlhtdw = this.url.finhtwzxxhj
        urlhtmc = this.url.findhtwznamehj
      } else if (httpye === '原炉料合同') {
        urlhtdw = this.url.finhtyllxxhj
        urlhtmc = this.url.findhtyllnamehj
      }
      this.getghdw(hth, urlhtdw)
      this.getwzmc(hth, urlhtmc)
      this.$emit('gethtbh', hth)
      this.finlist(hth)
    },

    //供货单位
    getghdw(hth, urldw) {
      getAction(urldw, { hth: hth }).then((res) => {
        if (res.success) {
          if (res.result != null && res.result != '') {
            this.supplier = res.result[0].DanWeiName
          }
        }
        if (res.code === 510) {
          this.$message.warning(res.message)
        }
        this.loading = false
      })
    },

    //物资名称
    getwzmc(hth, urlmc) {
      getAction(urlmc, { hth: hth }).then((res) => {
        if (res.success) {
          if (res.result != null && res.result != '') {
            this.materialNamelist = res.result
          }
        }
        if (res.code === 510) {
          this.$message.warning(res.message)
        }
        this.loading = false
      })
    },

    //获取物资编码
    finwzcode(wzname) {
      let hth = this.htbh
      let httpye = this.hetongly
      let finwzname = this.url.finwzname
      getAction(finwzname, { hth: hth, wzname: wzname, httpye: httpye }).then((res) => {
        if (res.success) {
          if (res.result != '' && res.result != null) {
            this.wzcode = res.result
          }
        }
        if (res.code === 510) {
          this.$message.warning(res.message)
        }
        this.loading = false
      })
    },

    //物资大类
    finwztype(wztype) {
      alert(wztype)
    },
    //合同信息
    finlist(hth) {
      this.$refs.ContractList.htlist(hth)
    },

    //导入质检数据
    htAdd() {
      let htxx = JSON.stringify(this.selectionRows)
      let hetongly = this.hetongly
      //凭证号
      let pzh = this.voucherNo
      this.form.validateFields((err, values) => {
        if (!err) {
          let shdw = values.receivingUnit
          let htbhs = values.contractNo
          postAction(this.url.htadd, { htxx: htxx, htbhs: htbhs, pzh: pzh, shdw: shdw, hetongly: hetongly }).then(
            (res) => {
              if (res.success) {
                this.$message.warning(res.message)
                this.$refs.ContractList.htlist(htbhs)
              }
              if (res.code === 510) {
                this.$message.warning(res.message)
              }
              this.loading = false
            }
          )
        }
      })
    },

    //查询质检数据
    searchList() {
      let materialName = this.materialName
      if (
        materialName != '石灰石' &&
        materialName != '熟白云石粉' &&
        materialName != '硅石' &&
        materialName != '萤石' &&
        materialName != '熟白云石块'
      ) {
        this.$message.warning('物资名称只能是石灰石、熟白云石粉/块、硅石、萤石查询,请重新选择')
        return
      }
      this.form.validateFields((err, values) => {
        if (!err) {
          this.startTime = moment(values.rangeDate[0]).format('YYYY-MM-DD')
          this.endTime = moment(values.rangeDate[1]).format('YYYY-MM-DD')
          let shdw = this.supplier

          let datas = {
            startTime: this.startTime,
            endTime: this.endTime,
            supplier: shdw,
            materialName: materialName,
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
          let materialName = this.materialName
          let datas = {
            startTime: this.startTime,
            endTime: this.endTime,
            supplier: shdw,
            materialName: materialName,
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