package com.tesco.AccessManager_v2.service.implementation;

import com.tesco.AccessManager_v2.exception.DBEmptyException;
import com.tesco.AccessManager_v2.exception.IdNullException;
import com.tesco.AccessManager_v2.exception.UnitNotFoundException;
import com.tesco.AccessManager_v2.model.UserModel;
import com.tesco.AccessManager_v2.repository.UserRepository;
import com.tesco.AccessManager_v2.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;


    public Flux<UserModel> getUsers(){
        return userRepository.findAll().switchIfEmpty(Mono.error(new DBEmptyException()));
    }

    public Flux<UserModel> getUsersByUnit(String unitId) {
        return userRepository.findUsersByUnitId(Integer.parseInt(unitId));
    }

    public Mono<UserModel> getUserByUserId(String user_Id){

        int userId=Integer.parseInt(user_Id);
        if(userId == 0)
            throw new IdNullException();

        log.debug("return User details for User " + userId);

        Mono<UserModel> check = userRepository.findById(userId);
//        UserModel model1 = check.share().block();
//        assert model1 != null;
//        System.out.println(model1.getUser_Name());

        return userRepository.findById(userId).switchIfEmpty(Mono.error(new UnitNotFoundException(userId)));
    }

    public Mono<UserModel> addUser(UserModel userModel){

        log.debug("User added " + userModel.getUser_Id());
        Mono<UserModel> check = userRepository.findById(userModel.getUser_Id());

//        UserModel model1 = check.share().block();
//        assert model1 != null;
//        System.out.println(model1.getUser_Name());

        return userRepository.save(userModel).switchIfEmpty(Mono.error(new IdNullException()));
    }

    public Mono<UserModel> updateUser(UserModel model){

        int id = model.getUser_Id();
        if(id == 0)
        {
            throw new IdNullException();
        }
        return userRepository.findById(model.getUser_Id())
                .map(temp -> {
                    temp.setUser_Name(model.getUser_Name());
                    return temp;
                }).flatMap( user -> userRepository.save(user));
    }

    public Mono<Void> deleteUser(String id) {

        int userId=Integer.parseInt(id);
        if(userId == 0)
            throw new IdNullException();
        log.debug("Deleted Unit " + userId);

        return userRepository.deleteById(userId);

    }

}
