package org.jeecg.modules.contract.shengtie.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.contract.contract.entity.ContractInformation;
import org.jeecg.modules.contract.contract.service.IContractInformationService;
import org.jeecg.modules.contract.elements.entity.ContractElements;
import org.jeecg.modules.contract.elements.service.IContractElementsService;
import org.jeecg.modules.contract.hetong.service.IHeTongService;
import org.jeecg.modules.contract.shengtie.service.IShengTieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import javax.lang.model.util.Elements;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.Element;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Api(tags = "生铁结算")
@RestController
@RequestMapping("/shengtie/shengtie")
@Slf4j
public class ShengTieController extends JeecgController<ContractElements, IShengTieService> {

    @Autowired
    private IShengTieService shengTieService;

    @Autowired
    private IContractInformationService contractInformationService;

    @Autowired
    private IHeTongService htservice;

    @Autowired
    private IContractElementsService htelements;

    /**
     * 分页列表查询
     *
     * @param contractInformation
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "合同信息表-分页列表查询")
    @ApiOperation(value = "合同信息表-分页列表查询", notes = "合同信息表-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(ContractInformation contractInformation,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   @RequestParam(name = "hth", defaultValue = "") String hth,
                                   HttpServletRequest req) {
        contractInformation.setIsDelete(0);
        contractInformation.setContractType("生铁");
        contractInformation.setContractNo(hth);
        QueryWrapper<ContractInformation> queryWrapper = QueryGenerator.initQueryWrapper(contractInformation, req.getParameterMap());
        Page<ContractInformation> page = new Page<ContractInformation>(pageNo, pageSize);
        IPage<ContractInformation> pageList = contractInformationService.page(page, queryWrapper);
        return Result.ok(pageList);
    }

    @AutoLog(value = "生铁结算元素表")
    @ApiOperation(value = "生铁结算元素表", notes = "生铁结算元素表")
    @GetMapping(value = "/elementslist")
    public Result<?> findList(@RequestParam(name = "cid", defaultValue = "") String cid, HttpServletRequest req) {
        List<ContractElements> list = shengTieService.findList(cid);
        return Result.ok(list);
    }

    //删除
    @AutoLog(value = "合同元素表-通过id删除")
    @ApiOperation(value = "合同元素表-通过id删除", notes = "合同元素表-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        ContractInformation contractInformation = contractInformationService.getById(id);
        contractInformation.setIsDelete(1);
        contractInformationService.updateById(contractInformation);
        shengTieService.updateElelist(id);
        return Result.ok("删除成功!");
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
        //获取合同信息
        Map<Object, Object> htonexx = htservice.finOnehtxx(htbh);
        //HS
        Boolean hs = (Boolean) htonexx.get("HanShuiBiaoJi");
        //合同号
        //String hetongbh=htonexx.get("HeTongBianHao").toString();
        //物资单价
        BigDecimal dj = new BigDecimal(htonexx.get("WZDanJia").toString());
        //SL
        BigDecimal sl = new BigDecimal(htonexx.get("ShuiLv").toString());
        //物资名称
        String wzname = htonexx.get("WZName").toString();
        //物资编码
        String wzcode = htonexx.get("WZCode").toString();
        JSONArray htarray = ht.getJSONArray("htxx");

        for (int i = 0; i < htarray.size(); i++) {
            JSONObject htone = htarray.getJSONObject(i);
            BigDecimal p = htone.getBigDecimal("P");
            BigDecimal S = htone.getBigDecimal("S");
            BigDecimal Si = htone.getBigDecimal("Si");
            BigDecimal Mn = htone.getBigDecimal("Mn");
            BigDecimal jyl = htone.getBigDecimal("检验量");
            String data = htone.getString("取样日期");
            String ghdw = htone.getString("supplier");//供货单位

            //添加合同信息表
            ContractInformation cinfo = new ContractInformation();
            String uuid = UUID.randomUUID().toString();
            cinfo.setId(uuid);
            cinfo.setWeighingDate(sdf.parse(data));
            cinfo.setTaxIncluded(hs);
            cinfo.setContractNo(htbh);
            cinfo.setVoucherNo(pzh);
            cinfo.setContractType("生铁");
            cinfo.setMaterialCode(wzcode);
            cinfo.setContractPrice(dj);
            cinfo.setTaxRate(sl);
            cinfo.setSupplier(ghdw);
            cinfo.setReceivingUnit(shdw);
            cinfo.setMaterialName(wzname);
            cinfo.setWeighing(jyl);
            cinfo.setIsDelete(0);
            contractInformationService.save(cinfo);
            //添加合同元素表
            saveelement(uuid, htbh, pzh, "P", p);
            saveelement(uuid, htbh, pzh, "S", S);
            saveelement(uuid, htbh, pzh, "Si", Si);
            saveelement(uuid, htbh, pzh, "Mn", Mn);
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
