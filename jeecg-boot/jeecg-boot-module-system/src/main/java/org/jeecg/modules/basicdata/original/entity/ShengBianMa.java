package org.jeecg.modules.basicdata.original.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
@TableName("G_ShengBianMa")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="G_ShengBianMa对象", description="省级信息")
public class ShengBianMa {

    private static final long serialVersionUID = 1L;

    /**主键*/
    @TableId(type = IdType.ASSIGN_ID)
    private java.lang.Integer id;

    private java.lang.String ShengClassCode;

    private java.lang.String ShengCode;

    private java.lang.String ShengName;
}
