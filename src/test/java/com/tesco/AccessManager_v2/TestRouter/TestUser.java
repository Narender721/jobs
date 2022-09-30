package com.tesco.AccessManager_v2.TestRouter;

import com.tesco.AccessManager_v2.model.UnitsModel;
import com.tesco.AccessManager_v2.model.UserModel;
import com.tesco.AccessManager_v2.repository.UserRepository;
import com.tesco.AccessManager_v2.utils.Constants;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@AutoConfigureWebTestClient
public class TestUser {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private WebTestClient webTestClient;

    UnitsModel store1 = new UnitsModel(1, "Store 1");
    UnitsModel store2 = new UnitsModel(2, "Store 2");
    UnitsModel store3 = new UnitsModel(3, "Store 3");
    UserModel data1 = new UserModel(1,"User 1", store1);
    Mono<UserModel> data1_Mono = Mono.just(data1);
    UserModel data2 = new UserModel(2,"User 2", store2);
    Mono<UserModel> data2_Mono = Mono.just(data2);
    UserModel data3 = new UserModel(3,"User 3", store3);
    Mono<UserModel> data3_Mono = Mono.just(data3);

    Flux<UserModel> data_Flux = Flux.just(data1,data2,data3);
    Flux<UserModel> data_Null = Flux.empty();

    @Test
    public void testGetAllUsers(){
        Mockito.when(userRepository.findAll()).thenReturn(data_Flux);
        Flux<UserModel> responseBody = webTestClient.get().uri(Constants.UserPaths.GET_USERS)
                .exchange()
                .expectStatus().isOk()
                .returnResult(UserModel.class)
                .getResponseBody();

        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNext(data1)
                .expectNext(data2)
                .expectNext(data3)
                .verifyComplete();

    }

    @Test
    public void testGetUserById(){

        Mockito.when(userRepository.findById(1)).thenReturn(data1_Mono);

        webTestClient.get().uri("/user/getUser/1").accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isOk()
                .expectBody(UserModel.class).value(response -> {
                    Assertions.assertThat(response.getUser_Id()).isEqualTo(1);
                    Assertions.assertThat(response.getUser_Name()).isEqualTo("User 1");
                    Assertions.assertThat(response.getUnitsModel()).isEqualTo(store1);
                });
    }

    @Test
    public void testGetUserByUnitId(){

        Mockito.when(userRepository.findUsersByUnitId(1)).thenReturn(data_Flux);

        Flux<UserModel> responseBody = webTestClient.get().uri("/user/getUsersByUnit/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(UserModel.class)
                .getResponseBody();

        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNext(data1)
                .expectNext(data2)
                .expectNext(data3)
                .verifyComplete();

    }

    @Test
    public void testAddUser() throws Exception {
        Mockito.when(userRepository.save(data1)).thenReturn(data1_Mono);

        webTestClient.post().uri(Constants.UserPaths.ADD_USER)
                .body(Mono.just(data1),UserModel.class)
                .exchange().expectStatus().isOk()
                .expectBody(UserModel.class).isEqualTo(data1);
    }

    @Test
    public void testUpdateUser() throws Exception {
        Mockito.when(userRepository.save(data2)).thenReturn(data2_Mono);
        Mockito.when(userRepository.findById(data2.getUser_Id())).thenReturn(data2_Mono);

        webTestClient.post().uri(Constants.UserPaths.UPDATE_USER)
                .body(Mono.just(data2),UserModel.class)
                .exchange().expectStatus().isOk();
    }

    @Test
    public void testDeleteUser(){
        Mockito.when(userRepository.findById(1)).thenReturn(data1_Mono);
        Mockito.when(userRepository.deleteById(1)).thenReturn(Mono.empty());

        webTestClient.delete().uri("/user/delete/1")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void testGetAllUsersNull(){
        Mockito.when(userRepository.findAll()).thenReturn(data_Null);
        webTestClient.get().uri(Constants.UserPaths.GET_USERS)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .json("{\"message\":\"Database empty\"}");
    }

    @Test
    public void testGetUsersByIDNull(){
        webTestClient.get().uri("/user/getUser/0")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .json("{\"message\":\"Provide proper ID value\"}\n");
    }

    @Test
    public void testGetUserByIDNotFound(){
        Mockito.when(userRepository.findById(1)).thenReturn(Mono.empty());
        webTestClient.get().uri("/user/getUser/1")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .json("{\"message\":\"1 not found\"}\n");
    }

    @Test
    public void testDeleteUserIdNull(){
        String uri="/user/delete/0";
        System.out.println(webTestClient.delete().uri(uri)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .json("{\"message\":\"Provide proper ID value\"}\n"));
    }

    @Test
    public void testDeleteUserIDNotFound(){
        Mockito.when(userRepository.findById(1)).thenReturn(Mono.empty());

        webTestClient.get().uri("/user/delete/1")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody();
//                .json("{\"message\":\"1 not found\"}\n");

    }

    @Test
    public void testAddUserIdNull(){
        Mockito.when(userRepository.findById(0)).thenReturn(Mono.empty());
        webTestClient.post().uri("/user/addUser")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody();
    }

    @Test
    public void testUpdateUserIdNull(){
        Mockito.when(userRepository.findById(1)).thenReturn(Mono.empty());
        webTestClient.post().uri("/user/update")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody();
    }
}
