package com.tesco.AccessManager_v2.router;

import com.tesco.AccessManager_v2.model.LocationApiDTO;
import com.tesco.AccessManager_v2.model.UserModel;
import com.tesco.AccessManager_v2.service.LocationApiImpl;
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
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Controller
@Slf4j
public class LocationRouter {

    @Autowired
    LocationApiImpl locationApi;

    @Bean
    @RouterOperations(
            {
                    @RouterOperation(path = "/location", produces = {MediaType.APPLICATION_JSON_VALUE},
                            method = RequestMethod.GET,
                            operation = @Operation(operationId = "getLocation", responses = {
                                    @ApiResponse(responseCode = "200", description = "success", content = @Content(schema = @Schema(implementation = LocationApiDTO.class))),
                                    @ApiResponse(responseCode = "400", description = "Bad Request")}
                            ))
            }
    )

    RouterFunction<ServerResponse> routeLocation(){

        return RouterFunctions
                .route(RequestPredicates.GET("/location"),
                        req -> locationApi.getLocation().collectList()
                        .flatMap(data -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(data)));

    }
}
