package org.jeecg.modules.contract.Hetongmuban.controller;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;


import lombok.extern.slf4j.Slf4j;


import org.jeecg.modules.contract.Hetongmuban.entity.SysTemplate;
import org.jeecg.modules.contract.Hetongmuban.service.ISysTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 模板表
 * @Author: jeecg-boot
 * @Date:   2020-09-10
 * @Version: V1.0
 */
@Api(tags="模板表")
@RestController
@RequestMapping("/SysTemplate/sysTemplate")
@Slf4j
public class SysTemplateController extends JeecgController<SysTemplate, ISysTemplateService> {
	@Autowired
	private ISysTemplateService sysTemplateService;
	
	/**
	 * 分页列表查询
	 *
	 * @param sysTemplate
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "模板表-分页列表查询")
	@ApiOperation(value="模板表-分页列表查询", notes="模板表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(SysTemplate sysTemplate,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="100") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<SysTemplate> queryWrapper = QueryGenerator.initQueryWrapper(sysTemplate, req.getParameterMap());
		Page<SysTemplate> page = new Page<SysTemplate>(pageNo, pageSize);
		IPage<SysTemplate> pageList = sysTemplateService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param sysTemplate
	 * @return
	 */
	@AutoLog(value = "模板表-添加")
	@ApiOperation(value="模板表-添加", notes="模板表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody SysTemplate sysTemplate) {
		sysTemplateService.save(sysTemplate);
		return Result.ok("添加成功！");
	}

	 /**
	  *   保存
	  *
	  * @param sysTemplate
	  * @return
	  */
	 @AutoLog(value = "模板表-保存")
	 @ApiOperation(value="模板表-保存", notes="模板表-保存")
	 @PostMapping(value = "/saveTemplate")
	 public Result<?> saveTemplate(@RequestBody SysTemplate[] sysTemplate) {
		 for (int i = 0; i < sysTemplate.length; i++) {
			 sysTemplate[i].setSortnum(i+1);
			 sysTemplateService.save(sysTemplate[i]);
		 }
		 return Result.ok("保存成功");
	 }

	/**
	 *  编辑
	 *
	 * @param sysTemplate
	 * @return
	 */
	@AutoLog(value = "模板表-编辑")
	@ApiOperation(value="模板表-编辑", notes="模板表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody SysTemplate sysTemplate) {
		sysTemplateService.updateById(sysTemplate);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "模板表-通过id删除")
	@ApiOperation(value="模板表-通过id删除", notes="模板表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		sysTemplateService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "模板表-批量删除")
	@ApiOperation(value="模板表-批量删除", notes="模板表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.sysTemplateService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "模板表-通过id查询")
	@ApiOperation(value="模板表-通过id查询", notes="模板表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		SysTemplate sysTemplate = sysTemplateService.getById(id);
		if(sysTemplate==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(sysTemplate);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param sysTemplate
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, SysTemplate sysTemplate) {
        return super.exportXls(request, sysTemplate, SysTemplate.class, "模板表");
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
        return super.importExcel(request, response, SysTemplate.class);
    }

}
