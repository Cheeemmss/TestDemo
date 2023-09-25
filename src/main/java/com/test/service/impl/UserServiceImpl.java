package com.test.service.impl;

import cn.hutool.core.util.StrUtil;
import com.test.common.response.Result;
import com.test.common.utils.PasswordUtil;
import com.test.common.utils.SqlSessionUtil;
import com.test.dao.UserMapper;
import com.test.enties.User;
import com.test.service.UserService;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static com.test.common.response.CodeConstance.CODE_30002;
import static com.test.common.response.CodeConstance.CODE_30005;

/**
 * @Author cheems
 * @Date 2023/9/22 15:25
 */

public class UserServiceImpl implements UserService {

    @Override
    public User getUserByUsername(String username) {
        SqlSession sqlSession = null;
        try {
            sqlSession = SqlSessionUtil.GetSqlSession();
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            User user = userMapper.queryUserByUserName(username);
            return user;
        } catch (Exception e) {
            sqlSession.rollback();
            e.printStackTrace();
        }finally {
            if(sqlSession != null) {
                sqlSession.commit();
                sqlSession.close();
            }
        }
        return null;
    }

    @Override
    public Result login(String username, String password, HttpServletRequest req) {
        SqlSession sqlSession = null;
        try {
            sqlSession = SqlSessionUtil.GetSqlSession();
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            if(StrUtil.isBlank(username)) {
                return Result.fail(CODE_30002, "用户名不可以为空");
            }
            if(StrUtil.isBlank(password)) {
                return Result.fail(CODE_30002, "密码不可以为空");
            }

            User user = null;
            user =  getUserByUsername(username);
            if(user == null) {
                return Result.fail(CODE_30005, "用户不存在");
            }
            String encipherPwd = PasswordUtil.encipherPwd(password);
            user = userMapper.queryUserByUsernameAndPwd(username,encipherPwd);
            if(user == null) {
                return Result.fail(CODE_30002, "密码错误");
            }
            //登录成功
            req.getSession().setAttribute("user",user);
            return Result.success("登录成功");
        } catch (Exception e) {
            sqlSession.rollback();
            e.printStackTrace();
        }finally {
            if(sqlSession != null) {
                sqlSession.commit();
                sqlSession.close();
            }
        }
        return null;
    }

    @Override
    public Result userRegister(String username, String password) {
        SqlSession sqlSession = null;
        try {
            sqlSession = SqlSessionUtil.GetSqlSession();
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            if(StrUtil.isBlank(username)) {
                return Result.fail(CODE_30002, "用户名不可以为空");
            }
            if(StrUtil.isBlank(password)) {
                return Result.fail(CODE_30002, "密码不可以为空");
            }

            User user = null;
            user =  getUserByUsername(username);
            if(user != null) {
                return Result.fail(CODE_30005, "用户已存在");
            }
            //注册成功
            user = new User();
            user.setUsername(username);
            user.setPassword(PasswordUtil.encipherPwd(password));
            int i =  userMapper.insertUser(user);
            if(i <= 0) {
                sqlSession.rollback();
            }
            return Result.success("注册成功");
        } catch (Exception e) {
            sqlSession.rollback();
            e.printStackTrace();
        }finally {
            if(sqlSession != null) {
                sqlSession.commit();
                sqlSession.close();
            }
        }
        return null;
    }

}
