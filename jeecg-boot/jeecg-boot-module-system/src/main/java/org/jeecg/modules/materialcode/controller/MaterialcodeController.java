package org.jeecg.modules.materialcode.controller;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.materialcode.entity.Materialcode;
import org.jeecg.modules.materialcode.service.IMaterialcodeService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

import org.springframework.jdbc.core.JdbcTemplate;

import static java.lang.Integer.parseInt;
import static org.jeecg.common.system.query.QueryGenerator.installMplus;

/**
 * @Description: 物资编码表
 * @Author: MountCao
 * @Date:   2020-11-30
 * @Version: V1.0
 */
@Api(tags="物资编码表")
@RestController
@RequestMapping("/materialcode/materialcode")
@Slf4j

public class MaterialcodeController extends JeecgController<Materialcode, IMaterialcodeService> {
	@Autowired
	private IMaterialcodeService materialcodeService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	/**
	 * 分页列表查询
	 *
	 * @param materialcode
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "物资编码表-分页列表查询")
	@ApiOperation(value="物资编码表-分页列表查询", notes="物资编码表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(Materialcode materialcode,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<Materialcode> queryWrapper = QueryGenerator.initQueryWrapper(materialcode, req.getParameterMap());
		Page<Materialcode> page = new Page<Materialcode>(pageNo, pageSize);
		IPage<Materialcode> pageList = materialcodeService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param materialcode
	 * @return
	 */
	@AutoLog(value = "物资编码表-添加")
	@ApiOperation(value="物资编码表-添加", notes="物资编码表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody Materialcode materialcode){
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String code = "WX"+df.format(new Date());
		String sql = "SELECT count(*) as sum from materialcode where material_code like '" + code + "%'";
		String str = jdbcTemplate.queryForObject(sql, String.class);
		String y = code+String.format("%04d", parseInt(str)+1);
		materialcode.setMaterialCode(y);
		materialcodeService.save(materialcode);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param materialcode
	 * @return
	 */
	@AutoLog(value = "物资编码表-编辑")
	@ApiOperation(value="物资编码表-编辑", notes="物资编码表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody Materialcode materialcode) {
		materialcodeService.updateById(materialcode);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "物资编码表-通过id删除")
	@ApiOperation(value="物资编码表-通过id删除", notes="物资编码表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		materialcodeService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "物资编码表-批量删除")
	@ApiOperation(value="物资编码表-批量删除", notes="物资编码表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.materialcodeService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "物资编码表-通过id查询")
	@ApiOperation(value="物资编码表-通过id查询", notes="物资编码表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		Materialcode materialcode = materialcodeService.getById(id);
		if(materialcode==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(materialcode);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param materialcode
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Materialcode materialcode) {
        return super.exportXls(request, materialcode, Materialcode.class, "物资编码表");
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
        return super.importExcel(request, response, Materialcode.class);
    }

}
