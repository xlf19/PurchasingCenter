package org.jeecg.modules.contract_management.contractpurchase.controller;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.contract_management.contractpurchase.entity.ContractPurchase;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.contract_management.contractpurchase.service.IContractPurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.util.oConvertUtils;

import static java.lang.Integer.parseInt;

/**
 * @Description: 采购合同表
 * @Author: jeecg-boot
 * @Date:   2020-12-04
 * @Version: V1.0
 */
@Api(tags="采购合同表")
@RestController
@RequestMapping("/contractpurchase/contractPurchase")
@Slf4j
public class ContractPurchaseController extends JeecgController<ContractPurchase, IContractPurchaseService> {
	@Autowired
	private IContractPurchaseService contractPurchaseService;
	
	/**
	 * 分页列表查询
	 *
	 * @param contractPurchase
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "采购合同表-分页列表查询")
	@ApiOperation(value="采购合同表-分页列表查询", notes="采购合同表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(ContractPurchase contractPurchase,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<ContractPurchase> queryWrapper = QueryGenerator.initQueryWrapper(contractPurchase, req.getParameterMap());
		Page<ContractPurchase> page = new Page<ContractPurchase>(pageNo, pageSize);
		IPage<ContractPurchase> pageList = contractPurchaseService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param contractPurchase
	 * @return
	 */
	@AutoLog(value = "采购合同表-添加")
	@ApiOperation(value="采购合同表-添加", notes="采购合同表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody ContractPurchase contractPurchase) {
		contractPurchaseService.save(contractPurchase);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param contractPurchase
	 * @return
	 */
	@AutoLog(value = "采购合同表-编辑")
	@ApiOperation(value="采购合同表-编辑", notes="采购合同表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody ContractPurchase contractPurchase) {
		contractPurchaseService.updateById(contractPurchase);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "采购合同表-通过id删除")
	@ApiOperation(value="采购合同表-通过id删除", notes="采购合同表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		contractPurchaseService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "采购合同表-批量删除")
	@ApiOperation(value="采购合同表-批量删除", notes="采购合同表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.contractPurchaseService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "采购合同表-通过id查询")
	@ApiOperation(value="采购合同表-通过id查询", notes="采购合同表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		ContractPurchase contractPurchase = contractPurchaseService.getById(id);
		if(contractPurchase==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(contractPurchase);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param contractPurchase
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ContractPurchase contractPurchase) {
        return super.exportXls(request, contractPurchase, ContractPurchase.class, "采购合同表");
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
        return super.importExcel(request, response, ContractPurchase.class);
    }

     /**
      * 生成合同号
      *
      * @return
      */
     @AutoLog(value = "采购合同表-生成合同号")
     @ApiOperation(value="采购合同表-生成合同号", notes="采购合同表-生成合同号")
     @GetMapping(value = "/hthfind")
     public Result<?> hthfind() {
         String contractNo=contractPurchaseService.hthfind();
		 SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
		 String code = "WX"+df.format(new Date());
         if(oConvertUtils.isEmpty(contractNo)){
			 code=code+"0001";
		 }else{
			 code = code+String.format("%04d", parseInt(contractNo)+1);
		 }
         return Result.ok(code);
     }

}
