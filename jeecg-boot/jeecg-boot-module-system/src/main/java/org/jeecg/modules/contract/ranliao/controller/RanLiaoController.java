package org.jeecg.modules.contract.ranliao.controller;

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
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.contract.contract.entity.ContractInformation;
import org.jeecg.modules.contract.contract.service.IContractInformationService;
import org.jeecg.modules.contract.elements.entity.ContractElements;
import org.jeecg.modules.contract.elements.service.IContractElementsService;
import org.jeecg.modules.contract.hetong.service.IHeTongService;
import org.jeecg.modules.contract.ranliao.service.IRanLiaoService;
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
@RequestMapping("/ranliao/ranliao")
@Slf4j
public class RanLiaoController extends JeecgController<T, IRanLiaoService> {
    @Autowired
    private IRanLiaoService rlservice;
    @Autowired
    private IZhiJianXinXiService zjxxservice;
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
        contractInformation.setContractType("燃料");
        contractInformation.setContractNo(hth);
        QueryWrapper<ContractInformation> queryWrapper = QueryGenerator.initQueryWrapper(contractInformation, req.getParameterMap());
        Page<ContractInformation> page = new Page<ContractInformation>(pageNo, pageSize);
        IPage<ContractInformation> pageList = contractInformationService.page(page, queryWrapper);
        return Result.ok(pageList);
    }
    /**
     * 燃料手动添加结算单弹框
     *
     * @return
     */
    @AutoLog(value = "燃料手动添加结算单弹框")
    @ApiOperation(value = "燃料手动添加结算单弹框", notes = "燃料手动添加结算单弹框")
    @GetMapping(value = "/listzjjfxx")
    public Result<?> listzjjfxx(@RequestParam(name = "htbh", defaultValue = "") String htbh,
                                @RequestParam(name = "voucherno", defaultValue = "") String voucherno,
                                @RequestParam(name = "receiving", defaultValue = "") String receiving) {
        Map<Object, Object> list = zjxxservice.listzjjfxx(htbh, voucherno, receiving);
        if(list==null){
            return Result.ok("数据为空");
        }else{
            return Result.ok(list);
        }
    }

    @AutoLog(value = "编辑燃料手动结算单")
    @ApiOperation(value = "编辑燃料手动结算单", notes = "编辑燃料手动结算单")
    @GetMapping(value = "/updatejsd")
    public Result<?> updatejsd(@RequestParam(name = "id", defaultValue = "") String id) {
        Map<Object, Object> list = rlservice.updatejsd(id);
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
        String id = jsd.get("id").toString();
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
        if(jsd.get("ShuiLv")!=null){
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
        //总运杂费
        BigDecimal zyzf = null;
        if(jsd.containsKey("zyzf")){
            if(jsd.get("zyzf")!=null){
                weighing = new BigDecimal(jsd.get("weighing").toString());
            }
        }
        //贷款
        BigDecimal daikuan = null;
        if(jsd.containsKey("daikuan")){
            if(jsd.get("daikuan")!=null){
                weighing = new BigDecimal(jsd.get("daikuan").toString());
            }
        }
        //税金
        BigDecimal shuijin = null;
        if(jsd.containsKey("shuijin")){
            if(jsd.get("shuijin")!=null){
                weighing = new BigDecimal(jsd.get("shuijin").toString());
            }
        }
        //合同单价
        BigDecimal WZDanJia = new BigDecimal(jsd.get("WZDanJia").toString());
        //添加合同信息表
        ContractInformation cinfo = new ContractInformation();
        String uuid = UUID.randomUUID().toString();
        cinfo.setId(uuid);
        cinfo.setContractType("燃料");
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
        cinfo.setTaxes(shuijin);
        cinfo.setLoan(daikuan);
        cinfo.setShipping(zyzf);
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
        //Ad
        BigDecimal Ad=null;
        if(jsd.containsKey("Ad")){
            if(jsd.get("Ad")!=null){
                 Ad = new BigDecimal(jsd.get("Ad").toString());
            }
        }
        saveelement(uuid, htbh, pzh, "Ad", Ad,id);
        //Vdaf
        BigDecimal Vdaf=null;
        if(jsd.containsKey("Vdaf")){
            if(jsd.get("Vdaf")!=null){
                 Vdaf = new BigDecimal(jsd.get("Vdaf").toString());
            }
        }
        saveelement(uuid, htbh, pzh, "Vdaf", Vdaf,id);
        //Std
        BigDecimal Std =null;
        if(jsd.containsKey("Std")){
            if(jsd.get("Std")!=null){
                 Std = new BigDecimal(jsd.get("Std").toString());
            }
        }
        saveelement(uuid, htbh, pzh, "Std", Std,id);
        //H2O
        BigDecimal H2O =null;
        if(jsd.containsKey("H2O")){
            if(jsd.get("H2O")!=null){
                 H2O = new BigDecimal(jsd.get("H2O").toString());
            }
        }
        saveelement(uuid, htbh, pzh, "H2O", H2O,id);
        //Xmm
        BigDecimal AL2O3 =null;
        if(jsd.containsKey("Xmm")){
            if(jsd.get("Xmm")!=null){
                 AL2O3 = new BigDecimal(jsd.get("Xmm").toString());
            }
        }
        saveelement(uuid, htbh, pzh, "Xmm", AL2O3,id);
        //Ymm
        BigDecimal Ymm =null;
        if(jsd.containsKey("Ymm")){
            if(jsd.get("Ymm")!=null){
                 Ymm = new BigDecimal(jsd.get("Ymm").toString());
            }
        }
        saveelement(uuid, htbh, pzh, "Ymm", Ymm,id);
        //Q
        BigDecimal Q =null;
        if(jsd.containsKey("Q")){
            if(jsd.get("Q")!=null){
                 Q = new BigDecimal(jsd.get("Q").toString());
            }
        }
        saveelement(uuid, htbh, pzh, "Q", Q,id);
        //粘度
        BigDecimal midu =null;
        if(jsd.containsKey("midu")){
            if(jsd.get("midu")!=null){
                 midu = new BigDecimal(jsd.get("midu").toString());
            }
        }
        saveelement(uuid, htbh, pzh, "粘度", midu,id);
        //M10
        BigDecimal M10 =null;
        if(jsd.containsKey("M10")){
            if(jsd.get("cu")!=null){
                 M10 = new BigDecimal(jsd.get("M10").toString());
            }
        }
        saveelement(uuid, htbh, pzh, "M10", M10,id);
        //M25
        BigDecimal M25 =null;
        if(jsd.containsKey("M25")){
            if(jsd.get("M25")!=null){
                 M25 = new BigDecimal(jsd.get("M25").toString());
            }
        }
        saveelement(uuid, htbh, pzh, "M25", M25,id);
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
        Map<Object, Object> htonexx = htservice.finhtxxjf(htbh);
        //HS 含税
        Boolean hs = (Boolean) htonexx.get("HanShuiBiaoJi");
        //物资单价
        BigDecimal dj = new BigDecimal(htonexx.get("WZDanJia").toString());
        //SL  税率
        BigDecimal sl = new BigDecimal(htonexx.get("ShuiLv").toString());
        //物资编码
        String wzcode = htonexx.get("WZCode").toString();

        JSONArray htarray = ht.getJSONArray("htxx");//合同信息
        for (int i = 0; i < htarray.size(); i++) {
            JSONObject htone = htarray.getJSONObject(i);
            BigDecimal Ad = htone.getBigDecimal("Ad");
            BigDecimal Vdaf = htone.getBigDecimal("Vdaf");
            BigDecimal Std = htone.getBigDecimal("Std");
            BigDecimal H2O = htone.getBigDecimal("H2O");
            BigDecimal Xmm = htone.getBigDecimal("Xmm");
            BigDecimal Ymm = htone.getBigDecimal("Ymm");
            BigDecimal Q = htone.getBigDecimal("Q");
            BigDecimal midu = htone.getBigDecimal("密度");
            BigDecimal M10 = htone.getBigDecimal("M10");
            BigDecimal M25 = htone.getBigDecimal("M25");
            BigDecimal jyl = htone.getBigDecimal("检验量");
            String data = htone.getString("取样日期");
            String dwname = htone.getString("供货单位");//供货单位
            String wzname = htone.getString("名称");//物资名称
            String pgdh = htone.getString("派工单号");//派工单号
            //添加合同信息表
            ContractInformation cinfo = new ContractInformation();
            String uuid = UUID.randomUUID().toString();
            cinfo.setId(uuid);
            cinfo.setWeighingDate(sdf.parse(data));
            cinfo.setTaxIncluded(hs);
            cinfo.setMaterialCode(wzcode);
            cinfo.setContractNo(htbh);
            cinfo.setVoucherNo(pzh);
            cinfo.setContractType("燃料");
            cinfo.setContractPrice(dj);
            cinfo.setTaxRate(sl);
            cinfo.setReceivingUnit(shdw);
            cinfo.setSupplier(dwname);
            cinfo.setWeighing(jyl);
            cinfo.setMaterialName(wzname);
            cinfo.setIsDelete(0);
            cinfo.setSettlementIdentification(0);
            cinfo.setWorkNumber(pgdh);
            contractInformationService.save(cinfo);
            //添加合同元素表
            saveelement(uuid, htbh, pzh, "Ad", Ad,"1");
            saveelement(uuid, htbh, pzh, "Vdaf", Vdaf,"1");
            saveelement(uuid, htbh, pzh, "Std", Std,"1");
            saveelement(uuid, htbh, pzh, "H2O", H2O,"1");
            saveelement(uuid, htbh, pzh, "Xmm", Xmm,"1");
            saveelement(uuid, htbh, pzh, "Ymm", Ymm,"1");
            saveelement(uuid, htbh, pzh, "Q", Q,"1");
            saveelement(uuid, htbh, pzh, "密度", midu,"1");
            saveelement(uuid, htbh, pzh, "M10", M10,"1");
            saveelement(uuid, htbh, pzh, "M25", M25,"1");
        }
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
    @GetMapping(value = "/selectrldy")
    public Result<?> selectrldy(ContractInformation contractInformation,
                                @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                HttpServletRequest req) {
        Page<Map<Object, String>> page = new Page<Map<Object, String>>(pageNo, pageSize);
        IPage<Map<Object, String>> pageList = rlservice.selectrldy(page, contractInformation.getContractNo(),contractInformation.getVoucherNo());
        return Result.ok(pageList);
    }

}
