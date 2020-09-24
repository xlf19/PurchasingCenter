package org.jeecg.modules.contract.shengtie.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.poi.ss.formula.functions.T;


import java.util.List;
import java.util.Map;

public interface IShengTieService  extends IService<T> {


    //生铁打印查询
    IPage<Map<Object, String>> selectstdy(IPage<Map<Object, String>> page, String htbh,Integer pzh);
}

