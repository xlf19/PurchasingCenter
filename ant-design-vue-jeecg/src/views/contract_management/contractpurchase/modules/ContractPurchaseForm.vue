<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-item label="模板id" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['templateId']" placeholder="请输入模板id"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="合同编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['contractNo']" placeholder="请输入合同编号"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="供方单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['supplier']" placeholder="请输入供方单位"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="需方单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['demandSideUnit']" placeholder="请输入需方单位"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="合同标题" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['contractTitle']" placeholder="请输入合同标题"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="含税" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['taxIncludedString']" placeholder="请输入含税"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="税率" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['taxRate']" placeholder="请输入税率"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="币种" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['currency']" placeholder="请输入币种"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="签订地点" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['placeSigning']" placeholder="请输入签订地点"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="交货地点" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['tradingLocations']" placeholder="请输入交货地点"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="签订时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择签订时间" v-decorator="['signingTime']" :trigger-change="true" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="交货时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['deliveryTime']" placeholder="请输入交货时间"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="开始时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['startingTime']" placeholder="请输入开始时间"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="终止时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['stopTime']" placeholder="请输入终止时间"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="合同备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['contractNotes']" placeholder="请输入合同备注"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="其他备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['otherRemarks']" placeholder="请输入其他备注"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="文件" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['filePath']" placeholder="请输入文件"></a-input>
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
    name: 'ContractPurchaseForm',
    components: {
      JFormContainer,
      JDate,
    },
    props: {
      //流程表单data
      formData: {
        type: Object,
        default: ()=>{},
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
    data () {
      return {
        form: this.$form.createForm(this),
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        confirmLoading: false,
        validatorRules: {
        },
        url: {
          add: "/contractpurchase/contractPurchase/add",
          edit: "/contractpurchase/contractPurchase/edit",
          queryById: "/contractpurchase/contractPurchase/queryById"
        }
      }
    },
    computed: {
      formDisabled(){
        if(this.formBpm===true){
          if(this.formData.disabled===false){
            return false
          }
          return true
        }
        return this.disabled
      },
      showFlowSubmitButton(){
        if(this.formBpm===true){
          if(this.formData.disabled===false){
            return true
          }
        }
        return false
      }
    },
    created () {
      //如果是流程中表单，则需要加载流程表单data
      this.showFlowData();
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'templateId','contractNo','supplier','demandSideUnit','contractTitle','taxIncludedString','taxRate','currency','placeSigning','tradingLocations','signingTime','deliveryTime','startingTime','stopTime','contractNotes','otherRemarks','filePath'))
        })
      },
      //渲染流程表单数据
      showFlowData(){
        if(this.formBpm === true){
          let params = {id:this.formData.dataId};
          getAction(this.url.queryById,params).then((res)=>{
            if(res.success){
              this.edit (res.result);
            }
          });
        }
      },
      submitForm () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            let formData = Object.assign(this.model, values);
            console.log("表单提交数据",formData)
            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
            })
          }
         
        })
      },
      popupCallback(row){
        this.form.setFieldsValue(pick(row,'templateId','contractNo','supplier','demandSideUnit','contractTitle','taxIncludedString','taxRate','currency','placeSigning','tradingLocations','signingTime','deliveryTime','startingTime','stopTime','contractNotes','otherRemarks','filePath'))
      },
    }
  }
</script>