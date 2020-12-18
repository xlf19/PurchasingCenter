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
    <contract-product-form ref="realForm" @ok="submitCallback" :disabled="disableSubmit"></contract-product-form>
  </j-modal>
</template>

<script>
import ContractProductForm from './ContractProductForm'
export default {
  name: 'ContractProductModal',
  components: {
    ContractProductForm
  },
  data() {
    return {
      title: '',
      width: 1250,
      visible: false,
      disableSubmit: false
    }
  },
  methods: {
    add() {
      this.visible = true
      this.$nextTick(() => {})
    },
    close() {
      this.$emit('close')
      this.visible = false
    },
    handleOk() {
      this.$refs.realForm.submitForm()
    },
    submitCallback(data) {
      this.$emit('ok',data)
      this.visible = false
    },
    handleCancel() {
      this.close()
    }
  }
}
</script>