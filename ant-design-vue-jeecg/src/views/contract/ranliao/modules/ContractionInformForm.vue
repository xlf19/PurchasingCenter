<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="8">
            <a-form-item label="合同编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['HeTongBianHao']" placeholder="请输入合同编号" disabled></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="凭证号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['voucherno']" placeholder="请输入凭证号" disabled />
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="物资编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['WZCode']" placeholder="请输入物资编码" disabled></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="物资名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['WZName']" placeholder="请输入物资名称" disabled></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="供货单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['DanWeiName']" placeholder="请输入供货单位" disabled></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="收货单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['receiving']" placeholder="请输入收货单位" disabled></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="税率" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number
                v-decorator="['ShuiLv']"
                placeholder="请输入税率"
                style="width: 100%"
                disabled
              />
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="含税" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-radio-group v-decorator="['HanShuiBiaoJi']" placeholder="请输入含税" disabled>
                <a-radio :value="true">是</a-radio>
                <a-radio :value="false">否</a-radio>
              </a-radio-group>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="检验量" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number
                v-decorator="['weighing']"
                placeholder="请输入检验量"
                :step="0.001"
                style="width: 100%"
              />
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="合同单价" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number
                v-decorator="['WZDanJia']"
                placeholder="请输入合同单价"
                :step="0.001"
                style="width: 100%"
              />
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="Ad" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number
                v-decorator="['Ad']"
                placeholder="请输入Ad"
                style="width: 100%"
                :step="0.001"
              />
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="Vdaf" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number
                v-decorator="['Vdaf']"
                placeholder="请输入Vdaf"
                style="width: 100%"
                :step="0.001"
              />
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="Std" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number
                v-decorator="['Std']"
                placeholder="请输入Std"
                style="width: 100%"
                :step="0.001"
              />
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="H2O" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number
                v-decorator="['H2O']"
                placeholder="请输入H2O"
                style="width: 100%"
                :step="0.001"
              />
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="Xmm" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number
                v-decorator="['Xmm']"
                placeholder="请输入Xmm"
                style="width: 100%"
                :step="0.001"
              />
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="Ymm" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number
                v-decorator="['Ymm']"
                placeholder="请输入Ymm"
                style="width: 100%"
                :step="0.001"
              />
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="Q" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number
                v-decorator="['Q']"
                placeholder="请输入Q"
                style="width: 100%"
                :step="0.001"
              />
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="粘度" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number
                v-decorator="['midu']"
                placeholder="请输入粘度"
                style="width: 100%"
                :step="0.001"
              />
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="M10" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number
                v-decorator="['M10']"
                placeholder="请输入M10"
                style="width: 100%"
                :step="0.001"
              />
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="M25" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number
                v-decorator="['M25']"
                placeholder="请输入TFE"
                style="width: 100%"
                :step="0.001"
              />
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="总运杂费" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number
                v-decorator="['zyzf']"
                placeholder="请输入总运杂费"
                style="width: 100%"
                :step="0.001"
              />
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="贷款" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number
                v-decorator="['daikuan']"
                placeholder="请输入贷款"
                style="width: 100%"
                :step="0.001"
              />
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="税金" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number
                v-decorator="['shuijin']"
                placeholder="请输入税金"
                style="width: 100%"
                :step="0.001"
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
  name: 'QualityDateForm',
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
        addjsd: '/ranliao/ranliao/addjsd',
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
    edit(record) {
      this.form.resetFields()
      this.model = Object.assign({}, record)
      this.visible = true
      this.$nextTick(() => {
        this.form.setFieldsValue(
          pick(
            this.model,
            'id',
            'HeTongBianHao',
            'voucherno',
            'WZCode',
            'WZName',
            'DanWeiName',
            'receiving',
            'weighing',
            'WZDanJia',
            'ShuiLv',
            'HanShuiBiaoJi',
            'Ad',
            'Vdaf',
            'Std',
            'H2O',
            'Xmm',
            'Ymm',
            'Q',
            'midu',
            'M10',
            'M25',
            'zyzf',
            'daikuan',
            'shuijin'
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
          let formData = Object.assign(this.model, values)
          console.log('表单提交数据', formData)
          httpAction(this.url.addjsd, formData, 'post')
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
          'id',
          'HeTongBianHao',
          'voucherno',
          'WZCode',
          'WZName',
          'DanWeiName',
          'receiving',
          'weighing',
          'WZDanJia',
          'ShuiLv',
          'HanShuiBiaoJi',
          'Ad',
          'Vdaf',
          'Std',
          'H2O',
          'Xmm',
          'Ymm',
          'Q',
          'midu',
          'M10',
          'M25',
          'zyzf',
          'daikuan',
          'shuijin'
        )
      )
    },
  },
}
</script>