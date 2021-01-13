<template>
  <a-spin :spinning="confirmLoading">
    <a-card :bordered="false">
      <!-- 查询区域 -->
      <div class="table-page-search-wrapper">
        <a-form layout="inline" @keyup.enter.native="searchQuery">
          <a-row :gutter="24">
            <a-col :xl="6" :lg="8" :md="8" :sm="24">
              <a-form-item label="供应商名称">
                <a-input placeholder="请输入供应商名称" v-model="queryParam.companyName"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="8" :md="8" :sm="24">
              <span style="float: left; overflow: hidden" class="table-page-search-submitButtons">
                <a-button type="primary" icon="search" @click="searchQuery">查询数据</a-button>
                <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
              </span>
            </a-col>
          </a-row>
        </a-form>
      </div>
      <!-- table区域-begin -->
      <div>
        <a-table
          ref="table"
          size="middle"
          bordered
          rowKey="id"
          :columns="columns"
          :dataSource="dataSource"
          :pagination="ipagination"
          :loading="loading"
          :rowSelection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange, type: 'radio' }"
          class="j-table-force-nowrap"
          @change="handleTableChange"
        >
        </a-table>
      </div>
    </a-card>
  </a-spin>
</template>

<script>
import { httpAction, getAction } from '@/api/manage'
import pick from 'lodash.pick'
import { filterObj, validateDuplicateValue, randomUUID, handleStatus } from '@/utils/util'
import JFormContainer from '@/components/jeecg/JFormContainer'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import JDate from '@/components/jeecg/JDate'

export default {
  name: 'originaForm',
  mixins: [JeecgListMixin],
  components: {
    JFormContainer
  },
  props: {},
  data() {
    return {
      confirmLoading: false,
      queryParam: {
        companyCategory: 1
      },
      columns: [
        {
          title: '单位编码',
          align: 'center',
          dataIndex: 'supplierCode'
        },
        {
          title: '单位名称',
          align: 'center',
          dataIndex: 'companyName'
        },
        {
          title: '联系人',
          align: 'center',
          dataIndex: 'contactPerson'
        },
        {
          title: '单位地址',
          align: 'center',
          dataIndex: 'unitAddress'
        },
        {
          title: '开户行',
          align: 'center',
          dataIndex: 'bankName'
        },
        {
          title: '银行账号',
          align: 'center',
          dataIndex: 'bankAccount'
        },
        {
          title: '联系电话',
          align: 'center',
          dataIndex: 'contactNumber'
        },
        {
          title: '传真号码',
          align: 'center',
          dataIndex: 'contactFax'
        },
        {
          title: '信用代码',
          align: 'center',
          dataIndex: 'creditCode'
        }
      ],
      url: {
        list: '/original/originalCharge/list'
      }
    }
  },
  computed: {},
  created() {},
  methods: {
    submitForm() {
      if (this.selectionRows.length === 0) {
        this.$message.error('请选择一条数据！')
      } else {
        this.$message.success('添加成功')
        this.$emit('ok', this.selectionRows)
      }
    },
    searchQuery() {
      this.queryParam.companyCategory = 1
      this.loadData(1)
    },
    searchReset() {
      this.queryParam.companyCategory = 1
      this.queryParam.companyName = ''
      this.searchQuery()
    }
  }
}
</script>
<style lang='less' scoped>
@import '~@assets/less/common.less';
.action_btn {
  margin-left: 15px;
  background-color: red;
}
.using_btn {
  background-color: #00bb00;
  color: #f0f0f0;
}
.ban_btn {
  background-color: #cccccc;
  color: white;
}
</style>