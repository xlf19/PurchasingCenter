package org.jeecg.modules.contract_management.chargeSearch.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.modules.contract_management.productinformation.entity.ProductInformation;

import java.util.List;
import java.util.Map;

public interface IChargeSearchService extends IService<T> {

    //原炉料查询
    IPage<Map<Object, String>> list(IPage<Map<Object, String>> page, String startdate,  String enddate);

    //合同明细查询
    List<Map<Object, String>> findDetail(String contractid);

    //通过合同id查询产品信息表
    List<Map<Object, String>> productid(String contractid);

    //通过产品id查询产品明细表
    List<Map<Object, String>> productidlist(String pid);
}
