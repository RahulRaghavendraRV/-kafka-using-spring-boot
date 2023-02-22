package com.altimetrik.kafka.producer;

import com.altimetrik.kafka.domain.LibraryEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
@Slf4j
public class LibraryEventProducer {

    @Autowired
    KafkaTemplate<Integer,String> kafkaTemplate;

    @Autowired
    ObjectMapper objectMapper;


    //sendDefault don't expect topic value
    public void sendLibraryEvent(LibraryEvent libraryEvent) throws JsonProcessingException {
        Integer key = libraryEvent.getLibraryEventId();
        String value = objectMapper.writeValueAsString(libraryEvent);
        CompletableFuture<SendResult<Integer, String>> future =kafkaTemplate.sendDefault(key,value);
        future.whenComplete((result, ex) -> {
            if (ex != null) {
                log.error("Error Sending the Message and the exception is {}", ex.getMessage());
            } else {
                log.info("Message sent SuccessFully for the Key : {} and the value is {}, partition is {}",key,value,result.getRecordMetadata());
            }
        });
    }

    public void sendLibraryEventSynchronous (LibraryEvent libraryEvent) throws JsonProcessingException {
        Integer key = libraryEvent.getLibraryEventId();
        String value = objectMapper.writeValueAsString(libraryEvent);
        try {
           SendResult<Integer,String> result = kafkaTemplate.sendDefault(key,value).get();
            log.info("Message sent SuccessFully for the Key : {} and the value is {}, partition is {}",key,value,result.getRecordMetadata());
        } catch (InterruptedException | RuntimeException | ExecutionException e) {
            log.error("Error Sending the Message and the exception is {}", e.getMessage());
        }

    }

    public void sendLibraryEventSend (LibraryEvent libraryEvent) throws JsonProcessingException {
        Integer key = libraryEvent.getLibraryEventId();
        String value = objectMapper.writeValueAsString(libraryEvent);
        CompletableFuture<SendResult<Integer, String>> future =kafkaTemplate.send(buildProducerRecord("library-events",key,value));
        future.whenComplete((result, ex) -> {
            if (ex != null) {
                log.error("Error Sending the Message and the exception is {}", ex.getMessage());
            } else {
                log.info("Message sent SuccessFully for the Key : {} and the value is {}, partition is {}",key,value,result.getRecordMetadata());
            }
        });
    }

    ProducerRecord<Integer,String> buildProducerRecord(String topic, Integer key , String value){
        List<Header> headers = List.of(new RecordHeader("event-source","scanner".getBytes(StandardCharsets.UTF_8)));
        return new ProducerRecord<>(topic,null,key,value,headers);
    }
}
