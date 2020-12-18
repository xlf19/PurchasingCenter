package org.jeecg.modules.contract_management.contractedit.service;

import com.baomidou.mybatisplus.extension.service.IService;

import org.apache.poi.ss.formula.functions.T;

public interface IContractEditService extends IService<T> {
    //删除产品列表和产品信息表
    boolean deleteproduct(String id);



}
