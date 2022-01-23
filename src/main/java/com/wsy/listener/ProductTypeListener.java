package com.wsy.listener;

import com.wsy.pojo.ProductType;
import com.wsy.service.ProductTypeService;
import com.wsy.service.impl.ProductTypeServiceImpl;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;

@WebListener
public class ProductTypeListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        /**手工从spring容器中取出ProductTypeServiceImpl对象
         * 因为这个监听器和spring启动用的监听器是同一时间启动的，但启动先后随机，所以得手工取出，不能让spring取
        */
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext_*.xml");
        ProductTypeService productTypeService = (ProductTypeService)context.getBean("ProductTypeServiceImpl");
        List<ProductType> typeList = productTypeService.getAll();
        //嵌入全局应用作用域中，让修改页面，新增页面等功能使用
        servletContextEvent.getServletContext().setAttribute("typeList",typeList);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
