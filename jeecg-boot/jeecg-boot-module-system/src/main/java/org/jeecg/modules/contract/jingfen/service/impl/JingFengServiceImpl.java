package org.jeecg.modules.contract.jingfen.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.modules.contract.jingfen.mapper.JingFengMapper;
import org.jeecg.modules.contract.jingfen.service.IJingFengService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class JingFengServiceImpl extends ServiceImpl<JingFengMapper, T> implements IJingFengService {

    @Autowired
    JingFengMapper jf;

    public void addjqlist(String pgdh,String htbh) {
        jf.addjqlist(pgdh,htbh);
    }

    public void addjqlistat(String startTime, String endTime, String gudw,String htbh) {
        jf.addjqlistat(startTime,endTime,gudw,htbh);
    }


    public IPage<Map<Object, String>> selectjfdy(IPage<Map<Object, String>> page, String htbh, Integer pzh) {
        return  jf.selectjfdy(page,htbh,pzh);
    }


    public Map<Object, Object> updatejsd(String id) {
        return jf.updatejsd(id);
    }

}
