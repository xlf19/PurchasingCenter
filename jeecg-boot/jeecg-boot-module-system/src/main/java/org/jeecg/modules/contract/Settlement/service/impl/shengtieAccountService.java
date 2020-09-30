package org.jeecg.modules.contract.Settlement.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.formula.functions.T;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.contract.Hetonggongshi.entity.SysHetonggongshi;
import org.jeecg.modules.contract.Settlement.mapper.shengtieAccountMapper;
import org.jeecg.modules.contract.Settlement.service.IshengtieAccountService;
import org.jeecg.modules.contract.SysNumgongshi.entity.SysNumgongshi;
import org.jeecg.modules.contract.contract.entity.ContractInformation;

import org.jeecg.modules.contract.elements.entity.ContractElements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * author: wzs
 * Date:2020/9/159:59
 **/
@Service
public class shengtieAccountService extends ServiceImpl<shengtieAccountMapper, T> implements IshengtieAccountService {


  @Autowired
  shengtieAccountMapper shengtieHth;

  @Override
  public List<Map<Object, String>> findHthList(String hth,String contracttype) {
    System.out.print(hth);
    return shengtieHth.findHthList(hth,contracttype);
  }

  @Override
  public List<Map<Object, String>> findVoucherNo(String contractNo) {

    return shengtieHth.findVoucherNo(contractNo);
  }

  @Override
  public IPage<List<ContractInformation>> findContractList(IPage<Map<Object, String>> page , String contractNo,
                                                           String voucherNo,int settlementIdentification) {
    return shengtieHth.findContractList(page,contractNo,voucherNo,settlementIdentification);
  }

  @Override
  public List<ContractElements> findContractElement(String id) {
    return shengtieHth.findContractElement(id);
  }

  @Override
  public List<ContractElements> findElements(String[] ci_id) {
    List<ContractElements> li = shengtieHth.findElements(ci_id);

    return li;
  }

  @Override
  public List<SysHetonggongshi> findHtGs(String cno) {
    return shengtieHth.findHtGs(cno);
  }


  @Override
  @Transactional
  public List<ContractInformation> contractAccount(String[] ci_id) {

    //计算出元素与基准值的差值
    BigDecimal disparityElementData;

    //公式左值
    BigDecimal leftdata = null;
    //右值
    BigDecimal rightdata = null;
    //元素数据
    BigDecimal elementdata = null;
    //公式左符号
    String leftsysbol = null;
    //公式又符号
    String rightsysbol = null;
    //扣除还是增加的标识
    String IsReduce = null;
    //基数值
    BigDecimal basedatas = null;

    //当元素大于多少时要扣的值
    BigDecimal deductions = null;

    //计算出每个元素要扣的最终值
    BigDecimal resultData = null;

    //元素中的值减去左值得差值
    BigDecimal differenceValue = null;

    //取得元素
    String element = null;

    //存放合同信息表中的唯一ID值
    String ciId = null;

    //将查询到的合同信息表中的元素添加到List集合中返回到controller层
    List<ContractInformation> resultInformation = new ArrayList<>();

    List<Map<String,BigDecimal>> findElementData = null;

    //将计算好的resultData和element放入map中便于后期取值计算
    List<Map<String,List>> elementResultData = new ArrayList<>();

    //根据合同信息表中的唯一ID查询元素表中的所有相对应元素
    List<ContractElements> contractElements = shengtieHth.findElements(ci_id);

    //根据唯一的id将合同信息表中的数据给取出来
    List<ContractInformation> contractInformations = shengtieHth.findContractInformations(ci_id);

    //根据合同编号获取该公公式表中的相应的数据
    List<SysHetonggongshi> heTongGongShi = shengtieHth.findHtGs(contractElements.get(0).getContractNo());

    //根据合同编号获取合同数量表中的相应的数据
    List<SysNumgongshi> heTongNumber = shengtieHth.selectNumgongshi(contractElements.get(0).getContractNo());
    //合同单价
    BigDecimal contractPrice = null;

    //标识错误的原因便于向用户展示
    int heTongGongShiflag = 0;
    int numflag = 0;

    //匹配相应元素和对应的公式
    for (int i = 0; i<contractElements.size(); i++){

      ciId = contractElements.get(i).getCiId();
      elementdata = contractElements.get(i).getElelmentDate();
      element = contractElements.get(i).getElement();


      for (int j= 0; j<heTongGongShi.size(); j++){

        if(contractElements.get(i).getElement().equals(heTongGongShi.get(j).getElements())){

          //进行异常处理如果有些数据为空的时候
          leftdata = heTongGongShi.get(j).getLeftnum();

          rightdata = heTongGongShi.get(j).getRightnum();


          if ( heTongGongShi.get(j).getLeftsysbol() == null|| heTongGongShi.get(j).getLeftsysbol().equals("")) {
            leftsysbol = "-100";
            System.out.println("leftsysbol:"+leftsysbol);
          }else {
            leftsysbol = heTongGongShi.get(j).getLeftsysbol();
          }

          if (heTongGongShi .get(j).getRightsysbol() == null|| heTongGongShi.get(j).getRightsysbol().equals("")) {
            rightsysbol = "-100";
            System.out.println("rightsysbol:"+rightsysbol);
          }else {
            rightsysbol = heTongGongShi .get(j).getRightsysbol();

          }

          deductions = heTongGongShi.get(j).getDeductions();
          if (heTongGongShi.get(j).getBasedata() == null || heTongGongShi.get(j).getBasedata().equals("")){
            basedatas = new BigDecimal("0");
          }else {
            basedatas = heTongGongShi.get(j).getBasedata();
          }

          IsReduce = heTongGongShi.get(j).getIsreduce();

          //从公式表集合中取出相应的数据进行匹配
          if (1 == Integer.parseInt(leftsysbol)){

            if (1 == Integer.parseInt(rightsysbol)){

              if ((leftdata.compareTo(elementdata) <= 0) && (elementdata.compareTo(rightdata) <= 0)){

                if (basedatas.compareTo(new BigDecimal("0")) ==0){
                  //直接在单价上扣除或者加上该元素所对应的价格
                  if (IsReduce.equals("1")){

                    resultData = deductions;
                    List li = new ArrayList<>();
                    Map<String,List> map = new HashMap<>();
                    li.add(element);
                    li.add(resultData);
                    map.put(ciId, li);
                    elementResultData.add(map);

                    System.out.println("<= <= <= <= <= <=:"+resultData);
                    break;

                  }else if (IsReduce.equals("0")) {
                    //数据取反
                    resultData = deductions.negate();
                    List li = new ArrayList<>();
                    Map<String,List> map = new HashMap<>();
                    li.add(element);
                    li.add(resultData);
                    map.put(ciId, li);
                    elementResultData.add(map);

                    System.out.println("<= <= <= <= <= <=:"+resultData);
                    break;
                  }else {
                    System.out.println("出现系统错误IsReduce");
                    heTongGongShiflag = 1;
                  }

                }else {

                  if (IsReduce.equals("1")){

                    differenceValue = elementdata.subtract(leftdata);
                    resultData = (differenceValue.divide(basedatas,10,BigDecimal.ROUND_HALF_UP)).multiply(deductions);
                    List li = new ArrayList<>();
                    Map<String,List> map = new HashMap<>();
                    li.add(element);
                    li.add(resultData);
                    map.put(ciId, li);
                    elementResultData.add(map);

                    System.out.println("<= <= <= <= <= <=:"+resultData);
                    break;

                  }else if (IsReduce.equals("0")) {

                    differenceValue = elementdata.subtract(leftdata);
                    resultData = ((differenceValue.divide(basedatas,10,BigDecimal.ROUND_HALF_UP)).multiply(deductions)).negate();
                    List li = new ArrayList<>();
                    Map<String,List> map = new HashMap<>();
                    li.add(element);
                    li.add(resultData);
                    map.put(ciId, li);
                    elementResultData.add(map);

                    System.out.println("<= <= <= <= <= <=:"+resultData);
                    break;

                  }else {
                    System.out.println("出现系统错误IsReduce");
                    heTongGongShiflag = 1;
                  }

                }

              }else {
                System.out.println("元素数据所对应的值不在此区间中出错");
                heTongGongShiflag = 2;
              }

            }else if (0 == Integer.parseInt(rightsysbol)){
              //a<= b <c
              if ((leftdata.compareTo(elementdata) <= 0) && (elementdata.compareTo(rightdata) < 0)){

                if (basedatas.compareTo(new BigDecimal("0")) ==0){
                  //直接在单价上扣除或者加上该元素所对应的价格
                  if (IsReduce.equals("1")){

                    resultData = deductions;
                    List li = new ArrayList<>();
                    Map<String,List> map = new HashMap<>();
                    li.add(element);
                    li.add(resultData);
                    map.put(ciId, li);
                    elementResultData.add(map);

                    System.out.println("<= <= <= < < <:"+resultData);
                    break;

                  }else if (IsReduce.equals("0")) {
                    //数据取反
                    resultData = deductions.negate();
                    List li = new ArrayList<>();
                    Map<String,List> map = new HashMap<>();
                    li.add(element);
                    li.add(resultData);
                    map.put(ciId, li);
                    elementResultData.add(map);

                    System.out.println("<= <= <= <  <  <:"+resultData);
                    break;
                  }else {
                    System.out.println("出现系统错误IsReduce");
                    heTongGongShiflag = 1;
                  }

                }else {

                  if (IsReduce.equals("1")){

                    differenceValue = elementdata.subtract(leftdata);
                    resultData = (differenceValue.divide(basedatas,10,BigDecimal.ROUND_HALF_UP)).multiply(deductions);
                    List li = new ArrayList<>();
                    Map<String,List> map = new HashMap<>();
                    li.add(element);
                    li.add(resultData);
                    map.put(ciId, li);
                    elementResultData.add(map);

                    System.out.println("<= <= <= <  <  < :"+resultData);
                    break;

                  }else if (IsReduce.equals("0")) {

                    differenceValue = elementdata.subtract(leftdata);
                    resultData = ((differenceValue.divide(basedatas,10,BigDecimal.ROUND_HALF_UP)).multiply(deductions)).negate();
                    List li = new ArrayList<>();
                    Map<String,List> map = new HashMap<>();
                    li.add(element);
                    li.add(resultData);
                    map.put(ciId, li);
                    elementResultData.add(map);

                    System.out.println("<= <= <= <  <  < :"+resultData);
                    break;

                  }else {
                    System.out.println("出现系统错误IsReduce");
                    heTongGongShiflag = 1;
                  }

                }

              }else {
                System.out.println("元素数据所对应的值不在此区间中出错");
                heTongGongShiflag = 2;
              }


            }else if (rightsysbol.equals("-100")){
              //说明公式没有右区间
              if ((leftdata.compareTo(elementdata) <= 0)) {

                if (basedatas.compareTo(new BigDecimal("0")) ==0) {
                  //没有基数值说明就要直接扣除
                  if (IsReduce.equals("1")){

                    resultData = deductions;
                    List li = new ArrayList<>();
                    Map<String,List> map = new HashMap<>();
                    li.add(element);
                    li.add(resultData);
                    map.put(ciId, li);
                    elementResultData.add(map);

                    System.out.println("<= <= <= :"+resultData);
                    break;

                  }else if (IsReduce.equals("0")) {
                    //数据取反
                    resultData = deductions.negate();
                    List li = new ArrayList<>();
                    Map<String,List> map = new HashMap<>();
                    li.add(element);
                    li.add(resultData);
                    map.put(ciId, li);
                    elementResultData.add(map);

                    System.out.println("<= <= <= :"+resultData);
                    break;

                  }else {
                    System.out.println("<= <= <=出现系统错误IsReduce");
                    heTongGongShiflag = 1;
                  }

                }else {
                  //有基数值
                  if (IsReduce.equals("1")){

                    differenceValue = elementdata.subtract(leftdata);
                    resultData = (differenceValue.divide(basedatas,10,BigDecimal.ROUND_HALF_UP)).multiply(deductions);
                    List li = new ArrayList<>();
                    Map<String,List> map = new HashMap<>();
                    li.add(element);
                    li.add(resultData);
                    map.put(ciId, li);
                    elementResultData.add(map);

                    System.out.println("<= <= <=  :"+resultData);
                    break;

                  }else if (IsReduce.equals("0")) {

                    differenceValue = elementdata.subtract(leftdata);
                    resultData = ((differenceValue.divide(basedatas,10,BigDecimal.ROUND_HALF_UP)).multiply(deductions)).negate();
                    List li = new ArrayList<>();
                    Map<String,List> map = new HashMap<>();
                    li.add(element);
                    li.add(resultData);
                    map.put(ciId, li);
                    elementResultData.add(map);

                    System.out.println("<= <= <= :"+resultData);
                    break;

                  }else {
                    System.out.println("<= <= <=出现系统错误IsReduce");
                    heTongGongShiflag = 1;
                  }

                }

              }else {
                System.out.println("<= <= <=元素数据所对应的值不在此区间中出错");
                heTongGongShiflag = 2;
              }
            }


          }else if (0 == Integer.parseInt(leftsysbol)){

            if (1 == Integer.parseInt(rightsysbol)){

              if ((leftdata.compareTo(elementdata) == -1) && ((elementdata.compareTo(rightdata) <= 0))) {

                if (basedatas.compareTo(new BigDecimal("0")) ==0) {
                  //没有基数值说明就要直接扣除
                  if (IsReduce.equals("1")){

                    resultData = deductions;
                    List li = new ArrayList<>();
                    Map<String,List> map = new HashMap<>();
                    li.add(element);
                    li.add(resultData);
                    map.put(ciId, li);
                    elementResultData.add(map);

                    System.out.println("<  <  <  <= <= <= :"+resultData);
                    break;

                  }else if (IsReduce.equals("0")) {
                    //数据取反
                    resultData = deductions.negate();
                    List li = new ArrayList<>();
                    Map<String,List> map = new HashMap<>();
                    li.add(element);
                    li.add(resultData);
                    map.put(ciId, li);
                    elementResultData.add(map);

                    System.out.println("<  <  <  <= <= <= :"+resultData);
                    break;
                  }else {
                    System.out.println("<  <  <  <= <= <=出现系统错误IsReduce");
                  }

                }else {
                  //有基数值
                  if (IsReduce.equals("1")){

                    differenceValue = elementdata.subtract(leftdata);
                    resultData = (differenceValue.divide(basedatas,10,BigDecimal.ROUND_HALF_UP)).multiply(deductions);
                    List li = new ArrayList<>();
                    Map<String,List> map = new HashMap<>();
                    li.add(element);
                    li.add(resultData);
                    map.put(ciId, li);
                    elementResultData.add(map);

                    System.out.println("<  <  <  <= <= <= :"+resultData);
                    break;

                  }else if (IsReduce.equals("0")) {

                    differenceValue = elementdata.subtract(leftdata);
                    resultData = ((differenceValue.divide(basedatas,10,BigDecimal.ROUND_HALF_UP)).multiply(deductions)).negate();
                    List li = new ArrayList<>();
                    Map<String,List> map = new HashMap<>();
                    li.add(element);
                    li.add(resultData);
                    map.put(ciId, li);
                    elementResultData.add(map);

                    System.out.println("<  <  <  <= <= <= :"+resultData);
                    break;

                  }else {
                    System.out.println("<  <  <  <= <= <=出现系统错误IsReduce");
                    heTongGongShiflag = 1;
                  }

                }

              }else {
                System.out.println("<  <  <  <=  <=  <=元素数据所对应的值不在此区间中出错");
                heTongGongShiflag = 2;
              }


            }else if (0 == Integer.parseInt(rightsysbol)){

              if ((leftdata.compareTo(elementdata) == -1) && ((elementdata.compareTo(rightdata) == -1))) {

                if (basedatas.compareTo(new BigDecimal("0")) ==0) {
                  //没有基数值说明就要直接扣除
                  if (IsReduce.equals("1")){

                    resultData = deductions;
                    List li = new ArrayList<>();
                    Map<String,List> map = new HashMap<>();
                    li.add(element);
                    li.add(resultData);
                    map.put(ciId, li);
                    elementResultData.add(map);

                    System.out.println("<  <  <  <  <  < :"+resultData);
                    break;

                  }else if (IsReduce.equals("0")) {
                    //数据取反
                    resultData = deductions.negate();
                    List li = new ArrayList<>();
                    Map<String,List> map = new HashMap<>();
                    li.add(element);
                    li.add(resultData);
                    map.put(ciId, li);
                    elementResultData.add(map);

                    System.out.println("<  <  <  <  <  < :"+resultData);
                    break;

                  }else {
                    System.out.println("<  <  <  <= <= <=出现系统错误IsReduce");
                    heTongGongShiflag = 1;
                  }

                }else {
                  //有基数值
                  if (IsReduce.equals("1")){

                    differenceValue = elementdata.subtract(leftdata);
                    resultData = (differenceValue.divide(basedatas,10,BigDecimal.ROUND_HALF_UP)).multiply(deductions);
                    List li = new ArrayList<>();
                    Map<String,List> map = new HashMap<>();
                    li.add(element);
                    li.add(resultData);
                    map.put(ciId, li);
                    elementResultData.add(map);

                    System.out.println("<  <  <  <  <  < :"+resultData);
                    break;

                  }else if (IsReduce.equals("0")) {

                    differenceValue = elementdata.subtract(leftdata);
                    resultData = ((differenceValue.divide(basedatas,10,BigDecimal.ROUND_HALF_UP)).multiply(deductions)).negate();
                    List li = new ArrayList<>();
                    Map<String,List> map = new HashMap<>();
                    li.add(element);
                    li.add(resultData);
                    map.put(ciId, li);
                    elementResultData.add(map);

                    System.out.println("<  <  <  <  <  < :"+resultData);
                    break;

                  }else {
                    System.out.println("<  <  <  <  <  < 出现系统错误IsReduce");
                    heTongGongShiflag = 1;
                  }

                }

              }else {
                System.out.println("<  <  <  <  <  <元素数据所对应的值不在此区间中出错");
                heTongGongShiflag = 2;
              }

            }else if (rightsysbol.equals("-100")){
              //说明公式没有右区间
              if ((leftdata.compareTo(elementdata) == -1)) {

                if (basedatas.compareTo(new BigDecimal("0")) ==0) {
                  //没有基数值说明就要直接扣除
                  if (IsReduce.equals("1")){

                    resultData = deductions;
                    List li = new ArrayList<>();
                    Map<String,List> map = new HashMap<>();
                    li.add(element);
                    li.add(resultData);
                    map.put(ciId, li);
                    elementResultData.add(map);

                    System.out.println("< < < :"+resultData);
                    break;

                  }else if (IsReduce.equals("0")) {
                    //数据取反
                    resultData = deductions.negate();
                    List li = new ArrayList<>();
                    Map<String,List> map = new HashMap<>();
                    li.add(element);
                    li.add(resultData);
                    map.put(ciId, li);
                    elementResultData.add(map);

                    System.out.println("< < < :"+resultData);
                    break;
                  }else {
                    heTongGongShiflag = 2;
                    System.out.println("< < <出现系统错误IsReduce");
                  }

                }else {
                  //有基数值
                  if (IsReduce.equals("1")){

                    differenceValue = elementdata.subtract(leftdata);
                    resultData = (differenceValue.divide(basedatas,10,BigDecimal.ROUND_HALF_UP)).multiply(deductions);
                    List li = new ArrayList<>();
                    Map<String,List> map = new HashMap<>();
                    li.add(element);
                    li.add(resultData);
                    map.put(ciId, li);
                    elementResultData.add(map);

                    System.out.println("< < <  :"+resultData);
                    break;

                  }else if (IsReduce.equals("0")) {

                    differenceValue = elementdata.subtract(leftdata);
                    resultData = ((differenceValue.divide(basedatas,10,BigDecimal.ROUND_HALF_UP)).multiply(deductions)).negate();
                    List li = new ArrayList<>();
                    Map<String,List> map = new HashMap<>();
                    li.add(element);
                    li.add(resultData);
                    map.put(ciId, li);
                    elementResultData.add(map);

                    System.out.println("< < < :"+resultData);
                    break;

                  }else {
                    System.out.println("< < <出现系统错误IsReduce");
                    heTongGongShiflag = 1;
                  }

                }

              }else {
                System.out.println("<  <  < 元素数据所对应的值不在此区间中出错");
                heTongGongShiflag = 2;
              }
            }

          }else if (leftsysbol.equals("-100")){
            //此处说明公式中左区间没有值
            if (1 == Integer.parseInt(rightsysbol)){
              if ((elementdata.compareTo(rightdata) <= 0)) {
                if (basedatas.compareTo(new BigDecimal("0")) ==0) {
                  //没有基数值说明就要直接扣除
                  if (IsReduce.equals("1")){

                    resultData = deductions;
                    List li = new ArrayList<>();
                    Map<String,List> map = new HashMap<>();
                    li.add(element);
                    li.add(resultData);
                    map.put(ciId, li);
                    elementResultData.add(map);

                    System.out.println("<=  <= <= :"+resultData);
                    break;

                  }else if (IsReduce.equals("0")) {
                    //数据取反
                    resultData = deductions.negate();
                    List li = new ArrayList<>();
                    Map<String,List> map = new HashMap<>();
                    li.add(element);
                    li.add(resultData);
                    map.put(ciId, li);
                    elementResultData.add(map);

                    System.out.println("<=  <= <= :"+resultData);
                    break;
                  }else {
                    System.out.println("<=  <= <=出现系统错误IsReduce");
                  }

                }else {
                  //有基数值
                  System.out.println("<= <= <=此处进入左区间没有值右区间有值");

                }
              }else {
                System.out.println("<=  <=  <= 元素数据大于右边区间值出错");
              }

            }else if (0 == Integer.parseInt(rightsysbol)){
              if ((elementdata.compareTo(rightdata) == -1)) {
                if (basedatas.compareTo(new BigDecimal("0")) ==0) {
                  //没有基数值说明就要直接扣除
                  if (IsReduce.equals("1")){

                    resultData = deductions;
                    List li = new ArrayList<>();
                    Map<String,List> map = new HashMap<>();
                    li.add(element);
                    li.add(resultData);
                    map.put(ciId, li);
                    elementResultData.add(map);

                    System.out.println("< < <  :"+resultData);
                    break;

                  }else if (IsReduce.equals("0")) {
                    //数据取反
                    resultData = deductions.negate();
                    List li = new ArrayList<>();
                    Map<String,List> map = new HashMap<>();
                    li.add(element);
                    li.add(resultData);
                    map.put(ciId, li);
                    elementResultData.add(map);

                    System.out.println("< <  < :"+resultData);
                    break;

                  }else {
                    System.out.println("<  <  < 出现系统错误IsReduce");
                    heTongGongShiflag = 1;
                  }

                }else {
                  //有基数值
                  System.out.println("<  <  < 此处进入左区间没有值右区间有值");

                }
              }else {
                System.out.println("<  <  < 元素数据大于右边区间值出错");
                heTongGongShiflag = 2;
              }

            }else {
              System.out.println("公式出现错误");
              heTongGongShiflag = 2;
            }
          }

        }

      }

    }

    //计算每个ID出现的次数后面判断是否将合同中对应所有元素都成功结算
    Map<String,Integer> map = new HashMap();
    for (int i=0;i<contractElements.size();i++) {
      Integer count = map.get(contractElements.get(i).getCiId());
      map.put(contractElements.get(i).getCiId(), (count == null)?1:count+1);
    }
    Map<String,Integer> map2 = new HashMap();
    for (int i = 0;i<elementResultData.size();i++) {
      for (Map.Entry vo : elementResultData.get(i).entrySet()) {
        String key = (String)vo.getKey();
        Integer count = map2.get(key);
        map2.put(key,(count == null)?1:count+1);
      }
    }

    Map<String,Boolean> map3 = new HashMap<>();

    for( Map.Entry<String,Integer> vo : map.entrySet()){
      String key = vo.getKey();
      Integer a = vo.getValue();
      Integer b = map2.get(key);
       // System.out.println("a："+a+"b"+b);
      if ( a == b) {
        map3.put(key, true);
      }else {
        map3.put(key, false);
      }
    }

    System.out.println(map2);
    System.out.println(map);
    System.out.println(map3);

    BigDecimal leftnum = null;
    String leftsysbolNum = null;
    String rightsysbolNum = null;
    BigDecimal rightnum = null;
    BigDecimal basedata = null;
    //计算的最终扣值
    BigDecimal weighingData = null;
    // String isReduce = heTongNumber.get(0).getIsreduce();
    //合同中的斤检数量
    BigDecimal weighing = null;
    //公式中的扣数值
    BigDecimal weigningDeduction = null;

    Map<String,BigDecimal> weighingMap = new HashMap<>();

    for (String key:map3.keySet()) {
      if (map3.get(key)) {
        for (int i=0;i<contractInformations.size() ;i++) {
          if (key.equals(contractInformations.get(i).getId())){
            weighing = contractInformations.get(i).getWeighing();
            weighingMap.put(key, weighing);
          }
        }
      }
    }

    System.out.println(weighingMap);

    Map<String,BigDecimal> map4 = new HashMap();

    for (int i=0;i<contractInformations.size() ;i++) {

      for(int j=0;j<heTongNumber.size();j++){
        weighing = weighingMap.get(contractInformations.get(i).getId());

        if(weighing != null){

          weigningDeduction = new BigDecimal(heTongNumber.get(j).getDeductions());

          //进行异常处理如果有些数据为空的时候
          if (heTongNumber.get(0).getBasedata() == null || heTongNumber.get(0).getBasedata().equals("")) {
            basedata = new BigDecimal("0");
          } else {
            basedata = new BigDecimal(heTongNumber.get(j).getBasedata());
          }


          leftnum = new BigDecimal(heTongNumber.get(j).getLeftnum().toString());

          rightnum = new BigDecimal(heTongNumber.get(j).getRightnum().toString());

          if ( heTongNumber.get(j).getLeftsysbol() == null || heTongNumber.get(j).getLeftsysbol().equals("")) {
            leftsysbolNum = "-100";
            System.out.println("leftsysbolNum:"+leftsysbolNum);
          }else {
            leftsysbolNum = heTongNumber.get(j).getLeftsysbol();
          }

          if (heTongNumber.get(j).getRightsysbol() == null || heTongNumber.get(j).getRightsysbol().equals("")) {
            rightsysbolNum = "-100";
            System.out.println("rightsysbolNum:"+rightsysbolNum);
          }else {
            rightsysbolNum = heTongNumber.get(j).getRightsysbol();
          }

          if (leftsysbolNum.equals("1")){

            if (rightsysbolNum.equals("1")){

              if ((leftnum.compareTo(weighing) <= 0) && (weighing.compareTo(rightnum) <= 0)){

                if (basedata.compareTo(new BigDecimal("0")) ==0){
                  //直接在单价上扣除或者加上该元素所对应的价格
                  weighingData = weighing.subtract(weigningDeduction);
                  map4.put(contractInformations.get(i).getId(),weighingData);
                  System.out.println("<= <= <= <= <= <=:"+weighingData);
                  break;
                }else {
                  weighingData = (((weighing.subtract(leftnum)).divide(basedata,10,BigDecimal.ROUND_HALF_UP)).multiply(weigningDeduction)).negate().add(weighing);
                  map4.put(contractInformations.get(i).getId(),weighingData);
                  System.out.println("<= <= <= <= <= <=:"+weighingData);
                  break;
                }

              } else {
                System.out.println("数量所对应的值不在此区间中出错");
                map4.put(contractInformations.get(i).getId(), null);
                numflag = 2;
              }

            }else if (rightsysbolNum.equals("0")){
              //a<= b <c
              if ((leftnum.compareTo(weighing) <= 0) && (weighing.compareTo(rightnum) < 0)){

                if (basedatas.compareTo(new BigDecimal("0")) ==0){

                  //直接在单价上扣除或者加上该元素所对应的价格
                  weighingData = weighing.subtract(weigningDeduction);
                  map4.put(contractInformations.get(i).getId(),weighingData);
                  System.out.println("<= <= <= <  <  < :"+weighingData);
                  break;

                }else {
                  weighingData = (((weighing.subtract(leftnum)).divide(basedata,10,BigDecimal.ROUND_HALF_UP)).multiply(weigningDeduction)).negate().add(weighing);
                  map4.put(contractInformations.get(i).getId(),weighingData);
                  System.out.println("<= <= <= <= <= <=:"+weighingData);
                  break;
                }

              }else {
                map4.put(contractInformations.get(i).getId(), null);
                System.out.println("数量所对应的值不在此区间中出错");
              }

            }else if (rightsysbol.equals("-100")){
              //说明公式没有右区间
              if ((leftnum.compareTo(weighing) <= 0)) {

                if (basedata.compareTo(new BigDecimal("0")) ==0) {
                  //没有基数值说明就要直接扣除

                  weighingData = weighing.subtract(weigningDeduction);
                  map4.put(contractInformations.get(i).getId(),weighingData);
                  System.out.println("<=  <=  <= :"+weighingData);
                  break;

                }else {
                  //有基数值
                  weighingData = (((weighing.subtract(leftnum)).divide(basedata,10,BigDecimal.ROUND_HALF_UP)).multiply(weigningDeduction)).negate().add(weighing);
                  map4.put(contractInformations.get(i).getId(),weighingData);
                  System.out.println("<= <= <=  :"+weighingData);
                  break;
                }

              } else {
                map4.put(contractInformations.get(i).getId(), null);
                System.out.println("<= <= <=数量所对应的值不在此区间中出错");
                numflag = 2;
              }
            }

          }else if(leftsysbolNum.equals("0")){

            if (rightsysbolNum.equals("1")){

              if ((leftnum.compareTo(weighing) < 0) && (weighing.compareTo(rightnum) <= 0)){

                if (basedata.compareTo(new BigDecimal("0")) ==0){
                  //直接在单价上扣除或者加上该元素所对应的价格
                  weighingData = weighing.subtract(weigningDeduction);
                  map4.put(contractInformations.get(i).getId(),weighingData);
                  System.out.println("< < < <= <= <=:"+weighingData);
                  break;
                }else {
                  weighingData = (((weighing.subtract(leftnum)).divide(basedata,10,BigDecimal.ROUND_HALF_UP)).multiply(weigningDeduction)).negate().add(weighing);
                  map4.put(contractInformations.get(i).getId(),weighingData);
                  System.out.println("<  < <  <= <= <=:"+weighingData);
                  break;
                }

              } else {
                map4.put(contractInformations.get(i).getId(), null);
                System.out.println("数量所对应的值不在此区间中出错");
                numflag = 2;
              }

            }else if (rightsysbolNum.equals("0")){
              //a<= b <c
              if ((leftnum.compareTo(weighing) < 0) && (weighing.compareTo(rightnum) < 0)){

                if (basedata.compareTo(new BigDecimal("0")) ==0){

                  //直接在单价上扣除或者加上该元素所对应的数量
                  weighingData = weighing.subtract(weigningDeduction);
                  map4.put(contractInformations.get(i).getId(),weighingData);
                  System.out.println("< < < <  <  < :"+weighingData);
                  break;

                }else {
                  weighingData = (((weighing.subtract(leftnum)).divide(basedata,10,BigDecimal.ROUND_HALF_UP)).multiply(weigningDeduction)).negate().add(weighing);
                  map4.put(contractInformations.get(i).getId(),weighingData);
                  System.out.println("< < < < < <:"+weighingData);
                  break;
                }

              }else {
                map4.put(contractInformations.get(i).getId(), null);
                System.out.println("数量所对应的值不在此区间中出错");
                numflag = 2;
              }

            }else if (rightsysbol.equals("-100")){
              //说明公式没有右区间
              if ((leftnum.compareTo(weighing) <= 0)) {

                if (basedata.compareTo(new BigDecimal("0")) ==0) {
                  //没有基数值说明就要直接扣除

                  weighingData = weighing.subtract(weigningDeduction);
                  map4.put(contractInformations.get(i).getId(),weighingData);
                  System.out.println("<=  <=  <= :"+weighingData);
                  break;

                }else {
                  //有基数值
                  weighingData = (((weighing.subtract(leftnum)).divide(basedata,10,BigDecimal.ROUND_HALF_UP)).multiply(weigningDeduction)).negate().add(weighing);
                  map4.put(contractInformations.get(i).getId(),weighingData);
                  System.out.println("<= <= <= :"+weighingData);
                  break;
                }

              } else {
                map4.put(contractInformations.get(i).getId(), null);
                System.out.println("<= <= <=数量所对应的值不在此区间中出错");
                numflag = 2;
              }
            }


          }else if(leftsysbolNum.equals("-100")){
            if (rightsysbolNum.equals("1")) {
              if ((weighing.compareTo(rightnum) <= 0)){
                if (basedata.compareTo(new BigDecimal("0")) ==0) {
                  //没有基数值说明就要直接扣除
                  weighingData = weighing.subtract(weigningDeduction);
                  map4.put(contractInformations.get(i).getId(),weighingData);
                  System.out.println("<=  <=   <= :"+weighingData);
                  break;

                }else {
                  //有基数值

                }
              }else {
                map4.put(contractInformations.get(i).getId(), null);
                System.out.println("<= <= <=数量所对应的值不在此区间中出错");
              }

            }else if (rightsysbolNum.equals("0")) {

              if ((weighing.compareTo(rightnum) < 0)){
                if (basedata.compareTo(new BigDecimal("0")) ==0) {
                  //没有基数值说明就要直接扣除
                  weighingData = weighing.subtract(weigningDeduction);
                  map4.put(contractInformations.get(i).getId(),weighingData);
                  System.out.println("<  <   < :"+weighingData);
                  break;

                }else {
                  //有基数值
                }
              }else {
                map4.put(contractInformations.get(i).getId(), null);
                System.out.println("<= <= <=数量所对应的值不在此区间中出错");
              }

            }else {
              map4.put(contractInformations.get(i).getId(), null);
              System.out.println("公式出现错误");
            }

          }else {
            map4.put(contractInformations.get(i).getId(), null);
            System.out.println("公式出现错误");
          }
        }else {
          System.out.print("结算失败！");
          map4.put(contractInformations.get(i).getId(), null);
        }
      }

    }

    System.out.println("map4:"+map4);

    //将所有元素计算后的数据累加
    BigDecimal elementAccountData = new BigDecimal("0");

    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String nowTime = sdf.format(date);//将时间格式转换成符合Timestamp要求的格式.
    Timestamp dates = Timestamp.valueOf(nowTime);//把时间转换

    //获取操作用户的信息
    LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

    //结算单价
    BigDecimal settlemenPrice = null;

    //结算数量
    //BigDecimal settlementQuantity = null;

    //结算结果
    BigDecimal settlementResults = null;

    //税率
    BigDecimal taxRate = null;

    for (String key:map4.keySet()) {

      if (map4.get(key) == null) {
        //结算失败
        shengtieHth.updataContractInformation(dates,null,null,null,null,sysUser.getUsername(),2,key);
        System.out.println("结算失败");
        List<ContractInformation> li2 = shengtieHth.selectContractInformation(key);
        resultInformation.addAll(li2);

      } else {

        List<ContractInformation> li = shengtieHth.selectContractInformation(key);

        for (int i = 0; i < elementResultData.size(); i++) {

          if (elementResultData.get(i).get(key) != null) {
            //System.out.println(elementResultData.get(i).toString());
            shengtieHth.depositElementData(elementResultData.get(i).get(key).get(0).toString(), key, new BigDecimal(elementResultData.get(i).get(key).get(1).toString()));
            elementAccountData = elementAccountData.add(new BigDecimal(elementResultData.get(i).get(key).get(1).toString()));
            //System.out.println("elementAccountData:"+elementAccountData);
          }

        }
        contractPrice = li.get(0).getContractPrice();
        taxRate = li.get(0).getTaxRate();
        settlemenPrice = contractPrice.add(elementAccountData);
        System.out.println("settlemenPrice"+settlemenPrice);
        settlementResults = settlemenPrice.multiply(map4.get(key));
        System.out.println("settlementResults"+settlementResults);

        BigDecimal taxes = (settlementResults.divide(new BigDecimal("1").add(taxRate.divide(new BigDecimal("100"),5,BigDecimal.ROUND_HALF_UP)),10,BigDecimal.ROUND_HALF_UP)).multiply(taxRate.divide(new BigDecimal("100"),5,BigDecimal.ROUND_HALF_UP));

        System.out.println(taxes);
        Integer flag = shengtieHth.updataContractInformation(dates,map4.get(key),settlemenPrice,settlementResults,taxes,sysUser.getUsername(),1,key);
        if (flag == 1) {
          List<ContractInformation> li1 = shengtieHth.selectContractInformation(key);
          resultInformation.addAll(li1);
        }else {
          System.out.println("发生未知错误结算失败");
        }
      }
    }
    return resultInformation;
  }

  @Override
  public List<Map<Object, String>> hejiesuan(String id) {
    return shengtieHth.hejiesuan(id);
  }


}

