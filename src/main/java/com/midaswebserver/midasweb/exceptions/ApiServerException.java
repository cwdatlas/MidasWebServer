package com.midaswebserver.midasweb.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApiServerException extends Exception{
    private static final Logger log = LoggerFactory.getLogger(ApiServerException.class);

    public ApiServerException(String message){
        super(message);
        log.info("ApiServerException: 5xx exception occurred with message: '{}'", message);
    }
}
