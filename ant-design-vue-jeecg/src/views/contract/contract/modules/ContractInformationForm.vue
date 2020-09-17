<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-item label="合同编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['contractNo']" placeholder="请输入合同编号"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="凭证号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['voucherNo']" placeholder="请输入凭证号" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="合同类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['contractType']" placeholder="请输入合同类型"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="物资编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['materialCode']" placeholder="请输入物资编码"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="物资名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['materialName']" placeholder="请输入物资名称"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="供货单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['supplier']" placeholder="请输入供货单位"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="收货单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['receivingUnit']" placeholder="请输入收货单位"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="检斤" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['weighing']" placeholder="请输入检斤" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="合同单价" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['contractPrice']" placeholder="请输入合同单价" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="结算结果" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['settlementResults']" placeholder="请输入结算结果" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="结算单价" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['settlemenPrice']" placeholder="请输入结算单价" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="结算数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['settlementQuantity']" placeholder="请输入结算数量" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="贷款" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['loan']" placeholder="请输入贷款" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="税金" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['taxes']" placeholder="请输入税金" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="含税" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['taxIncluded']" placeholder="请输入含税" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="税率" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['taxRate']" placeholder="请输入税率" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['remarks']" placeholder="请输入备注"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="过磅日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择过磅日期" v-decorator="['weighingDate']" :trigger-change="true" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="结算人" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['clearingHouse']" placeholder="请输入结算人"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="结算日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择结算日期" v-decorator="['settlementDate']" :trigger-change="true" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="结算标识" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['settlementIdentification']" placeholder="请输入结算标识" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="是否删除" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['isDelete']" placeholder="请输入是否删除" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="运费" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['shipping']" placeholder="请输入运费" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="点收" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['accept']" placeholder="请输入点收"></a-input>
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
          add: "/contract/contractInformation/add",
          edit: "/contract/contractInformation/edit",
          queryById: "/contract/contractInformation/queryById"
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
          this.form.setFieldsValue(pick(this.model,'contractNo','voucherNo','contractType','materialCode','materialName','supplier','receivingUnit','weighing','contractPrice','settlementResults','settlemenPrice','settlementQuantity','loan','taxes','taxIncluded','taxRate','remarks','weighingDate','clearingHouse','settlementDate','settlementIdentification','isDelete','shipping','accept'))
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
        this.form.setFieldsValue(pick(row,'contractNo','voucherNo','contractType','materialCode','materialName','supplier','receivingUnit','weighing','contractPrice','settlementResults','settlemenPrice','settlementQuantity','loan','taxes','taxIncluded','taxRate','remarks','weighingDate','clearingHouse','settlementDate','settlementIdentification','isDelete','shipping','accept'))
      },
    }
  }
</script>