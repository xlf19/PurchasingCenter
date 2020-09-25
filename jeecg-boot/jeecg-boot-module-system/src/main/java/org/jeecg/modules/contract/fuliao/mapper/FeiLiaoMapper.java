package org.jeecg.modules.contract.fuliao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;

import java.util.Map;

public interface FeiLiaoMapper extends BaseMapper<T> {

    //辅料打印查询
    IPage<Map<Object, String>> selectfldy(IPage<Map<Object, String>> page, @Param("htbh") String htbh, @Param("pzh") Integer pzh);
}
