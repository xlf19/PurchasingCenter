package org.jeecg.modules.contract_management.contractedit.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.contract_management.contractedit.service.IContractEditService;
import org.jeecg.modules.contract_management.contractpurchase.entity.ContractPurchase;
import org.jeecg.modules.contract_management.contractpurchase.service.IContractPurchaseService;
import org.jeecg.modules.contract_management.productinformation.entity.ProductInformation;
import org.jeecg.modules.contract_management.productinformation.service.IProductInformationService;
import org.jeecg.modules.contract_management.productlist.entity.ProductList;
import org.jeecg.modules.contract_management.productlist.service.IProductListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
