package org.jeecg.modules.contract.hejin.controller;

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
import org.jeecg.modules.contract.hejin.service.IHeJinService;
import org.jeecg.modules.contract.hetong.service.IHeTongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.UUID;

@Api(tags="合金信息")
@RestController
@RequestMapping("/hejin/hejin")
@Slf4j
public class HeJinController extends JeecgController<T, IHeJinService> {

    @Autowired
    private IContractInformationService contractInformationService;
    @Autowired
    private IContractElementsService htelements;

    @Autowired
    private IHeTongService htservice;

    @AutoLog(value = "合同信息表-分页列表查询")
    @ApiOperation(value = "合同信息表-分页列表查询", notes = "合同信息表-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(ContractInformation contractInformation,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   @RequestParam(name = "hth", defaultValue = "") String hth,
                                   HttpServletRequest req) {
        contractInformation.setIsDelete(0);
        contractInformation.setContractType("合金");
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
            String dwname = htone.getString("ghdw");//供货单位
            String wzname = htone.getString("wzname");//物资名称
            String data = htone.getString("riqi");//取样日期
            BigDecimal jyl = htone.getBigDecimal("JJ");//检验量
            String hybz = htone.getString("hybz");//化验备注
            String pgdh = htone.getString("pgdh");//派工单号
            //添加合同信息表
            ContractInformation cinfo = new ContractInformation();
            String uuid = UUID.randomUUID().toString();
            cinfo.setId(uuid);
            cinfo.setWeighingDate(sdf.parse(data));
            cinfo.setMaterialCode(wzcode);
            cinfo.setContractNo(htbh);
            cinfo.setVoucherNo(pzh);
            cinfo.setContractType("合金");
            cinfo.setContractPrice(dj);
            cinfo.setTaxRate(sl);
            cinfo.setReceivingUnit(shdw);
            cinfo.setSupplier(dwname);
            cinfo.setWeighing(jyl);
            cinfo.setMaterialName(wzname);
            cinfo.setIsDelete(0);
            cinfo.setWorkNumber(pgdh);
            cinfo.setModel(model);
            cinfo.setRemarks(hybz);
            contractInformationService.save(cinfo);
            //添加合同元素表
            if(htone.containsKey("wzshang")){
                BigDecimal  LD1 = htone.getBigDecimal("wzshang");
                saveelement(uuid, htbh, pzh, "LD1", LD1);
            }
            if(htone.containsKey("wzxia")){
                BigDecimal  LD2 = htone.getBigDecimal("wzxia");
                saveelement(uuid, htbh, pzh, "LD2",LD2);
            }
            if(htone.containsKey("SIO2")){
                BigDecimal SIO2 = htone.getBigDecimal("SIO2");
                saveelement(uuid, htbh, pzh, "SIO2", SIO2);
            }
            if(htone.containsKey("Ca")){
                BigDecimal Ca = htone.getBigDecimal("Ca");
                saveelement(uuid, htbh, pzh, "Ca", Ca);
            }
            if(htone.containsKey("P")){
                BigDecimal P = htone.getBigDecimal("P");
                saveelement(uuid, htbh, pzh, "P", P);
            }
            if(htone.containsKey("S")){
                BigDecimal S = htone.getBigDecimal("S");
                saveelement(uuid, htbh, pzh, "S", S);
            }
            if(htone.containsKey("SiC")){
                BigDecimal SiC = htone.getBigDecimal("SiC");
                saveelement(uuid, htbh, pzh, "SiC", SiC);
            }

            if(htone.containsKey("Cu")){
                BigDecimal Cu = htone.getBigDecimal("Cu");
                saveelement(uuid, htbh, pzh, "Cu", Cu);
            }
            if(htone.containsKey("C")){
                BigDecimal  C = htone.getBigDecimal("C");
                saveelement(uuid, htbh, pzh, "C", C);
            }

            if(htone.containsKey("Fe")){
                BigDecimal Fe = htone.getBigDecimal("Fe");
                saveelement(uuid, htbh, pzh, "Fe", Fe);
            }
            if(htone.containsKey("Al")){
                BigDecimal Al = htone.getBigDecimal("Al");
                saveelement(uuid, htbh, pzh, "Al", Al);
            }
            if(htone.containsKey("Te")){
                BigDecimal Te = htone.getBigDecimal("Te");
                saveelement(uuid, htbh, pzh, "Te", Te);
            }
            if(htone.containsKey("Ba")){
                BigDecimal Ba = htone.getBigDecimal("Ba");
                saveelement(uuid, htbh, pzh, "Ba", Ba);
            }
            if(htone.containsKey("H2O")){
                BigDecimal H2O = htone.getBigDecimal("H2O");
                saveelement(uuid, htbh, pzh, "H2O", H2O);
            }
            if(htone.containsKey("TFE")){
                BigDecimal TFE = htone.getBigDecimal("TFE");
                saveelement(uuid, htbh, pzh, "TFE", TFE);
            }
            if(htone.containsKey("AL2O3")){
                BigDecimal AL2O3 = htone.getBigDecimal("AL2O3");
                saveelement(uuid, htbh, pzh, "AL2O3", AL2O3);
            }
            if(htone.containsKey("MnO")){
                BigDecimal MnO = htone.getBigDecimal("MnO");
                saveelement(uuid, htbh, pzh, "MnO", MnO);
            }
            if(htone.containsKey("TiO2")){
                BigDecimal TiO2 = htone.getBigDecimal("TiO2");
                saveelement(uuid, htbh, pzh, "TiO2", TiO2);
            }
            if(htone.containsKey("CaF2")){
                BigDecimal CaF2 = htone.getBigDecimal("CaF2");
                saveelement(uuid, htbh, pzh, "CaF2", CaF2);
            }
            if(htone.containsKey("MgO")){
                BigDecimal MgO = htone.getBigDecimal("MgO");
                saveelement(uuid, htbh, pzh, "MgO", MgO);
            }
            if(htone.containsKey("CAO")){
                BigDecimal CAO = htone.getBigDecimal("CAO");
                saveelement(uuid, htbh, pzh, "CAO", CAO);
            }
            if(htone.containsKey("Ad")){
                BigDecimal Ad = htone.getBigDecimal("Ad");
                saveelement(uuid, htbh, pzh, "Ad", Ad);
            }
            if(htone.containsKey("Std")){
                BigDecimal Std = htone.getBigDecimal("Std");
                saveelement(uuid, htbh, pzh, "Std", Std);
            }
            if(htone.containsKey("Vdaf")){
                BigDecimal Vdaf = htone.getBigDecimal("Vdaf");
                saveelement(uuid, htbh, pzh, "Vdaf", Vdaf);
            }
            if(htone.containsKey("Q")){
                BigDecimal Q = htone.getBigDecimal("Q");
                saveelement(uuid, htbh, pzh, "Q", Q);
            }
            if(htone.containsKey("N")){
                BigDecimal N = htone.getBigDecimal("N");
                saveelement(uuid, htbh, pzh, "N", N);
            }
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
}
