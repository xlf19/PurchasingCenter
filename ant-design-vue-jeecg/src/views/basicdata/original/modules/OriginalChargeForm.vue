<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="8">
            <a-form-item label="所在省级" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-select
                v-decorator="['provincial', validatorRules.provincial]"
                placeholder="请选择所在省级"
                show-search
                allowClear
                @change="findshi"
              >
                <a-select-option :key="index" :value="item.shengCode" v-for="(item, index) in shengbimas">{{
                  item.shengName
                }}</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="所在市级" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-select
                v-decorator="['cityLevel', validatorRules.cityLevel]"
                placeholder="请选择所在市级"
                show-search
                allowClear
              >
                <a-select-option :key="index" :value="item.shiCode" v-for="(item, index) in shibimas">{{
                  item.shiName
                }}</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="供应商编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                v-decorator="['supplierCode', validatorRules.supplierCode]"
                placeholder="请输入供应商编码"
                style="width:57%"
              ></a-input>
              <a-button type="primary" icon="plus" @click="supplierCode">添加编码</a-button>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="单位名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['companyName', validatorRules.companyName]" placeholder="请输入单位名称"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="名称首字母" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['nameInitials']" placeholder="请输入名称首字母"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="联系人" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                v-decorator="['contactPerson', validatorRules.contactPerson]"
                placeholder="请输入联系人"
              ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="邮政编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['postalCode', validatorRules.postalCode]" placeholder="请输入邮政编码"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="单位地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['unitAddress', validatorRules.unitAddress]" placeholder="请输入单位地址"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="电子邮箱" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['email', validatorRules.email]" placeholder="请输入电子邮箱"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="银行名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['bankName', validatorRules.bankName]" placeholder="请输入银行名称"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="银行账号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['bankAccount', validatorRules.bankAccount]" placeholder="请输入银行账号"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="信用代码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['creditCode', validatorRules.creditCode]" placeholder="请输入信用代码"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="法人代表" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                v-decorator="['legalRepresentative', validatorRules.legalRepresentative]"
                placeholder="请输入法人代表"
              ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="联系电话" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                v-decorator="['contactNumber', validatorRules.contactNumber]"
                placeholder="请输入联系电话"
              ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="联系传真" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['contactFax', validatorRules.contactFax]" placeholder="请输入联系传真"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="注册资金" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number
                v-decorator="['registeredCapital', validatorRules.registeredCapital]"
                placeholder="请输入注册资金"
                style="width: 100%"
              />
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="成立日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date
                placeholder="请选择成立日期"
                v-decorator="['establishmentDate']"
                :trigger-change="true"
                style="width: 100%"
              />
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="准入日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date
                placeholder="请选择准入日期"
                v-decorator="['accessDate']"
                :trigger-change="true"
                style="width: 100%"
              />
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="企业性质" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-radio-group v-decorator="['enterpriseNature', { initialValue: 1 }]">
                <a-radio :value="1">
                  国企
                </a-radio>
                <a-radio :value="2">
                  民营
                </a-radio>
              </a-radio-group>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="供方状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-radio-group v-decorator="['supplierStatus', { initialValue: 1 }]">
                <a-radio :value="1">
                  正常
                </a-radio>
                <a-radio :value="2">
                  异常
                </a-radio>
              </a-radio-group>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="临时供应商" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-radio-group v-decorator="['temporarySupplier', { initialValue: 1 }]">
                <a-radio :value="1">
                  是
                </a-radio>
                <a-radio :value="2">
                  否
                </a-radio>
              </a-radio-group>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="内部供应商" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-radio-group v-decorator="['internalSupplier', { initialValue: 1 }]">
                <a-radio :value="1">
                  是
                </a-radio>
                <a-radio :value="2">
                  否
                </a-radio>
              </a-radio-group>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="公司类别" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-radio-group v-decorator="['companyCategory', { initialValue: 1 }]">
                <a-radio :value="1">
                  供方单位
                </a-radio>
                <a-radio :value="2">
                  需方单位
                </a-radio>
              </a-radio-group>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="经营范围">
              <a-textarea
                v-decorator="['businessScope', validatorRules.businessScope]"
                placeholder="请输入经营范围"
                :rows="4"
              />
            </a-form-item>
          </a-col>
          <a-col v-if="showFlowSubmitButton" :span="24" style="text-align: center">
            <a-button @click="submitForm">提 交</a-button>
          </a-col>
        </a-row>
      </a-form>
    </j-form-container>
  </a-spin>
</template>

<script>
import { httpAction, getAction } from '@/api/manage'
import pick from 'lodash.pick'
import { validateDuplicateValue } from '@/utils/util'
import JFormContainer from '@/components/jeecg/JFormContainer'
import JDate from '@/components/jeecg/JDate'

export default {
  name: 'OriginalChargeForm',
  components: {
    JFormContainer,
    JDate
  },
  props: {
    //流程表单data
    formData: {
      type: Object,
      default: () => {},
      required: false
    },
    //表单模式：true流程表单 false普通表单
    formBpm: {
      type: Boolean,
      default: false,
      required: false
    },
    //表单禁用
    disabled: {
      type: Boolean,
      default: false,
      required: false
    }
  },
  data() {
    return {
      form: this.$form.createForm(this),
      model: {},
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 }
      },
      confirmLoading: false,

      validatorRules: {
        provincial: { rules: [{ required: true, message: '请选择所在省级!' }] },
        cityLevel: { rules: [{ required: true, message: '请选择所在市级!' }] },
        supplierCode: { rules: [{ required: true, message: '请添加供应商编码!' }] },
        companyName: { rules: [{ required: true, message: '请输入单位名称!' }] },
        contactPerson: { rules: [{ required: true, message: '请输入联系人!' }] },
        postalCode: { rules: [{ required: true, message: '请输入邮政编码!' },{max:6, message: '邮政编码位数应小于6位数!'}] },
        unitAddress: { rules: [{ required: true, message: '请输入单位地址!' }] },
        email: { rules: [{ required: true, message: '请输入电子邮箱!' },{pattern:  /^\w[-\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\.)+[A-Za-z]{2,14}/,message:'电子邮箱格式不对！'}] },
        bankName: { rules: [{ required: true, message: '请输入银行名称!' }] },
        bankAccount: { rules: [{ required: true, message: '请输入银行账号!' }] },
        creditCode: { rules: [{ required: true, message: '请输入信用代码!' }] },
        legalRepresentative: { rules: [{ required: true, message: '请输入法人代表!' }] },
        contactNumber: { rules: [{ required: true, message: '请输入联系电话!' }] },
        contactFax: { rules: [{ required: true, message: '请输入联系传真!' }] },
        registeredCapital: { rules: [{ required: true, message: '请输入注册资金!' }] },
        businessScope: { rules: [{ required: true, message: '请输入经营范围!' }] }
      },
      shengbimas: [],
      shibimas: [],
      url: {
        add: '/original/originalCharge/add',
        edit: '/original/originalCharge/edit',
        queryById: '/original/originalCharge/queryById',
        shengbima: '/original/originalCharge/shengbima',
        shibima: '/original/originalCharge/shibima',
        supplierCode: '/original/originalCharge/supplierCode'
      }
    }
  },
  computed: {
    formDisabled() {
      if (this.formBpm === true) {
        if (this.formData.disabled === false) {
          return false
        }
        return true
      }
      return this.disabled
    },
    showFlowSubmitButton() {
      if (this.formBpm === true) {
        if (this.formData.disabled === false) {
          return true
        }
      }
      return false
    }
  },
  created() {
    //如果是流程中表单，则需要加载流程表单data
    this.showFlowData()
    this.shengbima()
  },
  methods: {
    //获取所有省级信息
    shengbima() {
      getAction(this.url.shengbima).then(res => {
        if (res.success) {
          this.shengbimas = res.result
        }
        if (res.code === 510) {
          this.$message.warning(res.message)
        }
      })
    },
    //根据省级获取所有市级信息
    findshi(shengcode) {
      this.form.setFieldsValue({
        cityLevel: ''
      })
      getAction(this.url.shibima, { code: shengcode }).then(res => {
        if (res.success) {
          this.shibimas = res.result
        }
        if (res.code === 510) {
          this.$message.warning(res.message)
        }
      })
    },
    //生成供应商编码
    supplierCode() {
      let scode = this.form.getFieldValue('provincial')
      if (scode === '' || scode === null || scode === undefined || scode === NaN) {
        this.$message.warning('所在省级不能为空')
        return
      }
      let ccdoe = this.form.getFieldValue('cityLevel')
      if (ccdoe === '' || ccdoe === null || ccdoe === undefined || ccdoe === NaN) {
        this.$message.warning('所在市级不能为空')
        return
      }
      let code = scode + ccdoe
      getAction(this.url.supplierCode, { code: code }).then(res => {
        if (res.success) {
          this.form.setFieldsValue({
            supplierCode: res.message
          })
        }
        if (res.code === 510) {
          this.$message.warning(res.message)
        }
      })
    },
    add() {
      this.edit({})
    },
    edit(record) {
      this.form.resetFields()
      this.model = Object.assign({}, record)
      this.visible = true
      this.$nextTick(() => {
        this.form.setFieldsValue(
          pick(
            this.model,
            'provincial',
            'cityLevel',
            'supplierCode',
            'companyName',
            'nameInitials',
            'contactPerson',
            'postalCode',
            'unitAddress',
            'email',
            'bankName',
            'bankAccount',
            'creditCode',
            'legalRepresentative',
            'contactNumber',
            'contactFax',
            'enterpriseNature',
            'supplierStatus',
            'temporarySupplier',
            'internalSupplier',
            'establishmentDate',
            'accessDate',
            'registeredCapital',
            'businessScope',
            'companyCategory'
          )
        )
      })
    },
    //渲染流程表单数据
    showFlowData() {
      if (this.formBpm === true) {
        let params = { id: this.formData.dataId }
        getAction(this.url.queryById, params).then(res => {
          if (res.success) {
            this.edit(res.result)
          }
        })
      }
    },
    submitForm() {
      const that = this
      // 触发表单验证
      this.form.validateFields((err, values) => {
        if (!err) {
          that.confirmLoading = true
          let httpurl = ''
          let method = ''
          if (!this.model.id) {
            httpurl += this.url.add
            method = 'post'
          } else {
            httpurl += this.url.edit
            method = 'put'
          }
          let formData = Object.assign(this.model, values)
          console.log('表单提交数据', formData)
          httpAction(httpurl, formData, method)
            .then(res => {
              if (res.success) {
                that.$message.success(res.message)
                that.$emit('ok')
              } else {
                that.$message.warning(res.message)
              }
            })
            .finally(() => {
              that.confirmLoading = false
            })
        }
      })
    },
    popupCallback(row) {
      this.form.setFieldsValue(
        pick(
          row,
          'provincial',
          'cityLevel',
          'supplierCode',
          'companyName',
          'nameInitials',
          'contactPerson',
          'postalCode',
          'unitAddress',
          'email',
          'bankName',
          'bankAccount',
          'creditCode',
          'legalRepresentative',
          'contactNumber',
          'contactFax',
          'enterpriseNature',
          'supplierStatus',
          'temporarySupplier',
          'internalSupplier',
          'establishmentDate',
          'accessDate',
          'registeredCapital',
          'businessScope',
          'companyCategory'
        )
      )
    }
  }
}
</script>