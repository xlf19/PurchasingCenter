package org.jeecg.modules.contract_management.chargeSearch.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;
import java.util.Map;

public interface IChargeSearchService extends IService<T> {

    //原炉料查询
    IPage<Map<Object, String>> list(IPage<Map<Object, String>> page, String startdate,  String enddate);

    //合同明细查询
    List<Map<Object, String>> findDetail(String contractid);
}
