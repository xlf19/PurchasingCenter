package org.jeecg.modules.contract_management.chargeSearch.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;
import java.util.List;
import java.util.Map;

public interface ChargeSearchMapper extends BaseMapper<T> {

    //原炉料查询
    IPage<Map<Object, String>> list(IPage<Map<Object, String>> page, @Param("startdate") String startdate, @Param("enddate") String enddate);

    //合同明细查询
    List<Map<Object, String>> findDetail(@Param("contractid")String contractid);

    //通过合同id查询产品信息表
    List<Map<Object, String>> productid(@Param("contractid")String contractid);

    //通过产品id查询产品明细表
    List<Map<Object, String>> productidlist(@Param("pid")String pid);
    //根据合同号改变合同状态
    boolean updatestatus(@Param("contractNo")String contractNo);
}
