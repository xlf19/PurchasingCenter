package org.jeecg.modules.basicdata.original.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.basicdata.original.entity.OriginalCharge;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.basicdata.original.entity.ShengBianMa;
import org.jeecg.modules.basicdata.original.entity.ShiBianMa;

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

    //查询省级信息
    List<ShengBianMa> shengbima();

    //查询市级信息
    List<ShiBianMa> shibima(@Param("code")String code);

    //供应商编码
    String supplierCode(@Param("code")String code);

    //查询供应商
    List<OriginalCharge> searchname(@Param("companyName")String companyName);
}
