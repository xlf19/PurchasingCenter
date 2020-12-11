<template>
  <a-card :bordered="false">
    <!-- table区域-begin -->
    <div>
      <a-tabs defaultActiveKey="1">
        <a-tab-pane tab="合同主项" key="1">
          <contract-main></contract-main>
        </a-tab-pane>
        <a-tab-pane tab="合同条款" key="2">
          <contract-terms></contract-terms>
        </a-tab-pane>
      </a-tabs>
    </div>
  </a-card>
</template>

<script>
import '@/assets/less/TableExpand.less'
import ContractMain from '../contractedit/ContractMain'
import ContractTerms from '../contractedit/ContractTerms'
export default {
  name: 'edittab',
  components: {
    ContractMain,
    ContractTerms
  },
  data() {
    return {
      description: '编辑采购合同界面',
      dictOptions: {}
    }
  },
  created() {},
  computed: {
    importExcelUrl: function() {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    }
  },
  methods: {
    initDictConfig() {}
  }
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>