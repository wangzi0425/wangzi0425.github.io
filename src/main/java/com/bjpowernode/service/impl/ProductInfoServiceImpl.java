package com.bjpowernode.service.impl;

import com.bjpowernode.mapper.ProductInfoMapper;
import com.bjpowernode.pojo.ProductInfo;
import com.bjpowernode.pojo.ProductInfoExample;
import com.bjpowernode.pojo.vo.ProductInfoVo;
import com.bjpowernode.service.ProductInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wzxstart
 * @create 2022-11-09 20:17
 */
@Service
public class ProductInfoServiceImpl  implements ProductInfoService {

//     业务逻辑层一定要有数据访问层的对象
    @Autowired
    ProductInfoMapper productInfoMapper;

    @Override
    public int save(ProductInfo info) {
 return productInfoMapper.insert(info);
    }

    @Override
    public List<ProductInfo> getAll() {

        return productInfoMapper.selectByExample(new ProductInfoExample());
    }


    //    select * from product_info limit 起始记录数=（（当前页数-1）*每页的条数 ）每页的条数
//    分页功能实现
    @Override
    public PageInfo splitPage(int pageNum, int pageSize) {
// 分页插件使用pageHelper工具类完成分页功能
        PageHelper.startPage(pageNum,pageSize);
//        进行PageInfo的封装
//        进行有条件的查询操作，必须要创建ProductInfoExample对象
        ProductInfoExample example = new ProductInfoExample();
//        设置排序.按主键降序排序
        example.setOrderByClause("p_id desc");
//        设置完排序后，取集合，切记一定要在取集合前，一定要PageHelper.startPage(pageNum,pageSize);
        List<ProductInfo> list = productInfoMapper.selectByExample(example);
        PageInfo<ProductInfo> pageInfo =new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public ProductInfo getByID(int pId) {
         return productInfoMapper.selectByPrimaryKey(pId);
    }

    @Override
    public int update(ProductInfo info) {
        return productInfoMapper.updateByPrimaryKey(info);
    }

    @Override
    public int delete(Integer pid) {
      return  productInfoMapper.deleteByPrimaryKey(pid);

    }

    @Override
    public int deleteBatch(String[] ids) {

        return productInfoMapper.deleteBatch(ids);
    }

    @Override
    public List<ProductInfo> selectCondition(ProductInfoVo vo) {

        return productInfoMapper.selectCondition(vo);
    }

    @Override
    public PageInfo<ProductInfo> splitPageVo(ProductInfoVo vo, int pageSize) {
        //取出集合之前，先要设置PageHelper.startPage()属性
        PageHelper.startPage(vo.getPage(),pageSize);
        List<ProductInfo> list = productInfoMapper.selectCondition(vo);

        return new PageInfo<>(list)  ;
    }
}
