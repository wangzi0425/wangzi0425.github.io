package com.bjpowernode.controller;

import com.bjpowernode.pojo.Admin;
import com.bjpowernode.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wzxstart
 * @create 2022-11-09 10:48
 */
@Controller
@RequestMapping("/admin")
public class AdminAction {
//    切记在所有的界面层，一定会有业务逻辑层的对象
    @Autowired
    AdminService adminService;
//实现登陆判断，并进行相应的跳转
    @RequestMapping("login")
    public String login(String name, String pwd, HttpServletRequest request){
        Admin admin = adminService.login(name,pwd);
        if(admin!=null){
//            登陆成功
            request.setAttribute("admin",admin);
            return "main";
        }else{
            request.setAttribute("errmsg","用户名或密码不正确!");
            return "login";
        }

    }

}
