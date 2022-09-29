package com.tesco.AccessManager_v2.service;

import com.tesco.AccessManager_v2.model.KafkaProducerMetadataDTO;
import com.tesco.AccessManager_v2.model.UserModel;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public interface MessageService {

    Mono<UserModel> retrieveMessage(KafkaProducerMetadataDTO kafkaProducerMetadataDTO);

    Mono<KafkaProducerMetadataDTO> sendMessage(UserModel userModel) throws InterruptedException;
}
