package com.tesco.AccessManager_v2.service;

import com.tesco.AccessManager_v2.model.UnitsModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
@Slf4j
public class LocationApiImpl {

    WebClient webClient;
    public Flux<UnitsModel> getLocation(){
        return webClient.get().uri("https://api-ppe.tesco.com/tescolocation/v4/locations?fields=lifecycle&limit=100&offset=200")
                .retrieve().bodyToFlux(UnitsModel.class);
    }
}
