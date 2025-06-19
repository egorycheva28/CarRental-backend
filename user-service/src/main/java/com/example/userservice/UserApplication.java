package com.example.userservice;

/*public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}*/

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(scanBasePackages = {
        "com.example.userservice",
        "com.example.common"
})

@EnableConfigurationProperties
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
/*@SpringBootApplication
//@EnableDiscoveryClient
//@EnableAsync
//@EnableFeignClients(basePackages = "com.example.security.client")
//@EnableScheduling
//@Import({SecurityConfig.class, JwtRequestFilter.class, JwtTokenUtils.class})
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

}*/