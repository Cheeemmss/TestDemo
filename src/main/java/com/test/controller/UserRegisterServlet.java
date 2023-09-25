package com.test.controller;

import com.alibaba.fastjson.JSON;
import com.test.common.response.Result;
import com.test.service.UserService;
import com.test.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author cheems
 * @Date 2023/9/22 20:19
 */
@WebServlet("/user/register.do")
public class UserRegisterServlet extends HttpServlet {

    private UserService userService = new UserServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        Result result =  userService.userRegister(username,password);
        PrintWriter writer = resp.getWriter();
        writer.write(JSON.toJSONString(result));
    }
}
