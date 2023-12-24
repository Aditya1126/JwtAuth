package com.example.demo.config;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CrossConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer(){
        return  new WebMvcConfigurer() {
            public void addCorsMappingS(CorsRegistry corsRegistry){
                corsRegistry.addMapping("/**").allowedMethods("GET","POST","PUT","DELETE").allowedOrigins("/**");
            }

        };
    }

}
