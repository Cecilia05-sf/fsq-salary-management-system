package com.fsq.fsqsalary;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.fsq.fsqsalary.dao"})
public class FsqSalaryApplication {


    public static void main(String[] args) {
        SpringApplication.run(FsqSalaryApplication.class, args);
    }
}
