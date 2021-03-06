package org.jeecg.modules.contract.syslegaltemplatedetail.controller;

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
import org.jeecg.modules.contract.Hetonggongshi.entity.SysHetonggongshi;
import org.jeecg.modules.contract.syslegaltemplatedetail.entity.SysLegalTemplateDetail;
import org.jeecg.modules.contract.syslegaltemplatedetail.service.ISysLegalTemplateDetailService;

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
 * @Description: 法务模板详情表
 * @Author: jeecg-boot
 * @Date:   2020-12-10
 * @Version: V1.0
 */
@Api(tags="法务模板详情表")
@RestController
@RequestMapping("/syslegaltemplatedetail/sysLegalTemplateDetail")
@Slf4j
public class SysLegalTemplateDetailController extends JeecgController<SysLegalTemplateDetail, ISysLegalTemplateDetailService> {
	@Autowired
	private ISysLegalTemplateDetailService sysLegalTemplateDetailService;
	
	/**
	 * 分页列表查询
	 *
	 * @param sysLegalTemplateDetail
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "法务模板详情表-分页列表查询")
	@ApiOperation(value="法务模板详情表-分页列表查询", notes="法务模板详情表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(SysLegalTemplateDetail sysLegalTemplateDetail,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<SysLegalTemplateDetail> queryWrapper = QueryGenerator.initQueryWrapper(sysLegalTemplateDetail, req.getParameterMap());
		Page<SysLegalTemplateDetail> page = new Page<SysLegalTemplateDetail>(pageNo, pageSize);
		IPage<SysLegalTemplateDetail> pageList = sysLegalTemplateDetailService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param sysLegalTemplateDetail
	 * @return
	 */
	@AutoLog(value = "法务模板详情表-添加")
	@ApiOperation(value="法务模板详情表-添加", notes="法务模板详情表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody SysLegalTemplateDetail[] sysLegalTemplateDetail) {
		for (int i = 0; i < sysLegalTemplateDetail.length; i++) {
			sysLegalTemplateDetailService.save(sysLegalTemplateDetail[i]);
		}
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param sysLegalTemplateDetail
	 * @return
	 */
	@AutoLog(value = "法务模板详情表-编辑")
	@ApiOperation(value="法务模板详情表-编辑", notes="法务模板详情表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody SysLegalTemplateDetail[] sysLegalTemplateDetail) {
		for (int i = 0; i < sysLegalTemplateDetail.length; i++) {
			sysLegalTemplateDetailService.updateById(sysLegalTemplateDetail[i]);
		}
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "法务模板详情表-通过id删除")
	@ApiOperation(value="法务模板详情表-通过id删除", notes="法务模板详情表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		sysLegalTemplateDetailService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "法务模板详情表-批量删除")
	@ApiOperation(value="法务模板详情表-批量删除", notes="法务模板详情表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.sysLegalTemplateDetailService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "法务模板详情表-通过id查询")
	@ApiOperation(value="法务模板详情表-通过id查询", notes="法务模板详情表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		SysLegalTemplateDetail sysLegalTemplateDetail = sysLegalTemplateDetailService.getById(id);
		if(sysLegalTemplateDetail==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(sysLegalTemplateDetail);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param sysLegalTemplateDetail
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, SysLegalTemplateDetail sysLegalTemplateDetail) {
        return super.exportXls(request, sysLegalTemplateDetail, SysLegalTemplateDetail.class, "法务模板详情表");
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
        return super.importExcel(request, response, SysLegalTemplateDetail.class);
    }

}
