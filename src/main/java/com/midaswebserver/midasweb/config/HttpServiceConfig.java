package com.midaswebserver.midasweb.config;

import com.midaswebserver.midasweb.controllers.LoginController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * HttpServiceConfig creates a servlet filter that replaces the implementation of HttpSession with a springboot version that uses redis
 */
@EnableRedisHttpSession
public class HttpServiceConfig {
    private static final Logger log = LoggerFactory.getLogger(HttpServiceConfig.class);
    @Bean
    public LettuceConnectionFactory connectionFactory() {
        LettuceConnectionFactory connection = new LettuceConnectionFactory();
        log.info("Connected to '{}'", connection.getConnection());
        return connection;
    }
}
