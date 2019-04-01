package com.sixeco.order.base.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 分页配置
 *
 * @author: Zhanghe
 * @date: 2019-04-01
 */
@Configuration
public class PageHelperConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
