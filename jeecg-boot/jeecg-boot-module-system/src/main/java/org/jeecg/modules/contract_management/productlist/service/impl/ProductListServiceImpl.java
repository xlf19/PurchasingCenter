package org.jeecg.modules.contract_management.productlist.service.impl;

import org.jeecg.modules.contract_management.productlist.entity.ProductList;
import org.jeecg.modules.contract_management.productlist.mapper.ProductListMapper;
import org.jeecg.modules.contract_management.productlist.service.IProductListService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 产品列表
 * @Author: jeecg-boot
 * @Date:   2020-12-10
 * @Version: V1.0
 */
@Service
public class ProductListServiceImpl extends ServiceImpl<ProductListMapper, ProductList> implements IProductListService {

}
