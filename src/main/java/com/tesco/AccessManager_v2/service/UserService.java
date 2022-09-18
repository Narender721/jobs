package com.tesco.AccessManager_v2.service;

import com.tesco.AccessManager_v2.model.UserModel;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public interface UserService {

    Mono<UserModel> addUser(UserModel userModel);
    Mono<UserModel> getUsersByUnit(String unitId);
}
