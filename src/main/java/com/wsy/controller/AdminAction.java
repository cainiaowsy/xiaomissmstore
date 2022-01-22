package com.wsy.controller;

import com.wsy.pojo.Admin;
import com.wsy.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin")
public class AdminAction {
    //controller层调用service层
    @Autowired
    AdminService adminService;
    //实现登录判断，并跳转
    @RequestMapping("/login")
    public String login(String name, String pwd, HttpServletRequest request){
        Admin admin = adminService.login(name,pwd);
        if(admin != null){
            request.setAttribute("admin",admin);
            return "main";
        }
        request.setAttribute("errmsg","用户名或密码不正确！");
            return "login";
    }
}
