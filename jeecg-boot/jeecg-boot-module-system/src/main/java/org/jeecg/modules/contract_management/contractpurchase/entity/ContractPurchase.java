package org.jeecg.modules.contract_management.contractpurchase.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 采购合同表
 * @Author: jeecg-boot
 * @Date:   2020-12-04
 * @Version: V1.0
 */
@Data
@TableName("contract_purchase")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="contract_purchase对象", description="采购合同表")
public class ContractPurchase implements Serializable {
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
	/**模板id*/
	@Excel(name = "模板id", width = 15)
    @ApiModelProperty(value = "模板id")
    private java.lang.String templateId;
	/**合同编号*/
	@Excel(name = "合同编号", width = 15)
    @ApiModelProperty(value = "合同编号")
    private java.lang.String contractNo;
    /**合同类别*/
    @Excel(name = "合同类别", width = 15)
    @ApiModelProperty(value = "合同类别")
    private java.lang.String contractType;
	/**供方单位*/
	@Excel(name = "供方单位", width = 15)
    @ApiModelProperty(value = "供方单位")
    private java.lang.String supplier;
	/**需方单位*/
	@Excel(name = "需方单位", width = 15)
    @ApiModelProperty(value = "需方单位")
    private java.lang.String demandSideUnit;
	/**合同标题*/
	@Excel(name = "合同标题", width = 15)
    @ApiModelProperty(value = "合同标题")
    private java.lang.String contractTitle;
	/**含税*/
	@Excel(name = "含税", width = 15)
    @ApiModelProperty(value = "含税")
    private  java.lang.Integer taxIncluded;

	/**税率*/
	@Excel(name = "税率", width = 15)
    @ApiModelProperty(value = "税率")
    private java.lang.String taxRate;
	/**币种*/
	@Excel(name = "币种", width = 15)
    @ApiModelProperty(value = "币种")
    private java.lang.String currency;
	/**签订地点*/
	@Excel(name = "签订地点", width = 15)
    @ApiModelProperty(value = "签订地点")
    private java.lang.String placeSigning;
	/**交货地点*/
	@Excel(name = "交货地点", width = 15)
    @ApiModelProperty(value = "交货地点")
    private java.lang.String tradingLocations;
	/**签订时间*/
	@Excel(name = "签订时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "签订时间")
    private Date signingTime;
	/**交货时间*/
	@Excel(name = "交货时间", width = 15,format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "交货时间")
    private Date deliveryTime;
	/**开始时间*/
	@Excel(name = "开始时间", width = 15,format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "开始时间")
    private Date startingTime;
	/**终止时间*/
	@Excel(name = "终止时间", width = 15,format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "终止时间")
    private Date stopTime;
	/**合同备注*/
	@Excel(name = "合同备注", width = 15)
    @ApiModelProperty(value = "合同备注")
    private java.lang.String contractNotes;
	/**其他备注*/
	@Excel(name = "其他备注", width = 15)
    @ApiModelProperty(value = "其他备注")
    private java.lang.String otherRemarks;
	/**文件*/
	@Excel(name = "文件", width = 15)
    @ApiModelProperty(value = "文件")
    private java.lang.String filePath;
    /**当前状态*/
    @Excel(name = "当前状态", width = 15)
    @ApiModelProperty(value = "当前状态")
    private java.lang.String currentState;
	/**是否删除*/
	@Excel(name = "是否删除", width = 15)
    @ApiModelProperty(value = "是否删除")
    private java.lang.Integer isdelete;


    private java.lang.String sphtCode;

    private java.lang.String spliuCheng;

    private java.lang.String spnowState;

    private java.lang.Integer spId;

}
