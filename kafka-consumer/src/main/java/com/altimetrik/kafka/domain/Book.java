package com.altimetrik.kafka.domain;


import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer bookId;
    private String bookName;
    private String bookAuthor;
    @OneToOne
    @JoinColumn(name = "libraryEventId")
    @ToString.Exclude
    private LibraryEvent libraryEvent;
}
