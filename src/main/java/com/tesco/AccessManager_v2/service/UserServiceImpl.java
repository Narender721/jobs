package com.tesco.AccessManager_v2.service;

import com.tesco.AccessManager_v2.model.UserModel;
import com.tesco.AccessManager_v2.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    public Mono<UserModel> addUser(UserModel userModel){
        log.debug("User added " + userModel.getUser_Id());
        return userRepository.save(userModel);
    }

    public Mono<UserModel> getUsersByUnit(String unitId) {
        return userRepository.findUsersByUnitId(Integer.parseInt(unitId));
    }
}
