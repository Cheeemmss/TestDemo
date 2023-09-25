package com.test.enties;

import lombok.Data;

/**
 * @Author cheems
 * @Date 2023/9/22 15:01
 */

@Data
public class User {

    /**
     * Id
     */
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 年龄
     */
    private int age;

}
