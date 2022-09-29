package com.tesco.AccessManager_v2.service;

import com.tesco.AccessManager_v2.model.AuthorizationModel;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public interface LocationApi {

    Flux<?> getLocation() throws InterruptedException;
    Mono<AuthorizationModel> getBearer() throws InterruptedException;
}
