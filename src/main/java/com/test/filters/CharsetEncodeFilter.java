package com.test.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CharsetEncodeFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
         HttpServletRequest request = (HttpServletRequest) servletRequest;
         HttpServletResponse response = (HttpServletResponse) servletResponse;
         if(request.getRequestURI().startsWith("/downloads")) {              //这里针对图片下载得单独设置contentType 不然图片无法下载且乱码
             response.setHeader("Content-Disposition", "attachment");
             response.setContentType("application/octet-stream");
             filterChain.doFilter(request, response);
             return;
         }
         servletRequest.setCharacterEncoding("UTF-8");
         servletResponse.setCharacterEncoding("UTF-8");
         servletResponse.setContentType("text/html;charset=utf-8");
         filterChain.doFilter(servletRequest,servletResponse);
    }

}
