package com.bjpowernode.service;

import com.bjpowernode.pojo.ProductInfo;
import com.bjpowernode.pojo.ProductType;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author wzxstart
 * @create 2022-11-10 15:05
 */
public interface ProductTypeService {




    List<ProductType> getAll();
}
