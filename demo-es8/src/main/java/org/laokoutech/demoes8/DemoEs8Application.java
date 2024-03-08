package org.laokoutech.demoes8;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class DemoEs8Application {

    public static void main(String[] args) {
        SpringApplication.run(DemoEs8Application.class, args);
    }

}
