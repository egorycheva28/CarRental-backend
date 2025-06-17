package com.example;

/*public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}*/

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//@EnableDiscoveryClient
//@EnableAsync
//@EnableFeignClients(basePackages = "com.example.security.client")
//@EnableScheduling
//@Import({SecurityConfig.class, JwtRequestFilter.class, JwtTokenUtils.class})
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

}