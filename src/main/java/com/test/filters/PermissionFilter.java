package com.test.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author cheems
 * @Date 2023/9/23 19:16
 */
public class PermissionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        Object user = request.getSession().getAttribute("user");
        if(user == null) {
            httpServletResponse.sendRedirect("/user/userLogin.html");
        }else {
            filterChain.doFilter(servletRequest,servletResponse);
        }
    }
}
