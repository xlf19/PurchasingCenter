package org.jeecg.modules.contract_management.contractpurchase.service.impl;

import org.jeecg.modules.contract_management.contractpurchase.entity.ContractPurchase;
import org.jeecg.modules.contract_management.contractpurchase.mapper.ContractPurchaseMapper;
import org.jeecg.modules.contract_management.contractpurchase.service.IContractPurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 采购合同表
 * @Author: jeecg-boot
 * @Date:   2020-12-04
 * @Version: V1.0
 */
@Service
public class ContractPurchaseServiceImpl extends ServiceImpl<ContractPurchaseMapper, ContractPurchase> implements IContractPurchaseService {

    @Autowired
    private ContractPurchaseMapper contractPurchaseMapper;
    @Override
    public String hthfind() {
        return contractPurchaseMapper.hthfind();
    }
}
