package org.jeecg.modules.contract.shengtiedate.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.jeecg.modules.contract.shengtiedate.entity.QualityDateShengtie;
import org.jeecg.modules.contract.shengtiedate.mapper.QualityDateShengtieMapper;
import org.jeecg.modules.contract.shengtiedate.service.IQualityDateShengtieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Map;

/**
 * @Description: 生铁加权平均
 * @Author: jeecg-boot
 * @Date:   2021-04-06
 * @Version: V1.0
 */
@Service
public class QualityDateShengtieServiceImpl extends ServiceImpl<QualityDateShengtieMapper, QualityDateShengtie> implements IQualityDateShengtieService {

    @Autowired
    private  QualityDateShengtieMapper qualityDateShengtieMapper;
    @Override
    public IPage<Map<Object, String>> queryPagelistst(IPage<Map<Object, String>> page, String htbh) {
        return qualityDateShengtieMapper.queryPagelistst(page,htbh);
    }
}
