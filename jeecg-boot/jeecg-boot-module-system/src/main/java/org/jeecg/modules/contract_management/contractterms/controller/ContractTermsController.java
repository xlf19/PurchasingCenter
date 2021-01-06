package org.jeecg.modules.contract_management.contractterms.controller;

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
import org.jeecg.modules.contract_management.contractterms.entity.ContractTerms;
import org.jeecg.modules.contract_management.contractterms.service.IContractTermsService;

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
 * @Description: 合同条款表
 * @Author: jeecg-boot
 * @Date:   2021-01-06
 * @Version: V1.0
 */
@Api(tags="合同条款表")
@RestController
@RequestMapping("/contractterms/contractTerms")
@Slf4j
public class ContractTermsController extends JeecgController<ContractTerms, IContractTermsService> {
	@Autowired
	private IContractTermsService contractTermsService;
	
	/**
	 * 分页列表查询
	 *
	 * @param contractTerms
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "合同条款表-分页列表查询")
	@ApiOperation(value="合同条款表-分页列表查询", notes="合同条款表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(ContractTerms contractTerms,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<ContractTerms> queryWrapper = QueryGenerator.initQueryWrapper(contractTerms, req.getParameterMap());
		Page<ContractTerms> page = new Page<ContractTerms>(pageNo, pageSize);
		IPage<ContractTerms> pageList = contractTermsService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param contractTerms
	 * @return
	 */
	@AutoLog(value = "合同条款表-添加")
	@ApiOperation(value="合同条款表-添加", notes="合同条款表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody ContractTerms contractTerms) {
		contractTermsService.save(contractTerms);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param contractTerms
	 * @return
	 */
	@AutoLog(value = "合同条款表-编辑")
	@ApiOperation(value="合同条款表-编辑", notes="合同条款表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody ContractTerms contractTerms) {
		contractTermsService.updateById(contractTerms);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "合同条款表-通过id删除")
	@ApiOperation(value="合同条款表-通过id删除", notes="合同条款表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		contractTermsService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "合同条款表-批量删除")
	 @ApiOperation(value="合同条款表-批量删除", notes="合同条款表-批量删除")
	 @DeleteMapping(value = "/deleteBatch")
	 public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		 this.contractTermsService.removeByIds(Arrays.asList(ids.split(",")));
		 return Result.ok("批量删除成功!");
	 }
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "合同条款表-通过id查询")
	@ApiOperation(value="合同条款表-通过id查询", notes="合同条款表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		ContractTerms contractTerms = contractTermsService.getById(id);
		if(contractTerms==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(contractTerms);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param contractTerms
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ContractTerms contractTerms) {
        return super.exportXls(request, contractTerms, ContractTerms.class, "合同条款表");
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
        return super.importExcel(request, response, ContractTerms.class);
    }

}
