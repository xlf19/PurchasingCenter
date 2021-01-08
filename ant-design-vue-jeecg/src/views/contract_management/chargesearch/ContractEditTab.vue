<template>
  <a-card :bordered="false">
    <!-- table区域-begin -->
    <div>
      <a-tabs>
        <a-tab-pane tab="合同主项" key="1" :forceRender="true">
          <contract-main
            ref="contractmain"
            @search="templatesearch"
            :whether="whether"
            :contractid="contractid"
            @addtemplat="addtemplat"
          ></contract-main>
        </a-tab-pane>
        <a-tab-pane tab="合同条款" key="2" :forceRender="true">
          <contract-terms ref="contractterms" :templateId="templateId" :cid="contractid"></contract-terms>
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
      //合同id
      contractid: '',
      //模板id
      templateId: '',
      //是否保存
      whether: false,
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
    templatesearch(id, cid,whether) {
      this.templateId = id
      this.cid = cid
      this.whether=whether
    },
    addtemplat(whether) {
      this.whether = whether
      this.$refs.contractterms.addlist()
    }
  }
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>