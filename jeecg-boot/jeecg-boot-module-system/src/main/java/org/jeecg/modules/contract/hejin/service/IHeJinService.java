package org.jeecg.modules.contract.hejin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;
import java.util.Map;

public interface IHeJinService extends IService<T> {
    //合金打印查询
    IPage<Map<Object, String>> selecthjdy(IPage<Map<Object, String>> page, String htbh, Integer pzh);

    //合金打印查询
    List<Map<String, Object>> selecthjdylist(String htbh, Integer pzh);
}
