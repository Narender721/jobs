package com.tesco.AccessManager_v2.service;

import com.tesco.AccessManager_v2.model.UserModel;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public interface UserService {

    Flux<UserModel> getUsers();
    Mono<UserModel> addUser(UserModel userModel);
//    Mono<UserModel> getUsersByUnit(String unitId);
    Mono<UserModel> getUserByUserId(String user_Id);
    Mono<UserModel> updateUser(UserModel userModel);
    Mono<Void> deleteUser(String id);



}
