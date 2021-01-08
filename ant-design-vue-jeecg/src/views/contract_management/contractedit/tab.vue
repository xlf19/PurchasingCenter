<template>
  <a-card :bordered="false">
    <!-- table区域-begin -->
    <div>
      <a-tabs>
        <a-tab-pane tab="合同主项" key="1" :forceRender="true">
          <contract-main
            ref="main"
            :whether="whether"
            @search="templatesearch"
            @addtemplat="addtemplat"
          ></contract-main>
        </a-tab-pane>
        <a-tab-pane tab="合同条款" key="2" :forceRender="true">
          <contract-terms ref="contractTerms" :templateId="templateId" :cid="cid"></contract-terms>
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
      //模板id
      templateId: '',
      //合同id
      cid: '',
      //是否保存
      whether: false,
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
    templatesearch(id, cid,whether) {
      this.templateId = id
      this.cid = cid
      this.whether=whether
    },
    addtemplat(whether) {
      this.whether = whether
      this.$refs.contractTerms.addlist()
    }
  }
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>