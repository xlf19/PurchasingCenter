package org.jeecg.modules.contract_management.productlist.controller;

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
import org.jeecg.modules.contract_management.productlist.entity.ProductList;
import org.jeecg.modules.contract_management.productlist.service.IProductListService;

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
 * @Description: 产品列表
 * @Author: jeecg-boot
 * @Date:   2020-12-10
 * @Version: V1.0
 */
@Api(tags="产品列表")
@RestController
@RequestMapping("/productlist/productList")
@Slf4j
public class ProductListController extends JeecgController<ProductList, IProductListService> {
	@Autowired
	private IProductListService productListService;
	
	/**
	 * 分页列表查询
	 *
	 * @param productList
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "产品列表-分页列表查询")
	@ApiOperation(value="产品列表-分页列表查询", notes="产品列表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(ProductList productList,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<ProductList> queryWrapper = QueryGenerator.initQueryWrapper(productList, req.getParameterMap());
		Page<ProductList> page = new Page<ProductList>(pageNo, pageSize);
		IPage<ProductList> pageList = productListService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param productList
	 * @return
	 */
	@AutoLog(value = "产品列表-添加")
	@ApiOperation(value="产品列表-添加", notes="产品列表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody ProductList productList) {
		productListService.save(productList);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param productList
	 * @return
	 */
	@AutoLog(value = "产品列表-编辑")
	@ApiOperation(value="产品列表-编辑", notes="产品列表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody ProductList productList) {
		productListService.updateById(productList);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "产品列表-通过id删除")
	@ApiOperation(value="产品列表-通过id删除", notes="产品列表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		productListService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "产品列表-批量删除")
	@ApiOperation(value="产品列表-批量删除", notes="产品列表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.productListService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "产品列表-通过id查询")
	@ApiOperation(value="产品列表-通过id查询", notes="产品列表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		ProductList productList = productListService.getById(id);
		if(productList==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(productList);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param productList
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ProductList productList) {
        return super.exportXls(request, productList, ProductList.class, "产品列表");
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
        return super.importExcel(request, response, ProductList.class);
    }

}
