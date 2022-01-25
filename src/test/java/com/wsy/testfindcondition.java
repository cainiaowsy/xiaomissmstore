package com.wsy;

import com.wsy.mapper.ProductInfoMapper;
import com.wsy.pojo.ProductInfo;
import com.wsy.vo.ProductInfoVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext_dao.xml","classpath:applicationContext_service.xml"})
public class testfindcondition {
    @Autowired
    ProductInfoMapper productInfoMapper;
    @Test
    public void testfc(){
        ProductInfoVo vo = new ProductInfoVo();
        vo.setPname("3");
        vo.setTypeid(1);
        vo.setLprice(2000);

        List<ProductInfo> list = productInfoMapper.selectCondition(vo);
        list.forEach(productInfo -> System.out.println(productInfo));

    }
}
