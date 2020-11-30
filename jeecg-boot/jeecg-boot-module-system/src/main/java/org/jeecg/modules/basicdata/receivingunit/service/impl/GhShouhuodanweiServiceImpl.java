package org.jeecg.modules.basicdata.receivingunit.service.impl;

import org.jeecg.modules.basicdata.receivingunit.entity.GhShouhuodanwei;
import org.jeecg.modules.basicdata.receivingunit.mapper.GhShouhuodanweiMapper;
import org.jeecg.modules.basicdata.receivingunit.service.IGhShouhuodanweiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description: 收货单位
 * @Author: jeecg-boot
 * @Date:   2020-11-30
 * @Version: V1.0
 */
@Service
public class GhShouhuodanweiServiceImpl extends ServiceImpl<GhShouhuodanweiMapper, GhShouhuodanwei> implements IGhShouhuodanweiService {

    @Autowired
    private GhShouhuodanweiMapper gh;
    @Override
    public List<GhShouhuodanwei> finddanwei(String dwname, String id) {
        return gh.finddanwei(dwname,id);
    }
}
