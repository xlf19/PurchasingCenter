package org.jeecg.modules.contract.shengtie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.contract.elements.entity.ContractElements;

import java.util.List;


public interface ShengTieMapper  extends BaseMapper<ContractElements> {

    //获取生铁合同元素信息
    public List<ContractElements> findList(@Param("cid") String cid);

    //删除生铁合同元素信息
    public void updateElelist(@Param("cid")String cid);



}
