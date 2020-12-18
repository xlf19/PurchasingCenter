package org.jeecg.modules.contract_management.contractattachment.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.contract_management.contractattachment.service.IContractAttachmentService;
import org.jeecg.modules.contract_management.contractpurchase.entity.ContractPurchase;
import org.jeecg.modules.contract_management.contractpurchase.entity.ContractSerach;
import org.jeecg.modules.contract_management.contractpurchase.service.IContractPurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Description: 合同附件管理
 * @Author: jeecg-boot
 * @Date: 2020-12-04
 * @Version: V1.0
 */
@Api(tags = "合同附件管理")
@RestController
@RequestMapping("/contractattachment/contractattachment")
@Slf4j
public class ContractAttachmentController extends JeecgController<T, IContractAttachmentService> {
    @Autowired
    private IContractAttachmentService iContractAttachmentService;
    @Autowired
    private IContractPurchaseService contractPurchaseService;


    //分页列表查询
    @AutoLog(value = "合同附件管理-分页列表查询")
    @ApiOperation(value = "合同附件管理-分页列表查询", notes = "合同附件管理-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> selectfldy(ContractSerach contractSerach,
                                @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                HttpServletRequest req) {
        Page<Map<Object, String>> page = new Page<Map<Object, String>>(pageNo, pageSize);
        IPage<Map<Object, String>> pageList = iContractAttachmentService.list(page, contractSerach.getStartingTime(), contractSerach.getStopTime());
        return Result.ok(pageList);
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "采购合同表-通过id查询")
    @ApiOperation(value="采购合同表-通过id查询", notes="采购合同表-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
        ContractPurchase contractPurchase = contractPurchaseService.getById(id);
        if(contractPurchase==null) {
            return Result.error("未找到对应数据");
        }
        return Result.ok(contractPurchase);
    }
    /**
     *  编辑
     *
     * @param contractPurchase
     * @return
     */
    @AutoLog(value = "采购合同表-编辑")
    @ApiOperation(value="采购合同表-编辑", notes="采购合同表-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody ContractPurchase contractPurchase) {
        System.out.println(contractPurchase);
        contractPurchaseService.updateById(contractPurchase);
        return Result.ok("编辑成功!");
    }
}
