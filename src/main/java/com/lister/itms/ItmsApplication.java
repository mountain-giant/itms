package com.lister.itms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@EnableRedisHttpSession
@MapperScan("com.lister.itms.dao.mapper")
public class ItmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItmsApplication.class, args);
    }

}


