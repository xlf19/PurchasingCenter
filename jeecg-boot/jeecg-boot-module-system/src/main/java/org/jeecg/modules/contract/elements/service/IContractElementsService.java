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
    //获取生铁合同元素信息
    public List<ContractElements> findList(String cid);

    //删除生铁合同元素信息
    public void updateElelist(String cid);

    //根据合同号获取合同元素信息
    public List<String> findListhth( String htbh);

}
