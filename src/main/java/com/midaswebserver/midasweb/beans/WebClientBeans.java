package com.midaswebserver.midasweb.beans;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorResourceFactory;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientBeans {
    @Bean
    public ReactorResourceFactory reactorResourceFactory() {
        return new ReactorResourceFactory();
    }

    @Bean(name = "backtrader")
    public WebClient webClient() {
        return WebClient.builder().baseUrl("http:"+System.getenv("BACKTRADE_MICROSERVICE")).build();
    }
}