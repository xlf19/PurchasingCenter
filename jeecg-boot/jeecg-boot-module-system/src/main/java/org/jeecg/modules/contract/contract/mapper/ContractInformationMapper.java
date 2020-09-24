package org.jeecg.modules.contract.contract.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.contract.contract.entity.ContractInformation;

import java.util.List;


/**
 * @Description: 合同信息表
 * @Author: jeecg-boot
 * @Date:   2020-08-28
 * @Version: V1.0
 */
public interface ContractInformationMapper extends BaseMapper<ContractInformation> {

    //获取凭证号
    public  Integer findpzh();

    //查询合同编号
    public List<String>  selectHtbh(@Param("htbh") String htbh,@Param("httype") String httype);

    //查询合同凭证号
    public List<String>  selectHtpzh(@Param("htbh") String htbh,@Param("httype") String httype);

}
