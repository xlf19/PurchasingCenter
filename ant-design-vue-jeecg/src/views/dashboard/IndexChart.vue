<template>
  <div class="page-header-index-wide">
    <a-row :gutter="24" style="margin-left: 1px; margin-right: 1px">
      <a-list :dataSource="visitSetInfo" :grid="{ gutter: 16, column: 4 }">
        <a-list-item slot="renderItem" slot-scope="item">
          <chart-card :loading="loading" :style="{background: item.colorBg}" :title="item.name" :total="item.allnum + ''">
            <a-tooltip :title="item.introInfo" slot="action">
              <a-icon type="info-circle-o" />
            </a-tooltip>
            <template slot="footer">
              <trend flag="down" style="margin-right: 16px">
                <span slot="term">本月</span>
                {{ item.monthallnum }}
              </trend>
              <trend flag="up">
                <span slot="term">最近一周</span>
                {{ item.weekallnum }}
              </trend>
            </template>
          </chart-card>
        </a-list-item>
      </a-list>
    </a-row>

    <a-row :gutter="24" style="margin-left: 1px; margin-right: 1px">
      <a-row>
        <a-col :span="24">
          <a-card :loading="loading" :bordered="false" title="本月登录次数排行榜TOP 6" :style="{ marginTop: '24px' }">
            <div style="background: #ececec; padding: 30px">
              <a-row :gutter="16">
                <a-list :dataSource="visitTopSixInfo" :grid="{ gutter: 16, column: 6 }">
                  <a-list-item slot="renderItem" slot-scope="item">
                    <a-card>
                      <a-statistic
                        :title="getRealname(item.userid)"
                        :value="item.visit"
                        :precision="0"
                        suffix="次"
                        :valueStyle="{ color: '#3f8600' }"
                        style="margin-right: 50px"
                      >
                        <template v-slot:prefix>
                          <a-icon type="arrow-up" />
                        </template>
                      </a-statistic>
                    </a-card>
                    <img class="loginNumBg" :src="item.imgbg" v-if="item.imgbg !=''">
                  </a-list-item>
                </a-list>
              </a-row>
            </div>
          </a-card>
        </a-col>
      </a-row>
    </a-row>

    <a-row>
      <a-col :span="24">
        <a-card :loading="loading" :bordered="false" title="最近一周访问量统计" :style="{ marginTop: '24px' }">
          <a-row>
            <a-col :span="6">
              <head-info title="今日IP" :content="loginfo.todayIp"></head-info>
            </a-col>
            <a-col :span="2">
              <a-spin class="circle-cust">
                <a-icon slot="indicator" type="environment" style="font-size: 24px" />
              </a-spin>
            </a-col>
            <a-col :span="6">
              <head-info title="今日访问" :content="loginfo.todayVisitCount"></head-info>
            </a-col>
            <a-col :span="2">
              <a-spin class="circle-cust">
                <a-icon slot="indicator" type="team" style="font-size: 24px" />
              </a-spin>
            </a-col>
            <a-col :span="6">
              <head-info title="总访问量" :content="loginfo.totalVisitCount"></head-info>
            </a-col>
            <a-col :span="2">
              <a-spin class="circle-cust">
                <a-icon slot="indicator" type="rise" style="font-size: 24px" />
              </a-spin>
            </a-col>
          </a-row>
          <line-chart-multid :fields="visitFields" :dataSource="visitInfo"></line-chart-multid>
        </a-card>
      </a-col>
    </a-row>
  </div>
</template>

<script>
import ChartCard from '@/components/ChartCard'
import ACol from 'ant-design-vue/es/grid/Col'
import ATooltip from 'ant-design-vue/es/tooltip/Tooltip'
import MiniArea from '@/components/chart/MiniArea'
import MiniBar from '@/components/chart/MiniBar'
import MiniProgress from '@/components/chart/MiniProgress'
import RankList from '@/components/chart/RankList'
import Bar from '@/components/chart/Bar'
import LineChartMultid from '@/components/chart/LineChartMultid'
import HeadInfo from '@/components/tools/HeadInfo.vue'
import Trend from '@/components/Trend'
import { getLoginfo, getVisitInfo, getVisitTopSixInfo, getVisitSetInfo } from '@/api/api'
import { initDictOptions, filterDictText } from '@/components/dict/JDictSelectUtil'

const barData = []
for (let i = 0; i < 12; i += 1) {
  barData.push({
    x: `${i + 1}月`,
    y: Math.floor(Math.random() * 1000) + 200,
  })
}
export default {
  name: 'IndexChart',
  components: {
    ATooltip,
    ACol,
    ChartCard,
    MiniArea,
    MiniBar,
    MiniProgress,
    RankList,
    Bar,
    Trend,
    LineChartMultid,
    HeadInfo,
  },
  data() {
    return {
      principalDictOptions: [],
      loading: true,
      center: null,
      barData,
      loginfo: {},
      visitFields: ['ip', 'visit'],
      visitInfo: [],
      visitTopSixInfo: [],
      visitSetInfo: [],
      indicator: <a-icon type="loading" style="font-size: 24px" spin />,
      img1:require('../../assets/1.png'),
      img2:require('../../assets/2.png'),
      img3:require('../../assets/3.png'),
      // imgArr:[img1,img2,img3]
    }
  },
  created() {
    setTimeout(() => {
      this.loading = !this.loading
    }, 1000)
    this.initLogInfo()
    //初始化字典 - 项目状态
    initDictOptions('sys_user,realname,username').then((res) => {
      if (res.success) {
        this.principalDictOptions = res.result
      }
    })
  },
  methods: {
    handleColor(index) {
      if (index % 2 == 0) {
        return 'rgb(19, 194, 194)'
      } else {
        return 'red'
      }
    },
    getRealname(text) {
      return filterDictText(this.principalDictOptions, text)
    },
    initLogInfo() {
      getLoginfo(null).then((res) => {
        if (res.success) {
          Object.keys(res.result).forEach((key) => {
            res.result[key] = res.result[key] + ''
          })
          this.loginfo = res.result
        }
      })
      getVisitInfo().then((res) => {
        if (res.success) {
          this.visitInfo = res.result
        }
      })
      getVisitTopSixInfo().then((res) => {
        if (res.success) {
          // console.log(res.result)
          let TopSixInfo = []
          let colorBg = ['#e4f3e3','#45b97c','#afb4db','#faa755']
          res.result.forEach((item,idx) =>{
            let x = {}
            x.userid = item.userid
            x.visit = item.visit
            x.imgbg = idx == 0 ? this.img1 : idx == 1 ? this.img2 : idx == 2 ? this.img3:''
            TopSixInfo.push(x)
          })
          console.log(TopSixInfo)
          this.visitTopSixInfo = TopSixInfo
        }
      })
      getVisitSetInfo().then((res) => {
        if (res.success) {
          // console.log(res.result)
          let InfoArr = []
          let colorBg = ['#e4f3e3','#45b97c','#afb4db','#faa755']
          let introInfo = ['合同总数','结算成功','结算失败','未结算']
          res.result.forEach((item,idx) =>{
            let x = {}
            x.allnum = item.allnum
            x.monthallnum = item.monthallnum
            x.name = item.name
            x.colorBg = colorBg[idx]
            x.introInfo = introInfo[idx]
            x.weekallnum = item.weekallnum
            InfoArr.push(x)
          })
          // console.log(InfoArr)
          this.visitSetInfo = InfoArr
        }
      })
    },
  },
}
</script>

<style lang="less" scoped>
.circle-cust {
  position: relative;
  top: 28px;
  left: -100%;
}
.extra-wrapper {
  line-height: 55px;
  padding-right: 24px;

  .extra-item {
    display: inline-block;
    margin-right: 24px;

    a {
      margin-left: 24px;
    }
  }
}

/* 首页访问量统计 */
.head-info {
  position: relative;
  text-align: left;
  padding: 0 32px 0 0;
  min-width: 125px;

  &.center {
    text-align: center;
    padding: 0 32px;
  }

  span {
    color: rgba(0, 0, 0, 0.45);
    display: inline-block;
    font-size: 0.95rem;
    line-height: 42px;
    margin-bottom: 4px;
  }
  p {
    line-height: 42px;
    margin: 0;
    a {
      font-weight: 600;
      font-size: 1rem;
    }
  }

}
.loginNumBg{
    width: 60px;
    position: absolute;
    height: 60px;
    top: 2px;
    right: 9px;
}
</style>
<style lang="less">
.page-header-index-wide{
  .chart-card-title{
    font-weight: 700;
    color: black;
  }
  .ant-card-head-title{
    font-weight: 700;
    color: black;
  }
  .ant-card-head{
    background: #fffef9;
    padding-left: 20px;
  }
}
</style>