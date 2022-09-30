package com.tesco.AccessManager_v2.exceptionHandler;

import com.tesco.AccessManager_v2.exception.DBEmptyException;
//import com.tesco.AccessManager_v2.exception.DuplicateEntryException;
//import com.tesco.AccessManager_v2.exception.DuplicateEntryException;
import com.tesco.AccessManager_v2.exception.IdNullException;
import com.tesco.AccessManager_v2.exception.UnitNotFoundException;
import org.springframework.boot.autoconfigure.web.WebProperties.Resources;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import java.util.Map;
import reactor.core.publisher.Mono;

@Component
public class GlobalExceptionHandler extends AbstractErrorWebExceptionHandler{

    public GlobalExceptionHandler(ErrorAttributes errorAttributes, Resources resources,
                                  ApplicationContext applicationContext,ServerCodecConfigurer serverCodecConfigurer) {
        super(errorAttributes, resources, applicationContext);
        super.setMessageWriters(serverCodecConfigurer.getWriters());
        super.setMessageReaders(serverCodecConfigurer.getReaders());
        // TODO Auto-generated constructor stub
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        // TODO Auto-generated method stub
        return RouterFunctions.route(RequestPredicates.all(), this::renderException);
    }

    private Mono<ServerResponse> renderException(ServerRequest request){
        Map<String, Object> error = this.getErrorAttributes(request, ErrorAttributeOptions.defaults());
        Throwable errorThrowable = getError(request);
        error.remove("status");
        error.remove("requestId");
        if(errorThrowable instanceof UnitNotFoundException) {

            return ServerResponse.status(HttpStatus.NOT_FOUND).
                    contentType(MediaType.APPLICATION_JSON).
                    body(BodyInserters.fromValue(error));
        }

        if(errorThrowable instanceof IdNullException) {

            return ServerResponse.status(HttpStatus.NOT_FOUND).
                    contentType(MediaType.APPLICATION_JSON).
                    body(BodyInserters.fromValue(error));
        }

//        if(errorThrowable instanceof DuplicateEntryException){
//            return ServerResponse.status(HttpStatus.NOT_FOUND)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .body(BodyInserters.fromValue(error));
//        }

        if(errorThrowable instanceof DBEmptyException){
            return ServerResponse.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(error));
        }

        return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).
                contentType(MediaType.APPLICATION_JSON).
                body(BodyInserters.fromValue(error));

    }

}
