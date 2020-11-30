package org.jeecg.modules.basicdata.original.service;

import org.jeecg.modules.basicdata.original.entity.OriginalCharge;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.basicdata.original.entity.ShengBianMa;
import org.jeecg.modules.basicdata.original.entity.ShiBianMa;

import java.util.List;
import java.util.Map;

/**
 * @Description: 原炉料供应商
 * @Author: jeecg-boot
 * @Date:   2020-11-26
 * @Version: V1.0
 */
public interface IOriginalChargeService extends IService<OriginalCharge> {

    //批量删除供应商信息
    boolean deleteOrilist(String[] ids);

    //供应商使用状态
    boolean usestatus(String[] ids,Integer status);

    //查询省级信息
    List<ShengBianMa> shengbima();


    //查询市级信息
    List<ShiBianMa> shibima(String code);

    //供应商编码
    String supplierCode(String code);

}
