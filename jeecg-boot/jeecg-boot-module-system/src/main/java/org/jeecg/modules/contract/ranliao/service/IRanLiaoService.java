package org.jeecg.modules.contract.ranliao.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.poi.ss.formula.functions.T;

import java.util.Map;

public interface IRanLiaoService extends IService<T> {

    //燃料打印查询
    IPage<Map<Object, String>> selectrldy(IPage<Map<Object, String>> page,String htbh, Integer pzh);

    //燃料编辑结算单
    Map<Object,Object> updatejsd(String id);
}
