package org.jeecg.modules.contract.hejin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.modules.contract.hejin.mapper.HeJinMapper;
import org.jeecg.modules.contract.hejin.service.IHeJinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class HeJinServiceImpl extends ServiceImpl<HeJinMapper, T> implements IHeJinService {

    @Autowired
    HeJinMapper hj;


    @Override
    public IPage<Map<Object, String>> selecthjdy(IPage<Map<Object, String>> page, String htbh, Integer pzh) {
        return hj.selecthjdy(page,htbh,pzh);
    }

    @Override
    public List<Map<String, Object>> selecthjdylist(String htbh, Integer pzh) {
        return hj.selecthjdylist(htbh,pzh);
    }
}
