package com.midaswebserver.midasweb.config;

import com.midaswebserver.midasweb.controllers.LoginController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

public class HttpInitializer extends AbstractHttpSessionApplicationInitializer {
    private static final Logger log = LoggerFactory.getLogger(HttpInitializer.class);
    public HttpInitializer() {
        super(HttpServiceConfig.class);
        log.debug("Initialized HttpServiceConfig");
    }
}

