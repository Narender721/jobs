package com.tesco.AccessManager_v2.router;

import com.tesco.AccessManager_v2.model.UserModel;
import com.tesco.AccessManager_v2.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Controller
@Slf4j
@Tag(name = "User APIs", description = "User api collection")
public class UserRouter {

    @Autowired
    UserServiceImpl userService;

//    @Bean
//    public WebProperties.Resources resources() {
//        return new WebProperties.Resources();
//    }

    @Bean
    @RouterOperations(
            {
                    @RouterOperation(path = "/user/create", produces = {MediaType.APPLICATION_JSON_VALUE},
                            method = RequestMethod.POST,
                            operation = @Operation(operationId = "addUsers", responses = {
                                    @ApiResponse(responseCode = "200", description = "success", content = @Content(schema = @Schema(implementation = UserModel.class))),
                                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                                    @ApiResponse(responseCode = "404", description = "create end point not found")},
                                    requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = UserModel.class)))
                            )),
                    @RouterOperation(path= "/user/getUsersByUnit/{unitId}", produces = {MediaType.APPLICATION_JSON_VALUE},
                            method = RequestMethod.GET,
                            operation = @Operation(operationId = "getUsersByUnit", responses = {
                                    @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = UserModel.class))),
                                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                                    @ApiResponse(responseCode = "404", description = "Unit not found")},
                                    requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = UserModel.class)))
                            ))
            })


    RouterFunction<ServerResponse> routeUser(){

        return RouterFunctions.route(RequestPredicates.POST("/user/create").and(RequestPredicates.contentType(MediaType.APPLICATION_JSON)), req ->
                        req.body(BodyExtractors.toMono(UserModel.class)).flatMap(user ->
                                userService.addUser(user)).flatMap(data ->
                                ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(data)))
                .andRoute(RequestPredicates.GET("/user/getUsersByUnit/{unitId}"), request ->
                        userService.getUsersByUnit(request.pathVariable("unit_Id"))
                                .flatMap(data -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(data)));

    }


}
