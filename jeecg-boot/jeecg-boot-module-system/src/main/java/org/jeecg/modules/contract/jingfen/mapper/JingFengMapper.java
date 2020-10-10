package org.jeecg.modules.contract.jingfen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;

import java.util.Map;

public interface JingFengMapper extends BaseMapper<T> {

    /**
    加权平均：可选
     pgdh：派工单号
     **/
    public void addjqlist(@Param("pgdh")String pgdh,@Param("htbh")String htbh);

    /**
     加权平均：按天
     startTime：取样开始日期
     endTime：取样结束日期
     gudw：供货单位
     **/
    public void addjqlistat(@Param("startTime")String startTime,@Param("endTime")String endTime,@Param("gudw")String gudw,@Param("htbh")String htbh);

    //精粉打印查询
    IPage<Map<Object, String>> selectjfdy(IPage<Map<Object, String>> page, @Param("htbh") String htbh, @Param("pzh") Integer pzh);

    //精粉编辑结算单
     Map<Object,Object> updatejsd(@Param("id") String id);
}
