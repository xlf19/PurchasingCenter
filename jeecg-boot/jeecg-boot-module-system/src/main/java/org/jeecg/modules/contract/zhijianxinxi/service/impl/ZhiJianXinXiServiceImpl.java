package org.jeecg.modules.contract.zhijianxinxi.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.modules.contract.zhijianxinxi.mapper.ZhiJianXinXiMapper;
import org.jeecg.modules.contract.zhijianxinxi.service.IZhiJianXinXiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ZhiJianXinXiServiceImpl extends ServiceImpl<ZhiJianXinXiMapper, T> implements IZhiJianXinXiService {
    @Autowired
    ZhiJianXinXiMapper zjxx;

    @DS("multi-datasource2")
    public IPage<Map<Object,String>> listzjxx(IPage<Map<Object,String>> page,String startTime, String endTime, String supplier) {
        return zjxx.listzjxx(page,startTime,endTime,supplier);
    }


    public IPage<Map<Object, String>> listzjjf(IPage<Map<Object, String>> page, String startTime, String endTime, String supplier) {
        return zjxx.listzjjf(page,startTime,endTime,supplier);
    }

    @DS("multi-datasource1")
    public Map<Object, Object> listzjjfxx(String htbh,String voucherno,String receiving) {
        return zjxx.listzjjfxx(htbh,voucherno,receiving);
    }

    @DS("multi-datasource2")
    public IPage<Map<Object, String>> listzjrl(IPage<Map<Object, String>> page, String startTime, String endTime, String supplier,String materialName) {
        return zjxx.listzjrl(page,startTime,endTime,supplier,materialName);
    }

    public IPage<Map<Object, String>> listzjfl(IPage<Map<Object, String>> page, String startTime, String endTime, String supplier, String materialName) {
        return zjxx.listzjfl(page,startTime,endTime,supplier,materialName);
    }

    @DS("multi-datasource2")
    public IPage<Map<Object, String>> listzjncxm(IPage<Map<Object, String>> page, String startTime, String endTime, String supplier, String materialName) {
        return zjxx.listzjncxm(page,startTime,endTime,supplier,materialName);
    }

    public IPage<Map<Object, String>> listzjnc(IPage<Map<Object, String>> page, String startTime, String endTime, String supplier, String materialName) {
        return zjxx.listzjnc(page,startTime,endTime,supplier,materialName);
    }

    @DS("multi-datasource1")
    public IPage<Map<Object, String>> listzjfg(IPage<Map<Object, String>> page, String htbh,String shdw) {
        return zjxx.listzjfg(page,htbh,shdw);
    }

    public IPage<Map<Object, String>> listzjhjhj(IPage<Map<Object, String>> page, String startTime, String endTime, String supplier, String materialName,String wzshang, String wzxia) {
        return zjxx.listzjhjhj(page,startTime,endTime,supplier,materialName,wzshang,wzxia);
    }

    public IPage<Map<Object, String>> listzjhjfl(IPage<Map<Object, String>> page, String startTime, String endTime, String supplier, String materialName,String wzshang, String wzxia) {
        return zjxx.listzjhjfl(page,startTime,endTime,supplier,materialName,wzshang,wzxia);
    }

    public IPage<Map<Object, String>> listzjhjrl(IPage<Map<Object, String>> page, String startTime, String endTime, String supplier, String materialName,String wzshang, String wzxia) {
        return zjxx.listzjhjrl(page,startTime,endTime,supplier,materialName,wzshang,wzxia);
    }
}
