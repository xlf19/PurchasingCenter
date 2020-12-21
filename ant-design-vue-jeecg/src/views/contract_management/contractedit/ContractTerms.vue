<template>
  <div>
    <div class="table-page-search-wrapper">
      <!-- 操作按钮区域 -->
      <div class="table-operator">
        <a-button type="primary" icon="save" @click="addlist">保存条款</a-button>
        <a-button type="primary" icon="save">保存个人模板</a-button>
      </div>
      <a-card :bordered="false" title="条款明细">
        <div v-for="(list, index) in list" :key="index">
          {{list.title}}
          <br />
          <a-input :value="list.value"></a-input>
        </div>
      </a-card>
    </div>
  </div>
</template>

<script>
import '@/assets/less/TableExpand.less'
export default {
  name: 'ContractTerms',
  data() {
    return {
      list: [
        { title: '一、合同效力', value: '123456' },
        { title: '二、违约责任', value: '123456' }
      ]
    }
  },
  methods: {
    addlist() {
      console.log(this.list)
    }
  }
}
</script>

<style scoped>
@import '~@assets/less/common.less';

</style>