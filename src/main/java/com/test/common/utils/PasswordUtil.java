package com.test.common.utils;

import cn.hutool.crypto.digest.MD5;

import static com.test.common.response.CommonConstance.PASSWORD_SALT;

/**
 * @Author cheems
 * @Date 2023/9/22 16:07
 */
public class PasswordUtil {

    /**
     * 对密码加密
     * @param unencryptedPwd 未加密的密码
     * @return 加密过后的密码
     */
    public static String encipherPwd(String unencryptedPwd) {
        return MD5.create().digestHex16(unencryptedPwd + PASSWORD_SALT);
    }
}
