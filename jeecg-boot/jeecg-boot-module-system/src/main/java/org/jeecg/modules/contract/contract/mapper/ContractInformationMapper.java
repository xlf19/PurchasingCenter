package org.jeecg.modules.contract.contract.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.contract.contract.entity.ContractInformation;


/**
 * @Description: 合同信息表
 * @Author: jeecg-boot
 * @Date:   2020-08-28
 * @Version: V1.0
 */
public interface ContractInformationMapper extends BaseMapper<ContractInformation> {

    //获取凭证号
    public  Integer findpzh();


}
