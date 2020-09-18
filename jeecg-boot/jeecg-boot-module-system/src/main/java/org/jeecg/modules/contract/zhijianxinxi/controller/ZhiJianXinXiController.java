package org.jeecg.modules.contract.zhijianxinxi.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.contract.zhijianxinxi.service.IZhiJianXinXiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

@Api(tags = "multi-datasource2质检信息")
@RestController
@RequestMapping("/zhijianxingxi/zhijianxingxi")
@Slf4j
public class ZhiJianXinXiController extends JeecgController<T, IZhiJianXinXiService> {

    @Autowired
    private IZhiJianXinXiService zjxxservice;

    //获取生铁质检信息
    @AutoLog(value = "获取生铁质检信息")
    @ApiOperation(value = "获取生铁质检信息", notes = "获取质检信息")
    @GetMapping(value = "/listzjxx")
    public Result<?> listzjxx(
            @RequestParam(name = "startTime", defaultValue = "") String startTime,
            @RequestParam(name = "endTime", defaultValue = "") String endTime,
            @RequestParam(name = "supplier", defaultValue = "") String supplier,
            @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
            HttpServletRequest req) {
        Page<Map<Object, String>> page = new Page<Map<Object, String>>(pageNo, pageSize);
        IPage<Map<Object, String>> pageList = zjxxservice.listzjxx(page, startTime, endTime, supplier);
        return Result.ok(pageList);
    }

    //获取精粉富粉球团质检信息
    @AutoLog(value = "获取精粉富粉球团质检信息")
    @ApiOperation(value = "获取精粉富粉球团质检信息", notes = "获取精粉富粉球团质检信息")
    @PostMapping(value = "/listzjjf")
    public Result<?> listzjjf(@RequestBody String jfzjxx) throws ParseException {
        JSONObject ht = JSONObject.parseObject(jfzjxx);
        String startTime = ht.getString("startTime");//取样开始日期
        String endTime = ht.getString("endTime");//取样开始日期
        String supplier = ht.getString("supplier");//供货单位
        Integer pageNo = ht.getInteger("pageNo");//页码
        Integer pageSize = ht.getInteger("pageSize");//条数
        Page<Map<Object, String>> page = new Page<Map<Object, String>>(pageNo, pageSize);
        IPage<Map<Object, String>> pageList = zjxxservice.listzjjf(page, startTime, endTime, supplier);
        return Result.ok(pageList);
    }


    //获取燃料质检信息
    @AutoLog(value = "获取燃料质检信息")
    @ApiOperation(value = "获取燃料质检信息", notes = "获取燃料质检信息")
    @GetMapping(value = "/listzjrl")
    public Result<?> listzjrl(
            @RequestParam(name = "startTime", defaultValue = "") String startTime,
            @RequestParam(name = "endTime", defaultValue = "") String endTime,
            @RequestParam(name = "supplier", defaultValue = "") String supplier,
            @RequestParam(name = "materialName", defaultValue = "")String materialName,
            @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
            HttpServletRequest req) {
        Page<Map<Object, String>> page = new Page<Map<Object, String>>(pageNo, pageSize);
        IPage<Map<Object, String>> pageList = zjxxservice.listzjrl(page, startTime, endTime, supplier,materialName);
        return Result.ok(pageList);
    }


    //获取辅料质检信息
    @AutoLog(value = "获取辅料质检信息")
    @ApiOperation(value = "获取辅料质检信息", notes = "获取辅料质检信息")
    @GetMapping(value = "/listzjfl")
    public Result<?> listzjfl(
            @RequestParam(name = "startTime", defaultValue = "") String startTime,
            @RequestParam(name = "endTime", defaultValue = "") String endTime,
            @RequestParam(name = "supplier", defaultValue = "") String supplier,
            @RequestParam(name = "materialName", defaultValue = "")String materialName,
            @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
            HttpServletRequest req) {
        Page<Map<Object, String>> page = new Page<Map<Object, String>>(pageNo, pageSize);
        IPage<Map<Object, String>> pageList = zjxxservice.listzjfl(page, startTime, endTime, supplier,materialName);
        return Result.ok(pageList);
    }

    //获取耐材质检信息 型煤
    @AutoLog(value = "获取耐材质检信息型煤")
    @ApiOperation(value = "获取耐材质检信息型煤", notes = "获取耐材质检信息型煤")
    @GetMapping(value = "/listzjncxm")
    public Result<?> listzjncxm(
            @RequestParam(name = "startTime", defaultValue = "") String startTime,
            @RequestParam(name = "endTime", defaultValue = "") String endTime,
            @RequestParam(name = "supplier", defaultValue = "") String supplier,
            @RequestParam(name = "materialName", defaultValue = "")String materialName,
            @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
            HttpServletRequest req) {
        Page<Map<Object, String>> page = new Page<Map<Object, String>>(pageNo, pageSize);
        IPage<Map<Object, String>> pageList = zjxxservice.listzjncxm(page, startTime, endTime, supplier,materialName);
        return Result.ok(pageList);
    }

    //获取耐材质检信息 其他
    @AutoLog(value = "获取耐材质检信息其他")
    @ApiOperation(value = "获取耐材质检信息其他", notes = "获取耐材质检信息其他")
    @GetMapping(value = "/listzjnc")
    public Result<?> listzjnc(
            @RequestParam(name = "startTime", defaultValue = "") String startTime,
            @RequestParam(name = "endTime", defaultValue = "") String endTime,
            @RequestParam(name = "supplier", defaultValue = "") String supplier,
            @RequestParam(name = "materialName", defaultValue = "")String materialName,
            @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
            HttpServletRequest req) {
        Page<Map<Object, String>> page = new Page<Map<Object, String>>(pageNo, pageSize);
        IPage<Map<Object, String>> pageList = zjxxservice.listzjnc(page, startTime, endTime, supplier,materialName);
        return Result.ok(pageList);
    }

    //获取废钢质检信息
    @AutoLog(value = "获取废钢质检信息")
    @ApiOperation(value = "获取废钢质检信息", notes = "获取废钢质检信息")
    @GetMapping(value = "/listzjfg")
    public Result<?> listzjfg(
            @RequestParam(name = "htbh", defaultValue = "") String htbh,
            @RequestParam(name = "shdw", defaultValue = "") String shdw,
            @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
            HttpServletRequest req) {
        Page<Map<Object, String>> page = new Page<Map<Object, String>>(pageNo, pageSize);
        IPage<Map<Object, String>> pageList = zjxxservice.listzjfg(page,htbh,shdw);
        return Result.ok(pageList);
    }

    //获取合金质检信息 合金
    @AutoLog(value = "获取合金质检信息合金")
    @ApiOperation(value = "获取合金质检信息合金", notes = "获取合金质检信息合金")
    @GetMapping(value = "/listzjhjhj")
    public Result<?> listzjhjhj(
            @RequestParam(name = "startTime", defaultValue = "") String startTime,
            @RequestParam(name = "endTime", defaultValue = "") String endTime,
            @RequestParam(name = "supplier", defaultValue = "") String supplier,
            @RequestParam(name = "materialName", defaultValue = "")String materialName,
            @RequestParam(name = "wzshang", defaultValue = "") String wzshang,
            @RequestParam(name = "wzxia", defaultValue = "")String wzxia,
            @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
            HttpServletRequest req) {
        Page<Map<Object, String>> page = new Page<Map<Object, String>>(pageNo, pageSize);
        IPage<Map<Object, String>> pageList = zjxxservice.listzjhjhj(page, startTime, endTime, supplier,materialName,wzshang,wzxia);
        return Result.ok(pageList);
    }
    //获取合金质检信息 辅料
    @AutoLog(value = "获取合金质检信息辅料")
    @ApiOperation(value = "获取合金质检信息辅料", notes = "获取合金质检信息辅料")
    @GetMapping(value = "/listzjhjfl")
    public Result<?> listzjhjfl(
            @RequestParam(name = "startTime", defaultValue = "") String startTime,
            @RequestParam(name = "endTime", defaultValue = "") String endTime,
            @RequestParam(name = "supplier", defaultValue = "") String supplier,
            @RequestParam(name = "materialName", defaultValue = "")String materialName,
            @RequestParam(name = "wzshang", defaultValue = "") String wzshang,
            @RequestParam(name = "wzxia", defaultValue = "")String wzxia,
            @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
            HttpServletRequest req) {
        Page<Map<Object, String>> page = new Page<Map<Object, String>>(pageNo, pageSize);
        IPage<Map<Object, String>> pageList = zjxxservice.listzjhjfl(page, startTime, endTime, supplier,materialName,wzshang,wzxia);
        return Result.ok(pageList);
    }
    //获取合金质检信息 燃料
    @AutoLog(value = "获取合金质检信息燃料")
    @ApiOperation(value = "获取合金质检信息燃料", notes = "获取合金质检信息燃料")
    @GetMapping(value = "/listzjhjrl")
    public Result<?> listzjhjrl(
            @RequestParam(name = "startTime", defaultValue = "") String startTime,
            @RequestParam(name = "endTime", defaultValue = "") String endTime,
            @RequestParam(name = "supplier", defaultValue = "") String supplier,
            @RequestParam(name = "materialName", defaultValue = "")String materialName,
            @RequestParam(name = "wzshang", defaultValue = "") String wzshang,
            @RequestParam(name = "wzxia", defaultValue = "")String wzxia,
            @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
            HttpServletRequest req) {
        Page<Map<Object, String>> page = new Page<Map<Object, String>>(pageNo, pageSize);
        IPage<Map<Object, String>> pageList = zjxxservice.listzjhjrl(page, startTime, endTime, supplier,materialName,wzshang,wzxia);
        return Result.ok(pageList);
    }
}
