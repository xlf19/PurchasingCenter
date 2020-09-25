package org.jeecg.modules.contract.fuliao.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.poi.ss.formula.functions.T;

import java.util.Map;

public interface IFuLiaoService extends IService<T> {

    //辅料打印查询
    IPage<Map<Object, String>> selectfldy(IPage<Map<Object, String>> page, String htbh, Integer pzh);
}
