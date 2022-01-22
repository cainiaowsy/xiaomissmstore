package com.wsy.controller;
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
    @Autowired
    ProductInfoService productInfoService;
    @RequestMapping("/getAll")
    public String getAll(HttpServletRequest request){
        List<ProductInfo> list = productInfoService.getAll();
        request.setAttribute("list",list);
        return "product";
    }
}
