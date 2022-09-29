package com.tesco.AccessManager_v2.router;

import com.tesco.AccessManager_v2.model.UnitsModel;
//import com.tesco.AccessManager_v2.service.GenerateThread;
import com.tesco.AccessManager_v2.service.UnitServiceImpl;
import com.tesco.AccessManager_v2.utils.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.server.*;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Controller
@Slf4j
@Tag(name = "Unit APIs",description = "Unit api collection")
public class UnitRouter {

    @Autowired
    UnitServiceImpl unitService;

//    @Autowired
//    GenerateThread generateThread;

    @Bean
    public WebProperties.Resources resources() {
        return new WebProperties.Resources();
    }


    @Bean
//    @RouterOperation(path = Constants.UnitPaths.BASEPATH_UNIT)
    @RouterOperations(
            {
                    @RouterOperation(path = Constants.UnitPaths.GETUNITS, produces = {
                            MediaType.APPLICATION_JSON_VALUE} ,method = RequestMethod.GET,
                            operation = @Operation(operationId = "getUnit", tags= {"Units"},
                                    responses = {
                                    @ApiResponse(responseCode = "200", description = "success", content = @Content(schema = @Schema(implementation = UnitsModel.class))),
                                    @ApiResponse(responseCode = "404", description = "Not found")}
                            )),
                    @RouterOperation(path = Constants.UnitPaths.UNIT_ID , produces = {
                            MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET,
                            operation = @Operation(operationId = "getUnitById", tags= {"Units"},
                                    responses = {
                                    @ApiResponse(responseCode = "200", description = "success", content = @Content(schema = @Schema(implementation = UnitsModel.class))),
                                    @ApiResponse(responseCode = "404", description = "Not found")},
                                    parameters = {@Parameter(in = ParameterIn.PATH, name = "unit_Id")}
                            )),
                    @RouterOperation(path = Constants.UnitPaths.ADDUNIT, produces = {
                            MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST,
                            operation = @Operation(operationId = "add", tags= {"Units"},
                                    responses = {
                                    @ApiResponse(responseCode = "200", description = "success", content = @Content(schema = @Schema(implementation = UnitsModel.class))),
                                    @ApiResponse(responseCode = "404", description = "Not found")},
                                    requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = UnitsModel.class)))
                            )),
                    @RouterOperation(path = Constants.UnitPaths.UPDATE, produces = {
                            MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST,
                            operation = @Operation(operationId = "update", tags= {"Units"},
                                    responses = {
                                    @ApiResponse(responseCode = "200", description = "success", content = @Content(schema = @Schema(implementation = UnitsModel.class))),
                                    @ApiResponse(responseCode = "404", description = "Not found")},
                                    requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = UnitsModel.class)))

                            )),
                    @RouterOperation(path = Constants.UnitPaths.DELETE, produces = {
                            MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.DELETE,
                            operation = @Operation(operationId = "delete", tags= {"Units"},
                                    responses = {
                                    @ApiResponse(responseCode = "200", description = "success", content = @Content(schema = @Schema(implementation = UnitsModel.class))),
                                    @ApiResponse(responseCode = "404", description = "Not found")},
                                    parameters = {@Parameter(in = ParameterIn.PATH, name = "unit_Id")}
                            ))
            })


    RouterFunction<ServerResponse> routeUnit() throws Exception {

        return RouterFunctions
                .route(GET(Constants.UnitPaths.UNIT_ID),
                        request -> unitService.getUnitById(request.pathVariable("unit_Id"))
                                .flatMap(data-> ok().contentType(MediaType.APPLICATION_JSON).bodyValue(data)))

                .andRoute(RequestPredicates.POST(Constants.UnitPaths.ADDUNIT)
                                .and(RequestPredicates.contentType(MediaType.APPLICATION_JSON)),
                        request -> request.body(BodyExtractors.toMono(UnitsModel.class))
                                .flatMap(u -> unitService.add(u))
                                .flatMap(data -> ok().contentType(MediaType.APPLICATION_JSON).bodyValue(data)))

                .andRoute(RequestPredicates.POST(Constants.UnitPaths.UPDATE)
                                .and(RequestPredicates.contentType(MediaType.APPLICATION_JSON)),
                        request -> request.body(BodyExtractors.toMono(UnitsModel.class))
                                .flatMap(u -> unitService.update(u))
                                .flatMap(data -> ok().contentType(MediaType.APPLICATION_JSON).bodyValue(data)))
                .andRoute(RequestPredicates.DELETE(Constants.UnitPaths.DELETE),
                        request -> unitService.deleteUnit(request.pathVariable("unit_Id"))
                                .flatMap(data -> ServerResponse.noContent().build()))

                .andRoute(GET(Constants.UnitPaths.GETUNITS),
                        request -> unitService.getUnits().collectList()
                                .flatMap(data-> ok().contentType(MediaType.APPLICATION_JSON).bodyValue(data)));
    }

}
