<!--
 * @descript: 
 * @Date: 2020-09-16 09:58:36
 * @LastEditTime: 2020-09-16 16:13:36
 * @version: 0.0.1
-->
<template>
  <div>
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
export default {
  components: { JEditableTableByCj, JSearchSelectTagSec },
  mixins: [JeecgListMixin, mixinDevice],
  data() {
    return {
      columns: [
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
          title: '数量',
          key: 'elements',
          width: '140px',
          type: FormTypes.input,
          options: [],
          disabled: true,
          defaultValue: '合同数值'
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
        // {
        //   title: '增减款额',
        //   key: 'isreduce',
        //   width: '140px',
        //   defaultValue: '扣除',
        //   type: FormTypes.select,
        // },
        {
          title: '扣数',
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
      SysbolOptions: [
        { title: '<', value: '0' },
        { title: '≤', value: '1' },
      ],
      //数据库传入的数据
      dataSource: [],
      dataSourceSec: [],
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
      disableMixinCreated: false,
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
      url: {
        list: '/SysNumgongshi/sysNumgongshi/list',
        
        delete: '/SysNumgongshi/sysNumgongshi/delete',
        deleteBatch: '/SysNumgongshi/sysNumgongshi/deleteBatch',
        savaHtGongShi: '/SysNumgongshi/sysNumgongshi/savaHtGongShi',

        edit: '/SysNumgongshi/sysNumgongshi/edit',
        queryById: '/SysNumgongshi/sysNumgongshi/queryById',
      },
    }
  },
  props: {
    contrac: {
      type: String,
      // required: true,
      default: 'WX20200900031',
    },
  },
  watch: {
    contrac(newVal, oldVal) {
      this.params.hetongId = newVal
      this.hetongId1 = newVal
      this.loadData(1)
    },
  },
  created() {
    // this.loadData()
    this.setSysbolOptions()
  },
  methods: {
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
        console.log(res)
        if (res.success) {
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
      this.columns.forEach((item, idx) => {
        if (item.key == 'leftsysbol' || item.key == 'rightsysbol') {
          this.columns[idx].options = this.SysbolOptions
        }
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

<style>
</style>