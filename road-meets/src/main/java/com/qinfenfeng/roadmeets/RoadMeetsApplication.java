package com.qinfenfeng.roadmeets;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("classpath:com.qinfenfeng.roadmeets.mbg.mapper")
public class RoadMeetsApplication {

    public static void main(String[] args) {
        SpringApplication.run(RoadMeetsApplication.class, args);
    }

}
