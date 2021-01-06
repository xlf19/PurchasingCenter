<template>
  <j-modal
    :title="title"
    :width="width"
    :visible="visible"
    switchFullscreen
    @ok="handleOk"
    :okButtonProps="{ class: { 'jee-hidden': disableSubmit } }"
    @cancel="handleCancel"
    cancelText="关闭"
  >
    <template-form ref="realForm" @ok="submitCallback" :disabled="disableSubmit"></template-form>
  </j-modal>
</template>

<script>
import TemplateForm from './TemplateForm'
export default {
  name: 'TemplateModal',
  components: {
    TemplateForm
  },
  data() {
    return {
      title: '',
      width: 800,
      visible: false,
      disableSubmit: false
    }
  },
  methods: {
    add (termsNumber) {
        this.visible=true
        this.$nextTick(()=>{
          this.$refs.realForm.add(termsNumber);
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
      submitCallback(id){
        this.$emit('ok',id);
        this.visible = false;
      },
      handleCancel () {
        this.close()
      }
    }
}
</script>