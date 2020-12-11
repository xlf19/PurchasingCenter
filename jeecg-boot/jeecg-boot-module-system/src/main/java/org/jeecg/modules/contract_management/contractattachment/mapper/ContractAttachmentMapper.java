package org.jeecg.modules.contract_management.contractattachment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;

import java.util.Map;

public interface ContractAttachmentMapper extends BaseMapper<T> {

    //合同附件查询
    IPage<Map<Object, String>> list(IPage<Map<Object, String>> page, @Param("startdate") String startdate, @Param("enddate") String enddate);
}
