package org.jeecg.modules.contract_management.chargeSearch.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.modules.contract_management.chargeSearch.mapper.ChargeSearchMapper;
import org.jeecg.modules.contract_management.chargeSearch.service.IChargeSearchService;
import org.jeecg.modules.contract_management.productinformation.entity.ProductInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ChargeSearchServiceImpl extends ServiceImpl<ChargeSearchMapper, T> implements IChargeSearchService {
    @Autowired
    private ChargeSearchMapper chargeSearchMapper;

    @Override
    public IPage<Map<Object, String>> list(IPage<Map<Object, String>> page, String startdate, String enddate) {
        return chargeSearchMapper.list(page,startdate,enddate);
    }

    @Override
    public List<Map<Object, String>> findDetail(String contractid) {
        return chargeSearchMapper.findDetail(contractid);
    }

    @Override
    public List<Map<Object, String>> productid(String contractid) {
        return chargeSearchMapper.productid(contractid);
    }

    @Override
    public List<Map<Object, String>> productidlist(String pid) {
        return chargeSearchMapper.productidlist(pid);
    }

    @Override
    public boolean updatestatus(String contractNo) {
        return chargeSearchMapper.updatestatus(contractNo);
    }
}
