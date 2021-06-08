package org.jeecg.modules.contract.shengtiedate.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.contract.shengtiedate.entity.QualityDateShengtie;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * @Description: 生铁加权平均
 * @Author: jeecg-boot
 * @Date:   2021-04-06
 * @Version: V1.0
 */
public interface IQualityDateShengtieService extends IService<QualityDateShengtie> {
    IPage<Map<Object, String>> queryPagelistst(IPage<Map<Object, String>> page, String htbh);
}
