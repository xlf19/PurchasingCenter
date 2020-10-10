package org.jeecg.modules.contract.jingfen.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
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
            BigDecimal jyl = htone.getBigDecimal("weighing");//检验量
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
            saveelement(uuid, htbh, pzh, "S", S,"1");
            saveelement(uuid, htbh, pzh, "P", P,"1");
            saveelement(uuid, htbh, pzh, "FEO", FEO,"1");
            saveelement(uuid, htbh, pzh, "H2O", h2o,"1");
            saveelement(uuid, htbh, pzh, "AL2O3", al2o3,"1");
            saveelement(uuid, htbh, pzh, "MnO", mno,"1");
            saveelement(uuid, htbh, pzh, "TiO2", tio2,"1");
            saveelement(uuid, htbh, pzh, "Cr", cr,"1");
            saveelement(uuid, htbh, pzh, "Cu", cu,"1");
            saveelement(uuid, htbh, pzh, "TFE", tfe,"1");
            saveelement(uuid, htbh, pzh, "MGO", mgo,"1");
            saveelement(uuid, htbh, pzh, "Pb", pb,"1");
            saveelement(uuid, htbh, pzh, "[As]", as1,"1");
            saveelement(uuid, htbh, pzh, "CAO", cao,"1");
            saveelement(uuid, htbh, pzh, "K2O", k2o,"1");
            saveelement(uuid, htbh, pzh, "SIO2", sio2,"1");
            saveelement(uuid, htbh, pzh, "Zn", zn,"1");
            saveelement(uuid, htbh, pzh, "抗压", compressive,"1");
            saveelement(uuid, htbh, pzh, "[粒度<5mm]", granularity,"1");
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
     * 编辑精粉手动结算单
     *
     * @return
     */
    @AutoLog(value = "编辑精粉手动结算单")
    @ApiOperation(value = "编辑精粉手动结算单", notes = "编辑精粉手动结算单")
    @GetMapping(value = "/updatejsd")
    public Result<?> updatejsd(@RequestParam(name = "id", defaultValue = "") String id) {
        Map<Object, Object> list = jfService.updatejsd(id);
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
        String id=jsd.get("id").toString();//合同信息表id
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
           if(jsd.get("weighing")!=null){
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
        cinfo.setSettlementIdentification(0);
        cinfo.setManual(1);
        if(id.equals("1")){
            contractInformationService.save(cinfo);
        }else{
            cinfo.setId(id);
            contractInformationService.updateById(cinfo);
        }
        //添加合同元素表
        //S
        BigDecimal S=null;
        if(jsd.containsKey("ss")){
            if(jsd.get("ss")!=null){
                S = new BigDecimal(jsd.get("ss").toString());
            }
        }
        //P
        BigDecimal P =null;
        if(jsd.containsKey("pp")){
            if(jsd.get("pp")!=null){
                P = new BigDecimal(jsd.get("pp").toString());
            }
        }
        //FEO
        BigDecimal FEO =null;
        if(jsd.containsKey("feo")){
            if(jsd.get("feo")!=null){
                 FEO = new BigDecimal(jsd.get("feo").toString());
            }
        }

        //H2O
        BigDecimal H2O =null;
        if(jsd.containsKey("h2o")){
            if(jsd.get("h2o")!=null){
                 H2O = new BigDecimal(jsd.get("h2o").toString());
            }
        }
        //AL2O3
        BigDecimal AL2O3 = null;
        if(jsd.containsKey("al2o3")){
            if(jsd.get("al2o3")!=null ){
                System.out.println(1);
                 AL2O3 = new BigDecimal(jsd.get("al2o3").toString());
            }
        }
        //MnO
        BigDecimal MnO =null;
        if(jsd.containsKey("mno")){
            if(jsd.get("mno")!=null){
                 MnO = new BigDecimal(jsd.get("mno").toString());
            }
        }
        //TiO2
        BigDecimal TiO2 =null;
        if(jsd.containsKey("tio2")){
            if(jsd.get("tio2")!=null){
                 TiO2 = new BigDecimal(jsd.get("tio2").toString());
            }
        }
        //Cr
        BigDecimal Cr =null;
        if(jsd.containsKey("cr")){
            if(jsd.get("cr")!=null){
                 Cr = new BigDecimal(jsd.get("cr").toString());

            }
        }
        //Cu
        BigDecimal Cu =null;
        if(jsd.containsKey("cu")){
            if(jsd.get("cu")!=null){
                 Cu = new BigDecimal(jsd.get("cu").toString());
            }
        }
        //TFE
        BigDecimal TFE =null;
        if(jsd.containsKey("tfe")){
            if(jsd.get("tfe")!=null){
                 TFE = new BigDecimal(jsd.get("tfe").toString());
            }
        }
        //MGO
        BigDecimal MGO =null;
        if(jsd.containsKey("mgo")){
            if(jsd.get("mgo")!=null){
                 MGO = new BigDecimal(jsd.get("mgo").toString());
            }
        }
        //Pb
        BigDecimal Pb =null;
        if(jsd.containsKey("pb")){
            if(jsd.get("pb")!=null){
                 Pb = new BigDecimal(jsd.get("pb").toString());
            }
        }
        //As
        BigDecimal As =null;
        if(jsd.containsKey("as1")){
            if(jsd.get("as1")!=null){
                 As = new BigDecimal(jsd.get("as1").toString());
            }
        }
        //CAO
        BigDecimal CAO =null;
        if(jsd.containsKey("cao")){
            if(jsd.get("cao")!=null){
                 CAO = new BigDecimal(jsd.get("cao").toString());
            }
        }
        //K2O
        BigDecimal K2O =null;
        if(jsd.containsKey("k2o")){
            if(jsd.get("k2o")!=null){
                 K2O = new BigDecimal(jsd.get("k2o").toString());
            }
        }
        //SIO2
        BigDecimal SIO2 =null;
        if(jsd.containsKey("sio2")){
            if(jsd.get("sio2")!=null){
                 SIO2 = new BigDecimal(jsd.get("sio2").toString());
            }
        }
        //含粉
        BigDecimal containsPowder =null;
        if(jsd.containsKey("containsPowder")){
            if(jsd.get("containsPowder")!=null){
                 containsPowder = new BigDecimal(jsd.get("containsPowder").toString());
            }
        }
        //抗压
        BigDecimal compressive =null;
        if(jsd.containsKey("compressive")){
            if(jsd.get("compressive")!=null){
                 compressive = new BigDecimal(jsd.get("compressive").toString());
            }
        }
        //[粒度<5mm]
        BigDecimal granularity =null;
        if(jsd.containsKey("granularity")){
            if(jsd.get("granularity")!=null){
                 granularity = new BigDecimal(jsd.get("granularity").toString());
            }
        }
        saveelement(uuid, htbh, pzh, "S", S,id);
        saveelement(uuid, htbh, pzh, "P", P,id);
        saveelement(uuid, htbh, pzh, "FEO", FEO,id);
        saveelement(uuid, htbh, pzh, "H2O", H2O,id);
        saveelement(uuid, htbh, pzh, "AL2O3", AL2O3,id);
        saveelement(uuid, htbh, pzh, "MnO", MnO,id);
        saveelement(uuid, htbh, pzh, "TiO2", TiO2,id);
        saveelement(uuid, htbh, pzh, "Cr", Cr,id);
        saveelement(uuid, htbh, pzh, "Cu", Cu,id);
        saveelement(uuid, htbh, pzh, "TFE", TFE,id);
        saveelement(uuid, htbh, pzh, "MGO", MGO,id);
        saveelement(uuid, htbh, pzh, "Pb", Pb,id);
        saveelement(uuid, htbh, pzh, "As", As,id);
        saveelement(uuid, htbh, pzh, "CAO", CAO,id);
        saveelement(uuid, htbh, pzh, "K2O", K2O,id);
        saveelement(uuid, htbh, pzh, "SIO2", SIO2,id);
        saveelement(uuid, htbh, pzh, "含粉", containsPowder,id);
        saveelement(uuid, htbh, pzh, "抗压", compressive,id);
        saveelement(uuid, htbh, pzh, "[粒度<5mm]", granularity,id);
        return Result.ok("添加成功！");
    }


    //导入合同元素表
    public void saveelement(String uuid, String htbh, Integer pzh, String name, BigDecimal ele,String id) {
       if(id.equals("1")){
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
       }else{
           UpdateWrapper<ContractElements> updateWrapper = new UpdateWrapper<ContractElements>();
           System.out.println(name+"-----"+id);
           updateWrapper.eq("element",name);
           updateWrapper.eq("ci_id",id);
           ContractElements celeupdate = new ContractElements();
           celeupdate.setElelmentDate(ele);
           htelements.update(celeupdate, updateWrapper);
       }

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
