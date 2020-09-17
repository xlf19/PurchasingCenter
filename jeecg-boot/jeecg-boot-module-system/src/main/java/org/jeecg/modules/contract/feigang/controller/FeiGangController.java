package org.jeecg.modules.contract.feigang.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.contract.contract.entity.ContractInformation;
import org.jeecg.modules.contract.contract.service.IContractInformationService;
import org.jeecg.modules.contract.elements.service.IContractElementsService;
import org.jeecg.modules.contract.feigang.service.IFeiGangService;
import org.jeecg.modules.contract.hetong.service.IHeTongService;
import org.jeecg.modules.contract.zhijianxinxi.service.IZhiJianXinXiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.UUID;

@Api(tags = "multi-datasource2燃料结算管理")
@RestController
@RequestMapping("/feigang/feigang")
@Slf4j
public class FeiGangController extends JeecgController<T, IFeiGangService> {


    @Autowired
    private IContractInformationService contractInformationService;

    @AutoLog(value = "合同信息表-分页列表查询")
    @ApiOperation(value = "合同信息表-分页列表查询", notes = "合同信息表-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(ContractInformation contractInformation,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   @RequestParam(name = "hth", defaultValue = "") String hth,
                                   HttpServletRequest req) {
        contractInformation.setIsDelete(0);
        contractInformation.setContractType("废钢");
        contractInformation.setContractNo(hth);
        QueryWrapper<ContractInformation> queryWrapper = QueryGenerator.initQueryWrapper(contractInformation, req.getParameterMap());
        Page<ContractInformation> page = new Page<ContractInformation>(pageNo, pageSize);
        IPage<ContractInformation> pageList = contractInformationService.page(page, queryWrapper);
        return Result.ok(pageList);
    }


    //导入质检数据
    @AutoLog(value = "导入质检数据")
    @ApiOperation(value = "-添加", notes = "-添加")
    @PostMapping(value = "/htadd")
    public Result<?> htadd(@RequestBody String htxx) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        JSONObject ht = JSONObject.parseObject(htxx);
        Integer pzh = ht.getInteger("pzh");//凭证号
        JSONArray htarray = ht.getJSONArray("htxx");
        for (int i = 0; i < htarray.size(); i++) {
            JSONObject htone = htarray.getJSONObject(i);
            String htbh = htone.getString("HeTongBianHao");//合同编号
            //物资编码
            String wzcode = htone.get("WZCode").toString();
            //物资名称
            String wzname = htone.get("WZName").toString();
            String ghdw = htone.getString("DanWeiName");//供货单位
            String shdw = htone.getString("shdw");//收货单位
            //物资单价
            BigDecimal dj = new BigDecimal(htone.get("WZDanJia").toString());
             //税金
            BigDecimal sj = new BigDecimal(htone.get("WZShuiJin").toString());

            //SL
            BigDecimal sl = new BigDecimal(htone.get("ShuiLv").toString());
            //HS
            String hs = htone.getString("HanShuiBiaoJi");

            //添加合同信息表
            ContractInformation cinfo = new ContractInformation();
            String uuid = UUID.randomUUID().toString();
            cinfo.setId(uuid);
            cinfo.setContractNo(htbh);
            cinfo.setVoucherNo(pzh);
            cinfo.setMaterialCode(wzcode);
            cinfo.setMaterialName(wzname);
            cinfo.setContractType("废钢");
            cinfo.setSupplier(ghdw);
            cinfo.setReceivingUnit(shdw);
            cinfo.setContractPrice(dj);
            cinfo.setTaxes(sj);
            cinfo.setTaxRate(sl);
            if(hs.equals("含税")){
                cinfo.setTaxIncluded(true);
            }else{
                cinfo.setTaxIncluded(false);
            }
            cinfo.setIsDelete(0);
            contractInformationService.save(cinfo);
        }
        return Result.ok("添加成功！");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "合同信息表-通过id查询")
    @ApiOperation(value = "合同信息表-通过id查询", notes = "合同信息表-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        ContractInformation contractInformation = contractInformationService.getById(id);
        if (contractInformation == null) {
            return Result.error("未找到对应数据");
        }
        return Result.ok(contractInformation);
    }

    /**
     * 编辑
     *
     * @param contractInformation
     * @return
     */
    //LoginUser(username=adminphone=18611111111, status=1, delFlag=0, activitiSync=1, createTime=Mon Jun 21 17:54:10 CST 2038, userIdentity=2, departIds=c6d7cb4deeac411cb3384b1b31278596, post=总经理, telephone=null, relTenantIds=)
    @AutoLog(value = "合同信息表-编辑")
    @ApiOperation(value = "合同信息表-编辑", notes = "合同信息表-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody ContractInformation contractInformation) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        contractInformation.setClearingHouse(sysUser.getUsername());//结算人
        BigDecimal dj=contractInformation.getContractPrice();//单价
        BigDecimal jz=contractInformation.getSettlementQuantity();//净重
        BigDecimal sl=contractInformation.getTaxRate();//税率
        BigDecimal cs = new BigDecimal("100");//
        BigDecimal cs1 = new BigDecimal("1");//常数
        BigDecimal je=dj.multiply(jz);//结算金额
        BigDecimal sj=je.multiply(sl).divide(cs);//结算税金
        BigDecimal sll=cs1.add(sl.divide(cs));//税率1
        if(contractInformation.getTaxIncluded()){
            contractInformation.setSettlementResults(je);
            contractInformation.setTaxes(sj.multiply(sll));
        }else{
            contractInformation.setSettlementResults(je.multiply(sll));
            contractInformation.setTaxes(sj);
        }
        contractInformation.setSettlementIdentification(1);
        contractInformationService.updateById(contractInformation);
        return Result.ok("编辑成功!");
    }

}
