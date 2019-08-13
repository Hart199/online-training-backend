package com.future.onlinetraining;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class OnlineTrainingApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineTrainingApplication.class, args);
    }

}
