package com.rocky.blogapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // <--- 加上這行，create_time 才會自動生效
@SpringBootApplication
public class JavaBlogCmsApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaBlogCmsApiApplication.class, args);
    }

}
