package org.jeecg.modules.contract_management.productlist.entity;

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
 * @Description: 产品列表
 * @Author: jeecg-boot
 * @Date:   2020-12-10
 * @Version: V1.0
 */
@Data
@TableName("product_list")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="product_list对象", description="产品列表")
public class ProductList implements Serializable {
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
	/**产品id*/
	@Excel(name = "产品id", width = 15)
    @ApiModelProperty(value = "产品id")
    private java.lang.String productId;
	/**总量*/
	@Excel(name = "总量", width = 15)
    @ApiModelProperty(value = "总量")
    private java.math.BigDecimal total;
	/**1月*/
	@Excel(name = "1月", width = 15)
    @ApiModelProperty(value = "1月")
    private java.math.BigDecimal january;
	/**2月*/
	@Excel(name = "2月", width = 15)
    @ApiModelProperty(value = "2月")
    private java.math.BigDecimal february;
	/**3月*/
	@Excel(name = "3月", width = 15)
    @ApiModelProperty(value = "3月")
    private java.math.BigDecimal march;
	/**4月*/
	@Excel(name = "4月", width = 15)
    @ApiModelProperty(value = "4月")
    private java.math.BigDecimal april;
	/**5月*/
	@Excel(name = "5月", width = 15)
    @ApiModelProperty(value = "5月")
    private java.math.BigDecimal may;
	/**6月*/
	@Excel(name = "6月", width = 15)
    @ApiModelProperty(value = "6月")
    private java.math.BigDecimal june;
	/**7月*/
	@Excel(name = "7月", width = 15)
    @ApiModelProperty(value = "7月")
    private java.math.BigDecimal july;
	/**8月*/
	@Excel(name = "8月", width = 15)
    @ApiModelProperty(value = "8月")
    private java.math.BigDecimal august;
	/**9月*/
	@Excel(name = "9月", width = 15)
    @ApiModelProperty(value = "9月")
    private java.math.BigDecimal september;
	/**10月*/
	@Excel(name = "10月", width = 15)
    @ApiModelProperty(value = "10月")
    private java.math.BigDecimal october;
	/**11月*/
	@Excel(name = "11月", width = 15)
    @ApiModelProperty(value = "11月")
    private java.math.BigDecimal november;
	/**12月*/
	@Excel(name = "12月", width = 15)
    @ApiModelProperty(value = "12月")
    private java.math.BigDecimal december;
	/**是否删除*/
	@Excel(name = "是否删除", width = 15)
    @ApiModelProperty(value = "是否删除")
    private java.lang.Integer isdelete;
}
