package org.jeecg.modules.contract.hetong.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;
import java.util.Map;

public interface HeTongMapper extends BaseMapper<T> {
    //获取合同号
    public List<Map<Object,String>> findListHt(@Param("hth")String hth);

    //获取收货单位
    public List<Map<Object,String>> findshdw();

    //获取废钢/生铁/精粉/燃料供货单位和物资名称
    public Object findOne(@Param("hth")String hth);

    //获取生铁合同信息
    Map<Object,Object>  finOnehtxx(@Param("hth")String hth);

    //获取精粉合同信息
    Map<Object,Object>  finhtxxjf(@Param("hth")String hth);

    //物资合同号
    public List<Map<Object,String>> findListHtwz(@Param("hth")String hth);


    //辅料 原炉料合同 获取供货单位、物资名称
    public List<Map<Object,String>> findyllht(@Param("hth")String hth);

    //辅料 物资合同 获取供货单位、物资名称
    public List<Map<Object,String>> findwzht(@Param("hth")String hth);

    //获取辅料合同信息 物资合同
    Map<Object,Object>  finhtwzxx(@Param("hth")String hth);

    //获取辅料合同信息  原炉料合同
    Map<Object,Object>  finhtyllxx(@Param("hth")String hth);


    //耐材 物资合同号
    public List<Map<Object,String>> findhtncwz(@Param("hth")String hth);


    //获取耐材供货单位  物资合同
    Map<Object,Object>  finhtwzxxnc(@Param("hth")String hth);


    //获取耐材供货单位  原炉料合同
    Map<Object,Object>  finhtyllxxnc(@Param("hth")String hth);

    //耐材 物资名称 物资合同
    public List<Map<Object,String>> findhtncwzname(@Param("hth")String hth);

    //耐材 物资名称 原炉料合同
    public List<Map<Object,String>> findhtncyllname(@Param("hth")String hth);

    //耐材 物资编码 物资合同
    public  String findwznamewz(@Param("hth")String hth,@Param("wzname")String wzname);

    //耐材 物资编码 原炉料合同
    public  String findwznameyll(@Param("hth")String hth,@Param("wzname")String wzname);

    //获取耐材合同信息 物资合同
    Map<Object,Object>  findhtncwzxx(@Param("hth")String hth,@Param("wzcode")String wzcode);

    //获取耐材合同信息 原炉料合同
    Map<Object,Object>  findhtncyllxx(@Param("hth")String hth,@Param("wzcode")String wzcode);


    //合金 原炉料合同 获取供货单位
    public List<Map<Object,String>> findyllhthj(@Param("hth")String hth);

    //合金 物资合同 获取供货单位
    public List<Map<Object,String>> findwzhthj(@Param("hth")String hth);




}
