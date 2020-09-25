package org.jeecg.modules.contract.fuliao.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.modules.contract.fuliao.mapper.FeiLiaoMapper;
import org.jeecg.modules.contract.fuliao.service.IFuLiaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class FuLiaoServiceImpl extends ServiceImpl<FeiLiaoMapper, T> implements IFuLiaoService {

    @Autowired
    FeiLiaoMapper fl;

    public IPage<Map<Object, String>> selectfldy(IPage<Map<Object, String>> page, String htbh, Integer pzh) {
        return fl.selectfldy(page,htbh,pzh);
    }
}
