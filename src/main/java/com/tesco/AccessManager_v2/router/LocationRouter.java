package com.tesco.AccessManager_v2.router;

import com.tesco.AccessManager_v2.service.LocationApiImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Controller
@Slf4j
public class LocationRouter {

    @Autowired
    LocationApiImpl locationApi;

//    RouterFunction<ServerResponse> routeLocation(){
//
//        return RouterFunctions.route(Re)
//    }
}
