<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-item label="取样日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择取样日期" v-decorator="['qyDate']" :trigger-change="true" style="width: 100%"/>
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
            <a-form-item label="需方单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['receivingunit']" placeholder="请输入需方单位"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="检验量" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['weighing']" placeholder="请输入检验量" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="TFE" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['tfe']" placeholder="请输入TFE" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="SIO2" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['sio2']" placeholder="请输入SIO2" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="S" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['ss']" placeholder="请输入S" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="P" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['pp']" placeholder="请输入P" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="AL2O3" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['al2o3']" placeholder="请输入AL2O3" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="MnO" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['mno']" placeholder="请输入MnO" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="TiO2" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['tio2']" placeholder="请输入TiO2" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="CAO" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['cao']" placeholder="请输入CAO" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="Cr" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['cr']" placeholder="请输入Cr" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="Cu" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['cu']" placeholder="请输入Cu" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="FEO" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['feo']" placeholder="请输入FEO" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="MGO" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['mgo']" placeholder="请输入MGO" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="Pb" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['pb']" placeholder="请输入Pb" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="As" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['as1']" placeholder="请输入As" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="Zn" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['zn']" placeholder="请输入Zn" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="K2O" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['k2o']" placeholder="请输入K2O" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="H2O" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['h2o']" placeholder="请输入H2O" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="含粉" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['containsPowder']" placeholder="请输入含粉" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="抗压" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['compressive']" placeholder="请输入抗压" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="粒度" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['granularity']" placeholder="请输入粒度" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="md" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['md']" placeholder="请输入md" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="凭证号标识" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['voucherId']" placeholder="请输入凭证号标识" style="width: 100%"/>
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
          add: "/quality_date/qualityDate/add",
          edit: "/quality_date/qualityDate/edit",
          queryById: "/quality_date/qualityDate/queryById"
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
          this.form.setFieldsValue(pick(this.model,'qyDate','materialName','supplier','receivingunit','weighing','tfe','sio2','ss','pp','al2o3','mno','tio2','cao','cr','cu','feo','mgo','pb','as1','zn','k2o','h2o','containsPowder','compressive','granularity','md','voucherId'))
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
        this.form.setFieldsValue(pick(row,'qyDate','materialName','supplier','receivingunit','weighing','tfe','sio2','ss','pp','al2o3','mno','tio2','cao','cr','cu','feo','mgo','pb','as1','zn','k2o','h2o','containsPowder','compressive','granularity','md','voucherId'))
      },
    }
  }
</script>