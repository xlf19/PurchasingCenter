<template>
  <a-card :bordered="false">
    <!-- table区域-begin -->
    <div>
      <a-tabs defaultActiveKey="1">
        <a-tab-pane tab="添加凭证号" key="1">
          <jing-fen ref="shengtie" @gethtbh="gethtbh"></jing-fen>
        </a-tab-pane>
        <a-tab-pane tab="结算公式维护" key="2">
          <first-test ref="firstTest" :contrac="contrac"></first-test>
        </a-tab-pane>
        <a-tab-pane tab="结算" key="3">
          <set-account ref="setaccount"></set-account>
        </a-tab-pane>
        <a-tab-pane tab="打印" key="4">
          <jin-fen-print ref="jinfenprint" :contrac="contrac"></jin-fen-print>
        </a-tab-pane>
      </a-tabs>
    </div>
  </a-card>
</template>

<script>
import '@/assets/less/TableExpand.less'
import jingFen from '../jingfen/Jingfen'
import firstTest from '../htgs/firstTest'
import setAccount from '../settlement/setAccount'
import JinFenPrint from '../jingfen/JinFenPrint'

export default {
  name: 'jinfen',

  components: {
    firstTest,
    jingFen,
    setAccount,
    JinFenPrint
  },
  data() {
    return {
      description: '精粉结算管理页面',
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