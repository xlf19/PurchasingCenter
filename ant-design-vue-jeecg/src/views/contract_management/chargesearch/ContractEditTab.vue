<template>
  <a-card :bordered="false">
    <!-- table区域-begin -->
    <div>
      <a-tabs defaultActiveKey="1">
        <a-tab-pane tab="合同主项" key="1" >
          <contract-main ref="contractmain" :contractid="contractid" ></contract-main>
        </a-tab-pane>
        <a-tab-pane tab="合同条款" key="2">
          <contract-terms ref="contractterms"></contract-terms>
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
      dictOptions: {}
    }
  },
  created() {
    this.contractid = this.$route.query.data.id
  },
  computed: {},
  methods: {
    initDictConfig() {}
  }
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>