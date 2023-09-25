package com.test.filters;

import com.test.common.utils.SqlSessionUtil;
import com.test.dao.UriViewsMapper;
import com.test.dao.UserMapper;
import com.test.enties.User;
import com.test.service.UriViewsService;
import com.test.service.impl.UriViewServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author cheems
 * @Date 2023/9/23 2:58
 */
public class CallCountFilter implements Filter {

    private UriViewsService uriViewsService = new UriViewServiceImpl();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestURI = request.getRequestURI();
        //TODO
        uriViewsService.logUriViews(requestURI);
        filterChain.doFilter(request,response);
    }
}
