package org.jeecg.modules.basicdata.original.entity;

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
 * @Description: 原炉料供应商
 * @Author: jeecg-boot
 * @Date:   2020-11-26
 * @Version: V1.0
 */
@Data
@TableName("original_charge")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="original_charge对象", description="原炉料供应商")
public class OriginalCharge implements Serializable {
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
	/**所在省级*/
	@Excel(name = "所在省级", width = 15)
    @ApiModelProperty(value = "所在省级")
    private java.lang.String provincial;
	/**所在市级*/
	@Excel(name = "所在市级", width = 15)
    @ApiModelProperty(value = "所在市级")
    private java.lang.String cityLevel;
	/**供应商编码*/
	@Excel(name = "供应商编码", width = 15)
    @ApiModelProperty(value = "供应商编码")
    private java.lang.String supplierCode;
	/**单位名称*/
	@Excel(name = "单位名称", width = 15)
    @ApiModelProperty(value = "单位名称")
    private java.lang.String companyName;
	/**名称首字母*/
	@Excel(name = "名称首字母", width = 15)
    @ApiModelProperty(value = "名称首字母")
    private java.lang.String nameInitials;
	/**联系人*/
	@Excel(name = "联系人", width = 15)
    @ApiModelProperty(value = "联系人")
    private java.lang.String contactPerson;
	/**邮政编码*/
	@Excel(name = "邮政编码", width = 15)
    @ApiModelProperty(value = "邮政编码")
    private java.lang.String postalCode;
	/**单位地址*/
	@Excel(name = "单位地址", width = 15)
    @ApiModelProperty(value = "单位地址")
    private java.lang.String unitAddress;
	/**电子邮箱*/
	@Excel(name = "电子邮箱", width = 15)
    @ApiModelProperty(value = "电子邮箱")
    private java.lang.String email;
	/**银行名称*/
	@Excel(name = "银行名称", width = 15)
    @ApiModelProperty(value = "银行名称")
    private java.lang.String bankName;
	/**银行账号*/
	@Excel(name = "银行账号", width = 15)
    @ApiModelProperty(value = "银行账号")
    private java.lang.String bankAccount;
	/**信用代码*/
	@Excel(name = "信用代码", width = 15)
    @ApiModelProperty(value = "信用代码")
    private java.lang.String creditCode;
	/**法人代表*/
	@Excel(name = "法人代表", width = 15)
    @ApiModelProperty(value = "法人代表")
    private java.lang.String legalRepresentative;
	/**联系电话*/
	@Excel(name = "联系电话", width = 15)
    @ApiModelProperty(value = "联系电话")
    private java.lang.String contactNumber;
	/**联系传真*/
	@Excel(name = "联系传真", width = 15)
    @ApiModelProperty(value = "联系传真")
    private java.lang.String contactFax;
	/**企业性质*/
	@Excel(name = "企业性质", width = 15)
    @ApiModelProperty(value = "企业性质")
    private java.lang.Integer enterpriseNature;
	/**供方状态*/
	@Excel(name = "供方状态", width = 15)
    @ApiModelProperty(value = "供方状态")
    private java.lang.Integer supplierStatus;
	/**临时供应商*/
	@Excel(name = "临时供应商", width = 15)
    @ApiModelProperty(value = "临时供应商")
    private java.lang.Integer temporarySupplier;
	/**内部供应商*/
	@Excel(name = "内部供应商", width = 15)
    @ApiModelProperty(value = "内部供应商")
    private java.lang.Integer internalSupplier;
	/**成立日期*/
	@Excel(name = "成立日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "成立日期")
    private java.util.Date establishmentDate;
	/**准入日期*/
	@Excel(name = "准入日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "准入日期")
    private java.util.Date accessDate;
	/**注册资金*/
	@Excel(name = "注册资金", width = 15)
    @ApiModelProperty(value = "注册资金")
    private java.lang.String registeredCapital;
	/**经营范围*/
	@Excel(name = "经营范围", width = 15)
    @ApiModelProperty(value = "经营范围")
    private java.lang.String businessScope;
	/**公司类别*/
	@Excel(name = "公司类别", width = 15)
    @ApiModelProperty(value = "公司类别")
    private java.lang.Integer companyCategory;
	/**使用状态*/
	@Excel(name = "使用状态", width = 15)
    @ApiModelProperty(value = "使用状态")
    private java.lang.Integer useStatus;
	/**删除状态*/
	@Excel(name = "删除状态", width = 15)
    @ApiModelProperty(value = "删除状态")
    private java.lang.Integer isdelete;
}
