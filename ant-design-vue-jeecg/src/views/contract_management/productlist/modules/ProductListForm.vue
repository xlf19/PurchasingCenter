<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-item label="产品id" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['productId']" placeholder="请输入产品id"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="总量" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['total']" placeholder="请输入总量"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="1月" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['january']" placeholder="请输入1月"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="2月" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['february']" placeholder="请输入2月"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="3月" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['march']" placeholder="请输入3月"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="4月" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['april']" placeholder="请输入4月"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="5月" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['may']" placeholder="请输入5月"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="6月" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['june']" placeholder="请输入6月"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="7月" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['july']" placeholder="请输入7月"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="8月" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['august']" placeholder="请输入8月"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="9月" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['september']" placeholder="请输入9月"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="10月" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['october']" placeholder="请输入10月"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="11月" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['november']" placeholder="请输入11月"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="12月" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['december']" placeholder="请输入12月"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="是否删除" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['isdelete']" placeholder="请输入是否删除" style="width: 100%"/>
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

  export default {
    name: 'ProductListForm',
    components: {
      JFormContainer,
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
          add: "/productlist/productList/add",
          edit: "/productlist/productList/edit",
          queryById: "/productlist/productList/queryById"
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
          this.form.setFieldsValue(pick(this.model,'productId','total','january','february','march','april','may','june','july','august','september','october','november','december','isdelete'))
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
        this.form.setFieldsValue(pick(row,'productId','total','january','february','march','april','may','june','july','august','september','october','november','december','isdelete'))
      },
    }
  }
</script>