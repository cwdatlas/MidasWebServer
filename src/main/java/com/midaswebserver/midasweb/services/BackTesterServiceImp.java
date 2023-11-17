package com.midaswebserver.midasweb.services;

import com.midaswebserver.midasweb.apiModels.BacktradeOptimize;
import com.midaswebserver.midasweb.apiModels.BacktradeReturn;
import com.midaswebserver.midasweb.apiModels.BacktradeTest;
import com.midaswebserver.midasweb.exceptions.ApiClientException;
import com.midaswebserver.midasweb.exceptions.ApiServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
                    .uri("/backtrade")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(params), BacktradeTest.class)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
                        //Handle 4xx errors here
                        return clientResponse.bodyToMono(String.class)
                                .flatMap(errorDetails ->
                                        Mono.error(new ApiClientException("Client Error: " + errorDetails)));
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
                    .uri("/optimize")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(params), BacktradeOptimize.class)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
                        //Handle 4xx errors here
                        return clientResponse.bodyToMono(String.class)
                                .flatMap(errorDetails ->
                                        Mono.error(new ApiClientException("Client Error: " + errorDetails)));
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
        BacktradeReturn errorResponse = new BacktradeReturn();
        if (e instanceof ApiClientException) {
            errorResponse.setErrorCode("4xx");
            errorResponse.setMessage(e.getMessage());
        } else if (e instanceof ApiServerException) {
            errorResponse.setErrorCode("5xx");
            errorResponse.setMessage(e.getMessage());
        } else {
            errorResponse.setErrorCode("UNKNOWN_ERROR");
            errorResponse.setMessage("Unknown Error: " + e.getMessage());
        }
        return Mono.just(errorResponse);
    }
}