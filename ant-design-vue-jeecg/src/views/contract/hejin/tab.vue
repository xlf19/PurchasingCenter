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
        <a-tab-pane tab="结算" key="3" >56</a-tab-pane>
      </a-tabs>
    </div>
  </a-card>
</template>

<script>
import '@/assets/less/TableExpand.less'
import HeJinList from '../hejin/HeJinList'
import firstTest from '../htgs/firstTest'

export default {
  name: 'shengtie',

  components: {
    firstTest,
    HeJinList,
  },
  data() {
    return {
      description: '生铁结算管理页面',
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