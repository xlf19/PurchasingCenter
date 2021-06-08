package org.jeecg.modules.contract.shengtiedate.entity;

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
 * @Description: 生铁加权平均
 * @Author: jeecg-boot
 * @Date:   2021-04-06
 * @Version: V1.0
 */
@Data
@TableName("quality_date_shengtie")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="quality_date_shengtie对象", description="生铁加权平均")
public class QualityDateShengtie implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private String sysOrgCode;
	/**取样日期*/
	@Excel(name = "取样日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "取样日期")
    private Date qyDate;
	/**合同编号*/
	@Excel(name = "合同编号", width = 15)
    @ApiModelProperty(value = "合同编号")
    private String contractNo;
	/**物资名称*/
	@Excel(name = "物资名称", width = 15)
    @ApiModelProperty(value = "物资名称")
    private String materialName;
	/**供货单位*/
	@Excel(name = "供货单位", width = 15)
    @ApiModelProperty(value = "供货单位")
    private String supplier;
	/**需方单位*/
	@Excel(name = "需方单位", width = 15)
    @ApiModelProperty(value = "需方单位")
    private BigDecimal receivingunit;
	/**检验量*/
	@Excel(name = "检验量", width = 15)
    @ApiModelProperty(value = "检验量")
    private BigDecimal weighing;
	/**Si*/
	@Excel(name = "Si", width = 15)
    @ApiModelProperty(value = "Si")
    private BigDecimal si;
	/**Mn*/
	@Excel(name = "Mn", width = 15)
    @ApiModelProperty(value = "Mn")
    private BigDecimal mn;
	/**S*/
	@Excel(name = "S", width = 15)
    @ApiModelProperty(value = "S")
    private BigDecimal ss;
	/**P*/
	@Excel(name = "P", width = 15)
    @ApiModelProperty(value = "P")
    private BigDecimal pp;
	/**派工单号*/
	@Excel(name = "派工单号", width = 15)
    @ApiModelProperty(value = "派工单号")
    private String workNumber;
	/**凭证号标识*/
	@Excel(name = "凭证号标识", width = 15)
    @ApiModelProperty(value = "凭证号标识")
    private Integer voucherId;
}
