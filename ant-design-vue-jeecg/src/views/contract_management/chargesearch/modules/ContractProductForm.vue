<template>
  <a-spin :spinning="confirmLoading">
    <a-card :bordered="false">
      <!-- 查询区域 -->
      <div class="table-page-search-wrapper">
        <a-form layout="inline" @keyup.enter.native="searchQuery">
          <a-row :gutter="24">
            <a-col :xl="6" :lg="8" :md="8" :sm="24">
              <a-form-item label="物资名称">
                <a-input placeholder="请输入物资名称" v-model="queryParam.materialName"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="8" :md="8" :sm="24">
              <a-form-item label="规格型号">
                <a-input placeholder="请输入供货商规格型号" v-model="queryParam.specification"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="8" :md="8" :sm="24">
              <a-form-item label="物资编码">
                <a-input placeholder="请输入物资编码" v-model="queryParam.materialCode"></a-input>
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
        ></a-table>
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
  name: 'ContractPurchaseForm',
  mixins: [JeecgListMixin],
  components: {
    JFormContainer
  },
  props: {},
  data() {
    return {
      confirmLoading: false,
      columns: [
        {
          title: '物资编码',
          align: 'center',
          dataIndex: 'materialCode'
        },
        {
          title: '物资名称',
          align: 'center',
          dataIndex: 'materialName'
        },
        {
          title: '规格型号',
          align: 'center',
          dataIndex: 'specification'
        },
        {
          title: '单位',
          align: 'center',
          dataIndex: 'unit'
        },
        {
          title: '使用状态',
          align: 'center',
          dataIndex: 'status'
        },

        {
          title: '备注',
          align: 'center',
          dataIndex: 'remake'
        }
      ],
      url: {
        list: '/materialcode/materialcode/list'
      }
    }
  },
  computed: {},
  created() {},
  methods: {
    submitForm() {
      if(this.selectionRows.length===0){
        this.$message.error('请选择一条数据！')
      }else{
         this.$message.success('添加成功')
          this.$emit('ok', this.selectionRows)
      }
    }
  }
}
</script>