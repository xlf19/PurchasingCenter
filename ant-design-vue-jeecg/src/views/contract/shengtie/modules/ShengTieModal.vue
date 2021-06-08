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
    <sheng-tie-form ref="realForm" @ok="submitCallback" :disabled="disableSubmit" ></sheng-tie-form>
  </j-modal>
</template>

<script>
import ShengTieForm from './ShengTieForm'
export default {
  name: 'ShengTieModal',
  components: {
    ShengTieForm,
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
    }
  },
  methods: {
    add(contractNo,shdw) {
      this.visible = true
      this.$nextTick(() => {
        this.$refs.realForm.guhw(contractNo,shdw);
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