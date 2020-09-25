package org.jeecg.modules.contract.ranliao.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.modules.contract.ranliao.mapper.RanLiaoMapper;
import org.jeecg.modules.contract.ranliao.service.IRanLiaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RanLiaoServiceImpl extends ServiceImpl<RanLiaoMapper, T> implements IRanLiaoService {

    @Autowired
    RanLiaoMapper rl;

    @Override
    public IPage<Map<Object, String>> selectrldy(IPage<Map<Object, String>> page, String htbh, Integer pzh) {
        return rl.selectrldy(page,htbh,pzh);
    }
}
