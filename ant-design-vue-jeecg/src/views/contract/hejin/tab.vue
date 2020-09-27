<template>
  <a-card :bordered="false">
    <!-- table区域-begin -->
    <div>
      <a-tabs defaultActiveKey="1">
        <a-tab-pane tab="添加凭证号" key="1">
          <he-jin-list ref="shengtie" @gethtbh="gethtbh"></he-jin-list>
        </a-tab-pane>
        <a-tab-pane tab="结算公式维护" key="2">
          <first-test ref="firstTest" :contrac="contrac"></first-test>
        </a-tab-pane>
        <a-tab-pane tab="结算" key="3">
          <set-account ref="setaccount" :contracttype="contracttype"></set-account>
        </a-tab-pane>
         <a-tab-pane tab="打印" key="4">
          <he-jin-print ref="hejinprint" :contrac="contrac"></he-jin-print>
        </a-tab-pane>
      </a-tabs>
    </div>
  </a-card>
</template>

<script>
import '@/assets/less/TableExpand.less'
import HeJinList from '../hejin/HeJinList'
import firstTest from '../htgs/firstTest'
import setAccount from '../settlement/setAccount'
import HeJinPrint from '../hejin/HeJinPrint'

export default {
  name: 'hejin',

  components: {
    firstTest,
    HeJinList,
    setAccount,
    HeJinPrint,
  },
  data() {
    return {
      description: '合金结算管理页面',
      dictOptions: {},
      contrac: '',
      contracttype: '合金',
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