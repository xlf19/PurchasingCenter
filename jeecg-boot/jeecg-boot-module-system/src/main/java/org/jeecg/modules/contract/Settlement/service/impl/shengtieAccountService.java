package org.jeecg.modules.contract.Settlement.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.formula.functions.T;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.contract.Hetonggongshi.entity.SysHetonggongshi;
import org.jeecg.modules.contract.Settlement.entity.ContractNumber;
import org.jeecg.modules.contract.Settlement.entity.countElementNum;
import org.jeecg.modules.contract.Settlement.entity.elementCalculation;
import org.jeecg.modules.contract.Settlement.mapper.shengtieAccountMapper;
import org.jeecg.modules.contract.Settlement.service.IshengtieAccountService;
import org.jeecg.modules.contract.Settlement.util.accountSettlementUtil;
import org.jeecg.modules.contract.SysNumgongshi.entity.SysNumgongshi;
import org.jeecg.modules.contract.contract.entity.ContractInformation;

import org.jeecg.modules.contract.elements.entity.ContractElements;
 import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import java.util.List;
import java.util.Map;


/**
 * author: wzs
 * Date:2020/9/159:59
 **/
@Service
public class shengtieAccountService extends ServiceImpl<shengtieAccountMapper, T> implements IshengtieAccountService {


  @Resource
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
  public Boolean settlementCalculation(String[] ci_id) {
    //通过元素数据匹配元素公式所查询的结果
    List<elementCalculation> li = shengtieHth.elementCalculations(ci_id);

    //元素公式的左边逻辑公式
    String leftFormula = null;
    //元素公式右边逻辑公式
    String rightFormula = null;

    //将字符串类型的逻辑公式转换成布尔类型
    ScriptEngineManager manager = new ScriptEngineManager();
    ScriptEngine engine = manager.getEngineByName("js");

    //统计公式中的元素信息
    Map<String,List<String>> elementInformation = new  HashMap<>();

    //统计每次结算计算每个元素扣值
   // List<Map<String,List<BigDecimal>>>
    Map<String,List<String>> elementCaculationById = new HashMap<>();

    //扣除还是增加的标识
    String IsReduce = null;
    //基数值
    BigDecimal basedatas = null;

    //当元素大于多少时要扣的值
    BigDecimal deductions = null;

    boolean leftFlag = false;
    boolean rightFlag = false;
    //元素数据
    BigDecimal elelmentData = null;

    //计算每次结算中的元素个数
    List<countElementNum> countElementNum = shengtieHth.countElementNum(ci_id);

    //获取操作用户的信息
    LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

    //最后结果存放处
    List<ContractInformation> resultSettlement = null;

    //计算一次结算的合同元素总值
    BigDecimal countContractPrice = null;
    List<String> la = null;
    List<String> la1 =  null;
    //将ID放入Map集合中初始化便于在后面进行计算
    for (int i=0; i<ci_id.length;i++) {
      la = new ArrayList<>();
      la1 = new ArrayList<>();
      elementInformation.put(ci_id[i],la1);
      elementCaculationById.put(ci_id[i],la);
    }

    try {

      for (int i=0;i<li.size();i++){

        //每循环一次后初始化标识状态
        leftFlag = false;
        rightFlag = false;

        IsReduce = li.get(i).getIsreDuce();

        deductions = li.get(i).getDeduction();

        if (li.get(i).getBaseData() == null || li.get(i).getBaseData().equals("")) {
          basedatas = new BigDecimal("0");
        }else {
          basedatas = li.get(i).getBaseData();
        }

        if (li.get(i).getLeftFormula() == null || li.get(i).getLeftFormula().equals("")){
          leftFormula = "0";

        }else {
          leftFormula = li.get(i).getLeftFormula();
          leftFlag = (boolean)engine.eval(leftFormula);

        }
        System.out.println("leftFlag:"+leftFlag+"---leftFormula:"+leftFormula);

        if (li.get(i).getRightFormula() == null || li.get(i).getRightFormula().equals("")){
          rightFormula = "0";
        }else {
          rightFormula = li.get(i).getRightFormula();
          rightFlag = (boolean)engine.eval(rightFormula);
        }
        System.out.println("rightFlag:"+rightFlag+"---rightFormula:"+rightFormula);

        if (li.get(i).getElelmentData() == null || li.get(i).getElelmentData().equals("")) {
          elelmentData = new BigDecimal("0");
        }else {
          elelmentData = li.get(i).getElelmentData();
        }


        if (leftFlag) {

          //左边右边都有
          if(rightFlag) {

            //直接在单价上扣除或者加上该元素所对应的价格
            if (basedatas.compareTo(new BigDecimal("0")) ==0){

              if (IsReduce.equals("1")){
                //增加
                elementCaculationById.get(li.get(i).getCid()).add(li.get(i).getElements()+"=="+deductions);

              }else if (IsReduce.equals("0")) {
                //减少
                elementCaculationById.get(li.get(i).getCid()).add(li.get(i).getElements()+"=="+deductions.negate());

              }else if (IsReduce.equals("2")) {
                //不做增加减少
                elementCaculationById.get(li.get(i).getCid()).add(li.get(i).getElements()+"==0");

              }else if (IsReduce.equals("3")) {
                //拒收
               // elementCaculationById.get(li.get(i).getCid()).add(li.get(i).getElements()+"==false");
                elementInformation.get(li.get(i).getCid()).add(li.get(i).getElements()+":元素含量超出规定要求所以拒收！");
                System.out.println(li.get(i).getElements()+":元素含量超出规定要求所以拒收！");

              }else {
                elementInformation.get(li.get(i).getCid()).add(li.get(i).getElements()+":元素对应是否扣款选项出现错误！");
                System.out.println(li.get(i).getElements()+":元素对应是否扣款选项出现错误！");
              }

            }else{
              //根据基数值来判断
              if (IsReduce.equals("1")){
                //增加
                BigDecimal res = ((elelmentData.subtract(li.get(i).getLeftElementData())).divide(basedatas,4,BigDecimal.ROUND_HALF_UP)).multiply(deductions);
                elementCaculationById.get(li.get(i).getCid()).add(li.get(i).getElements()+"=="+res);

              }else if (IsReduce.equals("0")) {
                //减少
                BigDecimal res = ((elelmentData.subtract(li.get(i).getLeftElementData())).divide(basedatas,4,BigDecimal.ROUND_HALF_UP)).multiply(deductions);
                elementCaculationById.get(li.get(i).getCid()).add(li.get(i).getElements()+"=="+res.negate());

              }else if (IsReduce.equals("2")) {
                //不做增加减少
                elementCaculationById.get(li.get(i).getCid()).add(li.get(i).getElements()+"==0");

              }else if (IsReduce.equals("3")) {
                //拒收
                elementInformation.get(li.get(i).getCid()).add(li.get(i).getElements()+":元素含量超出规定要求所以拒收！");
                System.out.println(li.get(i).getElements()+":元素含量超出规定要求所以拒收！");

              }else {
                elementInformation.get(li.get(i).getCid()).add(li.get(i).getElements()+":元素对应是否扣款选项出现错误！");
                System.out.println(li.get(i).getElements()+":元素对应是否扣款选项出现错误！");
              }


            }

          }else if(!rightFlag && rightFormula.equals("0")) {
            //左边有右边为空
            //直接在单价上扣除或者加上该元素所对应的价格
            if (basedatas.compareTo(new BigDecimal("0")) ==0){

              if (IsReduce.equals("1")){
                //增加
                elementCaculationById.get(li.get(i).getCid()).add(li.get(i).getElements()+"=="+deductions);

              }else if (IsReduce.equals("0")) {
                //减少
                elementCaculationById.get(li.get(i).getCid()).add(li.get(i).getElements()+"=="+deductions.negate());

              }else if (IsReduce.equals("2")) {
                //不做增加减少
                elementCaculationById.get(li.get(i).getCid()).add(li.get(i).getElements()+"==0");

              }else if (IsReduce.equals("3")) {
                //拒收

                elementInformation.get(li.get(i).getCid()).add(li.get(i).getElements()+":元素含量超出合同规定要求所以拒收！");
                System.out.println(li.get(i).getElements()+":元素含量超出规定要求所以拒收！");

              }else {
                elementInformation.get(li.get(i).getCid()).add(li.get(i).getElements()+":元素对应是否扣款选项出现错误！");
                System.out.println(li.get(i).getElements()+":元素对应是否扣款选项出现错误！");
              }


            }else{
              //根据基数值来判断
              if (IsReduce.equals("1")){
                //增加
                BigDecimal res = ((elelmentData.subtract(li.get(i).getLeftElementData())).divide(basedatas,4,BigDecimal.ROUND_HALF_UP)).multiply(deductions);
                elementCaculationById.get(li.get(i).getCid()).add(li.get(i).getElements()+"=="+res);

              }else if (IsReduce.equals("0")) {
                //减少
                BigDecimal res = ((elelmentData.subtract(li.get(i).getLeftElementData())).divide(basedatas,4,BigDecimal.ROUND_HALF_UP)).multiply(deductions);
                elementCaculationById.get(li.get(i).getCid()).add(li.get(i).getElements()+"=="+res.negate());

              }else if (IsReduce.equals("2")) {
                //不做增加减少
                elementCaculationById.get(li.get(i).getCid()).add(li.get(i).getElements()+"==0");

              }else if (IsReduce.equals("3")) {
                //拒收
                elementInformation.get(li.get(i).getCid()).add(li.get(i).getElements()+":元素含量超出规定要求，拒收！");
                System.out.println(li.get(i).getElements()+":元素含量超出规定要求所以拒收！");

              }else {
                elementInformation.get(li.get(i).getCid()).add(li.get(i).getElements()+":元素对应是否扣款选项出现错误！");
                System.out.println(li.get(i).getElements()+":元素对应是否扣款选项出现错误！");
              }


            }
            System.out.println("在元素公式右边为空！");

          }else if (!rightFlag && !rightFormula.equals("0")){
            //右边公式出现错误
            elementInformation.get(li.get(i).getCid()).add(li.get(i).getElements()+":元素数据不在所对应的右边公式区间中");
            System.out.println(li.get(i).getElements()+":元素数据不在所对应的右边公式区间中");

          }else {
            elementInformation.get(li.get(i).getCid()).add(li.get(i).getElements()+":在元素公式判断中出现系统性错误！");
            System.out.println("在元素公式判断中出现系统性错误！");
          }

        }else if (!leftFlag && !leftFormula.equals("0")){
          //左边公式出现错误
          elementInformation.get(li.get(i).getCid()).add(li.get(i).getElements()+":元素数据不在所对应的左边公式区间中");
          System.out.println(li.get(i).getElements()+":元素数据不在所对应的左边公式区间中");

        }else if (leftFormula.equals("0")&&!leftFlag){
          //左边公式为空
          System.out.println("在元素公式左边为空！");
          if(rightFlag) {
            //只有右边有此时直接扣除元素值
            if (basedatas.compareTo(new BigDecimal("0")) ==0){

              if (IsReduce.equals("1")){
                //增加
                elementCaculationById.get(li.get(i).getCid()).add(li.get(i).getElements()+"=="+deductions);

              }else if (IsReduce.equals("0")) {
                //减少
                elementCaculationById.get(li.get(i).getCid()).add(li.get(i).getElements()+"=="+deductions.negate());

              }else if (IsReduce.equals("2")) {
                //不做增加减少
                elementCaculationById.get(li.get(i).getCid()).add(li.get(i).getElements()+"==0");

              }else if (IsReduce.equals("3")) {
                //拒收
                elementInformation.get(li.get(i).getCid()).add(li.get(i).getElements()+":元素含量超出规定要求所以拒收！");
                System.out.println(li.get(i).getElements()+":元素含量超出规定要求所以拒收！");

              }else {
                elementInformation.get(li.get(i).getCid()).add(li.get(i).getElements()+":元素对应是否扣款选项出现错误！");
                System.out.println(li.get(i).getElements()+":元素对应是否扣款选项出现错误！！");
              }

            }else{
              System.out.println(li.get(i).getElements()+":没有这种状况");
            }

          }else if(!rightFlag && rightFormula.equals("0")) {
            //左右都为空
            elementInformation.get(li.get(i).getCid()).add(li.get(i).getElements()+":元素公式不存在！");
            System.out.println(li.get(i).getElements()+":元素公式不存在！");

          }else if (!rightFlag && !rightFormula.equals("0")){
            //右边公式出现错误
            elementInformation.get(li.get(i).getCid()).add(li.get(i).getElements()+":元素数据不在所对应的右边公式区间中");
            System.out.println(li.get(i).getElements()+":元素数据不在所对应的右边公式区间中");

          }else {
            elementInformation.get(li.get(i).getCid()).add(li.get(i).getElements()+":元素公式判断中出现系统性错误！");
            System.out.println("在元素公式判断中出现系统性错误！");
          }

        }else {
          elementInformation.get(li.get(i).getCid()).add(li.get(i).getElements()+":元素公式判断中出现系统性错误！");
          System.out.println("在元素公式判断中出现系统性错误！");
        }
      }
      System.out.println(countElementNum.toString());
      System.out.println("elementCaculationById"+elementCaculationById.toString());
      System.out.println("elementInformation"+elementInformation.toString());

      //将结算信息放入便于后面计算
      List<ContractInformation> informationList = new ArrayList<>();

      ContractInformation contracts = null;

      //将上面计算正确的元素对应合同唯一ID放入其中
      List<String> contractNumber = new ArrayList<>();

      for (int i=0;i<countElementNum.size();i++) {

        if(countElementNum.get(i).getElementNum().equals(elementCaculationById.get(countElementNum.get(i).getCid()).size())){

          int num = elementCaculationById.get(countElementNum.get(i).getCid()).size();
          countContractPrice = new BigDecimal("0");
          for (int j=0;j<num;j++) {

            String[] str = elementCaculationById.get(countElementNum.get(i).getCid()).get(j).split("==");

            BigDecimal eData = new BigDecimal(str[1]);
            System.out.println(eData);
            if(eData.compareTo(BigDecimal.ZERO) == 0){
               System.out.println("为0！！！！！！！！！");
               countContractPrice = countContractPrice.add(new BigDecimal("0"));
            }else {
               countContractPrice = countContractPrice.add(eData);
            }
          }

          //这是元素未出现错误时候的每个元素的叠加
          contractNumber.add(countElementNum.get(i).getCid());
          contracts = new ContractInformation();
          contracts.setSettlemenPrice(countContractPrice);
          contracts.setSettlementIdentification(1);
          contracts.setId(countElementNum.get(i).getCid());
          informationList.add(contracts);
          System.out.println("计算成功！"+contracts.toString());

        }else {
          contracts = new ContractInformation();
          contracts.setId(countElementNum.get(i).getCid());
          //将错误信息中的重复元素除去
          List<String> err = accountSettlementUtil.removeDuplicate(elementInformation.get(countElementNum.get(i).getCid()));

          List<String> inforByElement = elementCaculationById.get((countElementNum.get(i).getCid()));

          String erroInfomation = accountSettlementUtil.comparaToElement(err, inforByElement);

          //存放到list集合中
          contracts.setSettlementNotes(erroInfomation);
          contracts.setSettlementIdentification(2);

          informationList.add(contracts);

          System.out.println("inforByElement！"+inforByElement.toString());
          System.out.println("计算失败！"+erroInfomation);
          System.out.println("计算失败！"+contracts.toString());
        }

      }
      //结算数量
      BigDecimal settlementQuantity = null;

      //合同斤检时的重量
      BigDecimal contractWeight = null;

      //基数
      BigDecimal baseDataNum = null;

      //左值
      BigDecimal leftNumber =null;

      //扣除的数量
      BigDecimal numberDeductions = null;

      //左边匹配的公式
      String numLeftFormula;

      //右边匹配的公式
      String numRightFormula;

      //统计数量公式中出现错误的信息
      Map<String,List<String>> numberErroInformation = new  HashMap<>();
      //统计成功每次结算的正确数量
      Map<String,BigDecimal> numberCount = new HashMap<>();
      List<ContractNumber> conNumber = null;



      List<String> la2 =  null;
      for (int a= 0;a<contractNumber.size();a++) {
        la2 = new ArrayList<>();
        numberErroInformation.put(contractNumber.get(a), la2);
        numberCount.put(contractNumber.get(a), new BigDecimal("0"));
      }

      System.out.println("informationList！"+informationList.toString());

      //contractNumberd进行判断是否为空
      if (contractNumber.size() != 0){

        conNumber = shengtieHth.cotractNumberById(contractNumber);

        for (int i=0;i<conNumber.size();i++) {

          //每循环一次后初始化标识状态
          leftFlag = false;
          rightFlag = false;

          if(conNumber.get(i) != null){

            leftNumber = conNumber.get(i).getLeftNumber();

            if (conNumber.get(i).getContractWeight() != null && !conNumber.get(i).getContractWeight().equals("")) {
              contractWeight = conNumber.get(i).getContractWeight();
            }else {
              contractWeight = new BigDecimal("0");
            }
            if (conNumber.get(i).getBaseData() != null && !conNumber.get(i).getBaseData().equals("")) {
              baseDataNum = conNumber.get(i).getBaseData();
            }else {
              baseDataNum = new BigDecimal("0");
            }
            if (conNumber.get(i).getNumberDeductions()!= null &&  !conNumber.get(i).getNumberDeductions().equals("")) {
              numberDeductions = conNumber.get(i).getNumberDeductions();
            }else {
              numberDeductions = new BigDecimal("0");
            }

            if (conNumber.get(i).getLeftFormula() != null &&  !conNumber.get(i).getLeftFormula().equals("")) {
              numLeftFormula = conNumber.get(i).getLeftFormula();
              leftFlag = (boolean)engine.eval(numLeftFormula);
            }else {
              numLeftFormula = "0";
            }

            if (conNumber.get(i).getRightFormula()!= null &&  !conNumber.get(i).getRightFormula().equals("")) {
              numRightFormula = conNumber.get(i).getRightFormula();
              rightFlag = (boolean)engine.eval(numRightFormula);
            }else {
              numRightFormula = "0";
            }


            if (leftFlag) {
              //左边右边都有
              if (rightFlag) {

                if(baseDataNum.compareTo(new BigDecimal("0")) ==0) {
                  //直接在数量上扣除该合同所对应的数量
                  contractWeight = contractWeight.subtract(numberDeductions);
                  numberCount.put(conNumber.get(i).getCid(), contractWeight);

                }else {
                  //根据基数值来判断
                  BigDecimal res = ((contractWeight.subtract(leftNumber)).divide(baseDataNum,4,BigDecimal.ROUND_HALF_UP)).multiply(numberDeductions);
                  contractWeight = contractWeight.subtract(res);
                  numberCount.put(conNumber.get(i).getCid(), contractWeight);
                }

              }else if (!rightFlag && numRightFormula.equals("0")) {
                //左边有右边为空
                if(baseDataNum.compareTo(new BigDecimal("0")) ==0) {
                  //直接在数量上扣除该合同所对应的数量
                  contractWeight = contractWeight.subtract(numberDeductions);
                  numberCount.put(conNumber.get(i).getCid(), contractWeight);

                }else {
                  //根据基数值来判断
                  BigDecimal res = ((contractWeight.subtract(leftNumber)).divide(baseDataNum,4,BigDecimal.ROUND_HALF_UP)).multiply(numberDeductions);
                  contractWeight = contractWeight.subtract(res);

                  numberCount.put(conNumber.get(i).getCid(), contractWeight);
                }

              }else if (!rightFlag && !numRightFormula.equals("0")){
                ////右边公式出现错误
                numberErroInformation.get(conNumber.get(i).getCid()).add("合同"+conNumber.get(i).getContractNo()+":对应的数量不在所对应的右边公式区间中");
                System.out.println("合同"+conNumber.get(i).getContractNo()+":对应的数量不在所对应的右边公式区间中");
              }else {
                numberErroInformation.get(conNumber.get(i).getCid()).add("合同"+conNumber.get(i).getContractNo()+":对应的数量公式判断中出现系统性错误！");
                System.out.println("公式判断中出现系统性错误！");
              }

            }else if (!leftFlag && !numLeftFormula.equals("0")) {
              //左边公式出现错误
              numberErroInformation.get(conNumber.get(i).getCid()).add("合同"+conNumber.get(i).getContractNo()+":对应的数量不在所对应的左边公式区间中");
              System.out.println("合同"+conNumber.get(i).getContractNo()+":对应的数量不在所对应的左边公式区间中");

            }else if (!leftFlag && numLeftFormula.equals("0")) {
              //左边公式为空
              if(rightFlag) {
                if(baseDataNum.compareTo(new BigDecimal("0")) ==0) {
                  contractWeight = contractWeight.subtract(numberDeductions);
                }else {
                  //根据基数值来判断
                  numberErroInformation.get(conNumber.get(i).getCid()).add("合同"+conNumber.get(i).getContractNo()+":没有这种状况");
                  System.out.println("合同"+conNumber.get(i).getContractNo()+":没有这种状况");
                }
              }else if(!rightFlag && numRightFormula.equals("0")) {
                numberErroInformation.get(conNumber.get(i).getCid()).add("合同"+conNumber.get(i).getContractNo()+":元素公式不存在！");
                System.out.println("合同"+conNumber.get(i).getContractNo()+":元素公式不存在！");

              }else if (!rightFlag && !numRightFormula.equals("0")){
                //右边公式出现错误
                numberErroInformation.get(conNumber.get(i).getCid()).add("合同"+conNumber.get(i).getContractNo()+":对应的数量不在所对应的右边公式区间中");

                System.out.println("合同"+conNumber.get(i).getContractNo()+":对应的数量不在所对应的右边公式区间中");

              }else {
                numberErroInformation.get(conNumber.get(i).getCid()).add("合同"+conNumber.get(i).getContractNo()+":对应的数量公式判断中出现系统性错误！");

                System.out.println("公式判断中出现系统性错误！");
              }

            }else {
              numberErroInformation.get(conNumber.get(i).getCid()).add("合同"+conNumber.get(i).getContractNo()+":对应的数量公式判断中出现系统性错误！");
              System.out.println("在数量公式判断中出现系统性错误！");
            }

          }else{
            numberErroInformation.get(conNumber.get(i).getCid()).add("合同"+conNumber.get(i).getContractNo()+":请完善合同数量公式！");
            System.out.println("请完善合同数量公式");
          }

        }
      }else {
        System.out.println("没有合同的唯一ID呀老铁");
        for (int q= 0;q<informationList.size();q++) {
          informationList.get(q).setSettlementDate(accountSettlementUtil.getSettlementDate());
          informationList.get(q).setClearingHouse(sysUser.getUsername());
          informationList.get(q).setSettlementIdentification(2);
        }

      }

      Integer flag = 0;
      for(int i=0;i<informationList.size();i++){

        if(informationList.get(i).getSettlementIdentification().equals(1)){
          //进行合同中材料的数量判断
          if (numberCount.get(informationList.get(i).getId()).compareTo(new BigDecimal("0"))!=0) {
            List<ContractInformation> con = shengtieHth.selectContractInformation(informationList.get(i).getId());
            BigDecimal contractPrice = con.get(0).getContractPrice();
            BigDecimal result = contractPrice.add(informationList.get(i).getSettlemenPrice());
            BigDecimal settlementResults = numberCount.get(informationList.get(i).getId()).multiply(result);
            BigDecimal taxes = (settlementResults.divide(new BigDecimal("1").add(con.get(0).getTaxRate().divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP)),4,BigDecimal.ROUND_HALF_UP)).multiply(con.get(0).getTaxRate().divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP));
            //更新元素表中的数据
            List<ContractElements> ces = accountSettlementUtil.updataElement(informationList.get(i).getId(),elementCaculationById.get(informationList.get(i).getId()));
            //存入计算好的元素数据
            flag = shengtieHth.depositElementData(ces);
            //贷款金额
            BigDecimal loan = settlementResults.divide(new BigDecimal("1").add(con.get(0).getTaxRate().divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP)),4,BigDecimal.ROUND_HALF_UP);
            informationList.get(i).setLoan(loan);
            //结算单价
            informationList.get(i).setSettlemenPrice(result);
            //结算日期
            informationList.get(i).setSettlementDate(accountSettlementUtil.getSettlementDate());
            //结算数量
            informationList.get(i).setSettlementQuantity(numberCount.get(informationList.get(i).getId()));
            //结算结果
            informationList.get(i).setSettlementResults(settlementResults);
            //结算人
            informationList.get(i).setClearingHouse(sysUser.getRealname());
            //结算标识
            informationList.get(i).setSettlementIdentification(1);
            //结算税金
            informationList.get(i).setTaxes(taxes);

            System.out.println("进行最终计算！flag"+flag);
          }else {

            informationList.get(i).setSettlementDate(accountSettlementUtil.getSettlementDate());
            informationList.get(i).setSettlementNotes("数量公式出现错误！请维护！");
            informationList.get(i).setSettlementIdentification(2);
            System.out.println("最终计算失败！");
          }

        }else{
           //结算失败
          informationList.get(i).setClearingHouse(sysUser.getUsername());//结算失败
          informationList.get(i).setClearingHouse(sysUser.getRealname());
          informationList.get(i).setSettlementDate(accountSettlementUtil.getSettlementDate());
          informationList.get(i).setSettlementIdentification(2);
        }
      }

      //更新合同表中数据信息
      Integer tag = shengtieHth.updataContractInformation(informationList);
      if (flag == 1 && tag == 1) {
         return true;
      }


    }catch (ScriptException e) {
      e.printStackTrace();
    }
    System.out.println("result"+resultSettlement);
    return false;
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
      if ( a.equals(b)) {
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
       // shengtieHth.updataContractInformation(dates,null,null,null,null,sysUser.getUsername(),2,key);
        System.out.println("结算失败");
        List<ContractInformation> li2 = shengtieHth.selectContractInformation(key);
        resultInformation.addAll(li2);

      } else {

        List<ContractInformation> li = shengtieHth.selectContractInformation(key);

        for (int i = 0; i < elementResultData.size(); i++) {

          if (elementResultData.get(i).get(key) != null) {
            //System.out.println(elementResultData.get(i).toString());
            //shengtieHth.depositElementData(elementResultData.get(i).get(key).get(0).toString(), key, new BigDecimal(elementResultData.get(i).get(key).get(1).toString()));
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
        /**
        Integer flag = shengtieHth.updataContractInformation(dates,map4.get(key),settlemenPrice,settlementResults,taxes,sysUser.getUsername(),1,key);
        if (flag == 1) {
          List<ContractInformation> li1 = shengtieHth.selectContractInformation(key);
          resultInformation.addAll(li1);
        }else {
          System.out.println("发生未知错误结算失败");
        }**/
      }
    }
    return resultInformation;
  }

  @Override
  public List<Map<Object, String>> hejiesuan(String id) {
    return shengtieHth.hejiesuan(id);
  }

  //查询合同信息
  @Override
  public List<ContractInformation> selectConstractList (String[] ids) {
    return shengtieHth.findContractInformations(ids);
  }

  //<!--删除合同信息表中的结算信息假删除
  @Override
  @Transactional
  public Boolean deleteAccount(String[] ids) {
    Integer flag = shengtieHth.deleteAccount(ids);
    System.out.println(flag);
    if (flag == 1){
      return true;
    }

    return false;
  }
}

