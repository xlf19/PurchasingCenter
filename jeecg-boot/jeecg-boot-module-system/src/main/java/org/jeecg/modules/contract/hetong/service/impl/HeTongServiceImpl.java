package org.jeecg.modules.contract.hetong.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.modules.contract.hetong.mapper.HeTongMapper;
import org.jeecg.modules.contract.hetong.service.IHeTongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class HeTongServiceImpl extends ServiceImpl<HeTongMapper, T> implements IHeTongService {

    @Autowired
    HeTongMapper ht;

    @DS("multi-datasource1")
    public List<Map<Object, String>> findListHt(String hth) {
        return ht.findListHt(hth);
    }

    @DS("multi-datasource1")
    public List<Map<Object, String>> findshdw() {
        return ht.findshdw();
    }

    @DS("multi-datasource1")
    public Object findOne(String hth) {
        return ht.findOne(hth);
    }

    @DS("multi-datasource1")
    public Map<Object, Object> finOnehtxx(String hth) {
        return ht.finOnehtxx(hth);
    }

    @DS("multi-datasource1")
    public Map<Object, Object> finhtxxjf(String hth) {
        return ht.finhtxxjf(hth);
    }

    @DS("multi-datasource1")
    public List<Map<Object, String>> findListHtwz(String hth) {
        return ht.findListHtwz(hth);
    }

    @DS("multi-datasource1")
    public List<Map<Object, String>> findyllht(String hth) {
        return ht.findyllht(hth);
    }

    @DS("multi-datasource1")
    public List<Map<Object, String>> findwzht(String hth) {
        return ht.findwzht(hth);
    }

    @DS("multi-datasource1")
    public Map<Object, Object> finhtwzxx(String hth) {
        return ht.finhtwzxx(hth);
    }

    @DS("multi-datasource1")
    public Map<Object, Object> finhtyllxx(String hth) {
        return ht.finhtyllxx(hth);
    }

    @DS("multi-datasource1")
    public List<Map<Object, String>> findhtncwz(String hth) {
        return ht.findhtncwz(hth);
    }

    @DS("multi-datasource1")
    public Map<Object, Object> finhtwzxxnc(String hth) {
        return ht.finhtwzxxnc(hth);
    }

    @DS("multi-datasource1")
    public Map<Object, Object> finhtyllxxnc(String hth) {
        return ht.finhtyllxxnc(hth);
    }

    @DS("multi-datasource1")
    public List<Map<Object, String>> findhtncwzname(String hth) {
        return ht.findhtncwzname(hth);
    }

    @DS("multi-datasource1")
    public List<Map<Object, String>> findhtncyllname(String hth) {
        return ht.findhtncyllname(hth);
    }

    @DS("multi-datasource1")
    public String findwznamewz(String hth, String wzname) {
        return ht.findwznamewz(hth, wzname);
    }

    @DS("multi-datasource1")
    public String findwznameyll(String hth, String wzname) {
        return ht.findwznameyll(hth, wzname);
    }

    @DS("multi-datasource1")
    public Map<Object, Object> findhtncwzxx(String hth, String wzcode) {
        return ht.findhtncwzxx(hth, wzcode);
    }

    @DS("multi-datasource1")
    public Map<Object, Object> findhtncyllxx(String hth, String wzcode) {
        return ht.findhtncyllxx(hth, wzcode);
    }

    @DS("multi-datasource1")
    public List<Map<Object, String>> findyllhthj(String hth) {
        return ht.findyllhthj(hth);
    }

    @DS("multi-datasource1")
    public List<Map<Object, String>> findwzhthj(String hth) {
        return ht.findwzhthj(hth);
    }
}
