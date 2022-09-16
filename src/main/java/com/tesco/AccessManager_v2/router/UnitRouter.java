package com.tesco.AccessManager_v2.router;

import com.tesco.AccessManager_v2.model.UnitsModel;
import com.tesco.AccessManager_v2.service.UnitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebProperties;
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
public class UnitRouter {

    @Autowired
    UnitService unitService;

    @Bean
    public WebProperties.Resources resources() {
        return new WebProperties.Resources();
    }


    @Bean
    @RouterOperations(
            {
                    @RouterOperation(path = "/units", produces = {
                            MediaType.APPLICATION_JSON_VALUE} ,method = RequestMethod.GET,
                            operation = @Operation(operationId = "getUnit", responses = {
                                    @ApiResponse(responseCode = "200", description = "success", content = @Content(schema = @Schema(implementation = UnitsModel.class))),
                                    @ApiResponse(responseCode = "404", description = "Not found")}
                            )),
                    @RouterOperation(path = "/unit/{unit_Id}", produces = {
                            MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET,
                            operation = @Operation(operationId = "getUnitById", responses = {
                                    @ApiResponse(responseCode = "200", description = "success", content = @Content(schema = @Schema(implementation = UnitsModel.class))),
                                    @ApiResponse(responseCode = "404", description = "Not found")},
                                    parameters = {@Parameter(in = ParameterIn.PATH, name = "unit_Id")}
                            )),
                    @RouterOperation(path = "/addUnit", produces = {
                            MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST,
                            operation = @Operation(operationId = "add", responses = {
                                    @ApiResponse(responseCode = "200", description = "success", content = @Content(schema = @Schema(implementation = UnitsModel.class))),
                                    @ApiResponse(responseCode = "404", description = "Not found")},
                                    requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = UnitsModel.class)))
                            )),
                    @RouterOperation(path = "/update", produces = {
                            MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST,
                            operation = @Operation(operationId = "update", responses = {
                                    @ApiResponse(responseCode = "200", description = "success", content = @Content(schema = @Schema(implementation = UnitsModel.class))),
                                    @ApiResponse(responseCode = "404", description = "Not found")},
                                    requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = UnitsModel.class)))

                            )),
                    @RouterOperation(path = "/delete/{unit_Id}", produces = {
                            MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.DELETE,
                            operation = @Operation(operationId = "delete", responses = {
                                    @ApiResponse(responseCode = "200", description = "success", content = @Content(schema = @Schema(implementation = UnitsModel.class))),
                                    @ApiResponse(responseCode = "404", description = "Not found")},
                                    parameters = {@Parameter(in = ParameterIn.PATH, name = "unit_Id")}
                            ))
            })


    RouterFunction<ServerResponse> routeUnit(){

        return RouterFunctions
                .route(GET("/unit/{unit_Id}"),
                        request -> unitService.getUnitById(request.pathVariable("unit_Id"))
                                .flatMap(data-> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(data)))

                .andRoute(RequestPredicates.POST("/addUnit")
                                .and(RequestPredicates.contentType(MediaType.APPLICATION_JSON)),
                        request -> request.body(BodyExtractors.toMono(UnitsModel.class))
                                .flatMap(u -> unitService.add(u))
                                .flatMap(data -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(data)))

                .andRoute(RequestPredicates.POST("/update")
                                .and(RequestPredicates.contentType(MediaType.APPLICATION_JSON)),
                        request -> request.body(BodyExtractors.toMono(UnitsModel.class))
                                .flatMap(u -> unitService.update(u))
                                .flatMap(data -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(data)))
                .andRoute(RequestPredicates.DELETE("/delete/{unit_Id}"),
                        request -> unitService.deleteUnit(request.pathVariable("unit_Id"))
                                .flatMap(data -> ok().contentType(MediaType.APPLICATION_JSON).bodyValue(data))) //change
                .andRoute(GET("/units"),
                        request -> unitService.getUnits().collectList()
                                .flatMap(data->ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(data)));
    }

}
