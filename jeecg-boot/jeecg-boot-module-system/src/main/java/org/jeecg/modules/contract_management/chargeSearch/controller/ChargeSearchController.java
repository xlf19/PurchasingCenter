package org.jeecg.modules.contract_management.chargeSearch.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.contract_management.chargeSearch.service.IChargeSearchService;
import org.jeecg.modules.contract_management.contractpurchase.entity.ContractPurchase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Description: 原炉料查询
 * @Author: jeecg-boot
 * @Date: 2020-12-04
 * @Version: V1.0
 */
@Api(tags = "原炉料查询")
@RestController
@RequestMapping("/chargsearch/chargsearch")
@Slf4j
public class ChargeSearchController extends JeecgController<T, IChargeSearchService> {
    @Autowired
    private IChargeSearchService ichargeSearchService;

    //分页列表查询
    @AutoLog(value = "原炉料表-分页列表查询")
    @ApiOperation(value = "原炉料表-分页列表查询", notes = "原炉料表-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> selectfldy(ContractPurchase contractPurchase,
                                @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                HttpServletRequest req) {
        Page<Map<Object, String>> page = new Page<Map<Object, String>>(pageNo, pageSize);
        IPage<Map<Object, String>> pageList = ichargeSearchService.list(page, contractPurchase.getStartingTime(), contractPurchase.getStopTime());
        return Result.ok(pageList);
    }

    //分页列表查询
    @AutoLog(value = "原炉料表-产品明细查询")
    @ApiOperation(value = "原炉料表-产品明细查询", notes = "原炉料表-产品明细查询")
    @GetMapping(value = "/findDetail")
    public Result<?> findDetail(
            @RequestParam(name = "contractid") String contractid,
            HttpServletRequest req) {
        List<Map<Object,String>> list = ichargeSearchService.findDetail(contractid);
        return Result.ok(list);
    }
}
