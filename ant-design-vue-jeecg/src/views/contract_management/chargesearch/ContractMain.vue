<template>
  <div>
    <div class="table-page-search-wrapper">
      <a-form layout="inline" :form="form">
        <!-- 合同类别 -->
        <a-card :bordered="false" title="合同类别">
          <a-row :gutter="24">
            <a-col :xl="5" :lg="7" :md="8" :sm="24">
              <a-form-item label="合同模板">
                <a-select
                  placeholder="请选择合同模板"
                  @change="handleChange"
                  v-decorator="['templateId', validatorRules.templateId]"
                >
                  <a-select-option :value="item.id" v-for="item in templatelist" :key="item.id">{{
                    item.templateName
                  }}</a-select-option>
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
                <a-input
                  placeholder="请输入合同编号"
                  v-decorator="['contractNo', validatorRules.contractNo]"
                  disabled
                ></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="5" :lg="7" :md="8" :sm="24">
              <a-form-item label="供方单位">
                <a-input
                  placeholder="请选择供方单位"
                  v-decorator="['supplierName', validatorRules.supplierName]"
                  @click="orgmodal"
                ></a-input>
              </a-form-item>

              <a-form-item label="供方单位" v-show="false">
                <a-input placeholder="请选择供方单位" v-decorator="['supplier']"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="5" :lg="7" :md="8" :sm="24">
              <a-form-item label="需方单位">
                <a-select
                  placeholder="请选择需方单位"
                  allowClear
                  v-decorator="['demandSideUnit', validatorRules.demandSideUnit]"
                >
                  <a-select-option v-for="item in demandSideUnitlist" :key="item.id" :value="item.id">{{
                    item.companyName
                  }}</a-select-option>
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
                  <a-select-option v-for="(item, index) in titlelist" :key="index" :value="item.HTName">{{
                    item.HTName
                  }}</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="24">
            <a-col :xl="5" :lg="7" :md="8" :sm="24">
              <a-form-item label="含税">
                <a-radio-group v-decorator="['taxIncluded']" @change="changetax">
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
                <a-select placeholder="请选择税率" allowClear v-decorator="['taxRate']" @change="changetaxrate">
                  <a-select-option value="11%">11%</a-select-option>
                  <a-select-option value="13%">13%</a-select-option>
                  <a-select-option value="16%">16%</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :xl="5" :lg="7" :md="8" :sm="24">
              <a-form-item label="币种">
                <a-select placeholder="请选择币种" allowClear v-decorator="['currency']">
                  <a-select-option value="￥">￥</a-select-option>
                  <a-select-option value="$">$</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :xl="5" :lg="7" :md="8" :sm="24">
              <a-form-item label="签订地点">
                <a-input placeholder="请输入签订地点" v-decorator="['placeSigning']"></a-input>
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="24">
            <a-col :xl="5" :lg="7" :md="8" :sm="24">
              <a-form-item label="交货地点">
                <a-input placeholder="请输入交货地点" v-decorator="['tradingLocations']"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="5" :lg="7" :md="8" :sm="24">
              <a-form-item label="签订时间">
                <a-date-picker placeholder="请选择签订时间" style="width:100%" v-decorator="['signingTime']" />
              </a-form-item>
            </a-col>
            <a-col :xl="5" :lg="7" :md="8" :sm="24">
              <a-form-item label="交货时间">
                <a-date-picker placeholder="请选择交货时间" style="width:100%" v-decorator="['deliveryTime']" />
              </a-form-item>
            </a-col>
            <a-col :xl="5" :lg="7" :md="8" :sm="24">
              <a-form-item label="开始时间">
                <a-date-picker placeholder="请选择开始时间" style="width:100%" v-decorator="['startingTime']" />
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="24">
            <a-col :xl="5" :lg="7" :md="8" :sm="24">
              <a-form-item label="终止时间">
                <a-date-picker placeholder="请选择终止时间" style="width:100%" v-decorator="['stopTime']" />
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
          rowKey="id"
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
          rowKey="id"
          ref="editablelist"
        />
      </div>
    </a-card>
    <original-modal ref="orgmodalForm" @ok="addorginal"></original-modal>
  </div>
</template>

<script>
import '@/assets/less/TableExpand.less'
import { deleteAction, getAction, httpAction, getFileAccessHttpUrl } from '@/api/manage'
import pick from 'lodash.pick'
import { initDictOptions, filterMultiDictText } from '@/components/dict/JDictSelectUtil'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import { mixinDevice } from '@/utils/mixin'
import JEllipsis from '@/components/jeecg/JEllipsis'
import moment from 'moment'
import store from '@/store'
import ContractProductModal from './modules/ContractProductModal'
import OriginalModal from './originalmodules/OriginalModal'
import { FormTypes } from '@/utils/JEditableTableUtil'
import JEditableTable from '@/components/jeecg/JEditableTable'
import { randomUUID } from '@/utils/util'
import JDate from '@/components/jeecg/JDate'
import * as math from 'mathjs'
export default {
  name: 'ContractMain',
  mixins: [JeecgListMixin, mixinDevice],
  components: { ContractProductModal, JEditableTable, JDate, OriginalModal },
  props: {
    contractid: {
      type: String
    },
    whether: {
      type: Boolean
    }
  },
  watch: {
    contractid: function(newVal, oldVal) {
      this.contractid = newVal
      this.contractone(newVal)
    }
  },
  data() {
    this.dateFormat = 'YYYY-MM-DD'
    return {
      form: this.$form.createForm(this),
      validatorRules: {
        templateId: { rules: [{ required: true, message: '请选择合同模板!' }] },
        contractNo: { rules: [{ required: true, message: '请输入合同编号!' }] },
        supplierName: { rules: [{ required: true, message: '请选择供方单位!' }] },
        demandSideUnit: { rules: [{ required: true, message: '请选择需方单位!' }] },
        contractTitle: { rules: [{ required: true, message: '请选择合同标题!' }] }
      },
      time: new Date(),
      model: {},
      modeldata: {},
      modellist: {},
      data: [],
      datalist: [],

      //合同模板
      templatelist: [],
      //需方单位
      demandSideUnitlist: [],
      //合同标题
      titlelist: [],
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
        contractid: '/contractpurchase/contractPurchase/queryById',
        add: '/contractedit/contractedit/add',
        adddata: '/contractedit/contractedit/adddata',
        addlist: '/contractedit/contractedit/addlist',
        productid: '/chargsearch/chargsearch/productid',
        productidlist: '/chargsearch/chargsearch/productidlist',
        queryById: '/contractedit/contractedit/queryById',
        delete: '/contractedit/contractedit/delete',
        template: '/contractedit/contractedit/templatelist',
        contracttitle: '/contractstatus/contractstatus/searchTitle'
      }
    }
  },
  created() {
    this.contractone(this.contractid)
    this.templatelists()
    this.searchname()
    this.searchTitle()
  },
  computed: {
    importExcelUrl: function() {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    }
  },
  methods: {
    moment,
    //查询合同模板表信息
    templatelists() {
      getAction(this.url.template).then(res => {
        if (res.success) {
          this.templatelist = res.result
        }
        if (res.code === 510) {
          this.$message.warning(res.message)
        }
      })
    },
    //合同模板发生改变（向父组件传值）
    handleChange(value) {
      let cid = this.contractid
      this.$emit('search', value, cid, false)
    },
    //查询合同信息表
    contractone(id) {
      getAction(this.url.contractid, { id: id }).then(res => {
        if (res.success) {
          this.form.setFieldsValue({
            // id: res.result.id,
            templateId: res.result.templateId,
            contractNo: res.result.contractNo,
            supplier: res.result.supplier,
            supplierName: res.result.supplierName,
            demandSideUnit: res.result.demandSideUnit,
            contractTitle: res.result.contractTitle,
            taxIncluded: res.result.taxIncluded,
            taxRate: res.result.taxRate,
            currency: res.result.currency,
            placeSigning: res.result.placeSigning,
            tradingLocations: res.result.tradingLocations,
            signingTime: moment(res.result.signingTime, this.dateFormat),
            deliveryTime: moment(res.result.deliveryTime, this.dateFormat),
            startingTime: moment(res.result.startingTime, this.dateFormat),
            stopTime: moment(res.result.stopTime, this.dateFormat),
            contractNotes: res.result.contractNotes,
            otherRemarks: res.result.otherRemarks
          })
          getAction(this.url.productid, { contractid: id }).then(res => {
            if (res.success) {
              if (res.result.length > 0) {
                this.data = res.result
                let proid = res.result[0].id
                if (proid !== '' && proid !== null && proid !== undefined) {
                  getAction(this.url.productidlist, { pid: proid }).then(res => {
                    if (res.success) {
                      this.datalist = res.result
                    }
                  })
                }
              }
            }
          })
        }
      })
    },
    //查询供应商（模态框）
    orgmodal() {
      this.$refs.orgmodalForm.add()
      this.$refs.orgmodalForm.title = '查询供应商'
      this.$refs.orgmodalForm.disableSubmit = false
    },
    //添加供应商
    addorginal(value) {
      this.form.setFieldsValue({
        supplier: value[0].id,
        supplierName: value[0].companyName
      })
    },
    //查询需方单位
    searchname() {
      getAction(this.url.searchname, { companyName: '' }).then(res => {
        if (res.success) {
          this.demandSideUnitlist = res.result
        }
        if (res.code === 510) {
          this.$message.warning(res.message)
        }
      })
    },
    //查询合同标题
    searchTitle() {
      getAction(this.url.contracttitle).then(res => {
        if (res.success) {
          this.titlelist = res.result
        }
        if (res.code === 510) {
          this.$message.warning(res.message)
        }
      })
    },
    //新增产品
    modalFormOk(datao) {
      if (this.data.length === 0) {
        this.data = []
        this.datalist = []
        this.data.push({
          key: 1,
          materialCode: datao[0].materialCode,
          materialName: datao[0].materialName,
          specificationModel: datao[0].specification,
          unit: datao[0].unit,
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
      } else {
        this.$refs.editableTable.getValues((error, values) => {
          if (error === 0) {
            let id = values[0].id
            //先查询产品信息表是否存在信息
            getAction(this.url.queryById, { id: id }).then(res => {
              if (res.code == 200) {
                deleteAction(this.url.delete, { id: id }).then(rtx => {
                  if (rtx.code == 200) {
                    this.data = []
                    this.datalist = []
                    this.data.push({
                      key: 1,
                      materialCode: datao[0].materialCode,
                      materialName: datao[0].materialName,
                      specificationModel: datao[0].specification,
                      unit: datao[0].unit,
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
                  }
                })
              } else {
                this.data = []
                this.datalist = []
                this.data.push({
                  key: 1,
                  materialCode: datao[0].materialCode,
                  materialName: datao[0].materialName,
                  specificationModel: datao[0].specification,
                  unit: datao[0].unit,
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
              }
            })
          }
        })
      }
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
      let totals = this.printFn(
        january + february + march + april + may + june + july + august + september + october + november + december
      )

      // 设置总量
      target.setValues([
        {
          rowKey: row.id,
          values: { total: totals }
        }
      ])
      this.handleValuefirst(totals)
    },
    //副表设置数量、总价、税金
    handleValuefirst(datas) {
      this.$refs.editableTable.getValues((error, values) => {
        if (error === 0) {
          let unitPrice = 0
          if (!isNaN(parseFloat(values[0].unitPrice))) {
            unitPrice = parseFloat(values[0].unitPrice)
          }
          let sl = this.form.getFieldValue('taxRate') / 100 //税率
          let hs = this.form.getFieldValue('taxIncluded') //是否含税
          let sj = 0 //税金
          if (hs === 1) {
            sj = this.printFn((datas * unitPrice * sl) / (1 + sl))
          } else {
            sj = this.printFn(datas * unitPrice * sl)
          }
          values[0].taxes = parseFloat(sj).toFixed(2)
          values[0].quantity = datas
          values[0].totalPrice = this.printFn(datas * unitPrice)
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
      let totalPrice = this.printFn(unitPrice * quantity)
      let sl = this.form.getFieldValue('taxRate') / 100 //税率
      let hs = this.form.getFieldValue('taxIncluded') //是否含税
      let sj = 0 //税金
      let hsje = 0
      if (hs === 1) {
        sj = this.printFn((totalPrice * sl) / (1 + sl))
      } else {
        sj = this.printFn(totalPrice * sl)
      }
      hsje = parseFloat(sj).toFixed(2)
      // 设置总价
      target.setValues([
        {
          rowKey: row.id,
          values: { totalPrice: totalPrice, taxes: hsje }
        }
      ])
    },
    //提交合同
    submitForm() {
      const that = this
      // 触发表单验证
      this.form.validateFields((err, values) => {
        if (!err) {
          let httpurl = this.url.add
          let method = 'post'
          this.model.id = this.contractid
          if (values.signingTime !== '' && values.signingTime !== null && values.signingTime !== undefined) {
            values.signingTime = moment(values.signingTime).format('YYYY-MM-DD')
          }
          if (values.deliveryTime !== '' && values.deliveryTime !== null && values.deliveryTime !== undefined) {
            values.deliveryTime = moment(values.deliveryTime).format('YYYY-MM-DD')
          }
          if (values.startingTime !== '' && values.startingTime !== null && values.startingTime !== undefined) {
            values.startingTime = moment(values.startingTime).format('YYYY-MM-DD')
          }
          if (values.stopTime !== '' && values.stopTime !== null && values.stopTime !== undefined) {
            values.stopTime = moment(values.stopTime).format('YYYY-MM-DD')
          }

          let formData = Object.assign(this.model, values)
          httpAction(httpurl, formData, method).then(res => {
            if (res.success) {
              let whether = that.whether
              if (whether) {
                that.$message.success(res.message)
              } else {
                that.$emit('addtemplat', true)
              }
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
      let modelid = this.contractid
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
    },
    //是否含税
    changetax(e) {
      this.$refs.editableTable.getValues((error, values) => {
        if (error === 0) {
          let hs = e.target.value
          let unitPrice = 0
          let quantity = 0
          let sl = this.form.getFieldValue('taxRate') / 100 //税率
          let sj = 0 //税金
          if (!isNaN(parseFloat(values[0].unitPrice))) {
            unitPrice = parseFloat(values[0].unitPrice)
          }
          if (!isNaN(parseFloat(values[0].quantity))) {
            quantity = parseFloat(values[0].quantity)
          }
          if (hs === 1) {
            sj = this.printFn((quantity * unitPrice * sl) / (1 + sl))
          } else {
            sj = this.printFn(quantity * unitPrice * sl)
          }
          values[0].taxes = parseFloat(sj).toFixed(2)
          this.data = values
        }
      })
    },
    //税率改变
    changetaxrate(value) {
      this.$refs.editableTable.getValues((error, values) => {
        if (error === 0) {
          let hs = this.form.getFieldValue('taxIncluded') //是否含税
          let unitPrice = 0
          let quantity = 0
          let sl = value / 100 //税率
          let sj = 0 //税金
          if (!isNaN(parseFloat(values[0].unitPrice))) {
            unitPrice = parseFloat(values[0].unitPrice)
          }
          if (!isNaN(parseFloat(values[0].quantity))) {
            quantity = parseFloat(values[0].quantity)
          }
          if (hs === 1) {
            sj = this.printFn((quantity * unitPrice * sl) / (1 + sl))
          } else {
            sj = this.printFn(quantity * unitPrice * sl)
          }
          values[0].taxes = parseFloat(sj).toFixed(2)
          this.data = values
        }
      })
    },
    printFn(value) {
      const precision = 14
      return Number(math.format(value, precision))
    }
  }
}
</script>

<style scoped>
@import '~@assets/less/common.less';
</style>