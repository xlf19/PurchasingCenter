<!--
 * @descript: 
 * @Date: 2020-09-09 16:06:08
 * @LastEditTime: 2020-09-11 10:04:38
 * @version: 0.0.1
-->
<template>
  <j-modal
    :title="title"
    :width="width"
    :visible="visible"
    switchFullscreen
    @ok="handleOk"
    :okButtonProps="{ class:{'jee-hidden': disableSubmit} }"
    @cancel="handleCancel"
    cancelText="关闭">
    <sys-hetonggongshi-form ref="realForm" @ok="submitCallback($event)" :disabled="disableSubmit"></sys-hetonggongshi-form>
  </j-modal>
</template>

<script>

  import SysHetonggongshiForm from './SysHetonggongshiForm'
  export default {
    name: 'SysHetonggongshiModal',
    components: {
      SysHetonggongshiForm
    },
    data () {
      return {
        title:'',
        width:800,
        visible: false,
        disableSubmit: false
      }
    },
    methods: {
      add () {
        this.visible=true
        this.$nextTick(()=>{
          this.$refs.realForm.add();
        })
      },
      edit (record) {
        this.visible=true
        this.$nextTick(()=>{
          this.$refs.realForm.edit(record);
        })
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleOk () {
        this.$refs.realForm.submitForm();
      },
      submitCallback(value){
        this.$emit('ok',value);
        this.visible = false;
      },
      handleCancel () {
        this.close()
      }
    }
  }
</script>