package org.jeecg.modules.contract.Settlement.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.modules.contract.Hetonggongshi.entity.SysHetonggongshi;
import org.jeecg.modules.contract.contract.entity.ContractInformation;

import org.jeecg.modules.contract.elements.entity.ContractElements;

import java.util.List;
import java.util.Map;

/**
 * author: wzs
 * Date:2020/9/159:52
 **/
public interface IshengtieAccountService extends IService<T> {

  List<Map<Object,String>> findHthList(String hth,String contracttype);

  //通过合同号找相应的凭证号
  List<Map<Object,String>> findVoucherNo(String contractNo);

  //通过合同编号，以及凭证号查找所有信息
  IPage<List<ContractInformation>> findContractList(IPage<Map<Object, String>> page, String contractNo, String voucherNo);

  //通过合同信息表中的ID找元素表中对应的元素1对多
  List<ContractElements> findContractElement(String id);

  //<!--查取元素信息列表-->
  List<ContractElements> findElements(String[] ci_id);

  //查取元素公式表中的数据
  List<SysHetonggongshi> findHtGs(String cno);

  //合同结算
  List<ContractInformation> contractAccount(String[] ci_id);



}
