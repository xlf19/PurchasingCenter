package org.jeecg.modules.basicdata.original.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@TableName("G_ShiBianMa")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="G_ShiBianMa对象", description="市级信息")
public class ShiBianMa {

    private static final long serialVersionUID = 1L;


    /**主键*/
    @TableId(type = IdType.ASSIGN_ID)
    private java.lang.Integer id;

    private java.lang.String ShiClassCode;

    private java.lang.String ShiParentCode;

    private java.lang.String ShiCode;

    private java.lang.String ShiName;
}
