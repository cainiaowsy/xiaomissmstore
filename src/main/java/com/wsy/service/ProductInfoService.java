package com.wsy.service;

import com.github.pagehelper.PageInfo;
import com.wsy.pojo.ProductInfo;

import java.util.List;

public interface ProductInfoService {
    //显示所有商品，不分页
    List<ProductInfo> getAll();
    //分页显示
    PageInfo splitPage(int pagenum,int pagesize);
}