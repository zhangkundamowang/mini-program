package com.example.wx.xiaochengxu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class XiaochengxuApplication {

    public static void main(String[] args) {
        SpringApplication.run(XiaochengxuApplication.class, args);
    }

}
