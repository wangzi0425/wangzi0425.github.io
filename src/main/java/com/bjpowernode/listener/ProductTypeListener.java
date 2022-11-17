package com.bjpowernode.listener;

import com.bjpowernode.pojo.ProductType;
import com.bjpowernode.service.ProductTypeService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.jws.WebService;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;

/**
 * @author wzxstart
 * @create 2022-11-10 15:11
 */
@WebListener
public class ProductTypeListener  implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
//手工从spring容器中取出ProductTypeService对象
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext_*.xml");
        ProductTypeService productTypeServiceImpl = (ProductTypeService)context.getBean("ProductTypeServiceImpl");
        List<ProductType> typeList = productTypeServiceImpl.getAll();
//放入全局应用作用域中，供新增页面，修改页面，前台的查询功能提供全部商品类别集合。
       servletContextEvent.getServletContext().setAttribute("typeList",typeList);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
