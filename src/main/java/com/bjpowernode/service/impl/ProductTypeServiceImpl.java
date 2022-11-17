package com.bjpowernode.service.impl;

import com.bjpowernode.mapper.ProductTypeMapper;
import com.bjpowernode.pojo.ProductInfo;
import com.bjpowernode.pojo.ProductType;
import com.bjpowernode.pojo.ProductTypeExample;
import com.bjpowernode.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wzxstart
 * @create 2022-11-10 15:07
 */
@Service(value = "ProductTypeServiceImpl")
public class ProductTypeServiceImpl implements ProductTypeService {
//    在业务逻辑层，一定要有数据访问层对象
    @Autowired
    ProductTypeMapper productTypeMapper;



    @Override
    public List<ProductType> getAll() {

        return productTypeMapper.selectByExample(new ProductTypeExample());
    }
}
