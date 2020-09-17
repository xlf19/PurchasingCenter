package org.jeecg.modules.contract.elements.entity;

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
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 合同元素表
 * @Author: jeecg-boot
 * @Date: 2020-08-31
 * @Version: V1.0
 */
@Data
@TableName("contract_elements")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "contract_elements对象", description = "合同元素表")
public class ContractElements implements Serializable {
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
     * 合同信息表的外键
     */
    @Excel(name = "合同信息表的外键", width = 15)
    @ApiModelProperty(value = "合同信息表的外键")
    private String ciId;
    /**
     * 合同编号
     */
    @Excel(name = "合同编号", width = 15)
    @ApiModelProperty(value = "合同编号")
    private String contractNo;
    /**
     * 凭证号
     */
    @Excel(name = "凭证号", width = 15)
    @ApiModelProperty(value = "凭证号")
    private Integer voucherNo;
    /**
     * 元素
     */
    @Excel(name = "元素", width = 15)
    @ApiModelProperty(value = "元素")
    private String element;
    /**
     * 扣除结果
     */
    @Excel(name = "扣除结果", width = 15)
    @ApiModelProperty(value = "扣除结果")
    private BigDecimal deductionResult;
    /**
     * 是否删除
     */
    @Excel(name = "是否删除", width = 15)
    @ApiModelProperty(value = "是否删除")
    private Integer isDelete;
    /**
     * 元素数据
     */
    @Excel(name = "元素数据", width = 15)
    @ApiModelProperty(value = "元素数据")
    private BigDecimal elelmentDate;
}
