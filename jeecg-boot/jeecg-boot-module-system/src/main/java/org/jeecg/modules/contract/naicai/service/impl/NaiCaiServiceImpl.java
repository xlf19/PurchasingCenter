package org.jeecg.modules.contract.naicai.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.modules.contract.naicai.mapper.NaiCaiMapper;
import org.jeecg.modules.contract.naicai.service.INaiCaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class NaiCaiServiceImpl extends ServiceImpl<NaiCaiMapper, T> implements INaiCaiService {
    @Autowired
    NaiCaiMapper nc;

    public IPage<Map<Object, String>> selectncdy(IPage<Map<Object, String>> page, String htbh, Integer pzh) {
        return nc.selectncdy(page,htbh,pzh);
    }


    public List<Map<String, Object>> selectncylist(String htbh, Integer pzh) {
        return nc.selectncylist(htbh,pzh);
    }
}
