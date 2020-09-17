package org.jeecg.modules.contract.elements.service;


import org.jeecg.modules.contract.elements.entity.ContractElements;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: 合同元素表
 * @Author: jeecg-boot
 * @Date:   2020-08-31
 * @Version: V1.0
 */
public interface IContractElementsService extends IService<ContractElements> {
    //查询合同元素数据
    public List<ContractElements> elementlist(String htbh);

}
