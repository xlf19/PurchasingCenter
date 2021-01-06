<!--
 * @descript: MountCao
 * @Date: 2020-12-12 09:06:52
 * @LastEditTime: 2021-01-06 15:10:46
 * @version: 0.0.1
-->
<template>
  <div>
    <a-card :bordered="false">
      <!-- 查询区域 -->
      <div class="table-page-search-wrapper">
        <a-form :form="form" layout="inline">
          <a-row :gutter="24">
            <a-col :xl="5" :lg="7" :md="8" :sm="24">
              <a-form-item label="所在部门" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input v-decorator="['department', { initialValue: '原料部' }]" :disabled="true"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="5" :lg="7" :md="8" :sm="24">
              <a-form-item label="物资名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input
                  v-decorator="['templateName', { rules: [{ required: true, message: '请输入模板名称' }] }]"
                  placeholder="请输入物资名称"
                ></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="5" :lg="7" :md="8" :sm="24">
              <a-form-item label="条款数" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-select
                
                  @select="setNumber"
                  v-decorator="[
                    'termsNumber',
                    { rules: [{ required: true, message: '请选择条款数' }], initialValue: 11 },
                  ]"
                >
                  <a-select-option v-for="item in termsList" :key="item.key" :value="item.value">
                    {{ item.value }}
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>            
            <a-col :xl="5" :lg="7" :md="8" :sm="24">
              <a-form-item label="使用状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-select
                  
                  v-decorator="[
                    'status',
                    { rules: [{ required: true, message: '请选择使用状态' }], initialValue: '使用中' },
                  ]"
                >
                  <a-select-option v-for="item in statusList" :key="item.key" :value="item.value">
                    {{ item.value }}
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :xl="4" :lg="7" :md="8" :sm="24">
              <span style="float: left; overflow: hidden" class="table-page-search-submitButtons">
                <!-- <a-button type="primary" icon="search">加载数据</a-button> -->
                <a-button type="primary" @click="saveData" icon="reload" style="margin-left: 8px">保存</a-button>
              </span>
            </a-col>
          </a-row>
        </a-form>
      </div>
    </a-card>
    <a-card :bordered="false" style="margin-top: -25px">
      <!-- 内容区域 -->
      <div class="inputWarp" v-for="(item, index) in termsNumber" :key="index">
        <div>
          <a-tag class="title_input" color="pink">条款标题{{ index + 1 }}:</a-tag>
          <a-input v-model="inputTitleData[index]" style="width: 40%"></a-input>
          <a-button @click="insert(index)" class="canEdit" type="">插入可编辑内容</a-button>
          <!-- <a-button class="notEdit" type="">标记可编辑</a-button> -->
        </div>
        <div class="content_divide">
          <div class="title_x">条款内容{{ index + 1 }}:</div>
          <a-textarea
            v-model="inputContentData[index]"
            @blur="getChange(index)"
            :id="'text' + index"
            style="width: 60%"
            :rows="6"
          />
        </div>
      </div>
    </a-card>
  </div>
</template>

<script>
import { httpAction, getAction } from '@/api/manage'
import pick from 'lodash.pick'
import { validateDuplicateValue } from '@/utils/util'

export default {
  name: 'editTemplate',
  inject:['closeCurrent'],
  data() {
    return {
      form: this.$form.createForm(this),
      labelCol: {
        xs: {
          span: 24,
        },
        sm: {
          span: 5,
        },
      },
      wrapperCol: {
        xs: {
          span: 24,
        },
        sm: {
          span: 16,
        },
      },
      // 条款数列表
      termsList: [
        {
          key: 11,
          value: '11',
        },
        {
          key: 12,
          value: '12',
        },
        {
          key: 13,
          value: '13',
        },
        {
          key: 14,
          value: '14',
        },
      ],
      //使用状态
      statusList: [
        {
          key: '使用中',
          value: '使用中',
        },
        {
          key: '已禁用',
          value: '已禁用',
        },
      ],
      //条款数
      termsNumber: 11,
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
        list: '/syslegaltemplatedetail/sysLegalTemplateDetail/list',
        add: '/syslegaltemplatedetail/sysLegalTemplateDetail/add',
        edit: '/syslegaltemplatedetail/sysLegalTemplateDetail/edit',
        templateAdd: '/syslegaltemplate/sysLegalTemplate/add',
        templateEdit: '/syslegaltemplate/sysLegalTemplate/edit',
        queryByTemplateId: '/syslegaltemplate/sysLegalTemplate/queryById',
      },
    }
  },
  mounted() {
    let templateId = this.$route.query.id
    //根据id请求数据列表信息
    this.getTemplateList(templateId)
    this.getTemplateDetailList(templateId)
  },
  methods: {
    setNumber(value) {
      this.termsNumber = parseInt(value)
    },
    //判断内容区域插入符号规范
    copyShare(index) {
      let focus = document.getElementById('text' + index).value
      if (focus.match(/\【.*?\】/g)) {
        let left = focus.split('【').length - 1
        let right = focus.split('】').length - 1
        if (left != right) {
          this.$message.error('条款内容' + (index + 1) + '区域有误')
          this.contentMark[index] = false
        } else {
          //正则截取内容区域【】
          let arr = focus.match(/\【.*?\】/g)
          for (let i = 0; i < arr.length; i++) {
            let arrLeft = arr[i].split('【').length - 1
            let arrRight = arr[i].split('】').length - 1
            let errorLine = parseInt(index) + 1
            // debugger;
            if (arrLeft != arrRight) {
              this.$message.error('条款内容' + errorLine + '区域有误')
              this.contentMark[index] = false
            } else {
              this.contentMark[index] = true
            }
          }
        }
      }
    },
    //插入可编辑内容
    async insert(index) {
      //避免空插入符号错误
      if (this.inputContentData[index] == undefined) {
        this.$message.warning('请先输入内容', 1, true)
      } else {
        let myValue = '【 】'
        //获取这块的元素节点
        let myField = document.getElementById('text' + index)
        // debugger
        if (myField.selectionStart || myField.selectionStart === 0) {
          var startPos = myField.selectionStart
          var endPos = myField.selectionEnd
          this.inputContentData[index] =
            myField.value.substring(0, startPos) + myValue + myField.value.substring(endPos, myField.value.length)
          await this.$nextTick()
          myField.focus()
          myField.setSelectionRange(endPos + myValue.length, endPos + myValue.length)
        } else {
          this.inputContentData[index] += myValue
        }
        //执行判断
        this.copyShare(index)
      }
    },
    //保存数据
    saveData() {
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
            x.id = this.inputId[i]
            x.templateId = this.$route.query.id
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
    getChange(index) {
      this.copyShare(index)
    },
    //获取模板列表数据
    getTemplateList(templateId){
      let params = {
        id: templateId
      }
      getAction(this.url.queryByTemplateId,params).then(res => {
        // console.log(res)
        this.termsNumber = parseInt(res.result.termsNumber)
        this.form.setFieldsValue({
          department: res.result.department,
          templateName: res.result.templateName,
          termsNumber: res.result.termsNumber,
          status: res.result.status
        })
      })
    },
    //获取模板详情数据
    getTemplateDetailList(templateId){
      let params = {
        templateId: templateId
      }
      let isorter ={
        column: 'sortNum',
        order: 'asc',
      }
      let page = {
        pageNo: 1,
        pageSize: 30
      }
      let queryParam = Object.assign(params, isorter,page)
      getAction(this.url.list,queryParam).then(res => {
        // console.log(res.result.records)
        let arr = res.result.records
        for(let i =0;i<arr.length;i++){
          // this.inputTitleData[i] = arr[i].templateTitle
          //新增属性使视图更新
          this.$set(this.inputTitleData,i, arr[i].templateTitle)
          this.$set(this.inputContentData,i, arr[i].templateContent)         
          this.$set(this.inputId,i, arr[i].id)         
          this.$set(this.inputSortNum,i, arr[i].sortNum)         
        }
      })
    },
    //提交后台数据
    sendData(params) {
      httpAction(this.url.edit, params, 'put').then((res) => {
        if (res.success) {
          // this.$message.success(res.message)
          //编辑模板列表数据
          this.editTemplate()
        } else {
          this.$message.warning(res.message)
        }
      })
    },
    //编辑模板列表数据
    editTemplate() {
      this.form.validateFields((error, values) => {
        values.id = this.$route.query.id
        // console.log(values)
        if (!error) {
          httpAction(this.url.templateEdit, values, 'put').then((res) => {
            if (res.success) {
              this.$message.success(res.message)
              //关闭当前标签页
              this.closeCurrent()
              this.$router.push({
                name: 'template-syslegaltemplate-SysLegalTemplateList'
              })
            } else {
              this.$message.warning(res.message)
            }
          })
        }
      })
    },
  },
}
</script>
<style lang="less" scoped>
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