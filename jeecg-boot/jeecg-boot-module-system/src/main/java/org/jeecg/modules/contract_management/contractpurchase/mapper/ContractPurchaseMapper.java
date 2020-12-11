package org.jeecg.modules.contract_management.contractpurchase.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.contract_management.contractpurchase.entity.ContractPurchase;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 采购合同表
 * @Author: jeecg-boot
 * @Date:   2020-12-04
 * @Version: V1.0
 */
public interface ContractPurchaseMapper extends BaseMapper<ContractPurchase> {

    //生成合同号
    String hthfind();
}
