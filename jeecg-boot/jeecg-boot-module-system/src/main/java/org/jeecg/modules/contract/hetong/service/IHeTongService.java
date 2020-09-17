package org.jeecg.modules.contract.hetong.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.poi.ss.formula.functions.T;
import java.util.List;
import java.util.Map;

public interface IHeTongService extends IService<T> {
    //获取合同号
    public List<Map<Object, String>> findListHt(String hth);

    //获取收货单位
    public List<Map<Object, String>> findshdw();

    //获取供货单位和物资名称
    public Object findOne(String hth);

    //获取合同信息
    public Map<Object, Object> finOnehtxx(String hth);

    //获取精粉合同信息
    public Map<Object, Object> finhtxxjf(String hth);

    //物资合同号
    public List<Map<Object, String>> findListHtwz(String hth);

    //原炉料合同 获取供货单位、物资名称
    public List<Map<Object,String>> findyllht(String hth);

    //物资合同 获取供货单位、物资名称
    public List<Map<Object,String>> findwzht(String hth);


    //获取辅料合同信息 物资合同
    Map<Object,Object>  finhtwzxx(String hth);

    //获取辅料合同信息  原炉料合同
    Map<Object,Object>  finhtyllxx(String hth);

    //耐材 物资合同号
    public List<Map<Object,String>> findhtncwz(String hth);

    //获取耐材供货单位  物资合同
    Map<Object,Object>  finhtwzxxnc(String hth);


    //获取耐材供货单位  原炉料合同
    Map<Object,Object>  finhtyllxxnc(String hth);

    //耐材 物资名称 物资合同
    public List<Map<Object,String>> findhtncwzname(String hth);

    //耐材 物资名称 原炉料合同
    public List<Map<Object,String>> findhtncyllname(String hth);

    //耐材 物资编码 物资合同
    public  String findwznamewz(String hth, String wzname);

    //耐材 物资编码 原炉料合同
    public  String findwznameyll(String hth,String wzname);

    //获取耐材合同信息 物资合同
    Map<Object,Object>  findhtncwzxx(String hth,String wzcode);

    //获取耐材合同信息 原炉料合同
    Map<Object,Object>  findhtncyllxx(String hth,String wzcode);


    //合金 原炉料合同 获取供货单位
    public List<Map<Object,String>> findyllhthj(String hth);

    //合金 物资合同 获取供货单位
    public List<Map<Object,String>> findwzhthj(String hth);
}
