package com.altimetrik.kafka.consumer;

import com.altimetrik.kafka.service.LibraryEventsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LibraryEventConsumer {

    @Autowired
    LibraryEventsService libraryEventsService;

    @KafkaListener(topics = {"library-events"} )
    public void onMessage(ConsumerRecord<Integer,String> consumerRecord) throws JsonProcessingException {
        log.info("consumerRecord {} ",consumerRecord);
        libraryEventsService.processLibraryEvent(consumerRecord);
    }
}
