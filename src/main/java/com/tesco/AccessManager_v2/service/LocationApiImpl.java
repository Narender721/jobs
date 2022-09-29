package com.tesco.AccessManager_v2.service;

import com.tesco.AccessManager_v2.model.AuthorizationModel;
import com.tesco.AccessManager_v2.model.LocationApiDTO;
import com.tesco.AccessManager_v2.model.UnitsModel;
import com.tesco.AccessManager_v2.model.UserModel;
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
public class LocationApiImpl {

    private  WebClient webClient;
    String globalBearerToken=null;

    public Flux<?> getLocation() throws InterruptedException {

        Mono<AuthorizationModel> authModel = getBearer();

        return authModel.flatMapMany(auth -> {
            return WebClient.create().get().uri("https://api-ppe.tesco.com/tescolocation/v4/locations?fields=lifecycle&limit=100&offset=200")
                .header("X-Only-For-Testing-Purposes","true")
                .headers(header -> header.setBearerAuth(auth.getAccess_token()))
                .retrieve().bodyToFlux(LocationApiDTO.class);
        });
    }

    public Mono<AuthorizationModel> getBearer() throws InterruptedException {

        String json_body = "{\"grant_type\":\"client_credentials\",\"client_id\":\"6434f06e-028d-4a4a-9835-5637952d180e\",\"client_secret\":\"1a4e374d-3dc2-4c2e-aef9-e7a6e28718e1\"}";

        return WebClient.create().post()
                .uri("https://api-ppe.tesco.com/identity/v4/issue-token/token")
                .header("Content-Type","application/json")
                .body(BodyInserters.fromValue(json_body))
                .retrieve()
                .bodyToMono(AuthorizationModel.class);
    }

}

