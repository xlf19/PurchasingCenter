package org.jeecg.modules.contract.zhijianxinxi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;
import java.util.Map;

public interface ZhiJianXinXiMapper extends BaseMapper<T> {
    //获取生铁质检信息
    IPage<Map<Object, String>> listzjxx(IPage<Map<Object, String>> page, @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("supplier") String supplier);

    //获取精粉富粉球团质检信息
    IPage<Map<Object, String>> listzjjf(IPage<Map<Object, String>> page, @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("supplier") String supplier);

    //精粉和燃料手动添加结算单
    public Map<Object,Object> listzjjfxx(@Param("htbh") String htbh,@Param("voucherno") String voucherno,@Param("receiving") String receiving);

    //获取燃料质检信息
    IPage<Map<Object, String>> listzjrl(IPage<Map<Object, String>> page, @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("supplier") String supplier,@Param("materialName") String materialName);

    //获取辅料质检信息
    IPage<Map<Object, String>> listzjfl(IPage<Map<Object, String>> page, @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("supplier") String supplier,@Param("materialName") String materialName);

    //获取耐材质检信息 型煤
    IPage<Map<Object, String>> listzjncxm(IPage<Map<Object, String>> page, @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("supplier") String supplier,@Param("materialName") String materialName);

    //获取耐材质检信息 其他
    IPage<Map<Object, String>> listzjnc(IPage<Map<Object, String>> page, @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("supplier") String supplier,@Param("materialName") String materialName);

    //获取废钢质检信息
    IPage<Map<Object, String>> listzjfg(IPage<Map<Object, String>> page, @Param("htbh")String htbh,@Param("shdw")String shdw);

}
