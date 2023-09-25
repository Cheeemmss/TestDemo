package com.test.service;

import com.test.common.response.Result;
import com.test.enties.User;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author cheems
 * @Date 2023/9/22 15:24
 */
public interface UserService {

    /**
     * 根据用户名获取用户
     * @param username 用户名
     * @return
     */
    User getUserByUsername(String username);


    /**
     * 用户登录
     * @param username 账号
     * @param password 密码
     * @return
     */
    Result login(String username, String password, HttpServletRequest req);

    /**
     * 用户注册
     * @param username 账号
     * @param password 密码
     * @return
     */
    Result userRegister(String username, String password);

}
