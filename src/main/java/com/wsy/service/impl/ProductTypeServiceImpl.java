package com.wsy.service.impl;

import com.wsy.mapper.ProductTypeMapper;
import com.wsy.pojo.ProductType;
import com.wsy.pojo.ProductTypeExample;
import com.wsy.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("ProductTypeServiceImpl")
public class ProductTypeServiceImpl implements ProductTypeService {
    @Autowired
    ProductTypeMapper productTypeMapper;
    @Override
    public List<ProductType> getAll() {
        ProductTypeExample example = new ProductTypeExample();
        return  productTypeMapper.selectByExample(example);
    }
}
