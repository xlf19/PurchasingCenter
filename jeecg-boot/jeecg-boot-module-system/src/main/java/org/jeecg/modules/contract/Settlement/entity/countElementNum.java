package org.jeecg.modules.contract.Settlement.entity;

import lombok.Data;

/**
 * author: wzs
 * Date:2020/10/1415:55
 **/
@Data
public class countElementNum {

  //合同信息表中的惟一的ID
  private String cid;

  //元素数量
  private Integer elementNum;

}
