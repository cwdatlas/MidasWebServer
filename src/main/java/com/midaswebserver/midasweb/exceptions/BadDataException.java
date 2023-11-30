package com.midaswebserver.midasweb.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BadDataException extends Exception {
    private static final Logger log = LoggerFactory.getLogger(BadDataException.class);

    private String errorBody;

    public BadDataException(String message, String errorBody) {
        super(message);
        log.info("BadDataException: 400 exception occurred with message: '{}'", message);
        this.errorBody = errorBody;
    }

    public String getErrorBody() {
        return errorBody;
    }

    public void setErrorBody(String errorBody) {
        this.errorBody = errorBody;
    }
}
