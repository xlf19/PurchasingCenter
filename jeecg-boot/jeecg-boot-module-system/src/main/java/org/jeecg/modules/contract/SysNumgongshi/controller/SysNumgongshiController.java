package org.jeecg.modules.contract.SysNumgongshi.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;

import org.jeecg.modules.contract.SysNumgongshi.entity.SysNumgongshi;
import org.jeecg.modules.contract.SysNumgongshi.service.ISysNumgongshiService;

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
 * @Description: 数量公式表
 * @Author: jeecg-boot
 * @Date:   2020-09-16
 * @Version: V1.0
 */
@Api(tags="数量公式表")
@RestController
@RequestMapping("/SysNumgongshi/sysNumgongshi")
@Slf4j
public class SysNumgongshiController extends JeecgController<SysNumgongshi, ISysNumgongshiService> {
	@Autowired
	private ISysNumgongshiService sysNumgongshiService;
	
	/**
	 * 分页列表查询
	 *
	 * @param sysNumgongshi
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "数量公式表-分页列表查询")
	@ApiOperation(value="数量公式表-分页列表查询", notes="数量公式表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(SysNumgongshi sysNumgongshi,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<SysNumgongshi> queryWrapper = QueryGenerator.initQueryWrapper(sysNumgongshi, req.getParameterMap());
		Page<SysNumgongshi> page = new Page<SysNumgongshi>(pageNo, pageSize);
		IPage<SysNumgongshi> pageList = sysNumgongshiService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param sysNumgongshi
	 * @return
	 */
	@AutoLog(value = "数量公式表-添加")
	@ApiOperation(value="数量公式表-添加", notes="数量公式表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody SysNumgongshi sysNumgongshi) {
		sysNumgongshiService.save(sysNumgongshi);
		return Result.ok("添加成功！");
	}
	/**
	 *   保存数据
	 *
	 * @param sysNumgongshi
	 * @return
	 */
	@AutoLog(value = "数量公式表-添加")
	@ApiOperation(value="数量公式表-保存数据", notes="数量公式表-保存数据")
	@PostMapping(value = "/savaHtGongShi")
	public Result<?> savaHtGongShi(@RequestBody SysNumgongshi[] sysNumgongshi) {
		for (int i = 0; i < sysNumgongshi.length; i++) {
			//遍历数据,如果存在id值就是更新操作，不存在就是新增操作
			SysNumgongshi searchData = sysNumgongshiService.getById(sysNumgongshi[i].getId());
			if(searchData != null){
				//排序字段，查询到数据行数后，如果存在数据则替换掉排序字段
				sysNumgongshi[i].setSortnum(i+1);
				//查询结果不为空，表示数据库存在数据，则是更新数据
				sysNumgongshiService.updateById(sysNumgongshi[i]);
			}else{
				//排序字段，如果是新数据，则在总行数后面加上排序字段
				sysNumgongshi[i].setSortnum(i+1);
				//查询结果为空，表示数据库存在数据，则是新增数据
				sysNumgongshiService.save(sysNumgongshi[i]);
			}
		}
		return Result.ok("保存成功");
	}
	
	/**
	 *  编辑
	 *
	 * @param sysNumgongshi
	 * @return
	 */
	@AutoLog(value = "数量公式表-编辑")
	@ApiOperation(value="数量公式表-编辑", notes="数量公式表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody SysNumgongshi sysNumgongshi) {
		sysNumgongshiService.updateById(sysNumgongshi);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "数量公式表-通过id删除")
	@ApiOperation(value="数量公式表-通过id删除", notes="数量公式表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		sysNumgongshiService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "数量公式表-批量删除")
	@ApiOperation(value="数量公式表-批量删除", notes="数量公式表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.sysNumgongshiService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "数量公式表-通过id查询")
	@ApiOperation(value="数量公式表-通过id查询", notes="数量公式表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		SysNumgongshi sysNumgongshi = sysNumgongshiService.getById(id);
		if(sysNumgongshi==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(sysNumgongshi);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param sysNumgongshi
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, SysNumgongshi sysNumgongshi) {
        return super.exportXls(request, sysNumgongshi, SysNumgongshi.class, "数量公式表");
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
        return super.importExcel(request, response, SysNumgongshi.class);
    }

}
