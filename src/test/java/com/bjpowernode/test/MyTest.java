package com.bjpowernode.test;

import com.bjpowernode.utils.MD5Util;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wzxstart
 * @create 2022-11-08 22:03
 */
public class MyTest {
    @Test
    public void test(){
        String mi= MD5Util.getMD5("111111");
        System.out.println(mi);
    }
}
