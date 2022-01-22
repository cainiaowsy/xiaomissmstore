package com.wsy.service.impl;

import com.wsy.mapper.AdminMapper;
import com.wsy.pojo.Admin;
import com.wsy.pojo.AdminExample;
import com.wsy.service.AdminService;
import com.wsy.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceimpl implements AdminService {
    //service调用mapper
    @Autowired
    AdminMapper adminMapper;
    @Override
    public Admin login(String name, String pwd) {
        //根据传入的用户名和密码查询对象
        //如果有条件，则用xxxExample类封装条件，这里是AdminExample
        AdminExample adminExample = new AdminExample();
        //前边固定，后边的andANameEqualTo(name1)就是sql的where name = (name1)
        adminExample.createCriteria().andANameEqualTo(name);
        List<Admin> list = adminMapper.selectByExample(adminExample);
        if(list.size() > 0){
            Admin admin = list.get(0);
            //有用户，则对比密码，注意admin.getaPass()是md5加密后的，而形参中的pwd是用户给的
           String md5pwd = MD5Util.getMD5(pwd);
            if(admin.getaPass().equals(md5pwd)){
                return admin;
            }
        }
        return null;
    }
}
