package org.jeecg.modules.basicdata.original.controller;

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
import org.jeecg.modules.basicdata.original.entity.OriginalCharge;
import org.jeecg.modules.basicdata.original.service.IOriginalChargeService;

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
 * @Description: 原炉料供应商
 * @Author: jeecg-boot
 * @Date:   2020-11-26
 * @Version: V1.0
 */
@Api(tags="原炉料供应商")
@RestController
@RequestMapping("/original/originalCharge")
@Slf4j
public class OriginalChargeController extends JeecgController<OriginalCharge, IOriginalChargeService> {
	@Autowired
	private IOriginalChargeService originalChargeService;
	
	/**
	 * 分页列表查询
	 *
	 * @param originalCharge
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "原炉料供应商-分页列表查询")
	@ApiOperation(value="原炉料供应商-分页列表查询", notes="原炉料供应商-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(OriginalCharge originalCharge,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		originalCharge.setIsdelete(1);
		QueryWrapper<OriginalCharge> queryWrapper = QueryGenerator.initQueryWrapper(originalCharge, req.getParameterMap());
		Page<OriginalCharge> page = new Page<OriginalCharge>(pageNo, pageSize);
		IPage<OriginalCharge> pageList = originalChargeService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param originalCharge
	 * @return
	 */
	@AutoLog(value = "原炉料供应商-添加")
	@ApiOperation(value="原炉料供应商-添加", notes="原炉料供应商-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody OriginalCharge originalCharge) {
		originalCharge.setIsdelete(1);
		originalCharge.setUseStatus(1);
		originalChargeService.save(originalCharge);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param originalCharge
	 * @return
	 */
	@AutoLog(value = "原炉料供应商-编辑")
	@ApiOperation(value="原炉料供应商-编辑", notes="原炉料供应商-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody OriginalCharge originalCharge) {
		originalChargeService.updateById(originalCharge);
		return Result.ok("编辑成功!");
	}
	
//	/**
//	 *   通过id删除
//	 *
//	 * @param id
//	 * @return
//	 */
//	@AutoLog(value = "原炉料供应商-通过id删除")
//	@ApiOperation(value="原炉料供应商-通过id删除", notes="原炉料供应商-通过id删除")
//	@DeleteMapping(value = "/delete")
//	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
//		originalChargeService.removeById(id);
//		return Result.ok("删除成功!");
//	}
	 /**
	  *   通过id删除
	  *
	  * @param id
	  * @return
	  */
	 @AutoLog(value = "原炉料供应商-通过id删除")
	 @ApiOperation(value="原炉料供应商-通过id删除", notes="原炉料供应商-通过id删除")
	 @DeleteMapping(value = "/delete")
	 public Result<?> delete(@RequestParam(name="id",required=true) String id) {
	 	 OriginalCharge ori=originalChargeService.getById(id);
		 ori.setIsdelete(2);
		 originalChargeService.updateById(ori);
//		 originalChargeService.removeById(id);
		 return Result.ok("删除成功!");
	 }
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "原炉料供应商-批量删除")
	@ApiOperation(value="原炉料供应商-批量删除", notes="原炉料供应商-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		String[] s = ids.split(",");
        this.originalChargeService.deleteOrilist(s);
		//this.originalChargeService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

	 @AutoLog(value = "原炉料供应商-使用状态")
	 @ApiOperation(value="原炉料供应商-使用状态", notes="原炉料供应商-使用状态")
	 @DeleteMapping(value = "/usestatus")
	 public Result<?> usestatus(@RequestParam(name="ids",required=true) String ids,@RequestParam(name="status",required=true) Integer status) {
		 String[] s = ids.split(",");
		 this.originalChargeService.usestatus(s,status);
		 if(status==1){
			 return Result.ok("数据设为可用!");
		 }
		 return Result.ok("数据设为不可用!");
	 }

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "原炉料供应商-通过id查询")
	@ApiOperation(value="原炉料供应商-通过id查询", notes="原炉料供应商-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		OriginalCharge originalCharge = originalChargeService.getById(id);
		if(originalCharge==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(originalCharge);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param originalCharge
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, OriginalCharge originalCharge) {
        return super.exportXls(request, originalCharge, OriginalCharge.class, "原炉料供应商");
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
        return super.importExcel(request, response, OriginalCharge.class);
    }

}
