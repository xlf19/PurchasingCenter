package org.jeecg.modules.contract.hetong.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.contract.hetong.service.IHeTongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Api(tags="合同信息")
@RestController
@RequestMapping("/hetong/hetong")
@Slf4j
public class HeTongController extends JeecgController<T, IHeTongService> {


    @Autowired
    private IHeTongService htService;

    //获取合同号/原炉料合同号
    @AutoLog(value = "获取合同号")
    @ApiOperation(value="获取合同号", notes="获取合同号")
    @GetMapping(value = "/findListHt")
    public Result<?> findListHt(@RequestParam(name = "hth" ,defaultValue = "")String hth) {
        List<Map<Object,String>> listht=htService.findListHt(hth);
        return Result.ok(listht);
    }

    //获取收货单位
    @AutoLog(value = "获取收货单位")
    @ApiOperation(value="获取收货单位", notes="获取收货单位")
    @GetMapping(value = "/findshdw")
    public Result<?> findshdw() {
        List<Map<Object,String>> listht=htService.findshdw();
        return Result.ok(listht);
    }

    //获取供货单位和物资名称
    @AutoLog(value = "获取供货单位和物资名称")
    @ApiOperation(value="获取供货单位和物资名称", notes="获取供货单位和物资名称")
    @GetMapping(value = "/findOne")
    public Result<?> findOne(@RequestParam(name = "hth" ,defaultValue = "")String hth) {
        Object listht=htService.findOne(hth);
        return Result.ok(listht);
    }

    //物资合同号
    @AutoLog(value = "获取合同号")
    @ApiOperation(value="获取合同号", notes="获取合同号")
    @GetMapping(value = "/findListHtwz")
    public Result<?> findListHtwz(@RequestParam(name = "hth" ,defaultValue = "")String hth) {
        List<Map<Object,String>> listht=htService.findListHtwz(hth);
        return Result.ok(listht);
    }

    //辅料 原炉料合同 获取供货单位、物资名称
    @AutoLog(value = "原炉料合同获取供货单位和物资名称")
    @ApiOperation(value="原炉料合同获取供货单位和物资名称", notes="原炉料合同获取供货单位和物资名称")
    @GetMapping(value = "/findyllht")
    public Result<?> findyllht(@RequestParam(name = "hth" ,defaultValue = "")String hth) {
        Object listht=htService.findyllht(hth);
        return Result.ok(listht);
    }
    //辅料 物资合同 获取供货单位、物资名称
    @AutoLog(value = "物资合同获取供货单位和物资名称")
    @ApiOperation(value="物资合同获取供货单位和物资名称", notes="物资合同获取供货单位和物资名称")
    @GetMapping(value = "/findwzht")
    public Result<?> findwzht(@RequestParam(name = "hth" ,defaultValue = "")String hth) {
        Object listht=htService.findwzht(hth);
        return Result.ok(listht);
    }


    //耐材 获取合同号/物资合同号
    @AutoLog(value = "获取合同号")
    @ApiOperation(value="获取合同号", notes="获取合同号")
    @GetMapping(value = "/findhtncwz")
    public Result<?> findhtncwz(@RequestParam(name = "hth" ,defaultValue = "")String hth) {
        List<Map<Object,String>> listht=htService.findhtncwz(hth);
        return Result.ok(listht);
    }


    //获取耐材 供货单位  原炉料合同
    @AutoLog(value = "原炉料合同获取供货单位和物资名称")
    @ApiOperation(value="原炉料合同获取供货单位和物资名称", notes="原炉料合同获取供货单位和物资名称")
    @GetMapping(value = "/finhtyllxxnc")
    public Result<?> finhtyllxxnc(@RequestParam(name = "hth" ,defaultValue = "")String hth) {
        Object listht=htService.finhtyllxxnc(hth);
        return Result.ok(listht);
    }
    //获取耐材 供货单位  物资合同
    @AutoLog(value = "物资合同获取供货单位和物资名称")
    @ApiOperation(value="物资合同获取供货单位和物资名称", notes="物资合同获取供货单位和物资名称")
    @GetMapping(value = "/finhtwzxxnc")
    public Result<?> finhtwzxxnc(@RequestParam(name = "hth" ,defaultValue = "")String hth) {
        Object listht=htService.finhtwzxxnc(hth);
        return Result.ok(listht);
    }

    //耐材 物资名称 物资合同
    @AutoLog(value = "获取合同号")
    @ApiOperation(value="获取合同号", notes="获取合同号")
    @GetMapping(value = "/findhtncwzname")
    public Result<?> findhtncwzname(@RequestParam(name = "hth" ,defaultValue = "")String hth) {
        List<Map<Object,String>> listht=htService.findhtncwzname(hth);
        return Result.ok(listht);
    }

    //耐材 物资名称 原炉料合同
    @AutoLog(value = "获取合同号")
    @ApiOperation(value="获取合同号", notes="获取合同号")
    @GetMapping(value = "/findhtncyllname")
    public Result<?> findhtncyllname(@RequestParam(name = "hth" ,defaultValue = "")String hth) {
        List<Map<Object,String>> listht=htService.findhtncyllname(hth);
        return Result.ok(listht);
    }

    //耐材 物资编码 原炉料合同
    @AutoLog(value = "获取合同号")
    @ApiOperation(value="获取合同号", notes="获取合同号")
    @GetMapping(value = "/finwzname")
    public Result<?> finwzname(@RequestParam(name = "hth" ,defaultValue = "")String hth,
                               @RequestParam(name = "wzname" ,defaultValue = "")String wzname,
                               @RequestParam(name = "httpye" ,defaultValue = "")String httpye
                               ) {
        String wzcode ="";
        if(httpye.equals("物资合同")){
            wzcode=htService.findwznamewz(hth,wzname);
        }else {
            wzcode=htService.findwznameyll(hth,wzname);
        }
        return Result.ok(wzcode);
    }


    //合金 原炉料合同 获取供货单位
    @AutoLog(value = "原炉料合同获取供货单位和物资名称")
    @ApiOperation(value="原炉料合同获取供货单位和物资名称", notes="原炉料合同获取供货单位和物资名称")
    @GetMapping(value = "/findyllhthj")
    public Result<?> findyllhthj(@RequestParam(name = "hth" ,defaultValue = "")String hth) {
        Object listht=htService.findyllhthj(hth);
        return Result.ok(listht);
    }
    //合金 物资合同 获取供货单位
    @AutoLog(value = "物资合同获取供货单位和物资名称")
    @ApiOperation(value="物资合同获取供货单位和物资名称", notes="物资合同获取供货单位和物资名称")
    @GetMapping(value = "/findwzhthj")
    public Result<?> findwzhthj(@RequestParam(name = "hth" ,defaultValue = "")String hth) {
        Object listht=htService.findwzhthj(hth);
        return Result.ok(listht);
    }
}
