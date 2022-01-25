package com.wsy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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

    /**
     *select * from product_info limit 起始记录数=（（当前页-1）*每页条数），每页几条
     * select * from product_info limit 10 ，5
     * select * from product_info order by p_id desc
     */
    @Override
    public PageInfo splitPage(int pagenum, int pagesize) {
        //分页插件使用PageHelper工具类完成分页设置。必须最先写上！！！
        PageHelper.startPage(pagenum,pagesize);
        //用xxxExample封装查询条件，这里是ProductInfoExample
        ProductInfoExample productInfoExample = new ProductInfoExample();
        //按p_id降序排序。插入时用户体验好
        productInfoExample.setOrderByClause("p_id desc");
        List<ProductInfo> list = productInfoMapper.selectByExample(productInfoExample);
        //将查到的集合封装到pageinfo对象中
        PageInfo<ProductInfo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public int save(ProductInfo productInfo) {
        return productInfoMapper.insert(productInfo);
    }

    @Override
    public ProductInfo getById(int id) {
        return productInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public int update(ProductInfo info) {
        return productInfoMapper.updateByPrimaryKey(info);
    }
    @Override
    public int delete(int id){
        return productInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int deleteBatch(String[] ids) {
        return productInfoMapper.deleteBatch(ids);
    }
}
