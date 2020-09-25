package org.jeecg.modules.contract.jingfen.controller;

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
import org.jeecg.modules.contract.jingfen.service.IJingFengService;
import org.jeecg.modules.contract.zhijianxinxi.service.IZhiJianXinXiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.jeecg.common.util.oConvertUtils;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.UUID;

@Api(tags = "multi-datasource2精粉富粉球团结算管理")
@RestController
@RequestMapping("/jingfen/jingfen")
@Slf4j
public class JingFengController extends JeecgController<T, IJingFengService> {

    @Autowired
    private IJingFengService jfService;
    @Autowired
    private IHeTongService htservice;
    @Autowired
    private IContractInformationService contractInformationService;
    @Autowired
    private IContractElementsService htelements;
    @Autowired
    private IZhiJianXinXiService zjxxservice;

    //加权平均
    @AutoLog(value = "加权平均")
    @ApiOperation(value = "-加权平均", notes = "-加权平均")
    @PostMapping(value = "/addlistjf")
    public Result<?> addlistjf(@RequestBody String jfxx) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        JSONObject jf = JSONObject.parseObject(jfxx);
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
            jfService.addjqlist(pgdh, htbh);//加权平均
        } else {
            String startTime = jf.getString("startTime");//取样开始日期
            String endTime = jf.getString("endTime");//取样结束日期
            String gudw = jf.getString("shdw");//供货单位
            jfService.addjqlistat(startTime, endTime, gudw, htbh);
        }
        return Result.ok("添加成功！");
    }

    //导入加权均值
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
        Map<Object, Object> htonexx = htservice.finhtxxjf(htbh);
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
        JSONArray htarray = ht.getJSONArray("htxx");//合同信息
        for (int i = 0; i < htarray.size(); i++) {
            JSONObject htone = htarray.getJSONObject(i);
            BigDecimal S = htone.getBigDecimal("ss");
            BigDecimal P = htone.getBigDecimal("pp");
            BigDecimal FEO = htone.getBigDecimal("feo");
            BigDecimal h2o = htone.getBigDecimal("h2o");
            BigDecimal al2o3 = htone.getBigDecimal("al2o3");
            BigDecimal mno = htone.getBigDecimal("mno");
            BigDecimal tio2 = htone.getBigDecimal("tio2");
            BigDecimal cr = htone.getBigDecimal("cr");
            BigDecimal cu = htone.getBigDecimal("cu");
            BigDecimal tfe = htone.getBigDecimal("tfe");
            BigDecimal mgo = htone.getBigDecimal("mgo");
            BigDecimal pb = htone.getBigDecimal("pb");
            BigDecimal as1 = htone.getBigDecimal("as1");
            BigDecimal cao = htone.getBigDecimal("cao");
            BigDecimal k2o = htone.getBigDecimal("k2o");
            BigDecimal sio2 = htone.getBigDecimal("sio2");
            BigDecimal zn = htone.getBigDecimal("zn");
            BigDecimal compressive = null;
            BigDecimal granularity = null;
            if(htone.containsKey("granularity")){
                granularity = htone.getBigDecimal("granularity");
            }
            if(htone.containsKey("compressive")){
                compressive = htone.getBigDecimal("compressive");
            }
            BigDecimal jyl = htone.getBigDecimal("检验量");
            String data = htone.getString("riqi");
            String dwname = htone.getString("supplier");//供货单位
            String wzname = htone.getString("material_name");//物资名称
            String jqid=htone.getString("id");//加权表id
            //添加合同信息表
            ContractInformation cinfo = new ContractInformation();
            String uuid = UUID.randomUUID().toString();
            cinfo.setId(uuid);
            cinfo.setWeighingDate(sdf.parse(data));
            cinfo.setTaxIncluded(hs);
            cinfo.setMaterialCode(wzcode);
            cinfo.setContractNo(htbh);
            cinfo.setVoucherNo(pzh);
            cinfo.setContractType("精粉富粉球团");
            cinfo.setContractPrice(dj);
            cinfo.setTaxRate(sl);
            cinfo.setReceivingUnit(shdw);
            cinfo.setSupplier(dwname);
            cinfo.setWeighing(jyl);
            cinfo.setMaterialName(wzname);
            cinfo.setIsDelete(0);
            cinfo.setSettlementIdentification(0);
            cinfo.setJqId(jqid);
            contractInformationService.save(cinfo);
            //添加合同元素表
            saveelement(uuid, htbh, pzh, "S", S);
            saveelement(uuid, htbh, pzh, "P", P);
            saveelement(uuid, htbh, pzh, "FEO", FEO);
            saveelement(uuid, htbh, pzh, "H2O", h2o);
            saveelement(uuid, htbh, pzh, "AL2O3", al2o3);
            saveelement(uuid, htbh, pzh, "MnO", mno);
            saveelement(uuid, htbh, pzh, "TiO2", tio2);
            saveelement(uuid, htbh, pzh, "Cr", cr);
            saveelement(uuid, htbh, pzh, "Cu", cu);
            saveelement(uuid, htbh, pzh, "TFE", tfe);
            saveelement(uuid, htbh, pzh, "MGO", mgo);
            saveelement(uuid, htbh, pzh, "Pb", pb);
            saveelement(uuid, htbh, pzh, "[As]", as1);
            saveelement(uuid, htbh, pzh, "CAO", cao);
            saveelement(uuid, htbh, pzh, "K2O", k2o);
            saveelement(uuid, htbh, pzh, "SIO2", sio2);
            saveelement(uuid, htbh, pzh, "Zn", zn);
            saveelement(uuid, htbh, pzh, "抗压", compressive);
            saveelement(uuid, htbh, pzh, "[粒度<5mm]", granularity);
        }
        return Result.ok("添加成功！");
    }


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
        contractInformation.setContractType("精粉富粉球团");
        contractInformation.setContractNo(hth);
        QueryWrapper<ContractInformation> queryWrapper = QueryGenerator.initQueryWrapper(contractInformation, req.getParameterMap());
        Page<ContractInformation> page = new Page<ContractInformation>(pageNo, pageSize);
        IPage<ContractInformation> pageList = contractInformationService.page(page, queryWrapper);
        return Result.ok(pageList);
    }

    /**
     * 精粉手动添加结算单弹框
     *
     * @return
     */
    @AutoLog(value = "精粉手动添加结算单弹框")
    @ApiOperation(value = "精粉手动添加结算单弹框", notes = "精粉手动添加结算单弹框")
    @GetMapping(value = "/listzjjfxx")
    public Result<?> listzjjfxx(@RequestParam(name = "htbh", defaultValue = "") String htbh,
                                @RequestParam(name = "voucherno", defaultValue = "") String voucherno,
                                @RequestParam(name = "receiving", defaultValue = "") String receiving) {
        Map<Object, Object> list = zjxxservice.listzjjfxx(htbh, voucherno, receiving);
        return Result.ok(list);
    }

    /**
     * 精粉手动添加结算单
     *
     * @return
     */
    @AutoLog(value = "精粉手动添加结算单")
    @ApiOperation(value = "精粉手动添加结算单", notes = "精粉手动添加结算单")
    @PostMapping(value = "/addjsd")
    public Result<?> addjsd(@RequestBody Map<Object, Object> jsd) {
        String htbh = jsd.get("HeTongBianHao").toString();//合同编号
        Integer pzh =Integer.parseInt(jsd.get("voucherno").toString());//凭证号
        String shdw = jsd.get("receiving").toString();//收货单位
        //物资编码
        String wzcode = "";
        if(oConvertUtils.isNotEmpty(jsd.get("WZCode").toString())){
            wzcode = jsd.get("WZCode").toString();
        }
        //物资名称
        String WZName = "";
        if(oConvertUtils.isNotEmpty(jsd.get("WZName").toString())){
            WZName = jsd.get("WZName").toString();
        }
        //供货单位
        String ghdw ="";
        if(oConvertUtils.isNotEmpty(jsd.get("DanWeiName").toString())){
            ghdw = jsd.get("DanWeiName").toString();
        }
        //收货单位
        String receiving = "";
        if(oConvertUtils.isNotEmpty(jsd.get("receiving").toString())){
            receiving = jsd.get("receiving").toString();
        }
         //SL  税率
        BigDecimal sl =null;
        if(oConvertUtils.isNotEmpty(jsd.get("ShuiLv").toString())){
            sl = new BigDecimal(jsd.get("ShuiLv").toString());
        }
        //HS 含税
        Boolean hs = (Boolean) jsd.get("HanShuiBiaoJi");
        //检验量
        BigDecimal weighing = null;
        if(jsd.containsKey("weighing")){
            if(jsd.containsValue("weighing")){
                weighing = new BigDecimal(jsd.get("weighing").toString());
            }
        }
        //合同单价
        BigDecimal WZDanJia = new BigDecimal(jsd.get("WZDanJia").toString());
        //添加合同信息表
        ContractInformation cinfo = new ContractInformation();
        String uuid = UUID.randomUUID().toString();
        cinfo.setId(uuid);
        cinfo.setContractType("精粉富粉球团");
        cinfo.setContractNo(htbh);
        cinfo.setVoucherNo(pzh);
        cinfo.setReceivingUnit(shdw);
        cinfo.setMaterialCode(wzcode);
        cinfo.setMaterialName(WZName);
        cinfo.setSupplier(ghdw);
        cinfo.setReceivingUnit(receiving);
        cinfo.setTaxRate(sl);
        cinfo.setTaxIncluded(hs);
        cinfo.setWeighing(weighing);
        cinfo.setContractPrice(WZDanJia);
        cinfo.setIsDelete(0);
        contractInformationService.save(cinfo);
        //添加合同元素表
        //S
        if(jsd.containsKey("ss")){
            if(jsd.containsValue("ss")){
                BigDecimal S = new BigDecimal(jsd.get("ss").toString());
                saveelement(uuid, htbh, pzh, "S", S);
            }
        }
        //P
        if(jsd.containsKey("pp")){
            if(oConvertUtils.isNotEmpty(jsd.get("pp").toString())){
                BigDecimal P = new BigDecimal(jsd.get("pp").toString());
                saveelement(uuid, htbh, pzh, "P", P);
            }
        }
        //FEO
        if(jsd.containsKey("feo")){
            if(oConvertUtils.isNotEmpty(jsd.get("feo").toString())){
                BigDecimal FEO = new BigDecimal(jsd.get("feo").toString());
                saveelement(uuid, htbh, pzh, "FEO", FEO);
            }
        }

        //H2O
        if(jsd.containsKey("h2o")){
            if(oConvertUtils.isNotEmpty(jsd.get("h2o").toString())){
                BigDecimal H2O = new BigDecimal(jsd.get("h2o").toString());
                saveelement(uuid, htbh, pzh, "H2O", H2O);
            }

        }
        //AL2O3
        if(jsd.containsKey("al2o3")){
            if(oConvertUtils.isNotEmpty(jsd.get("al2o3").toString())){
                BigDecimal AL2O3 = new BigDecimal(jsd.get("al2o3").toString());
                saveelement(uuid, htbh, pzh, "AL2O3", AL2O3);
            }
        }

        //MnO
        if(jsd.containsKey("mno")){
            if(oConvertUtils.isNotEmpty(jsd.get("mno").toString())){
                BigDecimal MnO = new BigDecimal(jsd.get("mno").toString());
                saveelement(uuid, htbh, pzh, "MnO", MnO);
            }

        }

        //TiO2
        if(jsd.containsKey("tio2")){
            if(oConvertUtils.isNotEmpty(jsd.get("tio2").toString())){
                BigDecimal TiO2 = new BigDecimal(jsd.get("tio2").toString());
                saveelement(uuid, htbh, pzh, "TiO2", TiO2);
            }
        }

        //Cr
        if(jsd.containsKey("cr")){
            if(oConvertUtils.isNotEmpty(jsd.get("cr").toString())){
                BigDecimal Cr = new BigDecimal(jsd.get("cr").toString());
                saveelement(uuid, htbh, pzh, "Cr", Cr);
            }

        }

        //Cu
        if(jsd.containsKey("cu")){
            if(oConvertUtils.isNotEmpty(jsd.get("cu").toString())){
                BigDecimal Cu = new BigDecimal(jsd.get("cu").toString());
                saveelement(uuid, htbh, pzh, "Cu", Cu);
            }
        }

        //TFE
        if(jsd.containsKey("tfe")){
            if(oConvertUtils.isNotEmpty(jsd.get("tfe").toString())){
                BigDecimal TFE = new BigDecimal(jsd.get("tfe").toString());
                saveelement(uuid, htbh, pzh, "TFE", TFE);
            }
        }

        //MGO
        if(jsd.containsKey("mgo")){
            if(oConvertUtils.isNotEmpty(jsd.get("mgo").toString())){
                BigDecimal MGO = new BigDecimal(jsd.get("mgo").toString());
                saveelement(uuid, htbh, pzh, "MGO", MGO);
            }
        }

        //Pb
        if(jsd.containsKey("pb")){
            if(oConvertUtils.isNotEmpty(jsd.get("pb").toString())){
                BigDecimal Pb = new BigDecimal(jsd.get("pb").toString());
                saveelement(uuid, htbh, pzh, "Pb", Pb);
            }
        }

        //As
        if(jsd.containsKey("as1")){
            if(oConvertUtils.isNotEmpty(jsd.get("as1").toString())){
                BigDecimal As = new BigDecimal(jsd.get("as1").toString());
                saveelement(uuid, htbh, pzh, "As", As);
            }
        }

        //CAO
        if(jsd.containsKey("cao")){
            if(oConvertUtils.isNotEmpty(jsd.get("cao").toString())){
                BigDecimal CAO = new BigDecimal(jsd.get("cao").toString());
                saveelement(uuid, htbh, pzh, "CAO", CAO);
            }

        }

        //K2O
        if(jsd.containsKey("k2o")){
            if(oConvertUtils.isNotEmpty(jsd.get("k2o").toString())){
                BigDecimal K2O = new BigDecimal(jsd.get("k2o").toString());
                saveelement(uuid, htbh, pzh, "K2O", K2O);
            }
        }

        //SIO2
        if(jsd.containsKey("sio2")){
            if(oConvertUtils.isNotEmpty(jsd.get("sio2").toString())){
                BigDecimal SIO2 = new BigDecimal(jsd.get("sio2").toString());
                saveelement(uuid, htbh, pzh, "SIO2", SIO2);
            }
        }
        //含粉
        if(jsd.containsKey("containsPowder")){
            if(oConvertUtils.isNotEmpty(jsd.get("containsPowder").toString())){
                BigDecimal containsPowder = new BigDecimal(jsd.get("containsPowder").toString());
                saveelement(uuid, htbh, pzh, "含粉", containsPowder);
            }
        }
        //抗压
        if(jsd.containsKey("compressive")){
            if(oConvertUtils.isNotEmpty(jsd.get("compressive").toString())){
                BigDecimal compressive = new BigDecimal(jsd.get("compressive").toString());
                saveelement(uuid, htbh, pzh, "抗压", compressive);
            }

        }
        //[粒度<5mm]
        if(jsd.containsKey("granularity")){
            if(oConvertUtils.isNotEmpty(jsd.get("granularity").toString())){
                BigDecimal granularity = new BigDecimal(jsd.get("granularity").toString());
                saveelement(uuid, htbh, pzh, "[粒度<5mm]", granularity);
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

    //打印查询列表
    @AutoLog(value = "打印查询列表")
    @ApiOperation(value="打印查询列表", notes="打印查询列表")
    @GetMapping(value = "/selectjfdy")
    public Result<?> selectjfdy(ContractInformation contractInformation,
                                @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                HttpServletRequest req) {
        Page<Map<Object, String>> page = new Page<Map<Object, String>>(pageNo, pageSize);
        IPage<Map<Object, String>> pageList = jfService.selectjfdy(page, contractInformation.getContractNo(),contractInformation.getVoucherNo());
        return Result.ok(pageList);
    }
}
