package org.jeecg.modules.contract.shengtie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;


import java.util.List;
import java.util.Map;


public interface ShengTieMapper  extends BaseMapper<T> {


    //生铁打印查询
    IPage<Map<Object, String>> selectstdy(IPage<Map<Object, String>> page, @Param("htbh") String htbh, @Param("pzh") Integer pzh);


}
