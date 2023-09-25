package com.test.enties;

import lombok.Data;

/**
 * @Author cheems
 * @Date 2023/9/23 21:01
 */

@Data
public class UriViews {

    /**
     * 主键
     */
    private Integer id;

    /**
     * uri
     */
    private String uri;

    /**
     * 访问次数
     */
    private Integer viewNum;
}
