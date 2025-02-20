package org.example.hackentestapp.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.transaction.Transactional;
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
public class CsvService {
    private final CSVRecordRepository csvRecordRepository;
    private final MeterRegistry meterRegistry;
    private final Counter parseMethodCallCounter;
    private final Counter searchMethodCallCounter;

    public CsvService(CSVRecordRepository csvRecordRepository, MeterRegistry meterRegistry) {
        this.csvRecordRepository = csvRecordRepository;
        this.meterRegistry = meterRegistry;
        this.parseMethodCallCounter = Counter.builder("csv.parseMethod.calls")
                .description("The number of method calls to parseCsvFile")
                .register(meterRegistry);
        this.searchMethodCallCounter = Counter.builder("csv.searchMethod.calls")
                .description("The number of method calls to dataSearch")
                .register(meterRegistry);
    }

    @Transactional
    public void parseCsvFile(Path filePath) {
        parseMethodCallCounter.increment(); // Increment the counter each time the method is called
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
        searchMethodCallCounter.increment();
        return csvRecordRepository.findCSVRecordsByNameAndValue(name, value);
    }
}