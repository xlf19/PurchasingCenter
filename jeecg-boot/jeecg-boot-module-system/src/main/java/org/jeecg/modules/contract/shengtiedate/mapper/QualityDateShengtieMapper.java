package org.jeecg.modules.contract.shengtiedate.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.contract.shengtiedate.entity.QualityDateShengtie;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 生铁加权平均
 * @Author: jeecg-boot
 * @Date:   2021-04-06
 * @Version: V1.0
 */
public interface QualityDateShengtieMapper extends BaseMapper<QualityDateShengtie> {
    //获取精粉富粉球团质检信息
    IPage<Map<Object, String>> queryPagelistst(IPage<Map<Object, String>> page, @Param("htbh") String htbh);
}
