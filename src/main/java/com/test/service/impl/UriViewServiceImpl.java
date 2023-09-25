package com.test.service.impl;

import com.test.common.utils.SqlSessionUtil;
import com.test.dao.UriViewsMapper;
import com.test.enties.UriViews;
import com.test.service.UriViewsService;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * @Author cheems
 * @Date 2023/9/23 21:18
 */

public class UriViewServiceImpl implements UriViewsService {

    @Override
    public void logUriViews(String requestURI) {
        SqlSession sqlSession = null;
        try {
            sqlSession = SqlSessionUtil.GetSqlSession();
            UriViewsMapper mapper = sqlSession.getMapper(UriViewsMapper.class);
            UriViews uriViews =  mapper.selectByUri(requestURI);
            int i;
            if(uriViews == null) {
                i = mapper.insertUriView(requestURI);
            }else {
                i = mapper.updateUriView(requestURI);
            }
            if(i <= 0) {
                 sqlSession.rollback();
            }
        } catch (Exception e) {
            e.printStackTrace();
            sqlSession.rollback();
        }finally {
            if(sqlSession != null) {
                sqlSession.commit();
                sqlSession.close();
            }
        }
    }

    @Override
    public List<UriViews> getAllUriViews() {
        SqlSession sqlSession = null;
        try {
            sqlSession = SqlSessionUtil.GetSqlSession();
            UriViewsMapper mapper = sqlSession.getMapper(UriViewsMapper.class);
            List<UriViews> uriViewsList =  mapper.selectAll();
            return uriViewsList;
        } catch (Exception e) {
            e.printStackTrace();
            sqlSession.rollback();
        }finally {
            if(sqlSession != null) {
                sqlSession.commit();
                sqlSession.close();
            }
        }
        return null;
    }
}
