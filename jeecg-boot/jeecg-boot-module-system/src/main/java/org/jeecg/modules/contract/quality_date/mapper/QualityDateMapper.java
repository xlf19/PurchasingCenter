package org.jeecg.modules.contract.quality_date.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.contract.quality_date.entity.QualityDate;

/**
 * @Description: 加权平均
 * @Author: jeecg-boot
 * @Date:   2020-09-09
 * @Version: V1.0
 */
public interface QualityDateMapper extends BaseMapper<QualityDate> {

    //获取精粉富粉球团质检信息
    IPage<Map<Object, String>> queryPageListjf(IPage<Map<Object, String>> page, @Param("htbh") String htbh);

}
