<template>
  <a-card :bordered="false">
    <!-- table区域-begin -->
    <div>
      <a-tabs defaultActiveKey="1">
        <a-tab-pane tab="合同主项" key="1">
          <contract-main ref="main" @search="templatesearch" forceRender></contract-main>
        </a-tab-pane>
        <a-tab-pane tab="合同条款" key="2">
          <contract-terms ref="terms" :templateId="templateId" forceRender :cid='cid'></contract-terms>
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
      description: '添加采购合同界面',
      templateId: '',
      cid:'',
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
    initDictConfig() {},
    templatesearch(id,cid) {
      this.templateId = id
      this.cid=cid
    }
  }
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>