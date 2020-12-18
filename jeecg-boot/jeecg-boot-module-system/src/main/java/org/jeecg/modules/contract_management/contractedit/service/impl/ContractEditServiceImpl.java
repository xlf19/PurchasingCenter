package org.jeecg.modules.contract_management.contractedit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.modules.contract_management.contractedit.mapper.ContractEditMapper;
import org.jeecg.modules.contract_management.contractedit.service.IContractEditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContractEditServiceImpl extends ServiceImpl<ContractEditMapper, T> implements IContractEditService {
    @Autowired
    private ContractEditMapper contractEditMapper;

    @Override
    public boolean deleteproduct(String id) {
        return contractEditMapper.deleteproduct(id);
    }
}
