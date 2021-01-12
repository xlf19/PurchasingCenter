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
    <origina-form ref="realForm" @ok="submitCallback" :disabled="disableSubmit"></origina-form>
  </j-modal>
</template>

<script>
import OriginaForm from './OriginaForm'
export default {
  name: 'OriginaModal',
  components: {
    OriginaForm
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