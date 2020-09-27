package org.jeecg.modules.contract.hejin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;
import java.util.Map;

public interface HeJinMapper extends BaseMapper<T> {
    //合金打印查询
    IPage<Map<Object, String>> selecthjdy(IPage<Map<Object, String>> page, @Param("htbh") String htbh, @Param("pzh") Integer pzh);

    //合金打印查询
    List<Map<String, Object>> selecthjdylist(@Param("htbh") String htbh, @Param("pzh") Integer pzh);
}
