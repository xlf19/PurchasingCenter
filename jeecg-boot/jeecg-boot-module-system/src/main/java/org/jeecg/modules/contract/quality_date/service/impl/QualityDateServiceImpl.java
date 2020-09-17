package org.jeecg.modules.contract.quality_date.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.jeecg.modules.contract.quality_date.entity.QualityDate;
import org.jeecg.modules.contract.quality_date.mapper.QualityDateMapper;
import org.jeecg.modules.contract.quality_date.service.IQualityDateService;
import org.jeecg.modules.contract.zhijianxinxi.mapper.ZhiJianXinXiMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Map;

/**
 * @Description: 加权平均
 * @Author: jeecg-boot
 * @Date:   2020-09-09
 * @Version: V1.0
 */
@Service
public class QualityDateServiceImpl extends ServiceImpl<QualityDateMapper, QualityDate> implements IQualityDateService {

    @Autowired
    QualityDateMapper jfzjxx;
    public IPage<Map<Object, String>> queryPageListjf(IPage<Map<Object, String>> page, String htbh) {
        return jfzjxx.queryPageListjf(page,htbh);
    }
}
