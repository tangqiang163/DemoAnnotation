package com.sixeco.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableCaching
@MapperScan(basePackages= {"com.sixeco.order.mapper"})
public class OrderCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderCenterApplication.class, args);
    }
}
