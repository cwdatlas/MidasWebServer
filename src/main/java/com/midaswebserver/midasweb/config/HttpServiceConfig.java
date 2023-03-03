package com.midaswebserver.midasweb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * HttpServiceConfig creates a servlet filter that replaces the implementation of HttpSession with a springboot version that uses redis
 */
@EnableRedisHttpSession
public class HttpServiceConfig {
    @Bean
    public LettuceConnectionFactory connectionFactory() {
        return new LettuceConnectionFactory();
    }
}
