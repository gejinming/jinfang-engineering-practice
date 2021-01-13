package com.jinfang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("com.jinfang.mapper")
@EnableSwagger2
public class JinfangEngineeringPracticeApplication {

    public static void main(String[] args) {
        SpringApplication.run(JinfangEngineeringPracticeApplication.class, args);
    }

}
