package org.jeecg.modules.contract.shengtiedate.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.contract.shengtiedate.entity.QualityDateShengtie;
import org.jeecg.modules.contract.shengtiedate.service.IQualityDateShengtieService;

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
 * @Description: 生铁加权平均
 * @Author: jeecg-boot
 * @Date:   2021-04-06
 * @Version: V1.0
 */
@Api(tags="生铁加权平均")
@RestController
@RequestMapping("/shengtiedate/qualityDateShengtie")
@Slf4j
public class QualityDateShengtieController extends JeecgController<QualityDateShengtie, IQualityDateShengtieService> {
	@Autowired
	private IQualityDateShengtieService qualityDateShengtieService;

	 /**
	  * 分页列表查询
	  *获取生铁质检信息
	  * @return
	  */
	 @AutoLog(value = "加权平均-分页列表查询")
	 @ApiOperation(value="加权平均-分页列表查询", notes="加权平均-分页列表查询")
	 @PostMapping(value = "/listst")
	 public Result<?> queryPagelistst( @RequestBody String stzjxx) {
		 JSONObject ht = JSONObject.parseObject(stzjxx);
		 String htbh = ht.getString("htbh");//合同编号
		 Integer pageNo = ht.getInteger("pageNo");//页码
		 Integer pageSize = ht.getInteger("pageSize");//条数
		 Page<Map<Object, String>> page = new Page<Map<Object, String>>(pageNo, pageSize);
		 IPage<Map<Object, String>> pageList = qualityDateShengtieService.queryPagelistst(page, htbh);
		 return Result.ok(pageList);
	 }

}
