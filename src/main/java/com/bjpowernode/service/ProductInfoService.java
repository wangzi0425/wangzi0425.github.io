package com.bjpowernode.service;

import com.bjpowernode.pojo.ProductInfo;
import com.bjpowernode.pojo.vo.ProductInfoVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author wzxstart
 * @create 2022-11-09 20:16
 */
public interface ProductInfoService {
    //增加商品
     int save(ProductInfo info) ;

//    显示全部商品，不分页

    List<ProductInfo> getAll();
//    select * from product_info limit 起始记录数=（（当前页数-1）*每页的条数 ）每页的条数
//    分页功能实现
    PageInfo splitPage(int pageNum,int pageSize);
    //按主键id查询商品
    ProductInfo getByID(int pId);
  //更新商品
    int update(ProductInfo info);
     //单个商品删除
    int delete(Integer pid);
    //批量删除商品
    int deleteBatch(String [] ids);
    List<ProductInfo> selectCondition(ProductInfoVo vo);
    //多条件查询分页
    public PageInfo splitPageVo(ProductInfoVo vo,int pageSize);
}
