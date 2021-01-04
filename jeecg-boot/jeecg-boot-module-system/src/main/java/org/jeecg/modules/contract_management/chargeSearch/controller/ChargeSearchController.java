package org.jeecg.modules.contract_management.chargeSearch.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.contract_management.chargeSearch.service.IChargeSearchService;
import org.jeecg.modules.contract_management.contract_status.service.IContractStatusService;

import org.jeecg.modules.contract_management.contractpurchase.entity.ContractSerach;
import org.jeecg.modules.contract_management.contractpurchase.service.IContractPurchaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * @Description: 原炉料查询
 * @Author: jeecg-boot
 * @Date: 2020-12-04
 * @Version: V1.0
 */
@Api(tags = "原炉料查询")
@RestController
@RequestMapping("/chargsearch/chargsearch")
@Slf4j
public class ChargeSearchController extends JeecgController<T, IChargeSearchService> {
    @Autowired
    private IChargeSearchService ichargeSearchService;

    @Autowired
    private IContractPurchaseService contractPurchaseService;

    @Autowired
    private IContractStatusService icontractStatusService;

    //分页列表查询
    @AutoLog(value = "原炉料表-分页列表查询")
    @ApiOperation(value = "原炉料表-分页列表查询", notes = "原炉料表-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> selectfldy(ContractSerach contractSerach,
                                @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                HttpServletRequest req) {

        String startingTime = contractSerach.getStartingTime();
        String stopTime = contractSerach.getStopTime();
        Page<Map<Object, String>> page = new Page<Map<Object, String>>(pageNo, pageSize);
        IPage<Map<Object, String>> pageList = ichargeSearchService.list(page, startingTime, stopTime);
        return Result.ok(pageList);
    }

    //分页列表查询
    @AutoLog(value = "原炉料表-产品明细查询")
    @ApiOperation(value = "原炉料表-产品明细查询", notes = "原炉料表-产品明细查询")
    @GetMapping(value = "/findDetail")
    public Result<?> findDetail(
            @RequestParam(name = "contractid") String contractid,
            HttpServletRequest req) {
        List<Map<Object, String>> list = ichargeSearchService.findDetail(contractid);
        return Result.ok(list);
    }

    /**
     * 通过合同id查询
     *
     * @param contractid
     * @return
     */
    @AutoLog(value = "产品信息-通过合同id查询")
    @ApiOperation(value = "产品信息-通过合同id查询", notes = "产品信息-通过合同id查询")
    @GetMapping(value = "/productid")
    public Result<?> productid(@RequestParam(name = "contractid", required = true) String contractid) {
        List<Map<Object, String>> productInformation = ichargeSearchService.productid(contractid);
        return Result.ok(productInformation);
    }

    /**
     * 通过产品id查询
     *
     * @param pid
     * @return
     */
    @AutoLog(value = "产品明细-通过产品id查询")
    @ApiOperation(value = "产品明细-通过产" +
            "品id查询", notes = "产品明细-通过产品id查询")
    @GetMapping(value = "/productidlist")
    public Result<?> productidlist(@RequestParam(name = "pid", required = true) String pid) {
        List<Map<Object, String>> productlist = ichargeSearchService.productidlist(pid);
        return Result.ok(productlist);
    }

    @AutoLog(value = "从指定网站下载数据")
    @ApiOperation(value = "从指定网站下载数据", notes = "从指定网站下载数据")
    @GetMapping(value = "/getHttpRequestData")
    public Result<?> getHttpRequestData() {
        // 首先抓取异常并处理
        String returnString = "1";
        String urlPath = "http://192.168.3.8:9036/app/returnhtbhs";
        try {
            // 代码实现以GET请求方式为主,POST跳过
            /** 1 GET方式请求数据 start*/
            // 1  创建URL对象,接收用户传递访问地址对象链接
            URL url = new URL(urlPath);

            // 2 打开用户传递URL参数地址
            HttpURLConnection connect = (HttpURLConnection) url.openConnection();

            // 3 设置HTTP请求的一些参数信息
            connect.setRequestMethod("GET"); // 参数必须大写
            connect.connect();

            // 4 获取URL请求到的数据，并创建数据流接收
            InputStream isString = connect.getInputStream();

            // 5 构建一个字符流缓冲对象,承载URL读取到的数据
            BufferedReader isRead = new BufferedReader(new InputStreamReader(isString));

            // 6 输出打印获取到的文件流
            String str = "";
            while ((str = isRead.readLine()) != null) {
                str = new String(str.getBytes(), "UTF-8"); //解决中文乱码问题
                returnString = str;
            }

            // 7 关闭流
            isString.close();
            connect.disconnect();

            // 8 JSON转List对象
            JSONObject pageData = JSONObject.parseObject(returnString);//网页数据
            String bs = pageData.getString("bs");//网页状态
            if (bs.equals("200")) {
                JSONArray pageArray = pageData.getJSONArray("data");//数据信息
                for (int i = 0; i < pageArray.size(); i++) {
                    JSONObject page = pageArray.getJSONObject(i);
                    String ysbianhao = page.getString("ysbianhao");
                    if (ysbianhao.substring(0, 2).equals("WX")) {
                        String htzt = page.getString("htzt");//合同状态
                        String SPHTCode = page.getString("bianhao");//
                        String SPLiuCheng = page.getString("htqsr");//
                        String SPNowState = page.getString("nowname");//
                        Integer SPId = page.getInteger("id");//
                        if (htzt.equals("退回")) {
                            String currentState = contractPurchaseService.findcontract(ysbianhao);
                            if (oConvertUtils.isEmpty(currentState)) {
                                continue;
                            } else if (currentState.equals("生成合同")) {
                                continue;
                            }
                        }
                        contractPurchaseService.updatecontract(ysbianhao, htzt, SPHTCode, SPLiuCheng, SPNowState, SPId);
                    }
                }
                updatehtstatus();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.ok(returnString);
    }

    //更改合同状态
    private void  updatehtstatus() {
        List<Map<Object, String>> list = icontractStatusService.listbh();
        for(Map<Object,String> map : list){
            //根据合同号改变合同状态
            String  htcode=map.get("HeTongCode");//合同号
            if(htcode.substring(0, 1).equals("W")){
                ichargeSearchService.updatestatus(htcode);
            }else if(htcode.substring(0, 1).equals("X")){
                icontractStatusService.updatecontract(htcode);
            }
            icontractStatusService.updateisdelete(htcode);
        }
    }

//
//    @AutoLog(value = "测试数据")
//    @ApiOperation(value = "测试数据", notes = "测试数据")
//    @GetMapping(value = "/gethttp")
//    public Result<?> gethttp() {
//        // 首先抓取异常并处理
//        String returnString = "1";
//        String urlPath = "http://192.168.3.8:9036/app/returnhtbhs";
//        try {
//            // 代码实现以GET请求方式为主,POST跳过
//            /** 1 GET方式请求数据 start*/
//            // 1  创建URL对象,接收用户传递访问地址对象链接
//            URL url = new URL(urlPath);
//
//            // 2 打开用户传递URL参数地址
//            HttpURLConnection connect = (HttpURLConnection) url.openConnection();
//
//            // 3 设置HTTP请求的一些参数信息
//            connect.setRequestMethod("GET"); // 参数必须大写
//            connect.connect();
//
//            // 4 获取URL请求到的数据，并创建数据流接收
//            InputStream isString = connect.getInputStream();
//
//            // 5 构建一个字符流缓冲对象,承载URL读取到的数据
//            BufferedReader isRead = new BufferedReader(new InputStreamReader(isString));
//
//            // 6 输出打印获取到的文件流
//            String str = "";
//            while ((str = isRead.readLine()) != null) {
//                str = new String(str.getBytes(), "UTF-8"); //解决中文乱码问题
//                returnString = str;
//            }
//
//            // 7 关闭流
//            isString.close();
//            connect.disconnect();
//
//            // 8 JSON转List对象
//            JSONObject pageData = JSONObject.parseObject(returnString);//网页数据
//            String bs = pageData.getString("bs");//网页状态
//            if (bs.equals("200")) {
//                JSONArray pageArray = pageData.getJSONArray("data");//数据信息
//                for (int i = 0; i < pageArray.size(); i++) {
//                    JSONObject page = pageArray.getJSONObject(i);
//                    System.out.println(page);
//                }
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return Result.ok(returnString);
//    }

}
