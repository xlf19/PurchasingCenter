package org.jeecg.modules.contract.shengtie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.contract.elements.entity.ContractElements;

import java.util.List;

public interface IShengTieService  extends IService<ContractElements> {

   //获取生铁合同元素信息
   public List<ContractElements> findList(String cid);
   //删除生铁合同元素信息
   public void updateElelist(String cid);

}

