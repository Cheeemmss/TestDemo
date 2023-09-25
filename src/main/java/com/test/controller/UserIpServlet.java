package com.test.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author cheems
 * @Date 2023/9/23 20:38
 */

@WebServlet("/commService/userIp.do")
public class UserIpServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String clientIp = req.getRemoteAddr();
        resp.getWriter().write("IP: " + clientIp);
    }
}
