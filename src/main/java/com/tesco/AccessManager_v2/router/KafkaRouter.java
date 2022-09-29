package com.tesco.AccessManager_v2.router;

import com.tesco.AccessManager_v2.model.KafkaProducerMetadataDTO;
import com.tesco.AccessManager_v2.model.UserModel;
import com.tesco.AccessManager_v2.service.implementation.MessageServiceImpl;
import com.tesco.AccessManager_v2.service.implementation.UserServiceImpl;
import com.tesco.AccessManager_v2.utils.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
public class KafkaRouter {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    MessageServiceImpl messageService;

    @Bean
    @RouterOperations(
            {
                    @RouterOperation(path = Constants.KafkaPaths.SEND_KAKFA, produces = {
                            MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST,
                            operation = @Operation(operationId = "sendMessage", tags= {"Kafka"},
                                    responses = {
                                            @ApiResponse(responseCode = "200", description = "success", content = @Content(schema = @Schema(implementation = UserModel.class))),
                                            @ApiResponse(responseCode = "404", description = "Not found")},
                                    requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = UserModel.class)))
                            )),

                    @RouterOperation(path = Constants.KafkaPaths.RECEIVE_KAKFA, produces = { MediaType.APPLICATION_JSON_VALUE },
                            method = RequestMethod.POST,
                            operation = @Operation(operationId = "retrieveMessage", tags = {"Kafka"},
                                    responses = {
                                            @ApiResponse(responseCode = "200" , description = "success", content = @Content(schema = @Schema(implementation = UserModel.class))),
                                            @ApiResponse(responseCode = "404", description = "not found")},
                                    requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = KafkaProducerMetadataDTO.class))))
                    )
            })


    RouterFunction<ServerResponse> routeKafka(){

        return RouterFunctions.route(RequestPredicates.POST(Constants.KafkaPaths.SEND_KAKFA)
                                .and(RequestPredicates.contentType(MediaType.APPLICATION_JSON)),
                        req -> req.body(BodyExtractors.toMono(UserModel.class))
                                .flatMap(user -> {
                                    return messageService.sendMessage(user);
                                })
                                .flatMap(data -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(data)))

                .andRoute(RequestPredicates.POST(Constants.KafkaPaths.RECEIVE_KAKFA)
                                .and(RequestPredicates.contentType(MediaType.APPLICATION_JSON)),
                        request -> request.body(BodyExtractors.toMono(KafkaProducerMetadataDTO.class))
                                .flatMap(data -> messageService.retrieveMessage(data))
                                .flatMap(data -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(data)));
    }
}
