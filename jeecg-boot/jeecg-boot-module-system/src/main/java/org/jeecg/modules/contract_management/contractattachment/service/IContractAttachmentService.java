package org.jeecg.modules.contract_management.contractattachment.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.poi.ss.formula.functions.T;

import java.util.Map;

public interface IContractAttachmentService extends IService<T> {

    //合同附件查询
    IPage<Map<Object, String>> list(IPage<Map<Object, String>> page, String startdate,  String enddate);
}
