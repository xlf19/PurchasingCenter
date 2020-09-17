package org.jeecg.modules.contract.Hetongmuban.entity;

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
 * @Description: 模板表
 * @Author: jeecg-boot
 * @Date:   2020-09-10
 * @Version: V1.0
 */
@Data
@TableName("sys_template")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="sys_template对象", description="模板表")
public class SysTemplate implements Serializable {
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
	/**扣款类型*/
	@Excel(name = "扣款类型", width = 15)
    @ApiModelProperty(value = "扣款类型")
    private java.lang.String deductiontype;
	/**模板名称*/
	@Excel(name = "模板名称", width = 15)
    @ApiModelProperty(value = "模板名称")
    private java.lang.String templatename;
	/**左区间值*/
	@Excel(name = "左区间值", width = 15)
    @ApiModelProperty(value = "左区间值")
    private java.lang.Double leftnum;
	/**左符号值*/
	@Excel(name = "左符号值", width = 15)
    @ApiModelProperty(value = "左符号值")
    private java.lang.String leftsysbol;
	/**右区间值*/
	@Excel(name = "右区间值", width = 15)
    @ApiModelProperty(value = "右区间值")
    private java.lang.Double rightnum;
	/**右符号值*/
	@Excel(name = "右符号值", width = 15)
    @ApiModelProperty(value = "右符号值")
    private java.lang.String rightsysbol;
	/**元素*/
	@Excel(name = "元素", width = 15)
    @ApiModelProperty(value = "元素")
    private java.lang.String elements;
	/**基准数据*/
	@Excel(name = "基准数据", width = 15)
    @ApiModelProperty(value = "基准数据")
    private java.lang.Double basedata;
	/**扣款*/
	@Excel(name = "扣款", width = 15)
    @ApiModelProperty(value = "扣款")
    private java.lang.Double deductions;
    /**排序字段*/
    @Excel(name = "排序字段", width = 15)
    @ApiModelProperty(value = "排序字段")
    private java.lang.Integer sortnum;
	/**扣款类型*/
	@Excel(name = "扣款类型", width = 15)
    @ApiModelProperty(value = "扣款类型")
    private java.lang.String isreduce;
}
