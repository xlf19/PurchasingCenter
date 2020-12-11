package org.jeecg.modules.contract_management.contractattachment.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.modules.contract_management.contractattachment.mapper.ContractAttachmentMapper;
import org.jeecg.modules.contract_management.contractattachment.service.IContractAttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ContractAttachmentServiceImpl extends ServiceImpl<ContractAttachmentMapper, T> implements IContractAttachmentService {
    @Autowired
    private ContractAttachmentMapper contractAttachmentMapper;

    @Override
    public IPage<Map<Object, String>> list(IPage<Map<Object, String>> page, String startdate, String enddate) {
        return contractAttachmentMapper.list(page,startdate,enddate);
    }
}
