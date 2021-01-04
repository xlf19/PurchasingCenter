<template>
  <div>
    <div class="table-page-search-wrapper">
      <a-form layout="inline" :form="form">
        <!-- 合同类别 -->
        <a-card :bordered="false" title="合同类别">
          <a-row :gutter="24">
            <a-col :xl="5" :lg="7" :md="8" :sm="24">
              <a-form-item label="合同类别">
                <a-select
                  placeholder="请选择合同类别"
                  allowClear
                  v-decorator="['contractType', validatorRules.contractType]"
                >
                  <a-select-option value="原料">原料</a-select-option>
                  <a-select-option value="新原料">新原料</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :xl="5" :lg="7" :md="8" :sm="24">
              <a-form-item label="合同模板">
                <a-select
                  placeholder="请选择合同模板"
                  allowClear
                  v-decorator="['templateId', validatorRules.templateId]"
                >
                  <a-select-option value="1">模板1</a-select-option>
                  <a-select-option value="2">模板2</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :xl="5" :lg="7" :md="8" :sm="24">
              <a-button type="primary" @click="submitForm" icon="plus">保存合同</a-button>
            </a-col>
          </a-row>
        </a-card>
        <a-card :bordered="false" title="合同主项">
          <a-row :gutter="24">
            <a-col :xl="5" :lg="7" :md="8" :sm="24">
              <a-form-item label="合同编号">
                <a-input placeholder="请输入合同编号" v-decorator="['contractNo', validatorRules.contractNo]"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="5" :lg="7" :md="8" :sm="24">
              <a-form-item label="供方单位">
                <a-select
                  @search="searchname"
                  @change="searchname"
                  show-search
                  allowClear
                  v-decorator="['supplier', validatorRules.supplier]"
                  placeholder="请选择供方单位"
                >
                  <a-select-option :value="item.companyName" v-for="(item, index) in depratlist" :key="index" >{{
                    item.companyName
                  }}</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :xl="5" :lg="7" :md="8" :sm="24">
              <a-form-item label="需方单位">
                <a-select
                  placeholder="请选择需方单位"
                  allowClear
                  v-decorator="['demandSideUnit', validatorRules.demandSideUnit]"
                >
                  <a-select-option value="芜湖新兴铸管有限责任公司">芜湖新兴铸管有限责任公司</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :xl="5" :lg="7" :md="8" :sm="24">
              <a-form-item label="合同标题">
                <a-select
                  placeholder="请选择合同标题"
                  allowClear
                  v-decorator="['contractTitle', validatorRules.contractTitle]"
                >
                  <a-select-option value="原料购销合同">原料购销合同</a-select-option>
                  <a-select-option value="氧化铁皮购销合同">氧化铁皮购销合同</a-select-option>
                  <a-select-option value="富粉购销合同">富粉购销合同</a-select-option>
                  <a-select-option value="精矿粉购销合同">精矿粉购销合同</a-select-option>
                  <a-select-option value="球团购销合同">球团购销合同</a-select-option>
                  <a-select-option value="废钢购销合同">废钢购销合同</a-select-option>
                  <a-select-option value="制钢铁购销合同">制钢铁购销合同</a-select-option>
                  <a-select-option value="块矿购销合同">块矿购销合同</a-select-option>
                  <a-select-option value="生铁购销合同">生铁购销合同</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="24">
            <a-col :xl="5" :lg="7" :md="8" :sm="24">
              <a-form-item label="含税">
                <a-radio-group v-decorator="['taxIncluded', { initialValue: 1 }]">
                  <a-radio :value="1">
                    含税
                  </a-radio>
                  <a-radio :value="2">
                    不含税
                  </a-radio>
                </a-radio-group>
              </a-form-item>
            </a-col>
            <a-col :xl="5" :lg="7" :md="8" :sm="24">
              <a-form-item label="税率">
                <a-select placeholder="请选择税率" allowClear v-decorator="['taxRate', { initialValue: '13%' }]">
                  <a-select-option value="11%">11%</a-select-option>
                  <a-select-option value="13%">13%</a-select-option>
                  <a-select-option value="16%">16%</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :xl="5" :lg="7" :md="8" :sm="24">
              <a-form-item label="币种">
                <a-select placeholder="请选择币种" allowClear v-decorator="['currency', { initialValue: '￥' }]">
                  <a-select-option value="￥">￥</a-select-option>
                  <a-select-option value="$">$</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :xl="5" :lg="7" :md="8" :sm="24">
              <a-form-item label="签订地点">
                <a-input
                  placeholder="请输入签订地点"
                  v-decorator="['placeSigning', { initialValue: '新兴铸管芜湖工业区' }]"
                ></a-input>
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="24">
            <a-col :xl="5" :lg="7" :md="8" :sm="24">
              <a-form-item label="交货地点">
                <a-input
                  placeholder="请输入交货地点"
                  v-decorator="['tradingLocations', { initialValue: '新兴铸管芜湖工业区' }]"
                ></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="5" :lg="7" :md="8" :sm="24">
              <a-form-item label="签订时间">
                <a-date-picker
                  placeholder="请选择签订时间"
                  style="width:100%"
                  v-decorator="['signingTime', { initialValue: moment(this.time, dateFormat) }]"
                />
              </a-form-item>
            </a-col>
            <a-col :xl="5" :lg="7" :md="8" :sm="24">
              <a-form-item label="交货时间">
                <a-date-picker
                  placeholder="请选择交货时间"
                  style="width:100%"
                  v-decorator="['deliveryTime', { initialValue: moment(this.time, dateFormat) }]"
                />
              </a-form-item>
            </a-col>
            <a-col :xl="5" :lg="7" :md="8" :sm="24">
              <a-form-item label="开始时间">
                <a-date-picker
                  placeholder="请选择开始时间"
                  style="width:100%"
                  v-decorator="['startingTime', { initialValue: moment(this.time, dateFormat) }]"
                />
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="24">
            <a-col :xl="5" :lg="7" :md="8" :sm="24">
              <a-form-item label="终止时间">
                <a-date-picker
                  placeholder="请选择终止时间"
                  style="width:100%"
                  v-decorator="['stopTime', { initialValue: moment(this.time, dateFormat) }]"
                />
              </a-form-item>
            </a-col>
            <a-col :xl="5" :lg="7" :md="8" :sm="24">
              <a-form-item label="合同备注">
                <a-input placeholder="请输入合同备注" v-decorator="['contractNotes']"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="5" :lg="7" :md="8" :sm="24">
              <a-form-item label="其他备注">
                <a-input placeholder="请输入其他备注" v-decorator="['otherRemarks']"></a-input>
              </a-form-item>
            </a-col>
          </a-row>
        </a-card>
      </a-form>
    </div>
    <a-card title="产品列表" :bordered="false">
      <!-- 操作按钮区域 -->
      <div class="table-operator">
        <a-button type="primary" @click="handleAdd" icon="plus">新增产品</a-button>
        <a-button type="primary" icon="delete" @click="handledelete">删除产品</a-button>
        <a-button type="primary" icon="save" @click="handlesave">保存产品</a-button>
      </div>
      <!-- 主表table区域-begin -->
      <div>
        <j-editable-table
          :columns="columns"
          :dataSource="data"
          bordered
          :maxHeight="400"
          @valueChange="handleValueTwo"
          ref="editableTable"
        />
        <contract-product-modal ref="modalForm" @ok="modalFormOk"></contract-product-modal>
      </div>
      <!-- 副表table区域-begin -->
      <div style="margin-top:20px">
        <j-editable-table
          :columns="columnslist"
          :dataSource="datalist"
          bordered
          :maxHeight="400"
          @valueChange="handleValueChange"
          ref="editablelist"
        />
      </div>
    </a-card>
  </div>
</template>

<script>
import '@/assets/less/TableExpand.less'
import { deleteAction, getAction, httpAction, downFile, getFileAccessHttpUrl } from '@/api/manage'
import { initDictOptions, filterMultiDictText } from '@/components/dict/JDictSelectUtil'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import { mixinDevice } from '@/utils/mixin'
import JEllipsis from '@/components/jeecg/JEllipsis'
import moment from 'moment'
import store from '@/store'
import ContractProductModal from './modules/ContractProductModal'
import { FormTypes } from '@/utils/JEditableTableUtil'
import JEditableTable from '@/components/jeecg/JEditableTable'
import { randomUUID } from '@/utils/util'
export default {
  name: 'ContractMain',
  mixins: [JeecgListMixin, mixinDevice],
  components: { ContractProductModal, JEditableTable },
  data() {
    this.dateFormat = 'YYYY-MM-DD'
    return {
      form: this.$form.createForm(this),
      validatorRules: {
        contractType: { rules: [{ required: true, message: '请选择合同类别!' }] },
        templateId: { rules: [{ required: true, message: '请选择合同模板!' }] },
        contractNo: { rules: [{ required: true, message: '请输入合同编号!' }] },
        supplier: { rules: [{ required: true, message: '请选择供方单位!' }] },
        demandSideUnit: { rules: [{ required: true, message: '请选择需方单位!' }] },
        contractTitle: { rules: [{ required: true, message: '请选择合同标题!' }] }
      },
      time: new Date(),
      model: {},
      modeldata: {},
      modellist: {},
      data: [],
      datalist: [],
      //供方单位
      depratlist: [],
      disableMixinCreated: true,
      // 表头
      columns: [
        {
          title: '物资编码',
          key: 'materialCode'
        },
        {
          title: '物资名称',
          key: 'materialName'
        },
        {
          title: '规格型号',
          key: 'specificationModel'
        },
        {
          title: '单位',
          key: 'unit'
        },
        {
          title: '单价',
          key: 'unitPrice',
          type: FormTypes.inputNumber
        },
        {
          title: '数量',
          key: 'quantity'
        },

        {
          title: '总价',
          key: 'totalPrice'
        },
        {
          title: '税金',
          key: 'taxes'
        },
        {
          title: '备注',
          key: 'remarks',
          type: FormTypes.input
        }
      ],
      columnslist: [
        {
          title: '总量',
          key: 'total'
        },
        {
          title: '1月',
          key: 'january',
          type: FormTypes.inputNumber
        },
        {
          title: '2月',
          key: 'february',
          type: FormTypes.inputNumber
        },
        {
          title: '3月',
          key: 'march',
          type: FormTypes.inputNumber
        },
        {
          title: '4月',
          key: 'april',
          type: FormTypes.inputNumber
        },
        {
          title: '5月',
          key: 'may',
          type: FormTypes.inputNumber
        },
        {
          title: '6月',
          key: 'june',
          type: FormTypes.inputNumber
        },
        {
          title: '7月',
          key: 'july',
          type: FormTypes.inputNumber
        },
        {
          title: '8月',
          key: 'august',
          type: FormTypes.inputNumber
        },
        {
          title: '9月',
          key: 'september',
          type: FormTypes.inputNumber
        },
        {
          title: '10月',
          key: 'october',
          type: FormTypes.inputNumber
        },
        {
          title: '11月',
          key: 'november',
          type: FormTypes.inputNumber
        },
        {
          title: '12月',
          key: 'december',
          type: FormTypes.inputNumber
        }
      ],
      dictOptions: {},
      url: {
        searchname: '/original/originalCharge/searchname',
        hthfind: '/contractpurchase/contractPurchase/hthfind',
        add: '/contractedit/contractedit/add',
        adddata: '/contractedit/contractedit/adddata',
        addlist: '/contractedit/contractedit/addlist',
        queryById: '/contractedit/contractedit/queryById',
        delete: '/contractedit/contractedit/delete'
      }
    }
  },
  created() {
    // this.username = store.getters.userInfo.realname
    // this.departname = store.getters.userInfo.orgCodeTxt
    this.searchname('')
    this.hthfind()
  },
  computed: {
    importExcelUrl: function() {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    }
  },
  methods: {
    moment,
    //生成合同号
    hthfind() {
      getAction(this.url.hthfind).then(res => {
        if (res.success) {
          this.form.setFieldsValue({
            contractNo: res.message
          })
        }
        if (res.code === 510) {
          this.$message.warning(res.message)
        }
      })
    },
    //查询供应商
    searchname(value) {
      let companyName = ''
      if (value !== '' && value !== null && value !== undefined) {
        companyName = value
      }
      getAction(this.url.searchname, { companyName: companyName }).then(res => {
        if (res.success) {
          console.log(res.result)
          this.depratlist = res.result
        }
        if (res.code === 510) {
          this.$message.warning(res.message)
        }
      })
    },
    //新增产品
    modalFormOk(data) {
      this.data = []
      this.datalist = []
      this.data.push({
        key: 1,
        materialCode: data[0].materialCode,
        materialName: data[0].materialName,
        specificationModel: data[0].specification,
        unit: data[0].unit,
        unitPrice: 0,
        quantity: 0,
        totalPrice: 0,
        taxes: 0,
        remarks: ''
      })
      this.datalist.push({
        key: 1,
        total: 0,
        january: 0,
        february: 0,
        march: 0,
        april: 0,
        may: 0,
        june: 0,
        july: 0,
        august: 0,
        september: 0,
        october: 0,
        november: 0,
        december: 0
      })
    },
    //删除产品
    handledelete() {
      this.$refs.editableTable.getValues((error, values) => {
        if (error === 0) {
          if (values.length === 0) {
            this.$message.warning('产品数据为空')
          } else {
            let id = values[0].id
            //先查询产品信息表是否存在信息
            getAction(this.url.queryById, { id: id }).then(res => {
              if (res.code == 200) {
                deleteAction(this.url.delete, { id: id }).then(rtx => {
                  console.log(rtx)
                  if (rtx.code == 200) {
                    this.$message.success('删除成功')
                    this.data = []
                    this.datalist = []
                  }
                })
              } else {
                this.$message.success('删除成功')
                this.data = []
                this.datalist = []
              }
            })
          }
        }
      })
    },
    //修改副表总量
    handleValueChange(event) {
      const { type, row, column, value, target } = event
      let january = 0
      let february = 0
      let march = 0
      let april = 0
      let may = 0
      let june = 0
      let july = 0
      let august = 0
      let september = 0
      let october = 0
      let november = 0
      let december = 0
      if (!isNaN(parseFloat(row.january))) {
        january = parseFloat(row.january)
      }
      if (!isNaN(parseFloat(row.february))) {
        february = parseFloat(row.february)
      }
      if (!isNaN(parseFloat(row.march))) {
        march = parseFloat(row.march)
      }
      if (!isNaN(parseFloat(row.april))) {
        april = parseFloat(row.april)
      }
      if (!isNaN(parseFloat(row.may))) {
        may = parseFloat(row.may)
      }
      if (!isNaN(parseFloat(row.june))) {
        june = parseFloat(row.june)
      }
      if (!isNaN(parseFloat(row.july))) {
        july = parseFloat(row.july)
      }
      if (!isNaN(parseFloat(row.august))) {
        august = parseFloat(row.august)
      }
      if (!isNaN(parseFloat(row.september))) {
        september = parseFloat(row.september)
      }
      if (!isNaN(parseFloat(row.october))) {
        october = parseFloat(row.october)
      }
      if (!isNaN(parseFloat(row.november))) {
        november = parseFloat(row.november)
      }
      if (!isNaN(parseFloat(row.december))) {
        december = parseFloat(row.december)
      }
      let totals =
        january + february + march + april + may + june + july + august + september + october + november + december
      // 设置总量
      target.setValues([
        {
          rowKey: row.id,
          values: { total: totals }
        }
      ])
      this.handleValuefirst(totals)
    },
    //设置数量、总价、税金
    handleValuefirst(datas) {
      this.$refs.editableTable.getValues((error, values) => {
        if (error === 0) {
          let unitPrice = 0
          if (!isNaN(parseFloat(values[0].unitPrice))) {
            unitPrice = parseFloat(values[0].unitPrice)
          }
          values[0].quantity = datas
          values[0].totalPrice = datas * unitPrice
          this.data = values
        }
      })
    },
    //设置总价、税金
    handleValueTwo(event) {
      const { type, row, column, value, target } = event
      let unitPrice = 0 //单价
      let quantity = 0 //数量
      if (!isNaN(parseFloat(row.unitPrice))) {
        unitPrice = parseFloat(row.unitPrice)
      }
      if (!isNaN(parseFloat(row.quantity))) {
        quantity = parseFloat(row.quantity)
      }
      let totalPrice = unitPrice * quantity
      // 设置总价
      target.setValues([
        {
          rowKey: row.id,
          values: { totalPrice: totalPrice }
        }
      ])
    },
    //提交合同
    submitForm() {
      const that = this
      // 触发表单验证
      this.form.validateFields((err, values) => {
        if (!err) {
          if (!this.model.id) {
            this.model.id = randomUUID()
          }
          let httpurl = this.url.add
          let method = 'post'
          let formData = Object.assign(this.model, values)
          httpAction(httpurl, formData, method).then(res => {
            if (res.success) {
              that.$message.success(res.message)
            } else {
              that.$message.warning(res.message)
            }
          })
        }
      })
    },
    //保存产品
    handlesave() {
      if (this.data.length === 0) {
        this.$message.error('你还没有添加产品，请添加产品！')
        return
      }
      let modelid = this.model.id
      if (modelid === undefined || modelid === '' || modelid === null) {
        this.$message.error('你还没有保存合同，请先保存合同！')
        return
      }
      //保存产品列表主表
      this.$refs.editableTable.getValues((error, values) => {
        let id = ''
        if (error === 0) {
          id = values[0].id
          this.modeldata.contractId = modelid
          let modeldata = Object.assign(this.modeldata, values[0])
          httpAction(this.url.adddata, modeldata, 'post').then(res => {
            this.$refs.editablelist.getValues((error, values) => {
              if (error === 0) {
                this.modellist.productId = id
                let modellist = Object.assign(this.modellist, values[0])
                httpAction(this.url.addlist, modellist, 'post').then(res => {
                  this.$message.success(res.message)
                })
              }
            })
          })
        }
      })
    }
  }
}
</script>

<style scoped>
@import '~@assets/less/common.less';
</style>