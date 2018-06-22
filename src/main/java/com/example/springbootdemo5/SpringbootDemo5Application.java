package com.example.springbootdemo5;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan("com.example.springbootdemo5.dao") // 扫描dao层
@EnableCaching // 开启缓存支持
public class SpringbootDemo5Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootDemo5Application.class, args);
	}
}
