package org.jeecg.modules.contract_management.contract_status.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import org.jeecg.modules.contract_management.contract_status.service.IContractStatusService;
import org.jeecg.modules.system.entity.SysDepart;
import org.jeecg.modules.system.service.ISysDepartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Description: 合同状态
 * @Author: jeecg-boot
 * @Date: 2020-12-04
 * @Version: V1.0
 */
@Api(tags = "合同状态")
@RestController
@RequestMapping("/contractstatus/contractstatus")
@Slf4j
public class ContractStatusController extends JeecgController<T, IContractStatusService> {
    @Autowired
    private IContractStatusService icontractStatusService;
    @Autowired
    private ISysDepartService sysDepartService;
    /**
     * 通过部门查询
     *
     * @return
     */
    @AutoLog(value = "通过部门查询合同标题")
    @ApiOperation(value = "通过部门查询合同标题", notes = "通过部门查询合同标题")
    @GetMapping(value = "/searchTitle")
    public Result<?> searchTitle() {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        String departname="";
        if(oConvertUtils.isNotEmpty(sysUser.getOrgCode())){
            QueryWrapper<SysDepart> queryWrapper=new QueryWrapper<SysDepart>();
            queryWrapper.eq("org_code",sysUser.getOrgCode());
            SysDepart depart=sysDepartService.getOne(queryWrapper);
            departname=depart.getDepartName();
        }
        List<Map<String, Object>> list = icontractStatusService.searchTitle(departname);
        return Result.ok(list);
    }

}
