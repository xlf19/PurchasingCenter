package org.jeecg.modules.contract.Settlement.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * author: wzs
 * Date:2020/10/1614:15
 **/

@Data
public class ContractNumber {

  //合同表中的唯一ID
  private String cid;

  //合同编号
  private String contractNo;

  //合同斤检时的重量
  private BigDecimal contractWeight;

  //基数
  private BigDecimal baseData;

  //左值
  private BigDecimal leftNumber;

  //扣除的数量
  private BigDecimal numberDeductions;

  //左边匹配的公式
  private String leftFormula;

  //右边匹配的公式
  private String rightFormula;




}
