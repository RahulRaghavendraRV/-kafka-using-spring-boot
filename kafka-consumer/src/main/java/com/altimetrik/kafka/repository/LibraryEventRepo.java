package com.altimetrik.kafka.repository;

import com.altimetrik.kafka.domain.LibraryEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryEventRepo extends JpaRepository<LibraryEvent,Integer> {
}
