package org.jeecg.modules.contract_management.contractedit.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.contract.syslegaltemplate.entity.SysLegalTemplate;
import org.jeecg.modules.contract.syslegaltemplate.service.ISysLegalTemplateService;
import org.jeecg.modules.contract.syslegaltemplatedetail.entity.SysLegalTemplateDetail;
import org.jeecg.modules.contract.syslegaltemplatedetail.service.ISysLegalTemplateDetailService;
import org.jeecg.modules.contract_management.contractedit.service.IContractEditService;
import org.jeecg.modules.contract_management.contractpurchase.entity.ContractPurchase;
import org.jeecg.modules.contract_management.contractpurchase.service.IContractPurchaseService;
import org.jeecg.modules.contract_management.contractterms.entity.ContractTerms;
import org.jeecg.modules.contract_management.contractterms.service.IContractTermsService;
import org.jeecg.modules.contract_management.productinformation.entity.ProductInformation;
import org.jeecg.modules.contract_management.productinformation.service.IProductInformationService;
import org.jeecg.modules.contract_management.productlist.entity.ProductList;
import org.jeecg.modules.contract_management.productlist.service.IProductListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description: 添加采购合同
 * @Author: jeecg-boot
 * @Date: 2020-12-04
 * @Version: V1.0
 */
@Api(tags = "添加采购合同")
@RestController
@RequestMapping("/contractedit/contractedit")
@Slf4j
public class ContractEditController extends JeecgController<T, IContractEditService> {
    @Autowired
    private IContractEditService icontractEditService;
    @Autowired
    private IContractPurchaseService contractPurchaseService;
    @Autowired
    private IProductInformationService productInformationService;
    @Autowired
    private IProductListService productListService;
    @Autowired
    private ISysLegalTemplateService sysLegalTemplateService;
    @Autowired
    private ISysLegalTemplateDetailService sysLegalTemplateDetailService;
    @Autowired
    private IContractTermsService contractTermsService;
    /**
     *   添加
     *
     * @param contractPurchase
     * @return
     */
    @AutoLog(value = "采购合同表-添加")
    @ApiOperation(value="采购合同表-添加", notes="采购合同表-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody ContractPurchase contractPurchase) {
        String id =contractPurchase.getId();
        ContractPurchase cps = contractPurchaseService.getById(id);
        if(cps==null) {
            contractPurchase.setIsdelete(1);
            contractPurchase.setCurrentState("生成合同");
            contractPurchaseService.save(contractPurchase);
        }else{
            contractPurchaseService.updateById(contractPurchase);
        }
        return Result.ok("保存成功");
    }

    @AutoLog(value = "产品信息-添加")
    @ApiOperation(value="产品信息-添加", notes="产品信息-添加")
    @PostMapping(value = "/adddata")
    public Result<?> adddata(@RequestBody ProductInformation productInformation) {
        String id =productInformation.getId();
        ProductInformation pif = productInformationService.getById(id);
        if(pif==null){
            productInformation.setIsdelete(1);
            productInformationService.save(productInformation);
        }else{
            productInformationService.updateById(productInformation);
        }
        return Result.ok("保存成功！");
    }

    @AutoLog(value = "产品列表-添加")
    @ApiOperation(value="产品列表-添加", notes="产品列表-添加")
    @PostMapping(value = "/addlist")
    public Result<?> addlist(@RequestBody ProductList productList) {
        String id =productList.getId();
        ProductList plist = productListService.getById(id);
        if(plist==null){
            productList.setIsdelete(1);
            productListService.save(productList);
        }else{
            productListService.updateById(productList);
        }
        return Result.ok("保存成功！");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "产品信息-通过id查询")
    @ApiOperation(value="产品信息-通过id查询", notes="产品信息-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
        ProductInformation productInformation = productInformationService.getById(id);
        if(productInformation==null) {
            return Result.error("未找到对应数据");
        }
        return Result.ok(productInformation);
    }
    /**
     *   通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "产品信息-通过id删除")
    @ApiOperation(value="产品信息-通过id删除", notes="产品信息-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name="id",required=true) String id) {
        ProductInformation pif = new ProductInformation();
        pif.setId(id);
        pif.setIsdelete(2);
        productInformationService.updateById(pif);
        icontractEditService.deleteproduct(id);
//        productInformationService.removeById(id);
        return Result.ok("删除成功!");
    }


    @AutoLog(value = "查询合同模板表信息")
    @ApiOperation(value="查询合同模板表信息", notes="查询合同模板表信息")
    @GetMapping(value = "/templatelist")
    public Result<?> templatelist() {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        QueryWrapper<SysLegalTemplate> queryWrapper =new QueryWrapper<SysLegalTemplate>();
        queryWrapper.eq("status","使用中");
        queryWrapper.ne("is_delete",1);
        queryWrapper.eq("create_by",sysUser.getUsername()).or().eq("create_by","admin");
        List<SysLegalTemplate> list = sysLegalTemplateService.list(queryWrapper);
        return Result.ok(list);
    }

    @AutoLog(value = "查询合同条款信息")
    @ApiOperation(value="查询合同条款信息", notes="查询合同条款信息")
    @GetMapping(value = "/contracttemplate")
    public Result<?> contracttemplate(@RequestParam(name="contract_id") String contract_id) {
        QueryWrapper<ContractTerms> queryWrapper =new QueryWrapper<ContractTerms>();
        queryWrapper.eq("contract_id",contract_id);
        List<ContractTerms> list = contractTermsService.list(queryWrapper);
        return Result.ok(list);
    }

    @AutoLog(value = "查询合同模板详情表信息")
    @ApiOperation(value="查询合同模板详情表信息", notes="查询合同模板详情表信息")
    @GetMapping(value = "/templateDetail")
    public Result<?> templateDetail(@RequestParam(name="template_id") String templateId) {
        QueryWrapper<SysLegalTemplateDetail> queryWrapper =new QueryWrapper<SysLegalTemplateDetail>();
        queryWrapper.eq("template_id",templateId);
        queryWrapper.orderByAsc("sort_num");
        List<SysLegalTemplateDetail> list = sysLegalTemplateDetailService.list(queryWrapper);
        return Result.ok(list);
    }



    /**
     *   通过contract_id删除
     *
     * @param contract_id
     * @return
     */
    @AutoLog(value = "合同条款表-通过合同id删除")
    @ApiOperation(value="合同条款表-通过合同id删除", notes="合同条款表-通过合同id删除")
    @DeleteMapping(value = "/deletetemplate")
    public Result<?> deletetemplate(@RequestParam(name="contract_id") String contract_id) {
        QueryWrapper<ContractTerms> queryWrapper =new QueryWrapper<ContractTerms>();
        queryWrapper.eq("contract_id",contract_id);
        contractTermsService.remove(queryWrapper);
        return Result.ok("删除成功!");
    }

    @AutoLog(value = "查询合同条款信息")
    @ApiOperation(value="查询合同条款信息", notes="查询合同条款信息")
    @GetMapping(value = "/templateid")
    public Result<?> templateid(@RequestParam(name="template_id") String templateId,@RequestParam(name="cid") String cid) {
        System.out.println(templateId+"---------"+cid);
        QueryWrapper<ContractTerms> qcontract =new QueryWrapper<ContractTerms>();
        qcontract.eq("contract_id",cid);
        qcontract.orderByAsc("sort");
        List<ContractTerms> listc =contractTermsService.list(qcontract);
        if(listc.isEmpty()){
            QueryWrapper<SysLegalTemplateDetail> queryWrapper =new QueryWrapper<SysLegalTemplateDetail>();
            queryWrapper.eq("template_id",templateId);
            queryWrapper.orderByAsc("sort_num");
            List<SysLegalTemplateDetail> list = sysLegalTemplateDetailService.list(queryWrapper);
            return Result.ok(list);
        }else{
            return Result.ok(listc);
        }
    }

    /**
     *  添加
     *
     * @param sysLegalTemplateDetail
     * @return
     */
    @AutoLog(value = "模板详情表-添加")
    @ApiOperation(value="模板详情表-添加", notes="模板详情表-添加")
    @PostMapping(value = "/addtemplate")
    public Result<?> addtemplate(@RequestBody SysLegalTemplateDetail[] sysLegalTemplateDetail) {
        for (int i = 0; i < sysLegalTemplateDetail.length; i++) {
            sysLegalTemplateDetailService.save(sysLegalTemplateDetail[i]);
        }
        return Result.ok("添加成功!");
    }

    /**
     *  添加
     *
     * @param contractTerms
     * @return
     */
    @AutoLog(value = "合同条款-添加")
    @ApiOperation(value="合同条款-添加", notes="合同条款-添加")
    @PostMapping(value = "/termsadd")
    public Result<?> termsadd(@RequestBody ContractTerms[] contractTerms) {
        for (int i = 0; i < contractTerms.length; i++) {
            contractTermsService.save(contractTerms[i]);
        }
        return Result.ok("添加成功!");
    }
}
