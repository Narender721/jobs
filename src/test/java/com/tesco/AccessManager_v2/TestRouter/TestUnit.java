package com.tesco.AccessManager_v2.TestRouter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.tesco.AccessManager_v2.model.UnitsModel;
import com.tesco.AccessManager_v2.repository.UnitsRepository;
import com.tesco.AccessManager_v2.router.UnitRouter;
import com.tesco.AccessManager_v2.utils.Constants;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static reactor.core.publisher.Mono.when;

@SpringBootTest
@AutoConfigureWebTestClient
public class TestUnit {

    @MockBean
    private UnitsRepository unitsRepository;
    @Autowired
    private WebTestClient webTestClient;

    UnitsModel data1 = new UnitsModel(1,"Store_1");
    UnitsModel data2 = new UnitsModel(2,"Store_2");
    UnitsModel data3 = new UnitsModel(3,"Store_3");
    UnitsModel data4 = new UnitsModel();
    UnitsModel data5 = new UnitsModel();
    Mono<UnitsModel> data1_Mono = Mono.just(data1);
    Mono<UnitsModel> data2_Mono = Mono.just(data2);
    Mono<UnitsModel> data3_Mono = Mono.just(data3);

    Flux<UnitsModel> data_Flux = Flux.just(data1,data2,data3);

    Flux<UnitsModel> data_Null = Flux.empty();

    @Test
    public void testGetAllUnits(){
        Mockito.when(unitsRepository.findAll()).thenReturn(data_Flux);

        Flux<UnitsModel> responseBody = webTestClient.get().uri(Constants.UnitPaths.GETUNITS)
                .exchange()
                .expectStatus().isOk()
                .returnResult(UnitsModel.class)
                .getResponseBody();

        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNext(new UnitsModel(1,"Store_1"))
                .expectNext(new UnitsModel(2,"Store_2"))
                .expectNext(new UnitsModel(3,"Store_3"))
                .verifyComplete();

    }

    @Test
    public void testGetUnitById(){

        Mockito.when(unitsRepository.findById(1)).thenReturn(data1_Mono);

        webTestClient.get().uri("/unit/getUnit/1").accept(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk()
                .expectBody(UnitsModel.class).value(response -> {
                    Assertions.assertThat(response.getUnit_Id()).isEqualTo(1);
                    Assertions.assertThat(response.getUnit_Name()).isEqualTo("Store_1");
                });
    }

    @Test
    public void testAddUnit() throws Exception {
        Mockito.when(unitsRepository.save(data1)).thenReturn(data1_Mono);

        webTestClient.post().uri(Constants.UnitPaths.ADDUNIT)
                .body(Mono.just(data1),UnitsModel.class)
                .exchange().expectStatus().isOk()
                .expectBody(UnitsModel.class).isEqualTo(data1);
    }

    @Test
    public void testUpdateUnit() throws Exception {
        Mockito.when(unitsRepository.save(data2)).thenReturn(data2_Mono);
        Mockito.when(unitsRepository.findById(data2.getUnit_Id())).thenReturn(data2_Mono);

        webTestClient.post().uri(Constants.UnitPaths.UPDATE)
                .body(Mono.just(data2),UnitsModel.class)
                .exchange().expectStatus().isOk();
    }

    @Test
    public void testDeleteUnit(){
        Mockito.when(unitsRepository.findById(1)).thenReturn(data1_Mono);
        Mockito.when(unitsRepository.deleteById(1)).thenReturn(Mono.empty());

        webTestClient.delete().uri("/unit/delete/1")
                .exchange()
                .expectStatus().isOk();
    }


    //        webTestClient.post().uri("/addUnit")
//                .accept(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk()
//                .expectBody()
//                .jsonPath
//                .value(response ->{
//                    MockMvcResultMatchers.jsonPath("$",notNullValue());
//                    jsonPath("$.unit_Name",is("Store_1"));
//                });


//    @Test
//    public void testGetAllUnits(){
//        Mockito.when(unitsRepository.findAll()).thenReturn(data_Flux);
//
//        webTestClient.get().uri("/units").accept(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk()
//                .expectBody(UnitsModel.class).value(response -> {
//                    Assertions.assertThat(response).
//                });
//    }

    @Test
    public void testGetAllUnitsNull(){
        Mockito.when(unitsRepository.findAll()).thenReturn(data_Null);

//        Flux<UnitsModel> responseBody =

        webTestClient.get().uri(Constants.UnitPaths.GETUNITS)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .json("{\"message\":\"Database empty\"}");
    }

    @Test
    public void testGetUnitsByIDNull(){

        webTestClient.get().uri("/unit/getUnit/0")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .json("{\"message\":\"Provide proper ID value\"}\n");
    }

    @Test
    public void testGetUnitByIDNotFound(){
        Mockito.when(unitsRepository.findById(1)).thenReturn(Mono.empty());

        webTestClient.get().uri("/unit/getUnit/1")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .json("{\"message\":\"1 not found\"}\n");
    }

    @Test
    public void testDeleteUnitIdNull(){
        String uri="/unit/delete/0";
        System.out.println(webTestClient.delete().uri(uri)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .json("{\"message\":\"Provide proper ID value\"}\n"));

    }

    @Test
    public void testDeleteUnitIDNotFound(){
        Mockito.when(unitsRepository.findById(1)).thenReturn(Mono.empty());

        webTestClient.get().uri("/unit/getUnit/1")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .json("{\"message\":\"1 not found\"}\n");
    }

    @Test
    public void testAddUnitIdNull(){

        Mockito.when(unitsRepository.findById(0)).thenReturn(Mono.empty());
        webTestClient.post().uri("/unit/addUser")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody();
//                .json("{\"message\":\"Provide proper ID value\"}\n");
    }

    @Test
    public void testUpdateUnitIdNull(){
        Mockito.when(unitsRepository.findById(1)).thenReturn(Mono.empty());

        webTestClient.post().uri("/unit/update")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody();
//                .json("{\"message\":\"Provide proper ID value\"}\n");
    }

//    @Test
//    public void test(){
//
//        webTestClient.post().uri("/unit/update")
//                .exchange()
//                .expectStatus().is5xxServerError();
//    }
}