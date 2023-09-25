package com.test.common.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Author cheems
 * @Date 2023/9/22 15:29
 */
public class SqlSessionUtil {

    private static SqlSessionFactory factory = null;
    static {
        try {
            InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
            factory = new SqlSessionFactoryBuilder().build(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static SqlSession GetSqlSession() throws IOException {
        SqlSession sqlSession = factory.openSession(false); //关闭自动提交
        return sqlSession;
    }

}
