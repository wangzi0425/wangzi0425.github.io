package com.bjpowernode.service;

import com.bjpowernode.pojo.Admin;

/**
 * @author wzxstart
 * @create 2022-11-08 22:06
 */
public interface AdminService {
     Admin login(String name, String pwd);
}
