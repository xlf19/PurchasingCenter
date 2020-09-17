<template>
  <j-modal
    :title="title"
    :width="width"
    :visible="visible"
    switchFullscreen
    @ok="handleOk"
    :okButtonProps="{ class:{'jee-hidden': disableSubmit} }"
    @cancel="handleCancel"
    cancelText="关闭"
  >
    <jia-quan-form ref="realForm" @ok="submitCallback" :disabled="disableSubmit" :shdwone="shdwone"></jia-quan-form>
  </j-modal>
</template>

<script>
import JiaQuanForm from './JiaQuanForm'
export default {
  name: 'JiaQuanModal',
  components: {
    JiaQuanForm,
  },
  props: {
    gudw: {
      type: String,
      required: false,
    },
  },
  data() {
    return {
      title: '',
      width: 1600,
      visible: false,
      disableSubmit: false,
      shdwone: '',
    }
  },
  methods: {
    add(wzmc,htbh) {
      this.visible = true
      this.shdwone=this.gudw
      this.$nextTick(() => {
        this.$refs.realForm.guhw(this.shdwone,wzmc,htbh);
      })
    },
    close() {
      this.$emit('close')
      this.visible = false
    },
    handleOk() {
       this.$refs.realForm.addjflist();
    },
    submitCallback() {
      this.$emit('ok')
      this.visible = false
    },
    handleCancel() {
      this.close()
    },
  },
}
</script>