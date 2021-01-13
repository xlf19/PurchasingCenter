<template>
  <a-card :bordered="false" :class="{ abcdefg: true }">
    <div class="no-print" style="text-align: right;">
      <a-button v-print="'#printContent'" ghost type="primary">打印</a-button>
    </div>
    <section ref="print" id="printContent" class="abcdefg">
      <div class="sign">
        <!-- 合同基本信息 -->
        <a-row>
          <a-col :span="24">
            <div style="text-align: center">
              <p style="font-size: 30px;font-weight: 800">{{ contract.contractTitle }}</p>
            </div>
          </a-col>
        </a-row>
        <a-row class="contract1">
          <a-col :span="12">
            <div style="margin-left:10%">
              供货单位：<span style="text-decoration:underline">{{ contract.supplierName }}</span>
            </div>
          </a-col>
          <a-col :span="12">
            <div style="margin-left:10%">
              合同编号：<span style="text-decoration:underline">{{ contract.contractNo }}</span>
            </div>
          </a-col>
        </a-row>
        <a-row style="margin-top: 20px" class="contract1">
          <a-col :span="12">
            <div style="margin-left:10%">
              需方单位：<span style="text-decoration:underline">{{ demandSideUnitlist.companyName }}</span>
            </div>
          </a-col>
          <a-col :span="12">
            <div style="margin-left:10%">
              签订地点：<span style="text-decoration:underline">{{ contract.placeSigning }}</span>
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
              备&#12288;&#12288;注：<span style="text-decoration:underline">{{ contract.contractNotes }}</span>
            </div>
          </a-col>
        </a-row>
        <a-row style="margin-top: 20px">
          <a-col :span="24" class="contract1">
            <table border="1" style="margin-left:5%;width:90%;text-align:center;">
              <tr style="text-align:center">
                <td>编号</td>
                <td colspan="3">产品名称</td>
                <td>质量指标</td>
              </tr>
              <tr style="text-align:center;">
                <td>{{ product.materialCode }}</td>
                <td colspan="3">{{ product.materialName }}</td>
                <td>{{ product.specificationModel }}</td>
              </tr>
              <tr style="text-align:center">
                <td>订货数量（吨）</td>
                <td>单价（元/千吨）</td>
                <td>税率（%）</td>
                <td>税金</td>
                <td>含税总金额（元）</td>
              </tr>
              <tr style="text-align:center;">
                <td>{{ product.quantity }}</td>
                <td>{{ product.unitPrice }}</td>
                <td>{{ contract.taxRate }}%</td>
                <td>{{ product.taxes }}</td>
                <td>{{ product.totalPrice }}</td>
              </tr>
              <tr>
                <td colspan="5" style="text-align:left;">&#12288;&#12288;合同金额大写：{{ money }}</td>
              </tr>
            </table>
          </a-col>
        </a-row>
        <a-row class="contract2" v-for="(item, index) in tlist" :key="index" :style="{'margin-top': (number >=12 ? '15px':'20px')}">
          <a-col :span="24">{{ item.templateTitle }}</a-col>
          <a-col :span="24" style="text-decoration:underline">{{ item.templateContent }} </a-col>
        </a-row>
        <!-- 供方、需方详情 -->
        <div
          style="text-align:left;height:inherit;font-size: 20px;margin-left:5%;margin-top: 20px;border:1px solid #000"
        >
          <div>
            <div class="contract3">
              <table style="margin-left:20px;">
                <tr style="text-align:center;">
                  <td colspan="3">供方</td>
                </tr>
                <tr>
                  <td style="text-align:right;">单位名称(章)：</td>
                  <td>{{ orgingal.companyName }}</td>
                </tr>
                <tr>
                  <td style="text-align:right;">单位地址：</td>
                  <td>{{ orgingal.unitAddress }}</td>
                </tr>
                <tr>
                  <td style="text-align:right;">法定代表人 ：</td>
                  <td>{{ orgingal.contactPerson }}</td>
                </tr>
                <tr>
                  <td style="text-align:right;">委托代理人 ：</td>
                  <td></td>
                </tr>
                <tr>
                  <td style="text-align:right;">电 话 ：</td>
                  <td>{{ orgingal.contactNumber }}</td>
                </tr>
                <tr>
                  <td style="text-align:right;">传 真 ：</td>
                  <td>{{ orgingal.contactFax }}</td>
                </tr>
                <tr>
                  <td style="text-align:right;">开 户 银 行 ：</td>
                  <td>{{ orgingal.bankName }}</td>
                </tr>
                <tr>
                  <td style="text-align:right;">账 号 ：</td>
                  <td>{{ orgingal.bankAccount }}</td>
                </tr>
                <tr>
                  <td style="text-align:right;">增 值 税 号 ：</td>
                  <td>{{ orgingal.creditCode }}</td>
                </tr>
                <tr>
                  <td style="text-align:right;">邮 政 编 码 ：</td>
                  <td>{{ orgingal.postalCode }}</td>
                </tr>
                <tr>
                  <td style="text-align:right;">电 子 邮 件 ：</td>
                  <td>{{ orgingal.email }}</td>
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
                  <td>{{ demandSideUnitlist.companyName }}</td>
                </tr>
                <tr>
                  <td style="text-align:right;">单位地址：</td>
                  <td>{{ demandSideUnitlist.unitAddress }}</td>
                </tr>
                <tr>
                  <td style="text-align:right;">法定代表人 ：</td>
                  <td>{{ demandSideUnitlist.legalRepresentative }}</td>
                </tr>
                <tr>
                  <td style="text-align:right;">委托代理人 ：</td>
                  <td></td>
                </tr>
                <tr>
                  <td style="text-align:right;">电 话 ：</td>
                  <td>{{ demandSideUnitlist.contactNumber }}</td>
                </tr>
                <tr>
                  <td style="text-align:right;">传 真 ：</td>
                  <td>{{ demandSideUnitlist.contactFax }}</td>
                </tr>
                <tr>
                  <td style="text-align:right;">开 户 银 行 ：</td>
                  <td>{{ demandSideUnitlist.bankName }}</td>
                </tr>
                <tr>
                  <td style="text-align:right;">账 号 ：</td>
                  <td>{{ demandSideUnitlist.bankAccount }}</td>
                </tr>
                <tr>
                  <td style="text-align:right;">增 值 税 号 ：</td>
                  <td>{{ demandSideUnitlist.creditCode }}</td>
                </tr>
                <tr>
                  <td style="text-align:right;">邮 政 编 码 ：</td>
                  <td>{{ demandSideUnitlist.postalCode }}</td>
                </tr>
                <tr>
                  <td style="text-align:right;">电 子 邮 件 ：</td>
                  <td>{{ demandSideUnitlist.email }}</td>
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
// import ACol from 'ant-design-vue/es/grid/Col'
// import ARow from 'ant-design-vue/es/grid/Row'
// import ATextarea from 'ant-design-vue/es/input/TextArea'
import { getAction } from '@/api/manage'
export default {
  name: 'ContractPrint',
  components: {
    // ATextarea,
    // ARow,
    // ACol
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
      tlist: [],
      contract: {},
      product: {},
      orgingal: {},
      demandSideUnitlist: {},
      number: 0,
      money: '',
      url: {
        templatelist: '/chargsearch/chargsearch/templatelist',
        contractid: '/contractpurchase/contractPurchase/queryById',
        productid: '/chargsearch/chargsearch/productId',
        orgingalid: '/original/originalCharge/queryById',
        demandSideUnitid: '/original/originalCharge/queryById'
      }
    }
  },
  created() {
    //合同id
    let id = this.$route.query.data.id
    let oid = this.$route.query.data.supplier
    let did = this.$route.query.data.demand_side_unit
    this.templatelist(id)
    this.contractid(id)
    this.productid(id)
    this.supplierDetails(oid)
    this.demandSideUnit(did)
  },
  methods: {
    //获取合同条款
    templatelist(id) {
      getAction(this.url.templatelist, { cid: id }).then(res => {
        if (res.success) {
          this.tlist = res.result
          this.number = res.result.length
        }
      })
    },
    //获取合同信息
    contractid(id) {
      getAction(this.url.contractid, { id: id }).then(res => {
        if (res.success) {
          this.contract = res.result
        }
      })
    },
    //获取产品信息
    productid(id) {
      getAction(this.url.productid, { cid: id }).then(res => {
        if (res.success) {
          this.product = res.result
          let moneyo = res.result.totalPrice
          this.money = this.smallToBig(moneyo)
        }
      })
    },
    //将小写金额转为大写金额
    smallToBig(money) {
      //  将数字金额转换为大写金额
      var cnNums = new Array('零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖') //汉字的数字
      var cnIntRadice = new Array('', '拾', '佰', '仟') //基本单位
      var cnIntUnits = new Array('', '万', '亿', '兆') //对应整数部分扩展单位
      var cnDecUnits = new Array('角', '分', '毫', '厘') //对应小数部分单位
      var cnInteger = '整' //整数金额时后面跟的字符
      var cnIntLast = '元' //整数完以后的单位
      //最大处理的数字
      var maxNum = 999999999999999.9999
      var integerNum //金额整数部分
      var decimalNum //金额小数部分
      //输出的中文金额字符串
      var chineseStr = ''
      var parts //分离金额后用的数组，预定义
      if (money == '') {
        return ''
      }

      money = parseFloat(money)
      if (money >= maxNum) {
        //超出最大处理数字
        return '超出最大处理数字'
      }
      if (money == 0) {
        chineseStr = cnNums[0] + cnIntLast + cnInteger
        return chineseStr
      }

      //四舍五入保留两位小数,转换为字符串
      money = Math.round(money * 100).toString()
      integerNum = money.substr(0, money.length - 2)
      decimalNum = money.substr(money.length - 2)

      //获取整型部分转换
      if (parseInt(integerNum, 10) > 0) {
        var zeroCount = 0
        var IntLen = integerNum.length
        for (var i = 0; i < IntLen; i++) {
          var n = integerNum.substr(i, 1)
          var p = IntLen - i - 1
          var q = p / 4
          var m = p % 4
          if (n == '0') {
            zeroCount++
          } else {
            if (zeroCount > 0) {
              chineseStr += cnNums[0]
            }
            //归零
            zeroCount = 0
            chineseStr += cnNums[parseInt(n)] + cnIntRadice[m]
          }
          if (m == 0 && zeroCount < 4) {
            chineseStr += cnIntUnits[q]
          }
        }
        chineseStr += cnIntLast
      }
      //小数部分
      if (decimalNum != '') {
        var decLen = decimalNum.length
        for (var i = 0; i < decLen; i++) {
          var n = decimalNum.substr(i, 1)
          if (n != '0') {
            chineseStr += cnNums[Number(n)] + cnDecUnits[i]
          }
        }
      }
      if (chineseStr == '') {
        chineseStr += cnNums[0] + cnIntLast + cnInteger
      } else if (decimalNum == '' || /^0*$/.test(decimalNum)) {
        chineseStr += cnInteger
      }
      return chineseStr
    },
    //获取供应商信息
    supplierDetails(oid) {
      getAction(this.url.orgingalid, { id: oid }).then(res => {
        if (res.success) {
          this.orgingal = res.result
        }
      })
    },
    //获取需方单位信息
    demandSideUnit(did) {
      getAction(this.url.demandSideUnitid, { id: did }).then(res => {
        if (res.success) {
          this.demandSideUnitlist = res.result
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
  font-family: 宋体;
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
.contract3 {
  width: 50%;
  float: left;
  border-right: 1px solid #000;
  border-bottom: 1px solid #000;
}
.contract4 {
  width: 50%;
  float: left;
  border-bottom: 1px solid #000;
}

</style>