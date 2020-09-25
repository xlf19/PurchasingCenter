<template>
  <a-card :bordered="false">
    <!-- table区域-begin -->
    <div>
      <a-tabs defaultActiveKey="1">
        <a-tab-pane tab="添加凭证号" key="1">
          <nai-cai-list ref="naicai" @gethtbh="gethtbh"></nai-cai-list>
        </a-tab-pane>
        <a-tab-pane tab="结算公式维护" key="2">
          <first-test ref="firstTest" :contrac="contrac"></first-test>
        </a-tab-pane>
        <a-tab-pane tab="结算" key="3">
          <set-account ref="setaccount"></set-account>
        </a-tab-pane>
      </a-tabs>
    </div>
  </a-card>
</template>

<script>
import '@/assets/less/TableExpand.less'
import NaiCaiList from '../naicai/NaiCaiList'
import firstTest from '../htgs/firstTest'
import setAccount from '../settlement/setAccount'
export default {
  name: 'fuliao',
  components: {
    firstTest,
    NaiCaiList,
    setAccount,
  },
  data() {
    return {
      description: '辅料结算管理页面',
      dictOptions: {},
      contrac: '',
    }
  },
  created() {},
  computed: {
    importExcelUrl: function () {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    },
  },
  methods: {
    initDictConfig() {},
    gethtbh(data) {
      this.contrac = data
    },
  },
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>