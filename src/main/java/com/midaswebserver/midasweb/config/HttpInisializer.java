package com.midaswebserver.midasweb.config;

import java.io.ObjectInputFilter;

import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

public class HttpInisializer {
    public class HttpInitializer extends AbstractHttpSessionApplicationInitializer {

        public HttpInitializer() {
            super(HttpInisializer.class);
        }

    }
}
