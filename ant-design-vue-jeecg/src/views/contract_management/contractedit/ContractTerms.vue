<template>
  <div>
    <div class="table-page-search-wrapper">
      <!-- 操作按钮区域 -->
      <div class="table-operator">
        <a-button type="primary" icon="save" @click="addlist">保存条款</a-button>
        <a-button type="primary" icon="save" @click="openModal">保存个人模板</a-button>
      </div>
      <a-card :bordered="false" title="条款明细">
        <div class="inputWarp" v-for="(item, index) in termsNumber" :key="index">
          <div>
            <a-tag class="title_input" color="pink">条款标题{{ index + 1 }}:</a-tag>
            <a-input v-model="inputTitleData[index]" style="width: 40%" disabled></a-input>
            <!-- <span style="width: 40%">{{ item.templateTitle }}</span> -->
          </div>
          <div class="content_divide">
            <div class="title_x">条款内容{{ index + 1 }}:</div>
            <a-textarea
              v-model="inputContentData[index]"
              @change="getChange(index)"
              :id="'text' + index"
              style="width: 60%"
              :rows="6"
            />
          </div>
        </div>
      </a-card>
    </div>
    <template-modal ref="modalForm" @ok="tempadd"></template-modal>
  </div>
</template>

<script>
import '@/assets/less/TableExpand.less'
import { mixinDevice } from '@/utils/mixin'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import { deleteAction, getAction, httpAction } from '@/api/manage'
import TemplateModal from './tempmodules/TemplateModal'
export default {
  name: 'contractTerms',
  components: { TemplateModal },
  props: {
    templateId: {
      type: String
    },
    cid: {
      type: String
    }
  },
  watch: {
    templateId(newVal, oldVal) {
      this.templateId = newVal
      this.templateDetail()
    }
  },
  data() {
    return {
      //模板条款数
      termsNumber: 0,
      //内容区域数据对象
      inputContentData: {},
      //标题区域数据对象
      inputTitleData: {},
      //详情id
      inputId: {},
      //详情排序
      inputSortNum: {},
      //内容错误标志
      contentMark: {},
      url: {
        list: '/contractedit/contractedit/templateDetail',
        add: '/contractedit/contractedit/addtemplate',
        termsadd: '/contractedit/contractedit/termsadd',
        delete: '/contractedit/contractedit/deletetemplate'
      }
    }
  },
  created() {
    let id = this.templateId
    if (id != '' && id != null && id != undefined) {
      this.templateDetail()
    }
  },
  methods: {
   
    //保存合同条款
    addlist() {
      let termsNumber = this.termsNumber
      if (termsNumber > 0) {
        let cid = this.cid
        let templateId = this.templateId
        deleteAction(this.url.delete, { contract_id: this.cid }).then(res => {
          if (res.success) {
            this.addterms(cid)
          }
          if (res.code === 510) {
            this.$message.warning(res.message)
          }
        })
      } else {
        this.$message.warning('条款信息为空，不能添加！')
      }
    },
    //获取模板详情数据
    templateDetail() {
      getAction(this.url.list, { template_id: this.templateId }).then(res => {
        let arr = res.result
        this.termsNumber = res.result.length
        for (let i = 0; i < arr.length; i++) {
          //新增属性使视图更新
          this.$set(this.inputTitleData, i, arr[i].templateTitle)
          this.$set(this.inputContentData, i, arr[i].templateContent)
          this.$set(this.inputId, i, arr[i].id)
          this.$set(this.inputSortNum, i, arr[i].sortNum)
        }
      })
    },
    //判断合同编写规范
    getChange(index) {},
    //保存个人模板
    openModal() {
      let termsNumber = this.termsNumber
      if (termsNumber > 0) {
        let termsNumber = this.termsNumber
        this.$refs.modalForm.add(termsNumber)
        this.$refs.modalForm.title = '新增'
        this.$refs.modalForm.disableSubmit = false
      } else {
        this.$message.warning('条款信息为空，不能添加！')
      }
    },
    //保存个人模板的模板详情信息
    tempadd(id) {
      console.log(id)
      let mark = true
      for (let i in this.contentMark) {
        if (this.contentMark[i] == false) {
          let errorLine = parseInt(i) + 1
          this.$message.error('条款内容' + errorLine + '区域有误')
          mark = false
        } else {
          mark = true
        }
      }
      if (mark) {
        //设置标识
        let fullTitle = 0
        let fullContent = 0
        let titleArr = []
        for (let i = 0; i < this.termsNumber; i++) {
          if (this.inputTitleData[i] == undefined) {
            this.$message.error('请填写条款第' + (i + 1) + '项标题')
            return (this.fullTitle = 1)
          }
          if (this.inputContentData[i] == undefined) {
            this.$message.error('请填写条款第' + (i + 1) + '项内容')
            return (this.fullContent = 1)
          }
        }
        if (fullTitle != 1 && fullContent != 1) {
          for (let i in this.inputTitleData) {
            let x = {}
            x.templateTitle = this.inputTitleData[i]
            x.templateId = id
            x.sortNum = this.inputSortNum[i]
            titleArr.push(x)
          }
          let contentArr = []
          for (let i in this.inputContentData) {
            let x = {}
            x.templateContent = this.inputContentData[i]
            contentArr.push(x)
          }
          //合并
          let obj = titleArr.map((item, index) => {
            return { ...item, ...contentArr[index] }
          })
          // console.log(obj)
          //请求模板详情后台提交数据
          this.sendData(obj)
        }
      }
    },
    //提交后台数据
    sendData(params) {
      httpAction(this.url.add, params, 'post').then(res => {
        if (res.success) {
          this.$message.success(res.message)
        } else {
          this.$message.warning(res.message)
        }
      })
    },
    //保存条款
    addterms(id) {
      let mark = true
      for (let i in this.contentMark) {
        if (this.contentMark[i] == false) {
          let errorLine = parseInt(i) + 1
          this.$message.error('条款内容' + errorLine + '区域有误')
          mark = false
        } else {
          mark = true
        }
      }
      if (mark) {
        //设置标识
        let fullTitle = 0
        let fullContent = 0
        let titleArr = []
        for (let i = 0; i < this.termsNumber; i++) {
          if (this.inputTitleData[i] == undefined) {
            this.$message.error('请填写条款第' + (i + 1) + '项标题')
            return (this.fullTitle = 1)
          }
          if (this.inputContentData[i] == undefined) {
            this.$message.error('请填写条款第' + (i + 1) + '项内容')
            return (this.fullContent = 1)
          }
        }
        if (fullTitle != 1 && fullContent != 1) {
          for (let i in this.inputTitleData) {
            let x = {}
            x.templateTitle = this.inputTitleData[i]
            x.contractId = id
            x.sort = this.inputSortNum[i]
            titleArr.push(x)
          }
          let contentArr = []
          for (let i in this.inputContentData) {
            let x = {}
            x.templateContent = this.inputContentData[i]
            contentArr.push(x)
          }
          //合并
          let obj = titleArr.map((item, index) => {
            return { ...item, ...contentArr[index] }
          })
          // console.log(obj)
          //请求模板详情后台提交数据
          this.termsData(obj)
        }
      }
    },
    //条款提交后台数据
    termsData(params) {
      httpAction(this.url.termsadd, params, 'post').then(res => {
        if (res.success) {
          this.$message.success(res.message)
        } else {
          this.$message.warning(res.message)
        }
      })
    }
  }
}
</script>

<style scoped lang="less">
@import '~@assets/less/common.less';
.inputWarp {
  margin-left: -8px;
  margin-top: 10px;
  .ant-btn:hover,
  .ant-btn:focus {
    color: #ff7f24;
    background-color: #fff;
    border-color: #ff7f24;
  }
}
.content_divide {
  margin-top: 10px;
  display: flex;
}
.title_x {
  color: #52c41a;
  background: #f6ffed;
  height: 24px;
  text-align: center;
  font-size: 15px;
  border: 1px solid #b7eb8f;
  border-radius: 5px;
  padding-left: 5px;
  padding-right: 9px;
  margin-right: 10px;
  margin-left: 5px;
}
.title_input {
  margin-left: 5px;
  margin-right: 10px;
  font-size: 15px;
}
.canEdit {
  margin-left: 10px;
  background: #ff7f24;
  color: #f0f0f0;
}
.notEdit {
  margin-left: 10px;
  background: #00bb00;
  color: #f0f0f0;
}
</style>