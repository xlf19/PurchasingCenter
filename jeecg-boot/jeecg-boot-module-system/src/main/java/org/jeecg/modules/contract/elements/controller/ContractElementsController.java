package org.jeecg.modules.contract.elements.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.contract.contract.entity.ContractInformation;
import org.jeecg.modules.contract.contract.service.IContractInformationService;
import org.jeecg.modules.contract.elements.entity.ContractElements;
import org.jeecg.modules.contract.elements.service.IContractElementsService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 合同元素表
 * @Author: jeecg-boot
 * @Date:   2020-08-31
 * @Version: V1.0
 */
@Api(tags="合同元素表")
@RestController
@RequestMapping("/elements/contractElements")
@Slf4j
public class ContractElementsController extends JeecgController<ContractElements, IContractElementsService> {
	@Autowired
	private IContractElementsService contractElementsService;

	 @Autowired
	 private IContractInformationService contractInformationService;
	
	/**
	 * 分页列表查询
	 *
	 * @param contractElements
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "合同元素表-分页列表查询")
	@ApiOperation(value="合同元素表-分页列表查询", notes="合同元素表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(ContractElements contractElements,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<ContractElements> queryWrapper = QueryGenerator.initQueryWrapper(contractElements, req.getParameterMap());
		Page<ContractElements> page = new Page<ContractElements>(pageNo, pageSize);
		IPage<ContractElements> pageList = contractElementsService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param contractElements
	 * @return
	 */
	@AutoLog(value = "合同元素表-添加")
	@ApiOperation(value="合同元素表-添加", notes="合同元素表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody ContractElements contractElements) {
		contractElementsService.save(contractElements);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param contractElements
	 * @return
	 */
	@AutoLog(value = "合同元素表-编辑")
	@ApiOperation(value="合同元素表-编辑", notes="合同元素表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody ContractElements contractElements) {
		contractElementsService.updateById(contractElements);
		return Result.ok("编辑成功!");
	}
	

	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "合同元素表-批量删除")
	@ApiOperation(value="合同元素表-批量删除", notes="合同元素表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.contractElementsService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "合同元素表-通过id查询")
	@ApiOperation(value="合同元素表-通过id查询", notes="合同元素表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		ContractElements contractElements = contractElementsService.getById(id);
		if(contractElements==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(contractElements);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param contractElements
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ContractElements contractElements) {
        return super.exportXls(request, contractElements, ContractElements.class, "合同元素表");
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
        return super.importExcel(request, response, ContractElements.class);
    }


	 //查询元素数据
	 @AutoLog(value = "元素数据表")
	 @ApiOperation(value = "元素数据表", notes = "元素数据表")
	 @GetMapping(value = "/elementslist")
	 public Result<?> findList(@RequestParam(name = "cid", defaultValue = "") String cid, HttpServletRequest req) {
		 List<ContractElements> list = contractElementsService.findList(cid);
		 return Result.ok(list);
	 }

	 //删除
	 @AutoLog(value = "合同元素表-通过id删除")
	 @ApiOperation(value = "合同元素表-通过id删除", notes = "合同元素表-通过id删除")
	 @DeleteMapping(value = "/delete")
	 public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
		 ContractInformation contractInformation = contractInformationService.getById(id);
		 contractInformation.setIsDelete(1);
		 contractInformationService.updateById(contractInformation);
		 contractElementsService.updateElelist(id);
		 return Result.ok("删除成功!");
	 }

	 //根据合同号查询元素数据
	 @AutoLog(value = "元素数据表")
	 @ApiOperation(value = "元素数据表", notes = "元素数据表")
	 @GetMapping(value = "/findListhth")
	 public Result<?> findListhth(@RequestParam(name = "hth", defaultValue = "") String hth, HttpServletRequest req) {
		 List<String> list = contractElementsService.findListhth(hth);
		 return Result.ok(list);
	 }
}
