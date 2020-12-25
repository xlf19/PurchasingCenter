package org.jeecg.modules.contract_management.contractpurchase.service;

import org.jeecg.modules.contract_management.contractpurchase.entity.ContractPurchase;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 采购合同表
 * @Author: jeecg-boot
 * @Date:   2020-12-04
 * @Version: V1.0
 */
public interface IContractPurchaseService extends IService<ContractPurchase> {
    //生成合同号
    String hthfind();
    //根据合同号查询数据
    String findcontract(String contractNo);

    //根据合同编号修改合同信息状态
    boolean updatecontract(String HeTongBianHao,String CurrentState,String SPHTCode,String SPLiuCheng,String SPNowState,Integer SPId);

}
