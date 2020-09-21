<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="12">
            <a-form-item label="合同编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['contractNo']" placeholder="请输入合同编号" disabled></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="凭证号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number
                v-decorator="['voucherNo']"
                placeholder="请输入凭证号"
                style="width: 100%"
                disabled
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="物资编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['materialCode']" placeholder="请输入物资编码" disabled></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="物资名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['materialName']" placeholder="请输入物资名称" disabled></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="供货单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['supplier']" placeholder="请输入供货单位" disabled></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="收货单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['receivingUnit']" placeholder="请输入收货单位" disabled></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="点收日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date
                placeholder="请选择点收日期"
                v-decorator="['acceptTime']"
                :trigger-change="true"
                style="width: 100%"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="点收日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date
                placeholder="过磅日期"
                v-decorator="['weighTime']"
                :trigger-change="true"
                style="width: 100%"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="结算净重" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number
                v-decorator="['settlementQuantity']"
                placeholder="请输入结算净重"
                :step="0.001"
                style="width: 100%"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="合同单价" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number
                v-decorator="['contractPrice']"
                placeholder="请输入合同单价"
                :step="0.001"
                style="width: 100%"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['remarks']" placeholder="请输入备注"></a-input>
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
  name: 'ContractInformationForm',
  components: {
    JFormContainer,
    JDate,
  },
  props: {
    //流程表单data
    formData: {
      type: Object,
      default: () => {},
      required: false,
    },
    //表单模式：true流程表单 false普通表单
    formBpm: {
      type: Boolean,
      default: false,
      required: false,
    },
    //表单禁用
    disabled: {
      type: Boolean,
      default: false,
      required: false,
    },
  },
  data() {
    return {
      form: this.$form.createForm(this),
      model: {},
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 },
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 },
      },
      confirmLoading: false,
      validatorRules: {},
      url: {
        add: '/contract/contractInformation/add',
        edit: '/feigang/feigang/edit',
        queryById: '/feigang/feigang/queryById',
      },
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
    },
  },
  created() {
    //如果是流程中表单，则需要加载流程表单data
    this.showFlowData()
  },
  methods: {
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
            'contractNo',
            'voucherNo',
            'materialCode',
            'materialName',
            'supplier',
            'receivingUnit',
            'acceptTime',
            'contractPrice',
            'settlementQuantity',
            'taxIncluded',
            'taxRate',
            'remarks',
            'weighTime'
          )
        )
      })
    },
    //渲染流程表单数据
    showFlowData() {
      if (this.formBpm === true) {
        let params = { id: this.formData.dataId }
        getAction(this.url.queryById, params).then((res) => {
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
            .then((res) => {
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
          'contractNo',
          'voucherNo',
          'materialCode',
          'materialName',
          'supplier',
          'receivingUnit',
          'acceptTime',
          'contractPrice',
          'settlementQuantity',
          'taxIncluded',
          'taxRate',
          'remarks',
          'weighTime'
        )
      )
    },
  },
}
</script>