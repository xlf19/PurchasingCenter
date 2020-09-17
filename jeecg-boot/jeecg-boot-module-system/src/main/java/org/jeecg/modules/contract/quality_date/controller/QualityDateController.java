package org.jeecg.modules.contract.quality_date.controller;

import java.util.Arrays;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.contract.quality_date.entity.QualityDate;
import org.jeecg.modules.contract.quality_date.service.IQualityDateService;

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
 * @Description: 加权平均
 * @Author: jeecg-boot
 * @Date:   2020-09-09
 * @Version: V1.0
 */
@Api(tags="加权平均")
@RestController
@RequestMapping("/qualitydate/qualityDate")
@Slf4j
public class QualityDateController extends JeecgController<QualityDate, IQualityDateService> {
	@Autowired
	private IQualityDateService qualityDateService;

	 /**
	  * 分页列表查询
	  *获取精粉富粉球团质检信息
	  * @return
	  */
	 @AutoLog(value = "加权平均-分页列表查询")
	 @ApiOperation(value="加权平均-分页列表查询", notes="加权平均-分页列表查询")
	 @PostMapping(value = "/listjf")
	 public Result<?> queryPageListjf( @RequestBody String jfzjxx) {
		 JSONObject ht = JSONObject.parseObject(jfzjxx);
		 String htbh = ht.getString("htbh");//合同编号
		 Integer pageNo = ht.getInteger("pageNo");//页码
		 Integer pageSize = ht.getInteger("pageSize");//条数
		 Page<Map<Object, String>> page = new Page<Map<Object, String>>(pageNo, pageSize);
		 IPage<Map<Object, String>> pageList = qualityDateService.queryPageListjf(page, htbh);
		 return Result.ok(pageList);
	 }
}
