package org.jeecg.modules.basicdata.original.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.basicdata.original.entity.OriginalCharge;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 原炉料供应商
 * @Author: jeecg-boot
 * @Date:   2020-11-26
 * @Version: V1.0
 */
public interface OriginalChargeMapper extends BaseMapper<OriginalCharge> {

    //批量删除供应商信息
    boolean deleteOrilist(@Param("ids")String[] ids);

    //供应商使用状态
    boolean usestatus(@Param("ids")String[] ids,@Param("status")Integer status);
}
