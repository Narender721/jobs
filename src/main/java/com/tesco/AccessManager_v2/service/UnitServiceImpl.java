package com.tesco.AccessManager_v2.service;

import com.tesco.AccessManager_v2.exception.DBEmptyException;
//import com.tesco.AccessManager_v2.exception.DuplicateEntryException;
import com.tesco.AccessManager_v2.exception.IdNullException;
import com.tesco.AccessManager_v2.exception.UnitNotFoundException;
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
        return unitsRepository.findAll().switchIfEmpty(Mono.error(new DBEmptyException()));
        //raise exception if db is empty
    }

    public Mono<UnitsModel> getUnitById(String id){
        int unitId=Integer.parseInt(id);
        if(unitId == 0)
            throw new IdNullException();

        log.debug("return Unit details for Unit " + unitId);

        Mono<UnitsModel> check = unitsRepository.findById(unitId);
        UnitsModel model1 = check.share().block();
        assert model1 != null;
        System.out.println(model1.getUnit_Name());

        return unitsRepository.findById(unitId).switchIfEmpty(Mono.error(new UnitNotFoundException(unitId)));
    }


    public Mono<UnitsModel> add(UnitsModel model) { //post

//        log.debug("Created Unit " + model.getUnit_Id());
//        int id = model.getUnit_Id();
//        if(id == 0)
//        {
//            throw new IdNullException();
//        }
        Mono<UnitsModel> check = unitsRepository.findById(model.getUnit_Id());
//        UnitsModel model1 = check.share().block();
//        assert model1 != null;
//        System.out.println(model1.getUnit_Name());
//        final int[] get_unit = new int[1];
        check.subscribe(get -> System.out.println(get.getUnit_Id()));
        check.subscribe(get -> System.out.println(get.getUnit_Name()));
//        System.out.println(get_unit[0]);

//        Mono<Boolean> cc= check.hasElement();
//        //        unitsRepository.findById(id).thenReturn(new duplicateEntryException(id));
//        System.out.println(cc);
//        check.defaultIfEmpty(new UnitNotFoundException());
        return unitsRepository.save(model).switchIfEmpty(Mono.error(new IdNullException()));
    }

    public Mono<UnitsModel> update(UnitsModel model) {
//        Mono<UnitsModel>  um= unitsRepository.findById(model.getUnit_Id());
//        System.out.println(um);
//        if(um. == 0)
//        {
//            throw new IdNullException();
//        }
//        deptR.subscribe(d -> d.getDeptName());
//        um.subscribe(data -> data.getUnit_Id());

        int id = model.getUnit_Id();
        if(id == 0)
        {
            throw new IdNullException();
        }

        return unitsRepository.findById(model.getUnit_Id())
                .map( temp-> {
                    temp.setUnit_Name(model.getUnit_Name());
                    return temp;
                }).flatMap( c -> unitsRepository.save(c));
    }

    public Mono<Void> deleteUnit(String id) {
//        System.out.println(id);
        int unitId=Integer.parseInt(id);
        if(unitId == 0)
            throw new IdNullException();
        log.debug("Deleted Unit " + unitId);

//        unitsRepository.findById(unitId).switchIfEmpty(Mono.error(new UnitNotFoundException(unitId)));
        return unitsRepository.deleteById(unitId);
//                .switchIfEmpty(Mono.error(new UnitNotFoundException(unitId)));
    }
}
