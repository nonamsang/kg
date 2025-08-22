package com.fintecho.littleforest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.fintecho.littleforest.mapper")
public class LittleForestApplication {

	public static void main(String[] args) {
		SpringApplication.run(LittleForestApplication.class, args);
	}

}
