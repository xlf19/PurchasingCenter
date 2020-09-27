package org.jeecg.modules.contract.Settlement.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.contract.Settlement.service.impl.shengtieAccountService;
import org.jeecg.modules.contract.contract.entity.ContractInformation;

import org.jeecg.modules.contract.elements.entity.ContractElements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;


import java.util.List;
import java.util.Map;

/**
 * author: wzs
 * Date:2020/9/159:47
 **/
@Api(tags = "生铁结算")
@RestController
@RequestMapping("/settleAccounts/settleAccounts")
public class shengtieAccountController {

  @Autowired
  private shengtieAccountService shengtie;

  //获取合同号
  @AutoLog(value = "获取生铁相似合同号")
  @ApiOperation(value="获取生铁相似合同号", notes="获取生铁相似合同号")
  @PostMapping(value = "/contractNos")
  public Result<?> findContractNo(@RequestBody String contractNo){

    JSONObject ht = JSONObject.parseObject(contractNo);
    List<Map<Object,String>> list = shengtie.findHthList(ht.getString("contractNo"),ht.getString("contracttype"));
    return Result.ok(list);
  }

  //通过合同号找相应的凭证号
  @AutoLog(value = "获取同一合同号下的多个凭证号")
  @ApiOperation(value="获取同一合同号下的多个凭证号", notes="获取同一合同号下的多个凭证号")
  @PostMapping(value = "/voucherNos")
  public Result<?> findvoucherNo(@RequestBody String contractNo1){
    JSONObject cno1 = JSONObject.parseObject(contractNo1);
    System.out.println(contractNo1);
    System.out.println(cno1.getString("contractNo1"));
    List<Map<Object,String>> list = shengtie.findVoucherNo(cno1.getString("contractNo1"));
    return Result.ok(list);
  }

  //根据合同编号和凭证号查询数据列表
  @AutoLog(value = "根据合同编号和凭证号查询数据列表")
  @ApiOperation(value = "根据合同编号和凭证号查询数据列表", notes = "根据合同编号和凭证号查询数据列表")
  @GetMapping(value = "/contractList")
  public Result<?> contractList(
      @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
      @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
      @RequestParam(name = "cno", defaultValue = "") String cno,
      @RequestParam(name = "vno", defaultValue = "") String vno
  ) {

    Page<Map<Object, String>> page = new Page<Map<Object, String>>(pageNo, pageSize);
    IPage<List<ContractInformation>> pageList = shengtie.findContractList(page,cno, vno);
    return Result.ok(pageList);
  }

  //前端展开行事件动态获取对应合同表中的元素
  @AutoLog(value = "前端展开行事件动态获取对应合同表中的元素")
  @ApiOperation(value="前端展开行事件动态获取对应合同表中的元素", notes="前端展开行事件动态获取对应合同表中的元素")
  @PostMapping(value = "/elementList")
  public Result<?> findContractElement(@RequestBody String id){
    JSONObject cno = JSONObject.parseObject(id);
    System.out.println(cno);
    System.out.println(id);
    List<ContractElements> list = shengtie.findContractElement(cno.getString("id"));
    return Result.ok(list);
  }

  //合同结算
  @AutoLog(value = "合同结算")
  @ApiOperation(value = "合同结算", notes = "合同结算")
  @PostMapping(value = "/settlements")
  public Result<?> settlement(@RequestBody String id) {

    JSONObject cid = JSONObject.parseObject(id);
    JSONArray conid = cid.getJSONArray("id");
    String[] ci_id = new String[conid.size()];
    for (int j=0; j<conid.size(); j++) {
      System.out.println(conid.get(j));
      ci_id[j] = (String)conid.get(j);
    }

    List<ContractInformation> contractInformations = shengtie.contractAccount(ci_id);
    if(contractInformations ==null){
      return Result.error("结算失败！");
    }
    return Result.ok(contractInformations);
  }


}
