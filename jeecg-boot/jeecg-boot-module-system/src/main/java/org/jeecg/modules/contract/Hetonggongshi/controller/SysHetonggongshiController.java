package org.jeecg.modules.contract.Hetonggongshi.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.contract.Hetonggongshi.entity.SysHetonggongshi;
import org.jeecg.modules.contract.Hetonggongshi.service.ISysHetonggongshiService;

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
 * @Description: 合同公式表
 * @Author: jeecg-boot
 * @Date:   2020-09-09
 * @Version: V1.0
 */
@Api(tags="合同公式表")
@RestController
@RequestMapping("/Hetonggongshi/sysHetonggongshi")
@Slf4j
public class SysHetonggongshiController extends JeecgController<SysHetonggongshi, ISysHetonggongshiService> {
	@Autowired
	private ISysHetonggongshiService sysHetonggongshiService;
	
	/**
	 * 分页列表查询
	 *
	 * @param sysHetonggongshi
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "合同公式表-分页列表查询")
	@ApiOperation(value="合同公式表-分页列表查询", notes="合同公式表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(SysHetonggongshi sysHetonggongshi,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="100") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<SysHetonggongshi> queryWrapper = QueryGenerator.initQueryWrapper(sysHetonggongshi, req.getParameterMap());
		Page<SysHetonggongshi> page = new Page<SysHetonggongshi>(pageNo, pageSize);
		IPage<SysHetonggongshi> pageList = sysHetonggongshiService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param sysHetonggongshi
	 * @return
	 */
	@AutoLog(value = "合同公式表-添加")
	@ApiOperation(value="合同公式表-添加", notes="合同公式表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody SysHetonggongshi sysHetonggongshi) {
		sysHetonggongshiService.save(sysHetonggongshi);
		return Result.ok("添加成功！");
	}
	/**
	 *   添加
	 *
	 * @param sysHetonggongshis
	 * @return
	 */
	@AutoLog(value = "合同公式表-保存数据")
	@ApiOperation(value="合同公式表-保存数据", notes="合同公式表-保存数据")
	@PostMapping(value = "/savaHtGongShi")
	public Result<?> savaHtGongShi(@RequestBody SysHetonggongshi[] sysHetonggongshis) {
		for (int i = 0; i < sysHetonggongshis.length; i++) {
			//遍历数据,如果存在id值就是更新操作，不存在就是新增操作
			SysHetonggongshi searchData = sysHetonggongshiService.getById(sysHetonggongshis[i].getId());
			if(searchData != null){
				//排序字段，查询到数据行数后，如果存在数据则替换掉排序字段
				sysHetonggongshis[i].setSortnum(i+1);
				//查询结果不为空，表示数据库存在数据，则是更新数据
				sysHetonggongshiService.updateById(sysHetonggongshis[i]);
			}else{
				//排序字段，如果是新数据，则在总行数后面加上排序字段
				sysHetonggongshis[i].setSortnum(i+1);
				//查询结果为空，表示数据库存在数据，则是新增数据
				sysHetonggongshiService.save(sysHetonggongshis[i]);
			}
		}
		return Result.ok("保存成功");
	}
	
	/**
	 *  编辑
	 *
	 * @param sysHetonggongshi
	 * @return
	 */
	@AutoLog(value = "合同公式表-编辑")
	@ApiOperation(value="合同公式表-编辑", notes="合同公式表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody SysHetonggongshi sysHetonggongshi) {
		sysHetonggongshiService.updateById(sysHetonggongshi);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "合同公式表-通过id删除")
	@ApiOperation(value="合同公式表-通过id删除", notes="合同公式表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		sysHetonggongshiService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "合同公式表-批量删除")
	@ApiOperation(value="合同公式表-批量删除", notes="合同公式表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.sysHetonggongshiService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "合同公式表-通过id查询")
	@ApiOperation(value="合同公式表-通过id查询", notes="合同公式表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		SysHetonggongshi sysHetonggongshi = sysHetonggongshiService.getById(id);
		if(sysHetonggongshi==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(sysHetonggongshi);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param sysHetonggongshi
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, SysHetonggongshi sysHetonggongshi) {
        return super.exportXls(request, sysHetonggongshi, SysHetonggongshi.class, "合同公式表");
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
        return super.importExcel(request, response, SysHetonggongshi.class);
    }

}
