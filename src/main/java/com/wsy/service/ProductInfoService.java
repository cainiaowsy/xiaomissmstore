package com.wsy.service;

import com.github.pagehelper.PageInfo;
import com.wsy.pojo.ProductInfo;

import java.util.List;

public interface ProductInfoService {
    //显示所有商品，不分页
    List<ProductInfo> getAll();
    //分页显示
    PageInfo splitPage(int pagenum,int pagesize);
    //增加商品
     int save(ProductInfo productInfo);
     //按id查商品
    ProductInfo getById(int id);
    //更新商品
    int update(ProductInfo info);
    //单个商品删除
    int delete(int id);
    int deleteBatch(String[] ids);
}