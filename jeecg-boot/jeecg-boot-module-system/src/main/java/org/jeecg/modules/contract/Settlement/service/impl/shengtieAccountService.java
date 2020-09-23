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

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * author: wzs
 * Date:2020/9/159:59
 **/
@Service
public class shengtieAccountService extends ServiceImpl<shengtieAccountMapper, T> implements IshengtieAccountService {


  @Autowired
  shengtieAccountMapper shengtieHth;

  @Override
  public List<Map<Object, String>> findHthList(String hth) {
    System.out.print(hth);
    return shengtieHth.findHthList(hth);
  }

  @Override
  public List<Map<Object, String>> findVoucherNo(String contractNo) {

    return shengtieHth.findVoucherNo(contractNo);
  }

  @Override
  public IPage<List<ContractInformation>> findContractList(IPage<Map<Object, String>> page , String contractNo, String voucherNo) {
    return shengtieHth.findContractList(page,contractNo,voucherNo);
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
  public List<ContractInformation> contractAccount(String[] ci_id) {

    List<SysHetonggongshi> li = null;
    //计算出元素与基准值的差值
    BigDecimal disparityElementData;

    //当元素大于对少时要扣的值
    BigDecimal deductions;

    //计算出每个元素要扣的最终值
    BigDecimal resultData ;

    //将查询到的合同信息表中的元素添加到List集合中返回到controller层
    List<ContractInformation> resultInformation = new ArrayList<>();

    Boolean flag1 = true;
    Boolean flag2 = true;
    Boolean flag3 = true;
    Boolean flag4 = true;

    List<Map<String,BigDecimal>> findElementData = null;

    //此处有不同ID查询结果的集合
    for (String id:ci_id) {
      List<ContractElements> element = shengtieHth.findContractElement(id);

      Map<String,BigDecimal> elementResultData = new HashMap<>();

      List<SysHetonggongshi> hetonggongshis = shengtieHth.findHtGs(element.get(0).getContractNo());


      Map<String,List<SysHetonggongshi>> elementGongShiMap = new HashMap<>();


      for (int i=0;i<hetonggongshis.size();i++) {
        li = new ArrayList();
        li.add(hetonggongshis.get(i));
        elementGongShiMap.put(hetonggongshis.get(i).getElements(),li);

      }
      for(int i=0;i<element.size();i++){
        if(elementGongShiMap.get(element.get(i).getElement()) != null) {

          BigDecimal leftdata = new BigDecimal(elementGongShiMap.get(element.get(i).getElement()).get(0).getLeftnum().toString());
          BigDecimal rightdata = new BigDecimal(elementGongShiMap.get(element.get(i).getElement()).get(0).getRightnum().toString());
          BigDecimal elementdata = element.get(i).getElelmentDate();

          String leftsysbol = elementGongShiMap.get(element.get(i).getElement()).get(0).getLeftsysbol();
          String rightsysbol = elementGongShiMap.get(element.get(i).getElement()).get(0).getRightsysbol();

          BigDecimal IsReduce = new BigDecimal(elementGongShiMap.get(element.get(i).getElement()).get(0).getIsreduce());
          BigDecimal basedatas = elementGongShiMap.get(element.get(i).getElement()).get(0).getBasedata();
          deductions = new BigDecimal(elementGongShiMap.get(element.get(i).getElement()).get(0).getDeductions().toString());

          if(1 == Integer.parseInt(leftsysbol)) {

            if (1 == Integer.parseInt(rightsysbol)) {

              if ((leftdata.compareTo(elementdata) <= 0) && (elementdata.compareTo(rightdata) <= 0)) {

                disparityElementData= elementdata.subtract(leftdata);
                if (IsReduce.compareTo(new BigDecimal("0")) == 0){
                  resultData = new BigDecimal("0").subtract((disparityElementData.divide(basedatas,10,BigDecimal.ROUND_HALF_UP)).multiply(deductions));
                  elementResultData.put(element.get(i).getElement(),resultData);
                  flag1 = true;
                  System.out.println(disparityElementData+"<= <= <= <= <= <=");
                  System.out.println(resultData+"<= <= <= <= <= <=");
                }else if (IsReduce.compareTo(new BigDecimal("1")) == 0) {
                  resultData = (disparityElementData.divide(basedatas,10,BigDecimal.ROUND_HALF_UP)).multiply(deductions);
                  elementResultData.put(element.get(i).getElement(),resultData);
                  flag1 = true;
                  System.out.println(disparityElementData+"<= <= <= <= <= <=");
                  System.out.println(resultData+"<= <= <= <= <= <=");
                }else {
                  System.out.println("出现系统错误");
                }

              }else {
                flag1 = false;
                System.out.println("数据不在此区间中出错");
              }

            }else if (0 == Integer.parseInt(rightsysbol)) {

              if ((leftdata.compareTo(elementdata) <= 0) && (elementdata.compareTo(rightdata) < 0)) {

                disparityElementData= elementdata.subtract(leftdata);
                if (IsReduce.compareTo(new BigDecimal("0")) == 0){
                  resultData = new BigDecimal("0").subtract((disparityElementData.divide(basedatas,10,BigDecimal.ROUND_HALF_UP)).multiply(deductions));
                  elementResultData.put(element.get(i).getElement(),resultData);
                  flag2 = true;
                  System.out.println(disparityElementData+":<= <= <= <  <  < ");
                  System.out.println(resultData+":<= <= <= <  <  < ");
                }else if (IsReduce.compareTo(new BigDecimal("1")) == 0) {
                  resultData = (disparityElementData.divide(basedatas,10,BigDecimal.ROUND_HALF_UP)).multiply(deductions);
                  elementResultData.put(element.get(i).getElement(),resultData);
                  flag2 = true;
                  System.out.println(disparityElementData+":<= <= <= <  <  < ");
                  System.out.println(resultData+":<= <= <= <  <  < ");
                }else {
                  System.out.println("出现系统错误");
                }
              }else{
                flag2 = false;
                System.out.println("数据不在此区间中出错");
              }
            }
          }else if (0 == Integer.parseInt(leftsysbol)) {

            if (1 == Integer.parseInt(rightsysbol)) {

              if ((leftdata.compareTo(elementdata) < 0) && (elementdata.compareTo(rightdata) <= 0)) {

                disparityElementData= elementdata.subtract(leftdata);
                if (IsReduce.compareTo(new BigDecimal("0")) == 0){
                  resultData = new BigDecimal("0").subtract((disparityElementData.divide(basedatas,10,BigDecimal.ROUND_HALF_UP)).multiply(deductions));
                  elementResultData.put(element.get(i).getElement(),resultData);
                  flag3 = true;
                  System.out.println(disparityElementData+":<  <  <  <= <= <=");
                  System.out.println(resultData+":<  <  <  <= <= <=");
                }else if (IsReduce.compareTo(new BigDecimal("1")) == 0) {
                  resultData = (disparityElementData.divide(basedatas,10,BigDecimal.ROUND_HALF_UP)).multiply(deductions);
                  elementResultData.put(element.get(i).getElement(),resultData);
                  flag3 = true;
                  System.out.println(disparityElementData+":<  <  <  <= <= <=");
                  System.out.println(resultData+":<  <  <  <= <= <=");
                }else {
                  System.out.println("出现系统错误");
                }

              }else{
                flag3 = false;
                System.out.println("数据不在此区间中出错");
              }

            }else if (0 == Integer.parseInt(rightsysbol)) {

              if ((leftdata.compareTo(elementdata) < 0) && (elementdata.compareTo(rightdata) < 0)) {

                disparityElementData= elementdata.subtract(leftdata);
                if (IsReduce.compareTo(new BigDecimal("0")) == 0){
                  resultData = new BigDecimal("0").subtract((disparityElementData.divide(basedatas,10,BigDecimal.ROUND_HALF_UP)).multiply(deductions));
                  elementResultData.put(element.get(i).getElement(),resultData);
                  flag4 = true;
                  System.out.println(disparityElementData+":< <  <  <  <  < ");
                  System.out.println(resultData+":<  <  <  <  <  < ");
                }else if (IsReduce.compareTo(new BigDecimal("1")) == 0) {
                  resultData = (disparityElementData.divide(basedatas,10,BigDecimal.ROUND_HALF_UP)).multiply(deductions);
                  elementResultData.put(element.get(i).getElement(),resultData);
                  flag4 = true;
                  System.out.println(disparityElementData+":< <  <  <  <  < ");
                  System.out.println(resultData+":<  <  <  <  <  < ");
                }else {
                  System.out.println("出现系统错误");
                }

              }else{
                flag4 = false;
                System.out.println("数据不在此区间中出错");
              }

            }
          }
        }
      }
      //获取合同数量表中的所有信息
      List<SysNumgongshi> selectNumgongshi = shengtieHth.selectNumgongshi(element.get(0).getContractNo());
      //获取合同信息表中所有信息
      List<ContractInformation> ContractInformationList = shengtieHth.selectContractInformation(id);

      BigDecimal leftnum = new BigDecimal(selectNumgongshi.get(0).getLeftnum().toString());
      String leftsysbol =  selectNumgongshi.get(0).getLeftsysbol();
      String rightsysbol = selectNumgongshi.get(0).getRightsysbol();
      BigDecimal rightnum = new BigDecimal(selectNumgongshi.get(0).getRightnum().toString());
      BigDecimal basedata = new BigDecimal(selectNumgongshi.get(0).getBasedata().toString());

      BigDecimal weighingData = null;
      //String isReduce = selectNumgongshi.get(0).getIsreduce();
      boolean flag = false;

      BigDecimal weighing = ContractInformationList.get(0).getWeighing();
      BigDecimal weigningDeduction = new BigDecimal(selectNumgongshi.get(0).getDeductions().toString());

      //对合同数量值得区间判断
      if(1 == Integer.parseInt(leftsysbol)) {
        if (1 == Integer.parseInt(rightsysbol)) {
          if ((leftnum.compareTo(weighing) <= 0) && (weighing.compareTo(rightnum) <= 0)) {
              weighingData = new BigDecimal("0").subtract(((weighing.subtract(leftnum)).divide(basedata,10,BigDecimal.ROUND_HALF_UP)).multiply(weigningDeduction));
              flag = true;
            System.out.println(weighingData+"weighingData:<= <= <= <= <= <=");
          }else {
            flag = false;
            System.out.println("数量数据不在此区间中出错");
          }
        }else if (0 == Integer.parseInt(rightsysbol)) {
          if ((leftnum.compareTo(weighing) <= 0) && (weighing.compareTo(rightnum) < 0)) {
              weighingData = new BigDecimal("0").subtract(((weighing.subtract(leftnum)).divide(basedata,10,BigDecimal.ROUND_HALF_UP)).multiply(weigningDeduction));
              flag = true;
              System.out.println(weighingData+"weighingData:<= <= <= <  <  < ");
          }else{
            flag = false;
            System.out.println("数量数据不在此区间中出错");
          }
        }
      }else if (0 == Integer.parseInt(leftsysbol)) {
        if (1 == Integer.parseInt(rightsysbol)) {
          if ((leftnum.compareTo(weighing) < 0) && (weighing.compareTo(rightnum) <= 0)) {
              weighingData = new BigDecimal("0").subtract(((weighing.subtract(leftnum)).divide(basedata,10,BigDecimal.ROUND_HALF_UP)).multiply(weigningDeduction));
              flag = true;
            System.out.println(weighingData+"weighingData:<  <  <  <= <= <=");
          } else {
            flag = false;
            System.out.println("数量数据不在此区间中出错");
          }
        } else if (0 == Integer.parseInt(rightsysbol)) {
          if ((leftnum.compareTo(weighing) < 0) && (weighing.compareTo(rightnum) < 0)) {
              weighingData = new BigDecimal("0").subtract(((weighing.subtract(leftnum)).divide(basedata,10,BigDecimal.ROUND_HALF_UP)).multiply(weigningDeduction));
              flag = true;
              System.out.println(weighingData+"weighingData:<  <  <  <  <  <");
          } else {
            flag = false;
            System.out.println("数量数据不在此区间中出错");
          }

        }
      }
      Integer tag1 = null;
      Date date = new Date();
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      String nowTime = sdf.format(date);//将时间格式转换成符合Timestamp要求的格式.
      Timestamp dates = Timestamp.valueOf(nowTime);//把时间转换

      //获取操作用户的信息
      LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

      if(flag&&flag1&&flag2&&flag3&&flag4) {
        BigDecimal contractPrice = new BigDecimal("0");
        for (Object o : elementResultData.keySet()) {
          //将计算好的元素数据存入相应的元素表中
          contractPrice = contractPrice.add(elementResultData.get(o));
          tag1 = shengtieHth.depositElementData(o.toString(), id, elementResultData.get(o));

          System.out.println("tag1:" + tag1);
          System.out.println("key" + "value" + o + elementResultData.get(o));

        }

        contractPrice = ContractInformationList.get(0).getContractPrice().add(contractPrice);
        BigDecimal settlementQuantity = ContractInformationList.get(0).getWeighing().add(weighingData);
        BigDecimal settlementResults =  settlementQuantity.multiply(contractPrice);
        //更新合同信息表中的数据
        Integer tag2 = shengtieHth.updataContractInformation(dates,settlementQuantity,contractPrice,settlementResults,sysUser.getUsername(),1,id);

        if (tag2 == 1) {
          List<ContractInformation> informations = shengtieHth.selectContractInformation(id);
          resultInformation.addAll(informations);
        }
      }else {
        shengtieHth.updataContractInformation(dates,null,null,null,sysUser.getUsername(),2,id);
        List<ContractInformation> informations = shengtieHth.selectContractInformation(id);
        resultInformation.addAll(informations);
      }
    }
    return resultInformation;
  }




}

