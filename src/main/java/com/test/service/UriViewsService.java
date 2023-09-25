package com.test.service;

import com.test.enties.UriViews;

import java.util.List;

/**
 * @Author cheems
 * @Date 2023/9/23 21:17
 */
public interface UriViewsService {

    void logUriViews(String requestURI);

    List<UriViews> getAllUriViews();
}
