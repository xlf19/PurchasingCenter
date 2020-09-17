package org.jeecg.modules.contract.jingfen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;

public interface IJingFengService extends IService<T> {
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
