package org.jeecg.modules.contract.contract.service;


import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.contract.contract.entity.ContractInformation;

import java.util.List;


/**
 * @Description: 合同信息表
 * @Author: jeecg-boot
 * @Date:   2020-08-28
 * @Version: V1.0
 */
public interface IContractInformationService extends IService<ContractInformation> {
    public  Integer findpzh();

    //查询合同编号
    public List<String> selectHtbh(String htbh,String httype);

    //查询合同凭证号
    public List<String>  selectHtpzh(String htbh,String httype);
}
