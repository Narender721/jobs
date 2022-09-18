package com.tesco.AccessManager_v2.service;

import com.tesco.AccessManager_v2.exception.IdNullException;
import com.tesco.AccessManager_v2.exception.UnitNotFoundException;
import com.tesco.AccessManager_v2.exception.duplicateEntryException;
import com.tesco.AccessManager_v2.model.UnitsModel;
import com.tesco.AccessManager_v2.repository.UnitsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class UnitServiceImpl implements UnitService{

    @Autowired
    UnitsRepository unitsRepository;

    public Flux<UnitsModel> getUnits(){
        log.debug("return Units");
        return unitsRepository.findAll();
    }

    public Mono<UnitsModel> getUnitById(String id){
        int unitId=Integer.parseInt(id);
//        if(unitId == 0)
//            throw Invalid input exception
        log.debug("return Unit details for Unit " + unitId);
        return unitsRepository.findById(unitId).switchIfEmpty(Mono.error(new UnitNotFoundException(unitId)));
    }


    public Mono<UnitsModel> add(UnitsModel model) { //post
//        System.out.println(model.getUnit_Id() + ":" + model.getUnit_Name());
        log.debug("Created Unit " + model.getUnit_Id());
        int id = model.getUnit_Id();
        if(id == 0)
        {
            throw new IdNullException();
        }
//        int check = unitsRepository.findById(temp);
        unitsRepository.findById(id).thenReturn(new duplicateEntryException(id));

        return unitsRepository.save(model);
    }

    public Mono<UnitsModel> update(UnitsModel model) {
        Mono<UnitsModel>  um= unitsRepository.findById(model.getUnit_Id());
//        System.out.println(um);
//        if(um. == 0)
//        {
//            throw new IdNullException();
//        }
//
        return unitsRepository.findById(model.getUnit_Id())
                .map( temp-> {
                    temp.setUnit_Name(model.getUnit_Name());
                    return temp;
                }).flatMap( c -> unitsRepository.save(c));
    }

    public Mono<Void> deleteUnit(String id) {
//        System.out.println(id);
        int unitId=Integer.parseInt(id);
        log.debug("Deleted Unit " + unitId);
        
        return unitsRepository.deleteById(unitId);
    }
}
