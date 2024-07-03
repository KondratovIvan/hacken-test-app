package org.example.hackentestapp.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.hackentestapp.domain.CSVField;
import org.example.hackentestapp.repository.CSVRecordRepository;
import org.springframework.stereotype.Service;
import com.opencsv.CSVReader;
import org.example.hackentestapp.domain.CSVRecord;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CsvService {

    private final CSVRecordRepository csvRecordRepository;

    @Transactional
    public void parseCsvFile(Path filePath) {
        try (Reader reader = Files.newBufferedReader(filePath);
             CSVReader csvReader = new CSVReader(reader)) {

            String[] headers = csvReader.readNext();
            String[] nextRecord;

            while ((nextRecord = csvReader.readNext()) != null) {
                CSVRecord csvRecord = new CSVRecord();
                Set<CSVField> fields = new HashSet<>();

                for (int i = 0; i < headers.length; i++) {
                    CSVField field = new CSVField();
                    field.setName(headers[i]);
                    field.setValue(nextRecord[i]);
                    field.setRecord(csvRecord);
                    fields.add(field);
                }

                csvRecord.setFields(fields);
                csvRecordRepository.save(csvRecord);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<CSVRecord> dataSearch(String name, String value) {
        return csvRecordRepository.findCSVRecordsByNameAndValue(name, value);
    }
}
