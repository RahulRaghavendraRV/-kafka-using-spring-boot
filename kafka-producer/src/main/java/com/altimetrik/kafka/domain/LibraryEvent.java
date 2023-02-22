package com.altimetrik.kafka.domain;


import com.altimetrik.kafka.enums.LibraryEventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LibraryEvent {

    private Integer  libraryEventId;
    private LibraryEventType libraryEventType;
    private Book book;

}
