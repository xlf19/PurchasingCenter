package org.jeecg.modules.contract.elements.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.contract.elements.entity.ContractElements;

/**
 * @Description: 合同元素表
 * @Author: jeecg-boot
 * @Date:   2020-08-31
 * @Version: V1.0
 */
public interface ContractElementsMapper extends BaseMapper<ContractElements> {

    //获取合同元素信息
    public List<ContractElements> findList(@Param("cid") String cid);

    //删除合同元素信息
    public void updateElelist(@Param("cid")String cid);


    //根据合同号获取合同元素信息
    public List<String> findListhth(@Param("htbh") String htbh);
}
