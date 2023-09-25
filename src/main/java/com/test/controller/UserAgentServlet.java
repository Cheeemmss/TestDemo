package com.test.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author cheems
 * @Date 2023/9/23 20:43
 */

@WebServlet("/commService/userAgent.do")
public class UserAgentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userAgent = req.getHeader("User-Agent");
        resp.getWriter().write("User-Agent: " + userAgent);
    }
}
