package com.altimetrik.kafka.controller;


import com.altimetrik.kafka.domain.LibraryEvent;
import com.altimetrik.kafka.enums.LibraryEventType;
import com.altimetrik.kafka.producer.LibraryEventProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class LibraryEventsController {

    @Autowired
    LibraryEventProducer libraryEventProducer;

    @PostMapping("/v1/libraryEvent")
    public ResponseEntity<LibraryEvent> createLibraryEvent(@RequestBody LibraryEvent libraryEvent) throws JsonProcessingException {
        libraryEvent.setLibraryEventType(LibraryEventType.NEW);
        log.info("before send Library");
        //libraryEventProducer.sendLibraryEvent(libraryEvent);
        libraryEventProducer.sendLibraryEventSend(libraryEvent);
        log.info("after send Library");
        return ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent);
    }

    @PutMapping("/v1/libraryEvent")
    public ResponseEntity<?> updateLibraryEvent(@RequestBody  LibraryEvent libraryEvent) throws JsonProcessingException {
        if(libraryEvent.getLibraryEventId() ==  null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please pass Library Event Id");
        }
        libraryEvent.setLibraryEventType(LibraryEventType.UPDATE);
        log.info("before send Library");
        //libraryEventProducer.sendLibraryEvent(libraryEvent);
        libraryEventProducer.sendLibraryEventSend(libraryEvent);
        log.info("after send Library");
        return ResponseEntity.status(HttpStatus.OK).body(libraryEvent);
    }
}
