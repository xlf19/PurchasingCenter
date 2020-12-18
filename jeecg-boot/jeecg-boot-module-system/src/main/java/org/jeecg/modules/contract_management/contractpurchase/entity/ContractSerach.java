package org.jeecg.modules.contract_management.contractpurchase.entity;

import lombok.Data;

@Data
public class ContractSerach {
    private java.lang.String id;
    /**开始时间*/
    private java.lang.String startingTime;
    /**终止时间*/
    private java.lang.String stopTime;
}
