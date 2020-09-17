package org.jeecg.modules.contract.quality_date.entity;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

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
 * @Description: 加权平均
 * @Author: jeecg-boot
 * @Date: 2020-09-09
 * @Version: V1.0
 */
@Data
@TableName("quality_date")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "quality_date对象", description = "加权平均")
public class QualityDate implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String createBy;
    /**
     * 创建日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private Date createTime;
    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private String updateBy;
    /**
     * 更新日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private Date updateTime;
    /**
     * 所属部门
     */
    @ApiModelProperty(value = "所属部门")
    private String sysOrgCode;
    /**
     * 取样日期
     */
    @Excel(name = "取样日期", width = 15, format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "取样日期")
    private Date qyDate;

    /**
     * 合同编号
     */
    @Excel(name = "合同编号", width = 15)
    @ApiModelProperty(value = "合同编号")
    private java.lang.String contractNo;

    /**
     * 物资名称
     */
    @Excel(name = "物资名称", width = 15)
    @ApiModelProperty(value = "物资名称")
    private String materialName;
    /**
     * 供货单位
     */
    @Excel(name = "供货单位", width = 15)
    @ApiModelProperty(value = "供货单位")
    private String supplier;
    /**
     * 需方单位
     */
    @Excel(name = "需方单位", width = 15)
    @ApiModelProperty(value = "需方单位")
    private String receivingunit;
    /**
     * 检验量
     */
    @Excel(name = "检验量", width = 15)
    @ApiModelProperty(value = "检验量")
    private BigDecimal weighing;
    /**
     * TFE
     */
    @Excel(name = "TFE", width = 15)
    @ApiModelProperty(value = "TFE")
    private BigDecimal tfe;
    /**
     * SIO2
     */
    @Excel(name = "SIO2", width = 15)
    @ApiModelProperty(value = "SIO2")
    private BigDecimal sio2;
    /**
     * S
     */
    @Excel(name = "S", width = 15)
    @ApiModelProperty(value = "S")
    private BigDecimal ss;
    /**
     * P
     */
    @Excel(name = "P", width = 15)
    @ApiModelProperty(value = "P")
    private BigDecimal pp;
    /**
     * AL2O3
     */
    @Excel(name = "AL2O3", width = 15)
    @ApiModelProperty(value = "AL2O3")
    private BigDecimal al2o3;
    /**
     * MnO
     */
    @Excel(name = "MnO", width = 15)
    @ApiModelProperty(value = "MnO")
    private BigDecimal mno;
    /**
     * TiO2
     */
    @Excel(name = "TiO2", width = 15)
    @ApiModelProperty(value = "TiO2")
    private BigDecimal tio2;
    /**
     * CAO
     */
    @Excel(name = "CAO", width = 15)
    @ApiModelProperty(value = "CAO")
    private BigDecimal cao;
    /**
     * Cr
     */
    @Excel(name = "Cr", width = 15)
    @ApiModelProperty(value = "Cr")
    private BigDecimal cr;
    /**
     * Cu
     */
    @Excel(name = "Cu", width = 15)
    @ApiModelProperty(value = "Cu")
    private BigDecimal cu;
    /**
     * FEO
     */
    @Excel(name = "FEO", width = 15)
    @ApiModelProperty(value = "FEO")
    private BigDecimal feo;
    /**
     * MGO
     */
    @Excel(name = "MGO", width = 15)
    @ApiModelProperty(value = "MGO")
    private BigDecimal mgo;
    /**
     * Pb
     */
    @Excel(name = "Pb", width = 15)
    @ApiModelProperty(value = "Pb")
    private BigDecimal pb;
    /**
     * As
     */
    @Excel(name = "As", width = 15)
    @ApiModelProperty(value = "As")
    private BigDecimal as1;
    /**
     * Zn
     */
    @Excel(name = "Zn", width = 15)
    @ApiModelProperty(value = "Zn")
    private BigDecimal zn;
    /**
     * K2O
     */
    @Excel(name = "K2O", width = 15)
    @ApiModelProperty(value = "K2O")
    private BigDecimal k2o;
    /**
     * H2O
     */
    @Excel(name = "H2O", width = 15)
    @ApiModelProperty(value = "H2O")
    private BigDecimal h2o;
    /**
     * 含粉
     */
    @Excel(name = "含粉", width = 15)
    @ApiModelProperty(value = "含粉")
    private BigDecimal containsPowder;
    /**
     * 抗压
     */
    @Excel(name = "抗压", width = 15)
    @ApiModelProperty(value = "抗压")
    private BigDecimal compressive;
    /**
     * 粒度
     */
    @Excel(name = "[粒度<5mm]", width = 15)
    @ApiModelProperty(value = "[粒度<5mm]")
    private BigDecimal granularity;
    /**
     * md
     */
    @Excel(name = "md", width = 15)
    @ApiModelProperty(value = "md")
    private BigDecimal md;
    /**
     * 凭证号标识
     */
    @Excel(name = "凭证号标识", width = 15)
    @ApiModelProperty(value = "凭证号标识")
    private Integer voucherId;
}
