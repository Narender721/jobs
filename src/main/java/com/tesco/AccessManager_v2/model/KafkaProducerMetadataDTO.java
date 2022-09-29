package com.tesco.AccessManager_v2.model;

import lombok.Data;

@Data
public class KafkaProducerMetadataDTO {

    long offset;

    int partition;
}
