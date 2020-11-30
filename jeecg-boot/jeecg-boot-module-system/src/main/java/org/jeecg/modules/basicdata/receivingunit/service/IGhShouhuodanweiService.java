package org.jeecg.modules.basicdata.receivingunit.service;

import org.jeecg.modules.basicdata.receivingunit.entity.GhShouhuodanwei;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: 收货单位
 * @Author: jeecg-boot
 * @Date:   2020-11-30
 * @Version: V1.0
 */
public interface IGhShouhuodanweiService extends IService<GhShouhuodanwei> {

    //查询收货单位
    List<GhShouhuodanwei> finddanwei(String dwname, String id);

}
