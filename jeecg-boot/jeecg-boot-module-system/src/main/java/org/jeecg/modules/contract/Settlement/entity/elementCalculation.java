package org.jeecg.modules.contract.Settlement.entity;

import lombok.Data;

import java.math.BigDecimal;

/*
* 通过元素对比放回结果集
* */
@Data
public class elementCalculation {
    //合同信息表中的惟一的ID
    private String cid;

    //合同标号
    private String contractNo;

    //元素
    private String elements;

    //元素数据
    private BigDecimal elelmentData;

    //是否扣款
    private String isreDuce;

    //基准数值
    private BigDecimal baseData;

    //元素左边匹配公式
    private String leftFormula;

    //元素右边匹配公式
    private String rightFormula;

    //合同单价
    private BigDecimal contractPrice;

    //税率
    private BigDecimal taxRate;

    //扣款
    private BigDecimal deduction;

    //元素公式左边值
    private BigDecimal leftElementData;



}
