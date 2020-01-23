package com.qinfenfeng.roadmeets;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@MapperScan("com.qinfenfeng.roadmeets.mbg.mapper")
public class RoadMeetsApplication {

    public static void main(String[] args) {
        SpringApplication.run(RoadMeetsApplication.class, args);
    }

}
