package org.jeecg.modules.contract.feigang.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.poi.ss.formula.functions.T;

import java.util.Map;

public interface IFeiGangService  extends IService<T> {

    //废钢打印查询
    IPage<Map<Object, String>> selectfgdy(IPage<Map<Object, String>> page, String htbh,Integer pzh);
}
