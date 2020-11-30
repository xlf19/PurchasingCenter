package org.jeecg.modules.basicdata.receivingunit.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.basicdata.receivingunit.entity.GhShouhuodanwei;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 收货单位
 * @Author: jeecg-boot
 * @Date:   2020-11-30
 * @Version: V1.0
 */
public interface GhShouhuodanweiMapper extends BaseMapper<GhShouhuodanwei> {

    //查询收货单位
    List<GhShouhuodanwei> finddanwei(@Param("dwname")String dwname,@Param("id")String id);
}
