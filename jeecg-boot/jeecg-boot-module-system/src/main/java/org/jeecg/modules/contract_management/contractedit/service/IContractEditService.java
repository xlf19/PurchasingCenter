package org.jeecg.modules.contract_management.contractedit.service;

import com.baomidou.mybatisplus.extension.service.IService;

import org.apache.poi.ss.formula.functions.T;
import org.jeecg.modules.contract.syslegaltemplate.entity.SysLegalTemplate;

import java.util.List;

public interface IContractEditService extends IService<T> {
    //删除产品列表和产品信息表
    boolean deleteproduct(String id);




}
