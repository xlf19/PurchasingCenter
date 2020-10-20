package org.jeecg.modules.contract.Settlement.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jeecg.modules.contract.elements.entity.ContractElements;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * author: wzs
 * Date:2020/10/1516:34
 **/
public class accountSettlementUtil {

  //去除list集合中的重复元素
  public static List removeDuplicate(List list) {
    HashSet hashSet = new HashSet(list) ;
    list.clear();
    list.addAll(hashSet);
    return list;
  }

  //筛选错误信息向前台展示
  public static String comparaToElement(List<String> err,List<String> inforByElement) {

    for (int m=0;m<inforByElement.size();m++) {

      String[] infor =  inforByElement.get(m).split("==");

      for(String st : err) {

        String[] erroElement = st.split(":");
        if (infor[0].equals(erroElement[0])) {
          err.remove(st);
          System.out.println(erroElement[0]+":元素没有问题！" );
          break;
        }
      }

    }
    String information = "";
    for (int i=0 ;i<err.size();i++) {
      information = information+err.get(i);
    }

    return information;
  }

  //提取elementCaculationById中正确计算后的元素数据

  //获取结算日期
  public static Date getSettlementDate() {
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String nowTime = sdf.format(date);//将时间格式转换成符合Timestamp要求的格式.
    Timestamp dates = Timestamp.valueOf(nowTime);//把时间转换
    return dates;
  }

  //更新元素信息
  public static List<ContractElements> updataElement(String cid,List<String> list) {
    List<ContractElements> ces = new ArrayList<>();
    ContractElements ce = null;
    for (int i=0;i<list.size();i++) {
      ce = new ContractElements();
      String[] infor = list.get(i).split("==");
      ce.setCiId(cid);
      ce.setElement(infor[0]);
      ce.setDeductionResult(new BigDecimal(infor[1]));
      ces.add(ce);
    }

    return ces;
  }

  //批量删除时候对ID处理
  public static String[] handleById(String id) {

    JSONObject cid = JSONObject.parseObject(id);
    JSONArray conid = cid.getJSONArray("id");
    String[] ci_id = new String[conid.size()];
    for (int j=0; j<conid.size(); j++) {
      System.out.println(conid.get(j));
      ci_id[j] = (String) conid.get(j);
    }
    return ci_id;
  }

}
