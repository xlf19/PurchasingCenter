package org.jeecg.modules.contract.elements.service.impl;

import org.jeecg.modules.contract.elements.entity.ContractElements;
import org.jeecg.modules.contract.elements.mapper.ContractElementsMapper;
import org.jeecg.modules.contract.elements.service.IContractElementsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description: 合同元素表
 * @Author: jeecg-boot
 * @Date:   2020-08-31
 * @Version: V1.0
 */
@Service
public class ContractElementsServiceImpl extends ServiceImpl<ContractElementsMapper, ContractElements> implements IContractElementsService {

    @Autowired
    ContractElementsMapper ele;

    public List<ContractElements> findList(String cid) {
        return ele.findList(cid);
    }

    public void updateElelist(String cid){
        ele.updateElelist(cid);
    }

    @Override
    public List<String> findListhth(String htbh) {
        return ele.findListhth(htbh);
    }
}
