package com.tesco.AccessManager_v2.TestRouter;

import com.tesco.AccessManager_v2.model.LocationApiDTO;
import com.tesco.AccessManager_v2.utils.Constants;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@AutoConfigureWebTestClient
@ExtendWith(MockitoExtension.class)
public class TestLocation {

    @Autowired
    private static WebTestClient webTestClient;

    @BeforeAll
    private static void setup(){
        webTestClient = WebTestClient.bindToServer()
                .baseUrl(Constants.LocationPaths.LOCATIONAPI_URI)
                .build();
    }

    @Test
    public void testGetLocation(){

//        when(webClientMock.ge)
        webTestClient.get().uri(Constants.LocationPaths.GET_LOCATION)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isUnauthorized()
                .expectBody(LocationApiDTO.class);
    }
}
