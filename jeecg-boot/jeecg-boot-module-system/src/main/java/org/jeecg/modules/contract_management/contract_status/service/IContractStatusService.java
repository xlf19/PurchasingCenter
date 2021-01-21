package org.jeecg.modules.contract_management.contract_status.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;
import java.util.Map;

public interface IContractStatusService extends IService<T> {
    //查询合同号
    List<Map<Object,String>> listbh();

    //根据合同号改变合同状态
    boolean updateisdelete(String HeTongCode);


    // //根据合同号改变设备管理_合同_主表合同状态
    boolean updatecontract(String HeTongCode);

    //查询合同标题
    List<Map<String,Object>> searchTitle(List<String> bumen);
}
