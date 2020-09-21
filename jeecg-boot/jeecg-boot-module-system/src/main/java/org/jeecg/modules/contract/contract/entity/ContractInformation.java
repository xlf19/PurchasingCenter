package org.jeecg.modules.contract.contract.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 合同信息表
 * @Author: jeecg-boot
 * @Date:   2020-08-28
 * @Version: V1.0
 */
@Data
@TableName("contract_information")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="contract_information对象", description="合同信息表")
public class ContractInformation implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private java.lang.String sysOrgCode;
	/**合同编号*/
	@Excel(name = "合同编号", width = 15)
    @ApiModelProperty(value = "合同编号")
    private java.lang.String contractNo;
	/**凭证号*/
	@Excel(name = "凭证号", width = 15)
    @ApiModelProperty(value = "凭证号")
    private java.lang.Integer voucherNo;
	/**合同类型*/
	@Excel(name = "合同类型", width = 15)
    @ApiModelProperty(value = "合同类型")
    private java.lang.String contractType;
	/**物资编码*/
	@Excel(name = "物资编码", width = 15)
    @ApiModelProperty(value = "物资编码")
    private java.lang.String materialCode;
	/**物资名称*/
	@Excel(name = "物资名称", width = 15)
    @ApiModelProperty(value = "物资名称")
    private java.lang.String materialName;
	/**供货单位*/
	@Excel(name = "供货单位", width = 15)
    @ApiModelProperty(value = "供货单位")
    private java.lang.String supplier;
	/**收货单位*/
	@Excel(name = "收货单位", width = 15)
    @ApiModelProperty(value = "收货单位")
    private java.lang.String receivingUnit;
	/**检斤*/
	@Excel(name = "检斤", width = 15)
    @ApiModelProperty(value = "检斤")
    private java.math.BigDecimal weighing;
	/**合同单价*/
	@Excel(name = "合同单价", width = 15)
    @ApiModelProperty(value = "合同单价")
    private java.math.BigDecimal contractPrice;
	/**结算结果*/
	@Excel(name = "结算结果", width = 15)
    @ApiModelProperty(value = "结算结果")
    private java.math.BigDecimal settlementResults;
	/**结算单价*/
	@Excel(name = "结算单价", width = 15)
    @ApiModelProperty(value = "结算单价")
    private java.math.BigDecimal settlemenPrice;
	/**结算数量*/
	@Excel(name = "结算数量", width = 15)
    @ApiModelProperty(value = "结算数量")
    private java.math.BigDecimal settlementQuantity;
	/**贷款*/
	@Excel(name = "贷款", width = 15)
    @ApiModelProperty(value = "贷款")
    private java.math.BigDecimal loan;
	/**税金*/
	@Excel(name = "税金", width = 15)
    @ApiModelProperty(value = "税金")
    private java.math.BigDecimal taxes;
	/**含税*/
	@Excel(name = "含税", width = 15)
    @ApiModelProperty(value = "含税")
    private Boolean taxIncluded;
	/**税率*/
	@Excel(name = "税率", width = 15)
    @ApiModelProperty(value = "税率")
    private java.math.BigDecimal taxRate;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private java.lang.String remarks;
	/**取样日期*/
	@Excel(name = "取样日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "取样日期")
    private java.util.Date weighingDate;
	/**结算人*/
	@Excel(name = "结算人", width = 15)
    @ApiModelProperty(value = "结算人")
    private java.lang.String clearingHouse;
	/**结算日期*/
	@Excel(name = "结算日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "结算日期")
    private java.util.Date settlementDate;
	/**结算标识*/
	@Excel(name = "结算标识", width = 15)
    @ApiModelProperty(value = "结算标识")
    private java.lang.Integer settlementIdentification;
	/**是否删除*/
	@Excel(name = "是否删除", width = 15)
    @ApiModelProperty(value = "是否删除")
    private java.lang.Integer isDelete;
	/**运费*/
	@Excel(name = "运费", width = 15)
    @ApiModelProperty(value = "运费")
    private java.math.BigDecimal shipping;
	/**点收*/
	@Excel(name = "点收", width = 15)
    @ApiModelProperty(value = "点收")
    private java.lang.String accept;
    /**派工单号*/
    @Excel(name = "派工单号", width = 15)
    @ApiModelProperty(value = "派工单号")
    private java.lang.String workNumber;
    /**规格型号*/
    @Excel(name = "规格型号", width = 15)
    @ApiModelProperty(value = "规格型号")
    private java.lang.String model;
    /**点收日期*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "点收日期")
    private java.util.Date acceptTime;
    /**过磅日期*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "过磅日期")
    private java.util.Date weighTime;


}
