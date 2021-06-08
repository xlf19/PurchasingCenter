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
public class ShengTieController extends JeecgController<T, IShengTieService> {

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

    //加权平均
    @AutoLog(value = "加权平均")
    @ApiOperation(value = "-加权平均", notes = "-加权平均")
    @PostMapping(value = "/addlistst")
    public Result<?> addlistst(@RequestBody String stxx) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        JSONObject jf = JSONObject.parseObject(stxx);
        String fangshi = jf.getString("fangshi");//提交方式
        String htbh = jf.getString("htbh");//合同编号
        if (fangshi.equals("可选")) {
            String pgdh = "";
            JSONArray jfarray = jf.getJSONArray("jfjaxx");
            for (int i = 0; i < jfarray.size(); i++) {
                JSONObject jiaquan = jfarray.getJSONObject(i);
                if (i == 0) {
                    pgdh = jiaquan.getString("派工单号");
                } else {
                    pgdh += "," + jiaquan.getString("派工单号");
                }
            }
            shengTieService.addjqlist(pgdh, htbh);//加权平均
        } else {
            String startTime = jf.getString("startTime");//取样开始日期
            String endTime = jf.getString("endTime");//取样结束日期
            String gudw = jf.getString("shdw");//供货单位
            shengTieService.addjqlistat(startTime, endTime, gudw, htbh);
        }
        return Result.ok("添加成功！");
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
        Boolean hs=false;//HS
        BigDecimal dj =null; //物资单价
        BigDecimal sl =null;//税率
        String wzcode=""; //物资编码
        if(htonexx!=null){
            hs = (Boolean) htonexx.get("HanShuiBiaoJi");
            dj=new BigDecimal(htonexx.get("WZDanJia").toString());
            sl = new BigDecimal(htonexx.get("ShuiLv").toString());
            wzcode = htonexx.get("WZCode").toString();
        }
        JSONArray htarray = ht.getJSONArray("htxx");
        for (int i = 0; i < htarray.size(); i++) {
            JSONObject htone = htarray.getJSONObject(i);
            String ghdw = htone.getString("供货单位");//供货单位
            String wzname = htone.getString("名称");//物资名称
            String data = htone.getString("取样日期");
            BigDecimal jyl = htone.getBigDecimal("检验量");
            BigDecimal Si = htone.getBigDecimal("Si");
            BigDecimal Mn = htone.getBigDecimal("Mn");
            BigDecimal p = htone.getBigDecimal("P");
            BigDecimal S = htone.getBigDecimal("S");
            String jqid=htone.getString("id");//加权表id
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
            cinfo.setSettlementIdentification(0);
            cinfo.setJqId(jqid);
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


    //打印查询列表
    @AutoLog(value = "打印查询列表")
    @ApiOperation(value="打印查询列表", notes="打印查询列表")
    @GetMapping(value = "/selectstdy")
    public Result<?> selectstdy(ContractInformation contractInformation,
                                @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                HttpServletRequest req) {
        Page<Map<Object, String>> page = new Page<Map<Object, String>>(pageNo, pageSize);
        IPage<Map<Object, String>> pageList = shengTieService.selectstdy(page, contractInformation.getContractNo(),contractInformation.getVoucherNo());
        return Result.ok(pageList);
    }

    //打印查询列表
    @AutoLog(value = "生铁打印加权平均合计")
    @ApiOperation(value="生铁打印加权平均合计", notes="生铁打印加权平均合计")
    @GetMapping(value = "/dyhj")
    public Result<?> dyhj(@RequestParam(name = "contractNo") String contractNo,
                          @RequestParam(name = "voucherNo") Integer voucherNo) {
        Map<String, Object> map = shengTieService.dyhj(contractNo,voucherNo);
        return Result.ok(map);
    }
}
