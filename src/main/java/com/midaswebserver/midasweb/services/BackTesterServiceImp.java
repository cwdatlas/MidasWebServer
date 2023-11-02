package com.midaswebserver.midasweb.services;

import com.midaswebserver.midasweb.apiModels.BacktradeOptimize;
import com.midaswebserver.midasweb.apiModels.BacktradeReturn;
import com.midaswebserver.midasweb.apiModels.BacktradeTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

;

/**
 * @Author Aidan Scott
 * BackTesterService manages api requests and logic for backtesting activities
 * Webull will be queried in the future for realtime stock data
 */
@Service
public class BackTesterServiceImp implements BackTesterService {
    private static final Logger log = LoggerFactory.getLogger(com.midaswebserver.midasweb.services.BackTesterServiceImp.class);
    private final WebClient.Builder webClientBuilder; //I think this is not working because a bean is not provided to the environment

    /**
     * Inject Dependencies
     * @param webClientBuilder
     */
    public BackTesterServiceImp(WebClient.Builder webClientBuilder){
        this.webClientBuilder = webClientBuilder;
    }
    /**
     * Optimize takes variables and checks how profitable they would be while varying some variables each test. It will return
     * with the most profitable changing variables. If you plug the output into the Backtrade function, you will get the same
     * balance as you got from this function.
     * @param params
     * @return [optSMA, optEMA, optVolume, endBalance]
     */
    @Override
    public BacktradeReturn backtrade(BacktradeTest params) {
        BacktradeReturn results = null;
        if (params != null) {
            WebClient webclient = webClientBuilder.build();
            try{
                results = webclient.post()
                        .uri("http://localhost:5000/backtrade")
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(params), BacktradeTest.class)
                        .retrieve()
                        .bodyToMono(BacktradeReturn.class)//this is what is received, so we use this to specify what object needs to be received
                        .block();
            }
            catch(Exception e){
                log.error("BacktradeTest: query failed due to error:", e);
            }
            //log.info("BacktradeTest: returned object '{}'", results);
        }
        return results;
    }

    /**
     * @param params
     * @return
     */

    public BacktradeReturn optimize(BacktradeOptimize params) {
        BacktradeReturn results = null;
        if (params != null) {
            String url =
                    "http://localhost:5000/optimize" +
                            "?endDate=" + params.getEndDate() +
                            "&startDate=" + params.getStartDate() +
                            "&startSma=" + params.getStartSma() +
                            "&endSma=" + params.getEndSma() +
                            "&startEma=" + params.getStartEma() +
                            "&endEma=" + params.getEndEma() +
                            "&stockticker=" + params.getStockTicker().toString() +
                            "&stake=" + params.getStake() +
                            "&algorithm=" + params.getAlgorithm().toString() +
                            "&commission=" + params.getCommission();
            try{
                RestTemplate restTemplate = new RestTemplate();
                results = restTemplate.getForObject(url, BacktradeReturn.class);
            }catch (Exception e) {
                log.error("BacktradeOptimize: query failed due to error:", e);
            }
            log.info("BacktradeOptimize: returned object '{}'", results);

        }
        return results;
    }

}