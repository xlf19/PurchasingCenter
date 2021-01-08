<template>
  <a-card :bordered="false" :class="{ abcdefg: true }">
    <div class="no-print" style="text-align: right">
      <a-button v-print="'#printContent'" ghost type="primary">打印</a-button>
    </div>
    <section ref="print" id="printContent" class="abcdefg">
      <div class="sign">
        <!-- 合同基本信息 -->
        <a-row>
          <a-col :span="24">
            <div style="text-align: center">
              <p style="font-size: 30px;font-weight: 800">废钢购销合同</p>
            </div>
          </a-col>
        </a-row>
        <a-row class="contract1">
          <a-col :span="12">
            <div style="margin-left:10%">
              供货单位：<span style="text-decoration:underline">安庆市吉宽再生资源有限公司</span>
            </div>
          </a-col>
          <a-col :span="12">
            <div style="margin-left:10%">合同编号：<span style="text-decoration:underline">WX20210100007</span></div>
          </a-col>
        </a-row>
        <a-row style="margin-top: 20px" class="contract1">
          <a-col :span="12">
            <div style="margin-left:10%">
              需方单位：<span style="text-decoration:underline">芜湖新兴铸管有限责任公司</span>
            </div>
          </a-col>
          <a-col :span="12">
            <div style="margin-left:10%">
              签订地点：<span style="text-decoration:underline">新兴铸管芜湖工业区</span>
            </div>
          </a-col>
        </a-row>
        <!-- 合同条款 -->
        <a-row style="margin-top: 20px" class="contract1">
          <a-col :span="12">
            <div style="margin-left:10%">
              一、产品名称、规格型号、数量、金额：
            </div>
          </a-col>
          <a-col :span="12">
            <div style="margin-left:10%">
              备&#12288;&#12288;注：<span style="text-decoration:underline">签订日期：2021-01-01</span>
            </div>
          </a-col>
        </a-row>
        <a-row style="margin-top: 20px">
          <a-col :span="24" class="contract1">
            <table border="1" style="margin-left:5%;width:90%">
              <tr style="text-align:center">
                <td>编号</td>
                <td colspan="3">产品名称</td>
                <td>质量指标</td>
              </tr>
              <tr style="text-align:center;font-family:宋体">
                <td>43010100005</td>
                <td colspan="3">废钢（优质重废）</td>
                <td>长度≤600mm、宽度≤600mm 、厚度≥8mm</td>
              </tr>
              <tr style="text-align:center">
                <td>订货数量（吨）</td>
                <td>单价（元/干吨）</td>
                <td>税率（%）</td>
                <td>税金</td>
                <td>含税总金额（元）</td>
              </tr>
              <tr style="text-align:center;font-family:宋体">
                <td>300.00</td>
                <td>3200.00</td>
                <td>13%</td>
                <td>110442.48</td>
                <td>960000.00</td>
              </tr>
              <tr>
                <td colspan="5">合同金额大：玖拾陆万元整</td>
              </tr>
            </table>
          </a-col>
        </a-row>
        <a-row class="contract2" v-for="(item ,index) in tlist" :key="index">
          <a-col :span="24">{{item.templateTitle}}</a-col>
          <a-col :span="24" style="text-decoration:underline"
            >{{item.templateContent}}
          </a-col>
        </a-row>
        <!-- 供方、需方详情 -->
        <div style="text-align:left;height:inherit;font-size: 20px;margin-left:5%;margin-top: 20px;border:1px solid #000">
          <div>
            <div class="contract3">
              <table style="margin-left:20px;">
                <tr style="text-align:center;">
                  <td colspan="3">供方</td>
                </tr>
                <tr>
                  <td style="text-align:right;">单位名称(章)：</td>
                  <td>安庆市吉宽再生资源有限公司</td>
                </tr>
                <tr>
                  <td style="text-align:right;">单位地址：</td>
                  <td>安庆市怀宁县月山镇黄岭村</td>
                </tr>
                <tr>
                  <td style="text-align:right;">法定代表人 ：</td>
                  <td>许吉宽</td>
                </tr>
                <tr>
                  <td style="text-align:right;">委托代理人 ：</td>
                  <td></td>
                </tr>
                <tr>
                  <td style="text-align:right;">电 话 ：</td>
                  <td>0556-4054946</td>
                </tr>
                <tr>
                  <td style="text-align:right;">传 真 ：</td>
                  <td></td>
                </tr>
                <tr>
                  <td style="text-align:right;">开 户 银 行 ：</td>
                  <td>怀宁县工行月山分理处</td>
                </tr>
                <tr>
                  <td style="text-align:right;">账 号 ：</td>
                  <td>1309310709300003314</td>
                </tr>
                <tr>
                  <td style="text-align:right;">增 值 税 号 ：</td>
                  <td>340822746777596</td>
                </tr>
                <tr>
                  <td style="text-align:right;">邮 政 编 码 ：</td>
                  <td></td>
                </tr>
                <tr>
                  <td style="text-align:right;">电 子 邮 件 ：</td>
                  <td></td>
                </tr>
              </table>
            </div>
            <div class="contract4">
              <table style="margin-left:20px;">
                <tr style="text-align:center;">
                  <td colspan="3">需方</td>
                </tr>
                <tr>
                  <td style="text-align:right;">单位名称(章)：</td>
                  <td>芜湖新兴铸管有限责任公司</td>
                </tr>
                <tr>
                  <td style="text-align:right;">单位地址：</td>
                  <td>芜湖市三山区经济开发区春洲路2号</td>
                </tr>
                <tr>
                  <td style="text-align:right;">法定代表人 ：</td>
                  <td>刘涛</td>
                </tr>
                <tr>
                  <td style="text-align:right;">委托代理人 ：</td>
                  <td></td>
                </tr>
                <tr>
                  <td style="text-align:right;">电 话 ：</td>
                  <td>0553-5698511</td>
                </tr>
                <tr>
                  <td style="text-align:right;">传 真 ：</td>
                  <td>0553-5698531</td>
                </tr>
                <tr>
                  <td style="text-align:right;">开 户 银 行 ：</td>
                  <td>工行芜湖分行环城路支行</td>
                </tr>
                <tr>
                  <td style="text-align:right;">账 号 ：</td>
                  <td>1307023219000109264</td>
                </tr>
                <tr>
                  <td style="text-align:right;">增 值 税 号 ：</td>
                  <td>91340208748920392N</td>
                </tr>
                <tr>
                  <td style="text-align:right;">邮 政 编 码 ：</td>
                  <td>241081</td>
                </tr>
                <tr>
                  <td style="text-align:right;">电 子 邮 件 ：</td>
                  <td></td>
                </tr>
              </table>
            </div>
          </div>
          <div>
            <div class="contract3">
              <table style="margin-left:32px;">
                <tr>
                  <td style="text-align:right;">供方经办人：</td>
                  <td></td>
                </tr>
                <tr>
                  <td style="text-align:right;">签订日期：</td>
                  <td></td>
                </tr>
              </table>
            </div>
            <div class="contract4">
              <table style="margin-left:32px;">
                <tr>
                  <td style="text-align:right;">需方经办人：</td>
                  <td></td>
                </tr>
                <tr>
                  <td style="text-align:right;">签订日期：</td>
                  <td></td>
                </tr>
              </table>
            </div>
          </div>
          <div style="min-height:100px">
            <span style="margin-left:30px;"
              >备注：本合同所列双方商务信息经签章确认，将作为双方合同权利义务通知及法律诉讼文书送达的有效地址，未经向对方书面告知不得单方变更。</span
            >
          </div>
        </div>
      </div>
    </section>
  </a-card>
</template>

<script>
import ACol from 'ant-design-vue/es/grid/Col'
import ARow from 'ant-design-vue/es/grid/Row'
import ATextarea from 'ant-design-vue/es/input/TextArea'
import { getAction } from '@/api/manage'
export default {
  name: 'ContractPrint',
  components: {
    ATextarea,
    ARow,
    ACol
  },
  props: {
    reBizCode: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 }
      },
      previewVisible: false,
      previewImage: '',
      tlist:[],
      url: {
        templatelist: '/chargsearch/chargsearch/templatelist',
        loadRegisterFiles: '/sps/register/getRegisterFilesConfig'
      }
    }
  },
  created() {
    let id = this.$route.query.data.id 
    this.templatelist(id)
  },
  methods: {

    templatelist(id) {
      getAction(this.url.templatelist, { cid: id }).then(res => {
        if (res.success) {
          this.tlist=res.result
        }
      })
    }
  }
}
</script>

<style scoped>
/*update_begin author:scott date:20191203 for:打印机打印的字体模糊问题 */
* {
  color: #000000 !important;
  -webkit-tap-highlight-color: #000000 !important;
}
/*update_end author:scott date:20191203 for:打印机打印的字体模糊问题 */

.abcdefg .ant-card-body {
  margin-left: 0%;
  margin-right: 0%;
  margin-bottom: 1%;
  border: 0px solid black;
  min-width: 1200px;
  color: #000000 !important;
}
.explain {
  text-align: left;
  margin-left: 50px;
  color: #000000 !important;
}
.explain .ant-input,
.sign .ant-input {
  font-weight: bolder;
  text-align: center;
  border-left-width: 0px !important;
  border-top-width: 0px !important;
  border-right-width: 0px !important;
}
.explain div {
  margin-bottom: 10px;
}
.contract1 {
  text-align: left;
  height: inherit;
  font-size: 20px;
}
.contract2 {
  text-align: left;
  height: inherit;
  font-size: 20px;
  margin-left: 5%;
  margin-top: 20px;
}
.contract3{
  width:50%;
  float:left;
  border-right:1px solid #000;
  border-bottom:1px solid #000;
}
.contract4{
  width:50%;
  float:left;
  border-bottom:1px solid #000; 
}
</style>