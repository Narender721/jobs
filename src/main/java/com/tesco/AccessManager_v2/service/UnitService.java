package com.tesco.AccessManager_v2.service;

import com.tesco.AccessManager_v2.model.UnitsModel;
import com.tesco.AccessManager_v2.repository.UnitsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UnitService {

    @Autowired
    UnitsRepository unitsRepository;

    public Flux<UnitsModel> getUnits(){
        return unitsRepository.findAll();
    }

    public Mono<UnitsModel> getUnitById(String id){
        System.out.println(id);
        int unitId=Integer.parseInt(id);
        System.out.println(unitId);
        return unitsRepository.findById(unitId);
    }

    public Mono<UnitsModel> add(UnitsModel model) { //post
        System.out.println(model.getUnit_Id() + model.getUnit_Name());
        return unitsRepository.save(model);
    }

    public Mono<UnitsModel> update(UnitsModel model) {
        Mono<UnitsModel>  um= unitsRepository.findById(model.getUnit_Id());
        System.out.println(um);

        return unitsRepository.findById(model.getUnit_Id())
                .map( temp-> {
                    temp.setUnit_Name(model.getUnit_Name());
                    return temp;
                }).flatMap( c -> unitsRepository.save(c));
    }

    public Mono<Void> deleteUnit(String id) {
        System.out.println(id);
        int unitId=Integer.parseInt(id);
        return unitsRepository.deleteById(unitId);
    }
}
