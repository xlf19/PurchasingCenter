package org.jeecg.modules.contract.quality_date.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.jeecg.modules.contract.quality_date.entity.QualityDate;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * @Description: 加权平均
 * @Author: jeecg-boot
 * @Date:   2020-09-09
 * @Version: V1.0
 */
public interface IQualityDateService extends IService<QualityDate> {
    //获取精粉富粉球团质检信息
    public IPage<Map<Object,String>> queryPageListjf(IPage<Map<Object,String>> page, String htbh);

}
