package org.jeecg.modules.contract.fuliao.controller;

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
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.contract.contract.entity.ContractInformation;
import org.jeecg.modules.contract.contract.service.IContractInformationService;
import org.jeecg.modules.contract.elements.entity.ContractElements;
import org.jeecg.modules.contract.elements.service.IContractElementsService;
import org.jeecg.modules.contract.fuliao.service.IFuLiaoService;
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

@Api(tags = "辅料结算管理")
@RestController
@RequestMapping("/feiliao/feiliao")
@Slf4j
public class FeiLiaoController extends JeecgController<T, IFuLiaoService> {
    @Autowired
    private IFuLiaoService flservice;
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
        contractInformation.setContractType("辅料");
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
        //获取合同信息
        Map<Object, Object> htonexx;
        BigDecimal dj =null;//物资单价
        BigDecimal sl =null;//SL  税率
        String wzcode="";//物资编码
        String model="";//规格型号
        if (hetongly.equals("物资合同")) {
            htonexx = htservice.finhtwzxx(htbh);
        } else {
            htonexx = htservice.finhtyllxx(htbh);
        }
        if(htonexx!=null){
            wzcode = htonexx.get("物资编码").toString();
            dj = new BigDecimal(htonexx.get("单价").toString());
            sl = new BigDecimal(htonexx.get("税率").toString());
            model=htonexx.get("规格型号").toString();
        }
        JSONArray htarray = ht.getJSONArray("htxx");//合同信息
        for (int i = 0; i < htarray.size(); i++) {
            JSONObject htone = htarray.getJSONObject(i);
            BigDecimal jyl = htone.getBigDecimal("JJ");//检验量
            String pgdh = htone.getString("派工单号");
            String data = htone.getString("取样日期");
            String dwname = htone.getString("供货单位");//供货单位
            String wzname = htone.getString("物资名称");//物资名称
            BigDecimal LD1 = htone.getBigDecimal("LD1");
            BigDecimal LD2 = htone.getBigDecimal("LD2");
            BigDecimal CaO = htone.getBigDecimal("CaO");
            BigDecimal MgO = htone.getBigDecimal("MgO");
            BigDecimal SiO2 = htone.getBigDecimal("SiO2");
            BigDecimal P = htone.getBigDecimal("P");
            BigDecimal S = htone.getBigDecimal("S");
            BigDecimal CaF2 = htone.getBigDecimal("CaF2");
            BigDecimal Al2O3 = htone.getBigDecimal("Al2O3");
            BigDecimal TFe = htone.getBigDecimal("TFe");
            BigDecimal TMn = htone.getBigDecimal("TMn");
            BigDecimal H2O = htone.getBigDecimal("H2O");
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
            cinfo.setSettlementIdentification(0);
            contractInformationService.save(cinfo);

            //添加合同元素表
            saveelement(uuid, htbh, pzh, "LD1", LD1);
            saveelement(uuid, htbh, pzh, "LD2",LD2);
            saveelement(uuid, htbh, pzh, "CaO", CaO);
            saveelement(uuid, htbh, pzh, "MgO", MgO);
            saveelement(uuid, htbh, pzh, "SiO2", SiO2);
            saveelement(uuid, htbh, pzh, "P", P);
            saveelement(uuid, htbh, pzh, "S", S);
            saveelement(uuid, htbh, pzh, "CaF2", CaF2);
            saveelement(uuid, htbh, pzh, "Al2O3", Al2O3);
            saveelement(uuid, htbh, pzh, "TFe", TFe);
            saveelement(uuid, htbh, pzh, "TMn", TMn);
            saveelement(uuid, htbh, pzh, "H2O", H2O);
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
