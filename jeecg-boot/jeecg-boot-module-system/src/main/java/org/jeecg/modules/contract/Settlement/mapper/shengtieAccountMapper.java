package org.jeecg.modules.contract.Settlement.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.modules.contract.Hetonggongshi.entity.SysHetonggongshi;
import org.jeecg.modules.contract.Settlement.entity.ContractNumber;
import org.jeecg.modules.contract.Settlement.entity.countElementNum;
import org.jeecg.modules.contract.Settlement.entity.elementCalculation;
import org.jeecg.modules.contract.SysNumgongshi.entity.SysNumgongshi;
import org.jeecg.modules.contract.contract.entity.ContractInformation;

import org.jeecg.modules.contract.elements.entity.ContractElements;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * author: wzs
 * Date:2020/9/1510:03
 **/
public interface shengtieAccountMapper extends BaseMapper<T> {

  List<Map<Object,String>> findHthList(@Param("hth")String hth,@Param("contracttype")String contracttype);

  //通过合同号找相应的凭证号
  List<Map<Object,String>> findVoucherNo(@Param("contractNo")String contractNo);

  //通过合同信息表中的ID找

  //通过合同编号，以及凭证号查找所有信息
  IPage<List<ContractInformation>> findContractList(IPage<Map<Object, String>> page, @Param("contractNo")String contractNo,
                                                    @Param("voucherNo")String voucherNo,@Param("identification") int identification);

  //通过合同信息表中的ID找元素表中对应的元素1对多
  List<ContractElements> findContractElement(@Param("id") String id);

  //查取元素信息列表
  List<ContractElements> findElements(String[] ci_id);

  //根据唯一的id将合同信息表中的数据给取出来
  List<ContractInformation> findContractInformations(String[] ci_id);

      //查取元素公式表中的数据
  List<SysHetonggongshi> findHtGs(@Param("cno") String cno);

  //将计算好的元素数据存入
  Integer depositElementData(List<ContractElements> list);

  //更新合同信息表中的数据
  Integer updataContractInformation(List<ContractInformation> list);

  //查询数量表的数据
  List<SysNumgongshi> selectNumgongshi(@Param("hetongId")String hetongId);

  //查询合同信息表所有信息
  List<ContractInformation> selectContractInformation(@Param("id")String id);


  //查询结算信息
  List<Map<Object,String>> hejiesuan(@Param("id")String id);

  //元素数据公式匹配
  List<elementCalculation> elementCalculations(String[] ci_id);

  //计算每次结算中的元素个数
  List<countElementNum> countElementNum(String[] ci_id);

  //通过筛选后的合同唯一ID获取合同数量对比公式
  List<ContractNumber> cotractNumberById(List<String> cid);

  //<!--删除合同信息表中的结算信息假删除
  Integer deleteAccount(String[] ids);
}
