package org.jeecg.modules.contract.shengtie.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.poi.ss.formula.functions.T;


import java.util.List;
import java.util.Map;

public interface IShengTieService  extends IService<T> {


    //生铁打印查询
    IPage<Map<Object, String>> selectstdy(IPage<Map<Object, String>> page, String htbh,Integer pzh);

    //生铁打印加权平均合计
    Map<String,Object> dyhj(String htbh,Integer pzh);

    /**
     加权平均
     pgdh：派工单号
     **/
    public void addjqlist(String pgdh,String htbh);

    /**
     加权平均：按天
     startTime：取样开始日期
     endTime：取样结束日期
     gudw：供货单位
     **/
    public void addjqlistat(String startTime,String endTime,String gudw,String htbh);
}

