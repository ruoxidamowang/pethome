package com.ruoxi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 若兮
 */
@SpringBootApplication
@MapperScan("com.ruoxi.gm.mapper")
public class PetHomeApplication {
    public static void main(String[] args) {
        SpringApplication.run(PetHomeApplication.class, args);
    }
}
