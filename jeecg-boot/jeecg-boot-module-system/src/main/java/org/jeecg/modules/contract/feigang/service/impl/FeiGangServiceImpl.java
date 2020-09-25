package org.jeecg.modules.contract.feigang.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.modules.contract.feigang.mapper.FeiGangMapper;
import org.jeecg.modules.contract.feigang.service.IFeiGangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class FeiGangServiceImpl  extends ServiceImpl<FeiGangMapper, T> implements IFeiGangService {
    @Autowired
    FeiGangMapper fg;
    public IPage<Map<Object, String>> selectfgdy(IPage<Map<Object, String>> page, String htbh, Integer pzh) {
        return fg.selectfgdy(page,htbh,pzh);
    }
}
