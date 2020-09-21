<!--
 * @descript: 
 * @Date: 2020-08-28 14:27:18
 * @LastEditTime: 2020-09-16 15:22:09
 * @version: 0.0.1
-->
<template>
  <div class="hetonggongshi">
    <a-card title="合同信息展示" size="small">
      <div class="firstTitle">
        <a-form layout="inline">
          <a-form-item label="合同编号">
            <j-search-select-tag-sec
              placeholder="请做出你的选择"
              v-model="hetongId1"
              style="width:160px"
              dict="contract_information,contract_no,contract_no"
              :async="false"
              @change="searchChange($event)"
            ></j-search-select-tag-sec>
          </a-form-item>
          <a-form-item>
            <!-- <a-button type="primary" icon="search" @click="searchAllHt">查询</a-button> -->
          </a-form-item>
        </a-form>
      </div>
    </a-card>
    <a-card id="ctxTable" title="合同公式维护" size="small" style="margin-top: 15px;">
      <a-tabs @change="callback">
        <a-tab-pane key="0" tab="单价公式维护">
          <div class="mrgbottom mrgtop">
            <a-form layout="inline">
              <a-form-item>
                <a-button class="mrgright" type="primary" icon="plus" @click="add">新增一行</a-button>
              </a-form-item>
              <a-form-item>
                <a-popconfirm
                  class="mrgright"
                  v-if="selectedRowIds.length>0"
                  :title="`确定要删除这 ${selectedRowIds.length} 项吗?`"
                  @confirm="handleConfirmDelete"
                >
                  <a-button type="primary" icon="minus">批量删除</a-button>
                  <span class="gap"></span>
                </a-popconfirm>
              </a-form-item>
              <a-form-item>
                <a-button class="mrgright" type="primary" icon="save" @click="handleTableCheck">保存</a-button>
              </a-form-item>
              <a-form-item label="模板名称" style="margin-left:460px;">
                <j-search-select-tag-sec
                  placeholder="请选择模板"
                  v-model="hetongId2"
                  style="width:160px"
                  dict="sys_template,templatename,templatename"
                  :async="false"
                  @change="importChange($event)"
                ></j-search-select-tag-sec>
              </a-form-item>
              <a-form-item>
                <a-button class="mrgright" type="primary" @click="importTemplate" icon="save">导入模板</a-button>
              </a-form-item>
              <a-form-item>
                <a-button class="mrgright" type="primary" @click="openModal" icon="save">保存为模板</a-button>
              </a-form-item>
            </a-form>
          </div>
          <j-editable-table-by-cj
            ref="editableTable"
            :loading="loading"
            :columns="columns"
            :dataSource="dataSourceSec"
            :rowNumber="false"
            :rowSelection="true"
            :actionButton="true"
            :dragSort="true"
            style="margin-top: 8px;"
            @selectRowChange="handleSelectRowChange"
            @valueChange="handleValueChange"
          >
            <template v-slot:action="props">
              <a-button style="background-color: red;" type="danger" @click="handleDelete(props)">删除</a-button>
            </template>
          </j-editable-table-by-cj>
        </a-tab-pane>
        <a-tab-pane key="1" tab="数量公式维护">
          <num-formula :contrac="hetongId1"></num-formula>
        </a-tab-pane>
      </a-tabs>
    </a-card>

    <sys-hetonggongshi-modal ref="modalForm" @ok="saveTemplate($event)"></sys-hetonggongshi-modal>
  </div>
</template>

<script>
import { httpAction, getAction, deleteAction } from '@/api/manage'
import { randomUUID } from '@/utils/util'
import { mixinDevice } from '@/utils/mixin'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import moment from 'moment'
import { FormTypes } from '@/utils/JEditableTableUtil'
import JEditableTableByCj from './table/JEditableTableByCj'
import JSearchSelectTagSec from './table/JSearchSelectTagSec'
import SysHetonggongshiModal from './modules/SysHetonggongshiModal'
import NumFormula from './numFormula'
import { createLogger } from 'vuex'

export default {
  components: { JEditableTableByCj, JSearchSelectTagSec, SysHetonggongshiModal, NumFormula },
  mixins: [JeecgListMixin, mixinDevice],
  props: {
    contrac: {
      type: String,
      // required: true,
      // default: 'WX20200900031',
    },
  },
  watch: {
    contrac(newVal, oldVal) {
      this.params.hetongId = newVal
      this.hetongId1 = newVal
      this.loadData(1)
      //this.setSyselements(newVal)
    },
  },
  data() {
    return {
      visibleSaveTemplate: false,
      //收获单位
      loading: false,
      //合同编号
      hetongId1: this.contrac,
      hetongId2: '',
      contractOptions: [{ id: 'WX20200900031' }, { id: 'WX20200900039' }, { id: 'WX20200900040' }],

      //元素表
      elementsOptions: [
        // { title: 'S', value: 'S' },
        // { title: 'Mn', value: 'Mn' },
        // { title: 'H2O', value: 'H2O' },
        // { title: 'CaO', value: 'CaO' },
        // { title: 'SiO2', value: 'SiO2' },
        // { title: 'MgO', value: 'MgO' },
      ],

      SysbolOptions: [
        { title: '<', value: '0' },
        { title: '≤', value: '1' },
      ],
      deductionOptions: [
        { title: '单价', value: '0' },
        { title: '数量(吨)', value: '1' },
      ],
      isReduceOptions: [
        { title: '扣除', value: '0' },
        { title: '增加', value: '1' },
      ],
      columns: [
        // {
        //   title: '扣款类型',
        //   key: 'deductiontype',
        //   width: '140px',
        //   type: FormTypes.select,
        //   options: [],
        //   defaultValue: '',
        //   placeholder: '请选择${title}',
        //   validateRules: [{ required: true, message: '请选择${title}' }],
        // },
        {
          title: '左区间值',
          key: 'leftnum',
          width: '140px',
          type: FormTypes.inputNumber,
          defaultValue: '',
          placeholder: '请输入${title}',
          validateRules: [
            // {
            //   required: true, // 必填
            //   message: '请输入${title}', // 显示的文本
            // },
          ],
        },
        {
          title: '左符号',
          key: 'leftsysbol',
          width: '140px',
          type: FormTypes.select,
          options: [],
          defaultValue: '<',
          placeholder: '请选择${title}',
          validateRules: [
            // { required: true, message: '请选择${title}' }
          ],
        },
        {
          title: '元素',
          key: 'elements',
          width: '140px',
          type: FormTypes.select,
          options: [],
          defaultValue: '',
          placeholder: '请选择${title}',
          validateRules: [
            // { required: true, message: '请选择${title}' }
          ],
        },
        {
          title: '右符号',
          key: 'rightsysbol',
          width: '140px',
          type: FormTypes.select,
          options: [],
          defaultValue: '≤',
          placeholder: '请选择${title}',
          validateRules: [
            // { required: true, message: '请选择${title}' }
          ],
        },
        {
          title: '右区间值',
          key: 'rightnum',
          // width: '18%',
          width: '140px',
          type: FormTypes.inputNumber,
          defaultValue: '',
          placeholder: '请输入${title}',
          validateRules: [
            // {
            //   required: true, // 必填
            //   message: '请输入${title}', // 显示的文本
            // },
            {
              handler(type, value, row, column, callback, target) {
                // 获取所有行的数据
                let { values } = target.getValuesSync({ validate: false })
                //遍历查询数据
                let left
                let right
                values.forEach((item) => {
                  if (row.id == item.id) {
                    //通过id找到自己的数据
                    left = parseFloat(item.leftnum)
                    right = parseFloat(item.rightnum)
                    if (right <= left) {
                      callback(false, '右区间值不可小于左区间值')
                    } else {
                      callback(true)
                    }
                  }
                })
              },
              message: '${title}默认提示',
            },
          ],
        },
        {
          title: '基准数值',
          key: 'basedata',
          width: '140px',
          type: FormTypes.inputNumber,
          // disabled: true,
        },
        {
          title: '增减款额',
          key: 'isreduce',
          width: '140px',
          defaultValue: '扣除',
          type: FormTypes.select,
        },
        {
          title: '款额',
          key: 'deductions',
          width: '140px',
          type: FormTypes.inputNumber,
          // slotName: 'deductions'
        },
        {
          title: '操作',
          key: 'action',
          // width: '8%',
          width: '140px',
          type: FormTypes.slot,
          slotName: 'action',
        },
      ],
      //数据库传入的数据
      dataSource: [],
      dataSourceSec: [],
      url: {
        list: '/Hetonggongshi/sysHetonggongshi/list',
        delete: '/Hetonggongshi/sysHetonggongshi/delete',
        deleteBatch: '/Hetonggongshi/sysHetonggongshi/deleteBatch',
        savaHtGongShi: '/Hetonggongshi/sysHetonggongshi/savaHtGongShi',
        saveTemplate: '/SysTemplate/sysTemplate/saveTemplate',
        edit: '/Hetonggongshi/sysHetonggongshi/edit',
        queryById: '/Hetonggongshi/sysHetonggongshi/queryById',
        queryTemplateByName: '/SysTemplate/sysTemplate/list',
        elemetslist: '/elements/contractElements/findListhth',
      },
      selectedRowIds: [],
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 },
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 },
      },
      //关闭mixins的加载
      disableMixinCreated: true,
      //唯一验证
      validatorRules: {
        templatename: {
          rules: [
            { required: false },
            {
              validator: (rule, value, callback) =>
                validateDuplicateValue('sys_template', 'templatename', value, this.model.id, callback),
            },
          ],
        },
      },
      /* 排序参数 */
      isorter: {
        column: 'sortnum',
        order: 'asc',
      },
      params: {
        hetongId: this.contrac,
      },
      templatename: '',
    }
  },
  created() {
    // this.loadData()
    this.setSyselements(this.contrac)
    // this.setSysbolOptions()
  },
  methods: {
    //合同元素加载数据
    setSyselements(hth) {
      getAction(this.url.elemetslist, { hth: hth }).then((res) => {
        if (res.success) {
          let xdata = []
          res.result.forEach((item) => {
            let x = {}
            x.title = item
            x.value = item
            xdata.push(x)
          })
          this.elementsOptions = xdata
          this.setSysbolOptions()
        }
        if (res.code === 510) {
          this.$message.warning(res.message)
        }
      })
    },
    //加载数据
    loadData(arg) {
      if (!this.url.list) {
        this.$message.error('请设置url.list属性!')
        return
      }
      //加载数据 若传入参数1则加载第一页的内容
      if (arg === 1) {
        this.ipagination.current = 1
      }
      let sqp = {}
      var param = Object.assign(sqp, this.params, this.isorter)
      param.field = this.getQueryField()
      param.pageNo = this.ipagination.current
      param.pageSize = this.ipagination.pageSize
      // var params = this.getQueryParams();//查询条件
      this.loading = true
      getAction(this.url.list, param).then((res) => {
        // console.log(res)
        if (res.success) {
          //要根据后台返回的数据deductiontype给到不同tab模块
          // let records = res.result.records
          // let priceArr = []
          // let numArr = []
          // records.forEach((item, idx) => {
          //   // console.log(item)
          //   if (item.deductiontype == '0') {
          //     priceArr.push(item)
          //   } else {
          //     numArr.push(item)
          //   }
          // })
          // this.dataSourceSec = priceArr
          // console.log(x)
          this.dataSourceSec = res.result.records
          this.ipagination.total = res.result.total
        }
        if (res.code === 510) {
          this.$message.warning(res.message)
        }
        this.loading = false
      })
    },
    getQueryField() {
      //TODO 字段权限控制
      var str = 'id,'
      this.columns.forEach(function (value) {
        str += ',' + value.dataIndex
      })
      return str
    },
    //查询所有的合同单号信息
    // searchAllHt() {
    //   this.params.hetongId = this.hetongId1
    //   this.loadData(1)
    // },
    //下拉选择搜索
    searchChange(values) {
      this.params.hetongId = values
      this.loadData(1)
    },
    //批量删除
    handleConfirmDelete() {
      //先查询公式表里面有没有这条数据（防止是导入数据）
      for (let i = 0; i < this.selectedRowIds.length; i++) {
        // console.log(this.selectedRowIds[i])
        let params = {
          id: this.selectedRowIds[i],
        }
        getAction(this.url.queryById, params).then((res) => {
          if (res.code == 200) {
            deleteAction(this.url.delete, params).then((rtx) => {
              // console.log(rtx)
              if (rtx.code == 200) {
                this.$message.success('删除成功')
                this.$refs.editableTable.removeRows(this.selectedRowIds[i])
              }
            })
          } else {
            this.$refs.editableTable.removeRows(this.selectedRowIds[i])
          }
        })
      }
    },
    /** 表单验证后保存数据 */
    handleTableCheck() {
      this.$refs.editableTable.getValues((error, values) => {
        // console.log(values)
        if (error === 0) {
          values.forEach((item, idx) => {
            //处理数据信息展示
            if (item.leftsysbol == '<') {
              values[idx].leftsysbol = '0'
            } else if (item.leftsysbol == '≤') {
              values[idx].leftsysbol = '1'
            }
            if (item.rightsysbol == '<') {
              values[idx].rightsysbol = '0'
            } else if (item.rightsysbol == '≤') {
              values[idx].rightsysbol = '1'
            }
            if (item.isreduce == '扣除') {
              values[idx].isreduce = '0'
            } else if (item.isreduce == '增加') {
              values[idx].isreduce = '1'
            }
            values[idx].hetongId = this.hetongId1
          })
          // console.log('2222', values)
          httpAction(this.url.savaHtGongShi, values, 'post').then((res) => {
            this.$message.success(res.message)
          })
        } else {
          this.$message.error('验证未通过')
        }
      })
    },

    //打开保存模板弹框
    openModal() {
      this.$refs.modalForm.add()
      this.$refs.modalForm.title = '新增'
      this.$refs.modalForm.disableSubmit = false
    },

    //保存为模板
    saveTemplate(modal) {
      this.$refs.editableTable.getValues((error, values) => {
        console.log(values)
        // console.log(error)
        if (error === 0) {
          values.forEach((item, idx) => {
            //处理数据信息展示
            if (item.leftsysbol == '<') {
              values[idx].leftsysbol = '0'
            } else if (item.leftsysbol == '≤') {
              values[idx].leftsysbol = '1'
            }
            if (item.rightsysbol == '<') {
              values[idx].rightsysbol = '0'
            } else if (item.rightsysbol == '≤') {
              values[idx].rightsysbol = '1'
            }
            if (item.isreduce == '扣除') {
              values[idx].isreduce = '0'
            } else if (item.isreduce == '增加') {
              values[idx].isreduce = '1'
            }
            ;(values[idx].id = randomUUID()), (values[idx].templatename = modal.templatename)
          })
          httpAction(this.url.saveTemplate, values, 'post').then((res) => {
            this.$message.success(res.message)
          })
        } else {
          this.$message.error('验证未通过')
        }
      })
    },
    //接收模板参数
    importChange(values) {
      console.log(values)
      this.templatename = values
    },
    //导入模板
    importTemplate() {
      if (!this.templatename) {
        this.$message.error('请先选择模板')
      } else {
        let params = {
          templatename: this.templatename,
        }
        getAction(this.url.queryTemplateByName, params).then((res) => {
          this.dataSourceSec = res.result.records
          this.$message.success(res.message)
        })
      }
    },
    //新增一行
    add() {
      this.$refs.editableTable.handleClickAdd()
    },

    //选择操作
    handleSelectRowChange(selectedRowIds) {
      this.selectedRowIds = selectedRowIds
    },

    //删除特定行
    handleDelete(props) {
      let { rowId, target } = props
      let params = {
        id: rowId,
      }
      //先查询公式表里面有没有这条数据（防止是导入数据）
      getAction(this.url.queryById, params).then((res) => {
        if (res.code == 200) {
          deleteAction(this.url.delete, params).then((rtx) => {
            // console.log(rtx)
            if (rtx.code == 200) {
              this.$message.success('删除成功')
              target.removeRows(rowId)
            }
          })
        } else {
          target.removeRows(rowId)
        }
      })
      // target.removeRows(rowId)
    },

    //元素选择赋值
    setSysbolOptions() {
      // debugger;
      this.columns.forEach((item, idx) => {
        if (item.key == 'elements') {
          this.columns[idx].options = this.elementsOptions
        } else if (item.key == 'leftsysbol' || item.key == 'rightsysbol') {
          this.columns[idx].options = this.SysbolOptions
        }
        // else if (item.key == 'deductiontype') {
        //   this.columns[idx].options = this.deductionOptions
        // }
        else if (item.key == 'isreduce') {
          this.columns[idx].options = this.isReduceOptions
        }
      })
    },

    //绑定左符号值和基准数据值
    handleValueChange(event) {
      // if (event.row.leftnum != null) {
      //   this.$refs.editableTable.setValues([
      //     {
      //       rowKey: event.row.id,
      //       values: {
      //         basedata: event.row.leftnum,
      //       },
      //     },
      //   ])
      // }
    },

    //遍历处理数据
    formatData(data) {
      // console.log(data)
      let newData = []
      data.forEach((item) => {
        let itx = {}
        ;(itx.id = item.id),
          (itx.elements = item.elements),
          (itx.leftnum = item.leftnum),
          (itx.rightnum = item.rightnum),
          (itx.basedata = item.basedata),
          (itx.deductiontype = item.deductiontype == '0' ? '单价' : '数量(吨)'),
          (itx.leftsysbol = item.leftsysbol == '0' ? '<' : '≤'),
          (itx.rightsysbol = item.rightsysbol == '0' ? '<' : '≤'),
          (itx.isreduce = item.isreduce == '0' ? '扣除' : '增加'),
          (itx.deductions = item.deductions)
        newData.push(itx)
      })
      return newData
    },

    //选择维护公式类型
    callback(value) {
      console.log(value)
    },
  },
}
</script>

<style lang="less">
.ant-card-body {
  padding-top: 5px;
}
.firstTitle {
  margin-top: 10px;
  .searchBtn {
    margin-left: 35px;
  }
}
.hetonggongshi {
  .mrgright {
    margin-right: 15px;
  }
  .mrgbottom {
    margin-bottom: 15px;
  }
  .mrgtop {
    margin-top: 10px;
  }
}
</style>