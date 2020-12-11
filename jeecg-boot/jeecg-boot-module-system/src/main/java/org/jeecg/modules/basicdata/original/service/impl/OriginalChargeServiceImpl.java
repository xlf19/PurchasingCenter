package org.jeecg.modules.basicdata.original.service.impl;

import org.jeecg.modules.basicdata.original.entity.OriginalCharge;
import org.jeecg.modules.basicdata.original.entity.ShengBianMa;
import org.jeecg.modules.basicdata.original.entity.ShiBianMa;
import org.jeecg.modules.basicdata.original.mapper.OriginalChargeMapper;
import org.jeecg.modules.basicdata.original.service.IOriginalChargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;
import java.util.Map;

/**
 * @Description: 原炉料供应商
 * @Author: jeecg-boot
 * @Date:   2020-11-26
 * @Version: V1.0
 */
@Service
public class OriginalChargeServiceImpl extends ServiceImpl<OriginalChargeMapper, OriginalCharge> implements IOriginalChargeService {

    @Autowired
    private OriginalChargeMapper ori;
    @Override
    public boolean deleteOrilist(String[] ids) {
        return ori.deleteOrilist(ids);
    }

    @Override
    public boolean usestatus(String[] ids, Integer status) {
        return ori.usestatus(ids,status);
    }

    @Override
    public List<ShengBianMa> shengbima() {
        return ori.shengbima();
    }

    @Override
    public List<ShiBianMa> shibima(String code) {
        return ori.shibima(code);
    }

    @Override
    public String supplierCode(String code) {
        return ori.supplierCode(code);
    }

    @Override
    public List<OriginalCharge> searchname(String companyName) {
        return ori.searchname(companyName);
    }
}
