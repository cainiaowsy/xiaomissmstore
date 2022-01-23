package com.wsy.controller;
import com.github.pagehelper.PageInfo;
import com.wsy.pojo.ProductInfo;
import com.wsy.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("prod")
public class ProductInfoAction {
    //controller层调用service层，这里是ProductInfoService
    //每页显示的记录数
    public static final int PAGE_SIZE = 5;
    @Autowired
    ProductInfoService productInfoService;
    @RequestMapping("/getAll")
    public String getAll(HttpServletRequest request){
        List<ProductInfo> list = productInfoService.getAll();
        request.setAttribute("list",list);
        return "product";
    }
    //显示第一页的5个商品
    @RequestMapping("/split")
    public String split(HttpServletRequest request){
        PageInfo info = productInfoService.splitPage(1, PAGE_SIZE);
        request.setAttribute("info",info);
        return "product";
    }
}
