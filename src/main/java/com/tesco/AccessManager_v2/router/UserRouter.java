package com.tesco.AccessManager_v2.router;

import com.tesco.AccessManager_v2.model.KafkaProducerMetadataDTO;
import com.tesco.AccessManager_v2.model.UnitsModel;
import com.tesco.AccessManager_v2.model.UserModel;
import com.tesco.AccessManager_v2.service.MessageService;
import com.tesco.AccessManager_v2.service.MessageServiceImpl;
import com.tesco.AccessManager_v2.service.UserServiceImpl;
import com.tesco.AccessManager_v2.utils.Constants;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
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

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Controller
@Slf4j
@Tag(name = "User APIs", description = "User api collection")
//@RouterOperation(path = Constants.UserPaths.BASEPATH_USER)
public class UserRouter {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    MessageServiceImpl messageService;

//    @Bean
//    public WebProperties.Resources resources() {
//        return new WebProperties.Resources();
//    }

    @Bean
    @RouterOperations(
            {
                    @RouterOperation(path = Constants.UserPaths.ADD_USER, produces = {MediaType.APPLICATION_JSON_VALUE},
                            method = RequestMethod.POST,
                            operation = @Operation(operationId = "addUsers", tags= {"Users"},
                                    responses = {
                                    @ApiResponse(responseCode = "200", description = "success", content = @Content(schema = @Schema(implementation = UserModel.class))),
                                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                                    @ApiResponse(responseCode = "404", description = "create end point not found")},
                                    requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = UserModel.class)))
                            )),
                    @RouterOperation(path= Constants.UserPaths.GET_USER_BYUNIT, produces = {MediaType.APPLICATION_JSON_VALUE},
                            method = RequestMethod.GET,
                            operation = @Operation(operationId = "getUsersByUnit", tags= {"Users"},
                                    responses = {
                                    @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = UserModel.class))),
                                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                                    @ApiResponse(responseCode = "404", description = "Unit not found")},
                                    parameters = {@Parameter(in = ParameterIn.PATH, name = "unit_Id")}
                            )),
                    @RouterOperation(path = Constants.UserPaths.GET_USERS, produces = {
                            MediaType.APPLICATION_JSON_VALUE} ,method = RequestMethod.GET,
                            operation = @Operation(operationId = "getUsers", tags= {"Users"},
                                    responses = {
                                    @ApiResponse(responseCode = "200", description = "success", content = @Content(schema = @Schema(implementation = UserModel.class))),
                                    @ApiResponse(responseCode = "404", description = "Not found")}
                            )),
                    @RouterOperation(path = Constants.UserPaths.USER_ID, produces = {
                            MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET,
                            operation = @Operation(operationId = "getUserByUserId", tags= {"Users"},
                                    responses = {
                                    @ApiResponse(responseCode = "200", description = "success", content = @Content(schema = @Schema(implementation = UserModel.class))),
                                    @ApiResponse(responseCode = "404", description = "Not found")},
                                    parameters = {@Parameter(in = ParameterIn.PATH, name = "user_Id")}
                            )),
                    @RouterOperation(path = Constants.UserPaths.UPDATE_USER, produces = {
                            MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST,
                            operation = @Operation(operationId = "updateUser", tags= {"Users"},
                                    responses = {
                                    @ApiResponse(responseCode = "200", description = "success", content = @Content(schema = @Schema(implementation = UnitsModel.class))),
                                    @ApiResponse(responseCode = "404", description = "Not found")},
                                    requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = UserModel.class)))

                            )),
                    @RouterOperation(path = Constants.UserPaths.DELETE_USER, produces = {
                            MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.DELETE,
                            operation = @Operation(operationId = "delete", tags= {"Users"},
                                    responses = {
                                    @ApiResponse(responseCode = "200", description = "success", content = @Content(schema = @Schema(implementation = UserModel.class))),
                                    @ApiResponse(responseCode = "404", description = "Not found")},
                                    parameters = {@Parameter(in = ParameterIn.PATH, name = "user_Id")}
                            )),
                    @RouterOperation(path = "/send", produces = {
                            MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST,
                            operation = @Operation(operationId = "sendMessage", tags= {"Kafka"},
                                    responses = {
                                    @ApiResponse(responseCode = "200", description = "success", content = @Content(schema = @Schema(implementation = UserModel.class))),
                                    @ApiResponse(responseCode = "404", description = "Not found")},
                                    requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = UserModel.class)))
                            )),

                    @RouterOperation(path = "/receive", produces = { MediaType.APPLICATION_JSON_VALUE },
                            method = RequestMethod.POST,
                            operation = @Operation(operationId = "retrieveMessage", tags = {"Kafka"},
                                responses = {
                                    @ApiResponse(responseCode = "200" , description = "success", content = @Content(schema = @Schema(implementation = UserModel.class))),
                                        @ApiResponse(responseCode = "404", description = "not found")},
                                        requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = KafkaProducerMetadataDTO.class))))
                    )
            })


    RouterFunction<ServerResponse> routeUser(){

        return RouterFunctions.route(RequestPredicates.POST(Constants.UserPaths.ADD_USER)
                        .and(RequestPredicates.contentType(MediaType.APPLICATION_JSON)), req ->
                        req.body(BodyExtractors.toMono(UserModel.class)).flatMap(user ->
                                userService.addUser(user)).flatMap(data ->
                                ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(data)))

                .andRoute(GET(Constants.UserPaths.GET_USERS),
                        request -> userService.getUsers().collectList()
                                .flatMap(data-> ok().contentType(MediaType.APPLICATION_JSON).bodyValue(data)))

                .andRoute(GET(Constants.UserPaths.USER_ID),
                        request -> userService.getUserByUserId(request.pathVariable("user_Id"))
                        .flatMap(data-> ok().contentType(MediaType.APPLICATION_JSON).bodyValue(data)))

                .andRoute(RequestPredicates.GET(Constants.UserPaths.GET_USER_BYUNIT),
                        request -> userService.getUsersByUnit(request.pathVariable("unit_Id")).collectList()
                                .flatMap(data -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(data)))

                .andRoute(RequestPredicates.POST(Constants.UserPaths.UPDATE_USER)
                                .and(RequestPredicates.contentType(MediaType.APPLICATION_JSON)),
                        request -> request.body(BodyExtractors.toMono(UserModel.class))
                                .flatMap(u -> userService.updateUser(u))
                                .flatMap(data -> ok().contentType(MediaType.APPLICATION_JSON).bodyValue(data)))

                .andRoute(RequestPredicates.DELETE(Constants.UserPaths.DELETE_USER),
                        request -> userService.deleteUser(request.pathVariable("user_Id"))
                                .flatMap(data -> ServerResponse.noContent().build()))

                .andRoute(RequestPredicates.POST("/send")
                        .and(RequestPredicates.contentType(MediaType.APPLICATION_JSON)),
                        req -> req.body(BodyExtractors.toMono(UserModel.class))
                                .flatMap(user -> {
                                    return messageService.sendMessage(user);
                                })
                                .flatMap(data -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(data)))

                .andRoute(RequestPredicates.POST("/receive")
                        .and(RequestPredicates.contentType(MediaType.APPLICATION_JSON)),
                        request -> request.body(BodyExtractors.toMono(KafkaProducerMetadataDTO.class))
                                .flatMap(data -> messageService.retrieveMessage(data))
                                .flatMap(data -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(data)));


    }

}
