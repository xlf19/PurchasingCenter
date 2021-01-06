<template>
  <a-card :bordered="false">
    <!-- table区域-begin -->
    <div>
      <a-tabs defaultActiveKey="1">
        <a-tab-pane tab="合同主项" key="1">
          <contract-main ref="contractmain" @search="templatesearch" :contractid="contractid"></contract-main>
        </a-tab-pane>
        <a-tab-pane tab="合同条款" key="2">
          <contract-terms ref="contractterms" :templateId="templateId" forceRender :cid="contractid"></contract-terms>
        </a-tab-pane>
      </a-tabs>
    </div>
  </a-card>
</template>

<script>
import '@/assets/less/TableExpand.less'
import ContractMain from '../chargesearch/ContractMain'
import ContractTerms from '../chargesearch/ContractTerms'
export default {
  name: 'ContractEditTab',
  components: {
    ContractMain,
    ContractTerms
  },
  data() {
    return {
      description: '编辑采购合同界面',
      contractid: '',
      templateId: '',
      dictOptions: {}
    }
  },
  created() {
    this.contractid = this.$route.query.data.id  
    this.templateId = this.$route.query.data.template_id  
  },
  computed: {},
  methods: {
    initDictConfig() {},
    templatesearch(id, cid) {
      this.templateId = id
      this.cid = cid
    }
  }
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>