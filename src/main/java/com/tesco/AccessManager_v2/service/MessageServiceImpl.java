package com.tesco.AccessManager_v2.service;

import com.tesco.AccessManager_v2.model.KafkaProducerMetadataDTO;
import com.tesco.AccessManager_v2.model.UserModel;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import reactor.core.publisher.Mono;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.ExecutionException;

@Service
@Slf4j
public class MessageServiceImpl implements MessageService{

    @Autowired
    KafkaTemplate<String,UserModel> UserkafkaTemplate;

    @Value("${cloudkafka.topic}")
    String topicName;

    @Autowired
    KafkaProperties kafkaProperties;

    @Bean
    public ConsumerFactory<String, UserModel> userModelConsumerFactory() {
        JsonDeserializer<UserModel> jsonDeserializer = new JsonDeserializer<UserModel>();
        return new DefaultKafkaConsumerFactory<String,UserModel>(
                kafkaProperties.buildConsumerProperties(), new StringDeserializer(), jsonDeserializer
        );
    }

    @Override
    public Mono<UserModel> retrieveMessage(KafkaProducerMetadataDTO kafkaProducerMetadataDTO) {


        UserkafkaTemplate.setConsumerFactory(userModelConsumerFactory());
        int part = (kafkaProducerMetadataDTO.getPartition());
        long off = (kafkaProducerMetadataDTO.getOffset());
//        Consumer<String, UserModel> consumer = userModelConsumerFactory().createConsumer();
//
//        Collection<TopicPartition> partitions = consumer.partitionsFor(topicName).stream().map(p -> new TopicPartition(topicName, p.partition()))
//                .collect(Collectors.toList());
//
//        consumer.assign(partitions);
//        consumer.seekToEnd(partitions);
//
//        Map<TopicPartition, Long> endPartitions = partitions.stream().collect(Collectors.toMap(Function.identity(), consumer::position));
//
//        List<TopicPartitionOffset> topicPartitionOffsetList = new ArrayList<>();
//
//        endPartitions.forEach((key,value)->{
//            String topic = key.topic();
//            System.out.println("partition::" +key.partition());
//            long offsetPartition = value;
//            System.out.println("offsetPartition::" +offsetPartition);
//
//            for(int i=0;i<=offsetPartition;i++) {
//                TopicPartitionOffset topicPartitionOffset = new TopicPartitionOffset(topic,key.partition(),Long.valueOf(i));
//                topicPartitionOffsetList.add(topicPartitionOffset);
//            }
//        });
//        System.out.println("size::" +topicPartitionOffsetList.size());
//        ConsumerRecords<String, UserModel> orderRecord = UserkafkaTemplate.receive(topicPartitionOffsetList);
        ConsumerRecord<String, UserModel> orderRecord = UserkafkaTemplate.receive(topicName,part,off);

        assert orderRecord != null;
        log.debug(orderRecord.value().getUser_Id() + "ID" + orderRecord.value().getUser_Name() + "offset" + orderRecord.offset());
//                (record -> System.out.println(record.value().getUser_Id()+" ID "+record.value().getUser_Name()+" NAME "+record.offset()));

        return Mono.just(orderRecord.value());
//        return new UserModel();
    }

//    @Override
//    public Mono<KafkaProducerMetadataDTO> sendMessage(UserModel userModel) throws InterruptedException {
//        ListenableFuture<SendResult<String, UserModel>> future =UserkafkaTemplate.send(topicName, userModel);
//        Thread.sleep(1000);
//        KafkaProducerMetadataDTO producerMetadata = new KafkaProducerMetadataDTO();
//        future.addCallback(new ListenableFutureCallback<SendResult<String, UserModel>>() {
//
//            @Override
//            public void onSuccess(SendResult<String, UserModel> result) {
//                RecordMetadata metaData = result.getRecordMetadata();
//                producerMetadata.setOffset(metaData.offset());
//                System.out.println("Offset" + metaData.offset());
//                producerMetadata.setPartition(metaData.partition());
//                System.out.println("PARTITION" + metaData.partition());
//
//            }
//
//            @Override
//            public void onFailure(Throwable ex) {
//                // throw exception
//            }
//        });
//        System.out.println(producerMetadata.getOffset());
//        System.out.println(producerMetadata.getPartition());
//        Thread.sleep(1000);
//        return Mono.just(producerMetadata);
//    }

    public Mono<KafkaProducerMetadataDTO> sendMessage(UserModel userModel) {
        ListenableFuture<SendResult<String, UserModel>> future =UserkafkaTemplate.send(topicName, userModel);
        KafkaProducerMetadataDTO producerMetadata = new KafkaProducerMetadataDTO();

        try {
            SendResult<String, UserModel> result = future.get();
            RecordMetadata metaData = result.getRecordMetadata();
            producerMetadata.setOffset(metaData.offset());
            System.out.println("Offset" + metaData.offset());
            producerMetadata.setPartition(metaData.partition());
            System.out.println("PARTITION" + metaData.partition());
        } catch (InterruptedException | ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        /*
         * future.addCallback(new ListenableFutureCallback<SendResult<String,
         * UserModel>>() {
         *
         * @Override public void onSuccess(SendResult<String, UserModel> result) {
         * RecordMetadata metaData = result.getRecordMetadata();
         * producerMetadata.setOffset(metaData.offset());
         * producerMetadata.setPartition(metaData.partition());
         *
         * }
         *
         * @Override public void onFailure(Throwable ex) { // throw exception }
         *
         * });
         */
                System.out.println(producerMetadata.getOffset());
        System.out.println(producerMetadata.getPartition());
        return Mono.just(producerMetadata);
    }
}
