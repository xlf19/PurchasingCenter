package org.jeecg.modules.contract.feigang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;

import java.util.Map;

public interface FeiGangMapper extends BaseMapper<T> {

    //废钢打印查询
    IPage<Map<Object, String>> selectfgdy(IPage<Map<Object, String>> page, @Param("htbh") String htbh, @Param("pzh") Integer pzh);
}
