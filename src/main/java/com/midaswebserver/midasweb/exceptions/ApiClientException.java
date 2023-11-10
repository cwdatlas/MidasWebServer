package com.midaswebserver.midasweb.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApiClientException extends Exception{
    private static final Logger log = LoggerFactory.getLogger(ApiClientException.class);

    public ApiClientException(String message){
        super(message);
        log.info("ApiClientException: 4xx exception occurred with message: '{}'", message);
    }
}
