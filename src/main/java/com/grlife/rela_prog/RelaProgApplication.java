package com.grlife.rela_prog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class RelaProgApplication {

    public static void main(String[] args) {
        SpringApplication.run(RelaProgApplication.class, args);
    }

}
