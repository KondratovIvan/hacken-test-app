package org.example.hackentestapp.repository;

import org.example.hackentestapp.domain.CSVField;
import org.example.hackentestapp.domain.CSVRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CSVRecordRepository extends JpaRepository<CSVRecord, Long> {
    @Query("SELECT r FROM CSVRecord r JOIN r.fields f WHERE f.name = :name AND f.value = :value")
    List<CSVRecord> findCSVRecordsByNameAndValue(String name, String value);

}
