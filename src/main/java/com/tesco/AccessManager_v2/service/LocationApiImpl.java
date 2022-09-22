package com.tesco.AccessManager_v2.service;

import com.tesco.AccessManager_v2.model.LocationApiDTO;
import com.tesco.AccessManager_v2.model.UnitsModel;
import com.tesco.AccessManager_v2.model.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
@Slf4j
public class LocationApiImpl {

    private  WebClient webClient;

    public Flux<?> getLocation(){

        return WebClient.create().get().uri("https://api-ppe.tesco.com/tescolocation/v4/locations?fields=lifecycle&limit=100&offset=200")
                .header("X-Only-For-Testing-Purposes","true")
                .headers(auth -> auth.setBearerAuth("eyJraWQiOiIwNWU0ZjJkOS05MDY4LTQ5MGYtYTBmYy1jMTg4Y2NlODY5ZDciLCJhbGciOiJSUzI1NiJ9.eyJqdGkiOiJtOTZyekRhM0tPd0dPc1FDWE54aE13dE5qQWtXQ205ZnEzclUiLCJpc3MiOiJodHRwczovL2FwaS1wcGUudGVzY28uY29tL2lkZW50aXR5L3Y0L2lzc3VlLXRva2VuIiwic3ViIjoiNjQzNGYwNmUtMDI4ZC00YTRhLTk4MzUtNTYzNzk1MmQxODBlIiwiaWF0IjoxNjYzODU4NDc2LCJuYmYiOjE2NjM4NTg0NzYsImV4cCI6MTY2Mzg2MjA3Niwic2NvcGUiOiJpbnRlcm5hbCBwdWJsaWMiLCJjb25maWRlbmNlX2xldmVsIjoxMiwiY2xpZW50X2lkIjoiNjQzNGYwNmUtMDI4ZC00YTRhLTk4MzUtNTYzNzk1MmQxODBlIiwidG9rZW5fdHlwZSI6ImJlYXJlciJ9.SWXJdG4MxyTseO0vGDFGzyvxaJYDL_UMw1WLOrdzN_UOOk-_mYOqvTxxikJ09l85YDwESp4nhtfCyJ2tWhaE-OoKcXGD-T2DMTI_xaJqBkb8-QxAa3Or3X0tPmvND_nFhJ6nAek7nTNhG1ly_Sz5fA95qLMc1X4nOFJ_UPxcz0Ctkw8a0-ix6kto-8_EgJbILI2ejgL5JiuR4nX6UEXm_2Mym8fMp5UoPxy3AhU55Fx1Z-OUZCS3PB8czA0HxDGsVuYKAj-sKu7scLtAH4RYyqbqMo9cJhFYAgfgyHrO2hTjR93VjxYyAjK5vEMK1iKfByQ5KD4SHfRhHHlsa0vMaw"))
                .retrieve().bodyToFlux(LocationApiDTO.class);





    }
}
