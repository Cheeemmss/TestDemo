package com.test.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.test.common.response.Result;
import com.test.enties.User;
import com.test.service.UserService;
import com.test.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;
import static com.test.common.response.CodeConstance.CODE_200;
import static com.test.common.response.CodeConstance.CODE_30002;

/**
 * @Author cheems
 * @Date 2023/9/22 14:20
 */

@WebServlet("/user/login.do")
public class UserLoginServlet extends HttpServlet {

    private  UserService  userService = new UserServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String verification = req.getParameter("verification");
        PrintWriter writer = resp.getWriter();
        if(StrUtil.isBlank(verification)) {
            writer.write(JSON.toJSONString(Result.fail(CODE_30002,"验证码不可为空")));
            return;
        }
        String key = (String)req.getSession().getAttribute(KAPTCHA_SESSION_KEY); //拿session中的验证码(生成的时候会存到session中)
        //删除session域中的验证码(若用户在等待登录的过程当中再次提交,由于这里删除了第二次就取到的是null，后面的判断通不过，也就不会进入到登录流程)
        req.getSession().removeAttribute(KAPTCHA_SESSION_KEY);
        if(StrUtil.isNotBlank(key) && !key.equalsIgnoreCase(verification)) {
            writer.write(JSON.toJSONString(Result.fail(CODE_30002,"验证码错误")));
            return;
        }
        //验证码正确才会进入登录流程
        Result result = userService.login(username,password,req);
        writer.write(JSON.toJSONString(result));
    }

}
