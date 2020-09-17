package org.jeecg.modules.contract.zhijianxinxi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.poi.ss.formula.functions.T;

import java.util.Map;

public interface IZhiJianXinXiService extends IService<T> {
    //获取生铁质检信息
    public IPage<Map<Object,String>> listzjxx(IPage<Map<Object,String>> page,String startTime, String endTime, String supplier);

    //获取精粉富粉球团质检信息
    public IPage<Map<Object,String>> listzjjf(IPage<Map<Object,String>> page,String startTime, String endTime, String supplier);

    //精粉手动添加结算单
    public Map<Object,Object> listzjjfxx(String htbh,String voucherno ,String receiving);

    //获取燃料质检信息
    public IPage<Map<Object,String>> listzjrl(IPage<Map<Object,String>> page,String startTime, String endTime,String supplier, String materialName);

    //获取辅料质检信息
    public IPage<Map<Object,String>> listzjfl(IPage<Map<Object,String>> page,String startTime, String endTime,String supplier, String materialName);

    //获取耐材质检信息 型煤
    IPage<Map<Object, String>> listzjncxm(IPage<Map<Object, String>> page,String startTime,String endTime,  String supplier,String materialName);

    //获取耐材质检信息 其他
    IPage<Map<Object, String>> listzjnc(IPage<Map<Object, String>> page,  String startTime, String endTime, String supplier, String materialName);

    //获取废钢质检信息
    IPage<Map<Object, String>> listzjfg(IPage<Map<Object, String>> page,String htbh,String shdw);

}
