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
                  <a-select-option :value="item.companyName" v-for="(item, index) in depratlist" :key="index">{{
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
                <a-radio-group v-decorator="['taxIncludedString', { initialValue: 1 }]">
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
        <a-button type="primary" icon="plus">删除产品</a-button>
        <a-button type="primary" icon="plus">保存产品</a-button>
      </div>
      <!-- 主表table区域-begin -->
      <div>
        <a-table :columns="columns" bordered :data-source="data">
          <template v-for="col in ['bankAccount', 'contactPerson']" :slot="col" slot-scope="text">
            <div :key="col">
              <a-input-number :value="text" style="margin: -5px 0" :precision="2" />
            </div>
          </template>
        </a-table>
          <contract-product-modal ref="modalForm" @ok="modalFormOk"></contract-product-modal>
      </div>
      <!-- 副表table区域-begin -->
      <div>
        <a-table :columns="columnslist" bordered :data-source="datalist">
          <template v-for="col in ['january', 'february']" :slot="col" slot-scope="text, record">
            <div :key="col">
              <a-input-number
                :value="text"
                style="margin: -5px 0"
                :precision="2"
                @change="e => handleChange(e, record.key, col)"
              />
            </div>
          </template>
        </a-table>
      </div>
    </a-card>
  </div>
</template>

<script>
import '@/assets/less/TableExpand.less'
import { deleteAction, getAction, downFile, getFileAccessHttpUrl } from '@/api/manage'
import { initDictOptions, filterMultiDictText } from '@/components/dict/JDictSelectUtil'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import { mixinDevice } from '@/utils/mixin'
import JEllipsis from '@/components/jeecg/JEllipsis'
import moment from 'moment'
import store from '@/store'
import ContractProductModal from './modules/ContractProductModal'
export default {
  name: 'ContractMain',
  mixins: [JeecgListMixin, mixinDevice],
  components: { ContractProductModal },
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
      data: [],
      datalist: [],
      //供方单位
      depratlist: [],
      disableMixinCreated: true,
      // 表头
      columns: [
        {
          title: '物资编码',
          align: 'center',
          dataIndex: 'supplierCode'
        },
        {
          title: '物资名称',
          align: 'center',
          dataIndex: 'companyName'
        },
        {
          title: '规格型号',
          align: 'center',
          dataIndex: 'unitAddress'
        },
        {
          title: '单位',
          align: 'center',
          dataIndex: 'bankName'
        },
        {
          title: '单价',
          align: 'center',
          dataIndex: 'bankAccount',
          scopedSlots: { customRender: 'bankAccount' }
        },
        {
          title: '数量',
          align: 'center',
          dataIndex: 'creditCode'
        },

        {
          title: '总价',
          align: 'center',
          dataIndex: 'postalCode'
        },
        {
          title: '税金',
          align: 'center',
          dataIndex: 'legalRepresentative'
        },
        {
          title: '备注',
          align: 'center',
          dataIndex: 'contactPerson',
          scopedSlots: { customRender: 'contactPerson' }
        }
      ],
      columnslist: [
        {
          title: '总量',
          align: 'center',
          dataIndex: 'total'
        },
        {
          title: '1月',
          align: 'center',
          dataIndex: 'january',
          scopedSlots: { customRender: 'january' }
        },
        {
          title: '2月',
          align: 'center',
          dataIndex: 'february',
          scopedSlots: { customRender: 'february' }
        },
        {
          title: '3月',
          align: 'center'
        },
        {
          title: '4月',
          align: 'center'
        },
        {
          title: '5月',
          align: 'center'
        },
        {
          title: '6月',
          align: 'center'
        },
        {
          title: '7月',
          align: 'center'
        },
        {
          title: '8月',
          align: 'center'
        },
        {
          title: '9月',
          align: 'center'
        },
        {
          title: '10月',
          align: 'center'
        },
        {
          title: '11月',
          align: 'center'
        },
        {
          title: '12月',
          align: 'center'
        }
      ],
      dictOptions: {},
      url: {
        searchname: '/original/originalCharge/searchname',
        hthfind: '/contractpurchase/contractPurchase/hthfind'
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
    //副表修改每月中的数据
    handleChange(value, key, column) {
      const newData = [...this.datalist]
      const target = newData.filter(item => key === item.key)[0]
      if (target) {
        target[column] = value
        target['total'] = target['january'] + target['february']
        this.datalist = newData
        //计算数量
        const newDatas = [...this.data]
        const targets = newDatas.filter(item => key === item.key)[0]
        if (targets) {
          targets['creditCode'] = target['total']
        }
      }
    },
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
          this.depratlist = res.result
        }
        if (res.code === 510) {
          this.$message.warning(res.message)
        }
      })
    }
  }
}
</script>

<style scoped>
@import '~@assets/less/common.less';
</style>