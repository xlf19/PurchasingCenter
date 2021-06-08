package org.jeecg.modules.contract.shengtie.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.modules.contract.shengtie.mapper.ShengTieMapper;
import org.jeecg.modules.contract.shengtie.service.IShengTieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;


@Service
public class ShengTieServiceImpl extends ServiceImpl<ShengTieMapper, T> implements IShengTieService {

    @Autowired
    ShengTieMapper st;


    public IPage<Map<Object, String>> selectstdy(IPage<Map<Object, String>> page, String htbh, Integer pzh) {
        return st.selectstdy(page,htbh,pzh);
    }

    @Override
    public Map<String, Object> dyhj(String htbh, Integer pzh) {
        return st.dyhj(htbh,pzh);
    }

    @Override
    public void addjqlist(String pgdh, String htbh) {
         st.addjqlist(pgdh,htbh);
    }

    @Override
    public void addjqlistat(String startTime, String endTime, String gudw, String htbh) {
         st.addjqlistat(startTime,endTime,gudw,htbh);
    }


}
