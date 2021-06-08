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

    //生铁打印加权平均合计
    Map<String,Object> dyhj(@Param("htbh") String htbh, @Param("pzh") Integer pzh);


    /**
     加权平均：可选
     pgdh：派工单号
     **/
     void addjqlist(@Param("pgdh")String pgdh,@Param("htbh")String htbh);

    /**
     加权平均：按天
     startTime：取样开始日期
     endTime：取样结束日期
     gudw：供货单位
     **/
     void addjqlistat(@Param("startTime")String startTime,@Param("endTime")String endTime,@Param("gudw")String gudw,@Param("htbh")String htbh);
}
