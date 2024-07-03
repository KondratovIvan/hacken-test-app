package org.example.hackentestapp.repository;

import org.example.hackentestapp.domain.CSVRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CSVRecordRepository extends JpaRepository<CSVRecord, Long> {
}
