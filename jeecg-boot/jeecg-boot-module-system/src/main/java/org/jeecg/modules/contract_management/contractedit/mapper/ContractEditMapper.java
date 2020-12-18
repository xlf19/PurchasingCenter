package org.jeecg.modules.contract_management.contractedit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.modules.contract_management.productinformation.entity.ProductInformation;

public interface ContractEditMapper extends BaseMapper<T> {
    //删除产品列表和产品信息表
    boolean deleteproduct(@Param("id")String id);


}
