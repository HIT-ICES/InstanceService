package com.hitices.instance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author wangteng
 * @e-mail 1638235292@qq.com
 * @date 2023/5/1
 */
@SpringBootApplication
@EnableAsync
@EnableFeignClients
public class ApplicationMain {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationMain.class);
    }
}
