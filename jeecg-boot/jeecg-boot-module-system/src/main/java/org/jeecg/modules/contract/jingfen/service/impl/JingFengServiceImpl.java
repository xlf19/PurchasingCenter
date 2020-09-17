package org.jeecg.modules.contract.jingfen.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.modules.contract.jingfen.mapper.JingFengMapper;
import org.jeecg.modules.contract.jingfen.service.IJingFengService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    //@DS("multi-datasource2")
}
