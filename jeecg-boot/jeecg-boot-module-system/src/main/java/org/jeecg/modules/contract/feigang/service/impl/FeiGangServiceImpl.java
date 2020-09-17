package org.jeecg.modules.contract.feigang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.modules.contract.feigang.mapper.FeiGangMapper;
import org.jeecg.modules.contract.feigang.service.IFeiGangService;
import org.springframework.stereotype.Service;

@Service
public class FeiGangServiceImpl  extends ServiceImpl<FeiGangMapper, T> implements IFeiGangService {
}
