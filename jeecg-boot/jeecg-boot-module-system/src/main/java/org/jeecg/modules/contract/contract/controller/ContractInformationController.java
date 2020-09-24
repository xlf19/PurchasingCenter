package org.jeecg.modules.contract.contract.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;

import org.jeecg.modules.contract.contract.entity.ContractInformation;
import org.jeecg.modules.contract.contract.service.IContractInformationService;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

/**
 * @Description: 合同信息表
 * @Author: jeecg-boot
 * @Date: 2020-08-28
 * @Version: V1.0
 */
@Api(tags = "合同信息表")
@RestController
@RequestMapping("/contract/contractInformation")
@Slf4j
public class ContractInformationController extends JeecgController<ContractInformation, IContractInformationService> {
    @Autowired
    private IContractInformationService contractInformationService;

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
                                   HttpServletRequest req) {
        QueryWrapper<ContractInformation> queryWrapper = QueryGenerator.initQueryWrapper(contractInformation, req.getParameterMap());
        Page<ContractInformation> page = new Page<ContractInformation>(pageNo, pageSize);
        IPage<ContractInformation> pageList = contractInformationService.page(page, queryWrapper);
        return Result.ok(pageList);
    }

    /**
     * 添加
     *
     * @param contractInformation
     * @return
     */
    @AutoLog(value = "合同信息表-添加")
    @ApiOperation(value = "合同信息表-添加", notes = "合同信息表-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody ContractInformation contractInformation) {
        contractInformationService.save(contractInformation);
        return Result.ok("添加成功！");
    }

    /**
     * 编辑
     *
     * @param contractInformation
     * @return
     */
    @AutoLog(value = "合同信息表-编辑")
    @ApiOperation(value = "合同信息表-编辑", notes = "合同信息表-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody ContractInformation contractInformation) {
        contractInformationService.updateById(contractInformation);
        return Result.ok("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "合同信息表-通过id删除")
    @ApiOperation(value = "合同信息表-通过id删除", notes = "合同信息表-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        contractInformationService.removeById(id);
        return Result.ok("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "合同信息表-批量删除")
    @ApiOperation(value = "合同信息表-批量删除", notes = "合同信息表-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.contractInformationService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.ok("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "合同信息表-通过id查询")
    @ApiOperation(value = "合同信息表-通过id查询", notes = "合同信息表-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        ContractInformation contractInformation = contractInformationService.getById(id);
        if (contractInformation == null) {
            return Result.error("未找到对应数据");
        }
        return Result.ok(contractInformation);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param contractInformation
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ContractInformation contractInformation) {
        return super.exportXls(request, contractInformation, ContractInformation.class, "合同信息表");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, ContractInformation.class);
    }

	//获取凭证号
	@AutoLog(value = "合同信息表-凭证号")
	@ApiOperation(value = "合同信息表-凭证号", notes = "合同信息表-凭证号")
	@GetMapping(value = "/findpzh")
	public Result<?> findpzh() {
		Integer number=contractInformationService.findpzh();
		return Result.ok(number);
	}


	//查询合同编号
    @AutoLog(value = "查询合同编号")
    @ApiOperation(value = "查询合同编号", notes = "查询合同编号")
    @GetMapping(value = "/selectHtbh")
    public Result<?> selectHtbh(@RequestParam(name = "htbh") String htbh,
                                @RequestParam(name = "httype", required = true) String httype) {
        List<String> list =contractInformationService.selectHtbh(htbh,httype);
        return Result.ok(list);
    }

    //查询合同凭证号
    @AutoLog(value = "查询合同凭证号")
    @ApiOperation(value = "查询合同凭证号", notes = "查询合同凭证号")
    @GetMapping(value = "/selectHtpzh")
    public Result<?> selectHtpzh(@RequestParam(name = "htbh") String htbh,
                                @RequestParam(name = "httype", required = true) String httype) {
        List<String> list =contractInformationService.selectHtpzh(htbh,httype);
        return Result.ok(list);
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
    @GetMapping(value = "/listhtxx")
    public Result<?> listhtxx(ContractInformation contractInformation,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        contractInformation.setIsDelete(0);
        contractInformation.setSettlementIdentification(1);
        QueryWrapper<ContractInformation> queryWrapper = QueryGenerator.initQueryWrapper(contractInformation, req.getParameterMap());
        Page<ContractInformation> page = new Page<ContractInformation>(pageNo, pageSize);
        IPage<ContractInformation> pageList = contractInformationService.page(page, queryWrapper);
        return Result.ok(pageList);
    }
}
