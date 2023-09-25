package com.test.dao;

import com.test.enties.UriViews;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author cheems
 * @Date 2023/9/23 21:04
 */
public interface UriViewsMapper {

    int updateUriView(@Param("uri") String requestURI);

    UriViews selectByUri(@Param("uri") String requestURI);

    int insertUriView(@Param("uri")String requestURI);

    List<UriViews> selectAll();
}
