<template>
  <a-card :bordered="false">
    <!-- table区域-begin -->
    <div>
      <a-tabs defaultActiveKey="1">
        <a-tab-pane tab="添加凭证号" key="1">
          <fei-gang-list ref="naicai" @gethtbh="gethtbh"></fei-gang-list>
        </a-tab-pane>
        <a-tab-pane tab="打印" key="2">
          <fei-gang-print ref="ranliaoprint" :contrac="contrac"></fei-gang-print>
        </a-tab-pane>
      </a-tabs>
    </div>
  </a-card>
</template>

<script>
import '@/assets/less/TableExpand.less'
import FeiGangList from '../feigang/FeiGangList'
import FeiGangPrint from '../feigang/FeiGangPrint'


export default {
  name: 'fuliao',
  components: {
    FeiGangList,
    FeiGangPrint,
  },
  data() {
    return {
      description: '废钢结算管理页面',
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