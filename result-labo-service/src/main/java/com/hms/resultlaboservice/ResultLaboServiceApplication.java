package com.hms.resultlaboservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "com.hms.resultlabo",
        "com.hms.common"
})
public class ResultLaboServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ResultLaboServiceApplication.class, args);
    }
}
