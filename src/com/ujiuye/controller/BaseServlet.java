package com.ujiuye.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

public class BaseServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 处理post请求乱码
        req.setCharacterEncoding("UTF-8");
        // 响应内容
        resp.setContentType("text/html;charset=utf-8");
        // 获取请求参数
        String method = req.getParameter("method");
        System.out.println("================" + method + "====================");
        if(method != null && method.trim() != ""){
            // 获取类的类对象
            Class<? extends BaseServlet> clazz = this.getClass();
            try {
                // 通过方法名称来获取方法对象
                Method meth = clazz.getMethod(method, HttpServletRequest.class, HttpServletResponse.class);
                // 方法的调用
                meth.invoke(this, req,resp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("前先指定请求方法的名字" + method);
        }

    }
}
