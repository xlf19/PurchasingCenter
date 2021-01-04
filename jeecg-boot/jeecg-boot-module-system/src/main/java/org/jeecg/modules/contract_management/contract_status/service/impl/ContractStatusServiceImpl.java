package org.jeecg.modules.contract_management.contract_status.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.modules.contract_management.contract_status.mapper.ContractStatusMapper;
import org.jeecg.modules.contract_management.contract_status.service.IContractStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@DS("multi-datasource1")
public class ContractStatusServiceImpl extends ServiceImpl<ContractStatusMapper, T> implements IContractStatusService {

    @Autowired
    private ContractStatusMapper contractStatusMapper;

    @Override
    public List<Map<Object, String>> listbh() {
        return contractStatusMapper.listbh();
    }

    @Override
    public boolean updateisdelete(String HeTongCode) {
        return contractStatusMapper.updateisdelete(HeTongCode);
    }

    @Override
    @DS("multi-datasource2")
    public boolean updatecontract(String HeTongCode) {
        return contractStatusMapper.updatecontract(HeTongCode);
    }
}
