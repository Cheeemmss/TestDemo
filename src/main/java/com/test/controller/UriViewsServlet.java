package com.test.controller;

import com.alibaba.fastjson.JSON;
import com.test.enties.UriViews;
import com.test.service.UriViewsService;
import com.test.service.impl.UriViewServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @Author cheems
 * @Date 2023/9/23 21:33
 */
@WebServlet("/commService/uirViews.do")
public class UriViewsServlet extends HttpServlet {

    private UriViewsService uriViewsService = new UriViewServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<UriViews> uriViewsList =  uriViewsService.getAllUriViews();
        PrintWriter writer = resp.getWriter();
        writer.write(JSON.toJSONString(uriViewsList));
    }
}
