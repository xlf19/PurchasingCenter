package org.jeecg.modules.contract.naicai.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.poi.ss.formula.functions.T;

import java.util.Map;

public interface INaiCaiService extends IService<T> {


    //耐材打印查询
    IPage<Map<Object, String>> selectncdy(IPage<Map<Object, String>> page, String htbh, Integer pzh);
}
