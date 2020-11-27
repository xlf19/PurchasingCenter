<template>
  <a-card :bordered="false">
    <!-- table区域-begin -->
    <div>
      <a-tabs defaultActiveKey="1">
        <a-tab-pane tab="添加凭证号" key="1">
          <fu-liao-list ref="fuliao" @gethtbh="gethtbh"></fu-liao-list>
        </a-tab-pane>
        <a-tab-pane tab="结算公式维护" key="2">
          <first-test ref="firstTest" :contrac="contrac" :contracttype="contracttype"></first-test>
        </a-tab-pane>
        <a-tab-pane tab="结算" key="3">
          <set-account ref="setaccount" :contrac="contrac" :contracttype="contracttype"></set-account>
        </a-tab-pane>
        <a-tab-pane tab="打印" key="4">
          <fu-liao-print ref="fuliaoprint" :contrac="contrac"></fu-liao-print>
        </a-tab-pane>
      </a-tabs>
    </div>
  </a-card>
</template>

<script>
import '@/assets/less/TableExpand.less'
import FuLiaoList from '../fuliao/FuLiaoList'
import firstTest from '../htgs/firstTest'
import setAccount from '../settlement/setAccount'
import FuLiaoPrint from '../fuliao/FuLiaoPrint'
export default {
  name: 'fuliao',
  components: {
    firstTest,
    FuLiaoList,
    setAccount,
    FuLiaoPrint
  },
  data() {
    return {
      description: '辅料结算管理页面',
      dictOptions: {},
      contrac: '',
      contracttype: '辅料'
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
    gethtbh(data) {
      this.contrac = data
    }
  }
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>