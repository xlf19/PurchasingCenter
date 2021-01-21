package org.jeecg.modules.contract_management.contract_status.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;
import java.util.Map;

public interface ContractStatusMapper extends BaseMapper<T> {

    //查询合同号
    List<Map<Object,String>> listbh();


    //根据合同号改变合同状态
    boolean updateisdelete(@Param("HeTongCode")String HeTongCode);

    // //根据合同号改变设备管理_合同_主表合同状态
    boolean updatecontract(@Param("HeTongCode")String HeTongCode);

    //查询合同标题
    List<Map<String,Object>> searchTitle(@Param("bumen")List<String> bumen);
}
