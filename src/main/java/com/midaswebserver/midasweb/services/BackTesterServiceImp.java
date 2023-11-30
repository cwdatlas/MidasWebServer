package com.midaswebserver.midasweb.services;

import com.google.gson.Gson;
import com.midaswebserver.midasweb.apiModels.BacktradeOptimize;
import com.midaswebserver.midasweb.apiModels.BacktradeReturn;
import com.midaswebserver.midasweb.apiModels.BacktradeTest;
import com.midaswebserver.midasweb.exceptions.ApiClientException;
import com.midaswebserver.midasweb.exceptions.ApiServerException;
import com.midaswebserver.midasweb.exceptions.BadDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @Author Aidan Scott
 * BackTesterService manages api requests and logic for backtesting activities
 * Webull will be queried in the future for realtime stock data
 */
@Service
public class BackTesterServiceImp implements BackTesterService {
    private static final Logger log = LoggerFactory.getLogger(BackTesterServiceImp.class);
    private final WebClient webclient; //I think this is not working because a bean is not provided to the environment

    /**
     * Inject Dependencies
     *
     * @param webclient
     */
    public BackTesterServiceImp(WebClient webclient) {
        this.webclient = webclient;
    }

    /**
     * backtrade will consume a BacktradeTest object and check to see the ending value of a trade with those parameters
     * errors will be included in the returned object
     *
     * @return BacktradeReturn (if there are any errors this object will persist them
     */
    @Override
    public BacktradeReturn backtrade(BacktradeTest params) {
        BacktradeReturn results = null;
        if (params != null) {
            Mono<BacktradeReturn> mono = webclient.post()
                    .uri("/backtrade")//addition to the default webclient url
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(params), BacktradeTest.class)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
                        //Handle 4xx errors here
                        return clientResponse.bodyToMono(String.class).flatMap(errorBody -> {
                            if (clientResponse.statusCode().equals(HttpStatus.BAD_REQUEST)) {
                                return Mono.error(new BadDataException("Bad Data Error", errorBody));
                            } else {
                                return Mono.error(new ApiServerException("Client Error: " + errorBody));
                            }
                        });
                    })
                    .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> {
                        //Handle 5xx errors here
                        return clientResponse.bodyToMono(String.class)
                                .flatMap(errorDetails ->
                                        Mono.error(new ApiServerException("Server Error: " + errorDetails)));
                    })
                    .bodyToMono(BacktradeReturn.class)
                    //configure return with correct data based on error
                    .onErrorResume(this::errorHandler);

            results = mono.block();//I need to switch this to an asynchronous method
        }
        return results;
    }

    /**
     * optimize will consume a BacktradeOptimize object and check to see the ending value of a trade with the those parameters
     * The best preforming trade parameters will be returned.
     * errors will be included in the returned object
     *
     * @return BacktradeReturn
     */

    public BacktradeReturn optimize(BacktradeOptimize params) {
        BacktradeReturn results = null;
        if (params != null) {
            Mono<BacktradeReturn> mono = webclient.post()
                    .uri("/optimize")//addition to the default webclient url
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(params), BacktradeOptimize.class)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
                        //Handle 4xx errors here
                        return clientResponse.bodyToMono(String.class).flatMap(errorBody -> {
                            if (clientResponse.statusCode().equals(HttpStatus.BAD_REQUEST)) {
                                return Mono.error(new BadDataException("Bad Data Error", errorBody));
                            } else {
                                return Mono.error(new ApiServerException("Client Error: " + errorBody));
                            }
                        });
                    })
                    .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> {
                        //Handle 5xx errors here
                        return clientResponse.bodyToMono(String.class)
                                .flatMap(errorDetails ->
                                        Mono.error(new ApiServerException("Server Error: " + errorDetails)));
                    })
                    .bodyToMono(BacktradeReturn.class)
                    //configure return with correct data based on error
                    .onErrorResume(this::errorHandler);

            results = mono.block();//I need to switch this to an asynchronous method
        }
        return results;
    }

    /**
     * errorHandler is a redundancy decreasing function that packages known common errors for use in webclient
     * error handling
     *
     * @param e
     * @return error injected Mono
     */
    private Mono<BacktradeReturn> errorHandler(Throwable e) {
        Gson gson = new Gson();
        BacktradeReturn errorResponse = new BacktradeReturn();
        if (e instanceof BadDataException) {
            //if error is of type 400, then it will be decoded and stored in a mono of type BacktradeReturn
            log.warn("errorHandler: 400 error occurred: '{}'", e.getMessage());
            errorResponse = gson.fromJson(((BadDataException) e).getErrorBody(), BacktradeReturn.class);
            errorResponse.setErrorCode("400");
        } else if (e instanceof ApiClientException) {
            errorResponse.setErrorCode("4xx");
            log.warn("errorHandler: 4xx error occurred: '{}'", e.getMessage());
            errorResponse.setError("internal Error");
            errorResponse.setMessage("Internal Error");
        } else if (e instanceof ApiServerException) {
            //5xx errors aren't useful for the user, so propagating data which is simple and able to make decisions on
            errorResponse.setErrorCode("5xx");
            log.warn("errorHandler: 5xx error occurred: '{}'", e.getMessage());
            errorResponse.setError("internal Error");
            errorResponse.setMessage("Internal Error");
        } else {
            //unknown errors aren't useful for the user, so propagating data which is simple and able to make decisions on
            errorResponse.setErrorCode("UNKNOWN_ERROR");
            log.warn("errorHandler: Unknown Error occurred: '{}'", e.getMessage());
            errorResponse.setMessage(e.getMessage());
        }
        return Mono.just(errorResponse);
    }
}