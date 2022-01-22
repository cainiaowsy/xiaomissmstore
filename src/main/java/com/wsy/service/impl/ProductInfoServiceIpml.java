package com.wsy.service.impl;

import com.wsy.mapper.ProductInfoMapper;
import com.wsy.pojo.ProductInfo;
import com.wsy.pojo.ProductInfoExample;
import com.wsy.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductInfoServiceIpml implements ProductInfoService {
    //service调用controller
    @Autowired
    ProductInfoMapper productInfoMapper;
    @Override
    public List<ProductInfo> getAll() {
        ProductInfoExample productInfoExample = new ProductInfoExample();
        //因为不需要参数，所以不用productInfoExample.createCriteria().等等
        return productInfoMapper.selectByExample(productInfoExample);
    }
}
