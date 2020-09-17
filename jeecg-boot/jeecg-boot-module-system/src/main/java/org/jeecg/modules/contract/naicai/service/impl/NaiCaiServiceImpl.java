package org.jeecg.modules.contract.naicai.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.modules.contract.naicai.mapper.NaiCaiMapper;
import org.jeecg.modules.contract.naicai.service.INaiCaiService;
import org.springframework.stereotype.Service;

@Service
public class NaiCaiServiceImpl extends ServiceImpl<NaiCaiMapper, T> implements INaiCaiService {
}
