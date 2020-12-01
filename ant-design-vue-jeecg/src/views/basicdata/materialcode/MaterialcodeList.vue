<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="5" :lg="7" :md="8" :sm="24">
            <a-form-item label="物资名称">
              <a-input placeholder="请输入物资名称" v-model="queryParam.materialName"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="5" :lg="7" :md="8" :sm="24">
            <a-form-item label="规格型号">
              <a-input placeholder="请输入规格型号" v-model="queryParam.specification"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="5" :lg="7" :md="8" :sm="24">
            <a-form-item label="物资编码">
              <a-input placeholder="请输入物资编码" v-model="queryParam.materialCode"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="5" :lg="7" :md="8" :sm="24">
            <a-form-item label="使用状态">
              <a-select v-model="queryParam.status">
                <a-select-option value="">全部</a-select-option>
                <a-select-option value="1">使用中</a-select-option>
                <a-select-option value="0">已禁用</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :xl="4" :lg="7" :md="8" :sm="24">
            <span style="float: left; overflow: hidden" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <!-- <a-button type="primary" icon="download" @click="handleExportXls('物资编码表')">导出</a-button> -->
      <!-- <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl"
        @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload> -->
      <a-button 
        v-if="selectedRowKeys.length > 0" 
        icon="minus" 
        type="danger" 
        style="margin-left: 8px;background-color:red" 
        @click="batchDel">批量删除</a-button>

    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择
        <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table ref="table" size="middle" :scroll="{ x: true }" bordered rowKey="id" :columns="columns"
        :dataSource="dataSource" :pagination="ipagination" :loading="loading"
        :rowSelection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange }" class="j-table-force-nowrap"
        @change="handleTableChange">
        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px; font-style: italic">无图片</span>
          <img v-else :src="getImgView(text)" height="25px" alt=""
            style="max-width: 80px; font-size: 12px; font-style: italic" />
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px; font-style: italic">无文件</span>
          <a-button v-else :ghost="true" type="primary" icon="download" size="small" @click="downloadFile(text)">
            下载
          </a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <a-button type='primary' @click="handleEdit(record)">编辑</a-button>
          <a-button type="danger" class="action_btn" @click="handleDelete(record.id)">删除</a-button>
        </span>        
        <!-- 状态值 -->
        <span slot="status" slot-scope="text, record">
          <a-button class="using_btn" v-if="record.status == '使用中'">{{record.status}}</a-button>
          <a-button class="ban_btn" v-if="record.status == '已禁用'">{{record.status}}</a-button>
        </span>
      </a-table>
    </div>

    <materialcode-modal ref="modalForm" @ok="modalFormOk"></materialcode-modal>
  </a-card>
</template>

<script>
  import '@/assets/less/TableExpand.less'
  import {
    mixinDevice
  } from '@/utils/mixin'
  import {
    JeecgListMixin
  } from '@/mixins/JeecgListMixin'
  import MaterialcodeModal from './modules/MaterialcodeModal'
  import {
    httpAction,
    getAction
  } from '../../../api/manage'

  export default {
    name: 'MaterialcodeList',
    mixins: [JeecgListMixin, mixinDevice],
    components: {
      MaterialcodeModal,
    },
    data() {
      return {
        description: '物资编码表管理页面',
        // 表头
        columns: [
          // {
          //   title: '#',
          //   dataIndex: '',
          //   key:'rowIndex',
          //   width:60,
          //   align:"center",
          //   customRender:function (t,r,index) {
          //     return parseInt(index)+1;
          //   }
          // },
          {
            title: '物资编码',
            align: 'center',
            width: 180,
            dataIndex: 'materialCode',
          },
          {
            title: '物资名称',
            align: 'center',
            width: 240,
            dataIndex: 'materialName',
          },
          {
            title: '规格型号',
            align: 'center',
            width: 240,
            dataIndex: 'specification',
          },
          {
            title: '单位',
            align: 'center',
            width: 180,
            dataIndex: 'unit',
          },
          {
            title: '使用状态',
            align: 'center',
            width: 180,
            dataIndex: 'status',
            scopedSlots: {
              customRender: 'status'
            },
          },
          {
            title: '备注',
            align: 'center',
            dataIndex: 'remake',
          },
          {
            title: '操作',
            dataIndex: 'action',
            align: 'center',
            fixed: 'right',
            width: 147,
            scopedSlots: {
              customRender: 'action'
            },
          },
        ],
        url: {
          list: '/materialcode/materialcode/list',
          delete: '/materialcode/materialcode/delete',
          deleteBatch: '/materialcode/materialcode/deleteBatch',
          exportXlsUrl: '/materialcode/materialcode/exportXls',
          importExcelUrl: 'materialcode/materialcode/importExcel',
        },
        dictOptions: {},
        //不用默认方法加载
        disableMixinCreated: true,
      }
    },
    created() {
      this.loadData(1)
    },
    computed: {
      importExcelUrl: function () {
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
      },
    },
    methods: {
      //初始化加载数据
      loadData(arg) {
        if (!this.url.list) {
          this.$message.error('请设置url.list属性!')
          return
        }
        //加载数据 若传入参数1则加载第一页的内容
        if (arg === 1) {
          this.ipagination.current = 1
        }
        var params = this.getQueryParams() //查询条件
        this.loading = true
        getAction(this.url.list, params).then((res) => {
          if (res.success) {
            this.dataSource = this.formatData(res.result.records)
            this.ipagination.total = res.result.total
          }
          if (res.code === 510) {
            this.$message.warning(res.message)
          }
          this.loading = false
        })
      },
      initDictConfig() {},
      //数据格式化
      formatData(data) {
        // console.log(data)
        let newData = []
        data.forEach((item) => {
          if(item.status == '1'){
            item.status = '使用中'
          }else if(item.status == '0'){
            item.status = '已禁用'
          }
          newData.push(item)
        })
        return newData
      },
    },
  }
</script>
<style lang='less' scoped>
  @import '~@assets/less/common.less';
  .action_btn{
    margin-left: 15px;
    background-color: red;
  }
  .using_btn{
    background-color: #00BB00;
    color: #f0f0f0;
  }
  .ban_btn{
    background-color: #cccccc;
    color: white;
  }
</style>