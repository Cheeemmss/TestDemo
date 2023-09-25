package com.test.common.utils;


import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory;

public class DruidFactory extends UnpooledDataSourceFactory {
    public DruidFactory() {
        this.dataSource = new DruidDataSource();
    }
}

