package org.jeecg.modules.contract.contract.service;


import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.contract.contract.entity.ContractInformation;


/**
 * @Description: 合同信息表
 * @Author: jeecg-boot
 * @Date:   2020-08-28
 * @Version: V1.0
 */
public interface IContractInformationService extends IService<ContractInformation> {
    public  Integer findpzh();
}
