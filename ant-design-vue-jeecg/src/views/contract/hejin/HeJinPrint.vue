<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="合同编号">
              <a-select v-model="queryParam.contractNo" show-search allowClear @search="findHt" @change="findOne">
                <a-select-option
                  placeholder="请选择合同号"
                  :value="item"
                  v-for="(item, index) in contractNos"
                  :key="index"
                  >{{ item }}</a-select-option
                >
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="凭证号">
              <a-select v-model="queryParam.voucherNo" allowClear>
                <a-select-option
                  placeholder="请选择凭证号"
                  :value="item"
                  v-for="(item, index) in voucherNos"
                  :key="index"
                  >{{ item }}</a-select-option
                >
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left; overflow: hidden" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" icon="download" @click="handleExportXls('合金信息表')" style="margin-left: 8px"
                >导出</a-button
              >
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator"></div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择
        <a style="font-weight: 600">{{ selectedRowKeys.length }}</a
        >项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>
      <a-table
        ref="table"
        size="middle"
        :scroll="{ x: true }"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange }"
        class="j-table-force-nowrap"
        @change="handleTableChange"
      >
      </a-table>
    </div>
  </a-card>
</template>

<script>
import '@/assets/less/TableExpand.less'
import { mixinDevice } from '@/utils/mixin'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import { getAction, postAction } from '@/api/manage'

export default {
  name: 'ContractInformationList',
  mixins: [JeecgListMixin, mixinDevice],

  data() {
    return {
      description: '合金打印页面',
      props: {
        contrac: {
          type: String,
        },
      },
      //   /* 查询条件-请不要在queryParam中声明非字符串值的属性 */
      queryParam: {
        contractNo: this.contrac,
      },
      watch: {
        contrac(newVal, oldVal) {
          this.queryParam.contractNo = newVal
          this.queryParam.voucherNo = null
          this.findHt(newVal)
          this.findOne(newVal)
        },
      },
      disableMixinCreated: true,
      //合同号
      contractNos: [],
      //凭证号
      voucherNos: [],
      // 表头
      columns: [
        {
          title: '合同编号',
          align: 'center',
          dataIndex: 'contractNo',
        },
        {
          title: '凭证号',
          align: 'center',
          dataIndex: 'voucherNo',
        },
        {
          title: '物资名称',
          align: 'center',
          dataIndex: 'materialName',
        },
        {
          title: '供货单位',
          align: 'center',
          dataIndex: 'supplier',
        },
        {
          title: '收货单位',
          align: 'center',
          dataIndex: 'receivingUnit',
        },
        {
          title: '取样日期',
          align: 'center',
          dataIndex: 'weighingDate',
          customRender: function (text) {
            return !text ? '' : text.length > 10 ? text.substr(0, 10) : text
          },
        },
        {
          title: '检斤',
          align: 'center',
          dataIndex: 'weighing',
        },
        {
          title: '结算单价',
          align: 'center',
          dataIndex: 'settlemenPrice',
        },
        {
          title: '结算数量',
          align: 'center',
          dataIndex: 'settlementQuantity',
        },
        {
          title: '结算结果',
          align: 'center',
          dataIndex: 'settlementResults',
        },
        {
          title: '含税',
          align: 'center',
          dataIndex: 'taxIncluded',
          customRender: function (t, r, index) {
            if (t) {
              return '含税'
            } else {
              return '不含税'
            }
          },
        },
        {
          title: '税率',
          align: 'center',
          dataIndex: 'taxRate',
        },
        {
          title: '贷款',
          align: 'center',
          dataIndex: 'loan',
        },
        {
          title: '税金',
          align: 'center',
          dataIndex: 'taxes',
        },
      ],

      url: {
        list: '/hejin/hejin/selecthjdy',
        selectHtbh: '/contract/contractInformation/selectHtbh',
        selectHtpzh: '/contract/contractInformation/selectHtpzh',
        exportXlsUrl: '/hejin/hejin/exportXls',
      },
      dictOptions: {},
    }
  },
  created() {
    let hth = this.queryParam.contractNo
    if (hth === undefined) {
      hth = ''
    }
    this.findHt(hth)
    if (hth != '' && hth != null) {
      this.findOne(hth)
    }
  },

  methods: {
    initDictConfig() {},
    //获取合同号
    findHt(hth) {
      getAction(this.url.selectHtbh, { htbh: hth, httype: '合金' }).then((res) => {
        if (res.success) {
          this.contractNos = res.result
        }
        if (res.code === 510) {
          this.$message.warning(res.message)
        }
        this.loading = false
      })
    },
    findOne(hth) {
      getAction(this.url.selectHtpzh, { htbh: hth, httype: '合金' }).then((res) => {
        if (res.success) {
          this.voucherNos = res.result
        }
        if (res.code === 510) {
          this.$message.warning(res.message)
        }
        this.loading = false
      })
    },
  },
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>