package com.tesco.AccessManager_v2.service.implementation;

import com.tesco.AccessManager_v2.exception.DBEmptyException;
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
        int id = Integer.parseInt(user_Id);
        return userRepository.findById(id);
    }

    public Mono<UserModel> addUser(UserModel userModel){
        log.debug("User added " + userModel.getUser_Id());
        return userRepository.save(userModel);
    }

    public Mono<UserModel> updateUser(UserModel model){
        return userRepository.save(model);
    }

    public Mono<Void> deleteUser(String id) {
//        System.out.println(id);
        int userId=Integer.parseInt(id);
        return userRepository.deleteById(userId);
    }

}
