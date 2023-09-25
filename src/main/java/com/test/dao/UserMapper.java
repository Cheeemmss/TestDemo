package com.test.dao;

import com.test.enties.User;
import org.apache.ibatis.annotations.Param;

/**
 * @Author cheems
 * @Date 2023/9/22 15:27
 */
public interface UserMapper {

    User queryUserByUserName(@Param("username") String username);

    User queryUserByUsernameAndPwd(@Param("username") String username, @Param("password") String encipherPwd);

    int insertUser(User user);
}
