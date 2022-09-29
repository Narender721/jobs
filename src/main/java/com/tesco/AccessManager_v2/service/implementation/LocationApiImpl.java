package com.tesco.AccessManager_v2.service.implementation;

import com.tesco.AccessManager_v2.model.AuthorizationModel;
import com.tesco.AccessManager_v2.model.LocationApiDTO;
import com.tesco.AccessManager_v2.model.UnitsModel;
import com.tesco.AccessManager_v2.model.UserModel;
import com.tesco.AccessManager_v2.service.LocationApi;
import com.tesco.AccessManager_v2.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsProperties;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;

@Service
@Slf4j
public class LocationApiImpl implements LocationApi {

    private  WebClient webClient;
    String globalBearerToken=null;

    public Flux<?> getLocation() throws InterruptedException {

        Mono<AuthorizationModel> authModel = getBearer();

        return authModel.flatMapMany(auth -> {
            return WebClient.create().get().uri(Constants.LocationPaths.LOCATIONAPI_URI)
                .header("X-Only-For-Testing-Purposes","true")
                .headers(header -> header.setBearerAuth(auth.getAccess_token()))
                .retrieve().bodyToFlux(LocationApiDTO.class);
        });
    }

    public Mono<AuthorizationModel> getBearer() throws InterruptedException {

        return WebClient.create().post()
                .uri(Constants.LocationPaths.BEARER_URI)
                .header("Content-Type","application/json")
                .body(BodyInserters.fromValue(Constants.LocationPaths.BEARER_BODYJSON))
                .retrieve()
                .bodyToMono(AuthorizationModel.class);
    }

}

