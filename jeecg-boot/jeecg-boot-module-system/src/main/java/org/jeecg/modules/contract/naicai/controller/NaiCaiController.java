package org.jeecg.modules.contract.naicai.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.contract.contract.entity.ContractInformation;
import org.jeecg.modules.contract.contract.service.IContractInformationService;
import org.jeecg.modules.contract.elements.entity.ContractElements;
import org.jeecg.modules.contract.elements.service.IContractElementsService;
import org.jeecg.modules.contract.hetong.service.IHeTongService;
import org.jeecg.modules.contract.naicai.service.INaiCaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.UUID;

@Api(tags = "耐材结算管理")
@RestController
@RequestMapping("/naicai/naicai")
@Slf4j
public class NaiCaiController extends JeecgController<T, INaiCaiService> {
    @Autowired
    private IContractInformationService contractInformationService;
    @Autowired
    private IContractElementsService htelements;

    @Autowired
    private IHeTongService htservice;
    @Autowired
    private INaiCaiService inaiCaiService;

    @AutoLog(value = "合同信息表-分页列表查询")
    @ApiOperation(value = "合同信息表-分页列表查询", notes = "合同信息表-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(ContractInformation contractInformation,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   @RequestParam(name = "hth", defaultValue = "") String hth,
                                   HttpServletRequest req) {
        contractInformation.setIsDelete(0);
        contractInformation.setContractType("耐材");
        contractInformation.setContractNo(hth);
        QueryWrapper<ContractInformation> queryWrapper = QueryGenerator.initQueryWrapper(contractInformation, req.getParameterMap());
        Page<ContractInformation> page = new Page<ContractInformation>(pageNo, pageSize);
        IPage<ContractInformation> pageList = contractInformationService.page(page, queryWrapper);
        return Result.ok(pageList);
    }

    //导入质检数据
    //青阳县盛宝非金属材料科技有限公司
    @AutoLog(value = "导入质检数据")
    @ApiOperation(value = "-添加", notes = "-添加")
    @PostMapping(value = "/htadd")
    public Result<?> htadd(@RequestBody String htxx) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        JSONObject ht = JSONObject.parseObject(htxx);
        String htbh = ht.getString("htbhs");//合同编号
        Integer pzh = ht.getInteger("pzh");//凭证号
        String shdw = ht.getString("shdw");//收货单位
        String hetongly = ht.getString("hetongly");//合同来源
        String wzcode=ht.getString("wzcode");//物资编码

        //获取合同信息
        Map<Object, Object> htonexx;
        BigDecimal dj =null;//物资单价
        BigDecimal sl =null;//SL  税率
        String model="";//规格型号
        if (hetongly.equals("物资合同")) {
            htonexx = htservice.findhtncwzxx(htbh,wzcode);
        } else {
            htonexx = htservice.findhtncyllxx(htbh,wzcode);
        }
        if(htonexx!=null){
            dj = new BigDecimal(htonexx.get("单价").toString());
            sl = new BigDecimal(htonexx.get("税率").toString());
            model=htonexx.get("规格型号").toString();
        }
        JSONArray htarray = ht.getJSONArray("htxx");//合同信息
        for (int i = 0; i < htarray.size(); i++) {
            JSONObject htone = htarray.getJSONObject(i);
            BigDecimal jyl = htone.getBigDecimal("JJ");//检验量
            String pgdh = htone.getString("派工单号");
            String beizhu = htone.getString("制样备注");//制样备注
            String data = htone.getString("riqi");//取样日期
            String dwname = htone.getString("供货单位");//供货单位
            String wzname = htone.getString("物资名称");//物资名称

            BigDecimal H2O = htone.getBigDecimal("H2O");
            BigDecimal CaO = htone.getBigDecimal("CaO");
            BigDecimal SiO2 = htone.getBigDecimal("SiO2");
            BigDecimal MgO = htone.getBigDecimal("MgO");
            BigDecimal C = htone.getBigDecimal("C");
            BigDecimal Al2O3 = htone.getBigDecimal("Al2O3");
            BigDecimal Ad = htone.getBigDecimal("Ad");
            BigDecimal Vdaf = htone.getBigDecimal("Vdaf");
            BigDecimal Std = htone.getBigDecimal("Std");
            BigDecimal Q = htone.getBigDecimal("Q");
            BigDecimal lidu50mm = htone.getBigDecimal("lidu50mm");//[粒度>50mm]
            BigDecimal lidu5mm = htone.getBigDecimal("lidu5mm");//[粒度<5mm]
            BigDecimal zhuoj = htone.getBigDecimal("zhuoj");//灼减
            BigDecimal P = htone.getBigDecimal("P");
            BigDecimal S = htone.getBigDecimal("S");
            BigDecimal CaF2 = htone.getBigDecimal("CaF2");
            BigDecimal TFe = htone.getBigDecimal("TFe");

            //添加合同信息表
            ContractInformation cinfo = new ContractInformation();
            String uuid = UUID.randomUUID().toString();
            cinfo.setId(uuid);
            cinfo.setWeighingDate(sdf.parse(data));
            cinfo.setMaterialCode(wzcode);
            cinfo.setContractNo(htbh);
            cinfo.setVoucherNo(pzh);
            cinfo.setContractType("辅料");
            cinfo.setContractPrice(dj);
            cinfo.setTaxRate(sl);
            cinfo.setReceivingUnit(shdw);
            cinfo.setSupplier(dwname);
            cinfo.setWeighing(jyl);
            cinfo.setMaterialName(wzname);
            cinfo.setIsDelete(0);
            cinfo.setWorkNumber(pgdh);
            cinfo.setModel(model);
            cinfo.setRemarks(beizhu);
            cinfo.setSettlementIdentification(0);
            contractInformationService.save(cinfo);

            //添加合同元素表
            saveelement(uuid, htbh, pzh, "H2O", H2O);
            saveelement(uuid, htbh, pzh, "CaO",CaO);
            saveelement(uuid, htbh, pzh, "SiO2", SiO2);
            saveelement(uuid, htbh, pzh, "MgO", MgO);
            saveelement(uuid, htbh, pzh, "C", C);
            saveelement(uuid, htbh, pzh, "Al2O3", Al2O3);
            saveelement(uuid, htbh, pzh, "Ad", Ad);
            saveelement(uuid, htbh, pzh, "Vdaf", Vdaf);
            saveelement(uuid, htbh, pzh, "Std", Std);
            saveelement(uuid, htbh, pzh, "Q", Q);
            saveelement(uuid, htbh, pzh, "[粒度>50mm]", lidu50mm);
            saveelement(uuid, htbh, pzh, "[粒度<5mm]", lidu5mm);
            saveelement(uuid, htbh, pzh, "灼减", zhuoj);
            saveelement(uuid, htbh, pzh, "P", P);
            saveelement(uuid, htbh, pzh, "S", S);
            saveelement(uuid, htbh, pzh, "CaF2", CaF2);
            saveelement(uuid, htbh, pzh, "TFe", TFe);
        }
        return Result.ok("添加成功！");
    }


    //导入合同元素表
    public void saveelement(String uuid, String htbh, Integer pzh, String name, BigDecimal ele) {
        ContractElements cele = new ContractElements();
        String uuids = UUID.randomUUID().toString();
        cele.setId(uuids);
        cele.setCiId(uuid);
        cele.setContractNo(htbh);
        cele.setVoucherNo(pzh);
        cele.setElement(name);
        cele.setElelmentDate(ele);
        cele.setIsDelete(0);
        htelements.save(cele);
    }

    //打印查询列表
    @AutoLog(value = "打印查询列表")
    @ApiOperation(value="打印查询列表", notes="打印查询列表")
    @GetMapping(value = "/selectncdy")
    public Result<?> selectncdy(ContractInformation contractInformation,
                                @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                HttpServletRequest req) {
        Page<Map<Object, String>> page = new Page<Map<Object, String>>(pageNo, pageSize);
        IPage<Map<Object, String>> pageList = inaiCaiService.selectncdy(page, contractInformation.getContractNo(),contractInformation.getVoucherNo());
        return Result.ok(pageList);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param contractInformation
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, T contractInformation) {
        return super.exportXls(request, contractInformation, T.class, "合同信息表");
    }

}
