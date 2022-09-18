package com.tesco.AccessManager_v2.service;

import com.tesco.AccessManager_v2.model.UnitsModel;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public interface UnitService {

    Flux<UnitsModel> getUnits();
    Mono<UnitsModel> getUnitById(String id);
    Mono<UnitsModel> add(UnitsModel model);
    Mono<UnitsModel> update(UnitsModel model);
    Mono<Void> deleteUnit(String id);
}
