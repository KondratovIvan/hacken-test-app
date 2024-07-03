package org.example.hackentestapp.web;

import lombok.RequiredArgsConstructor;
import org.example.hackentestapp.domain.CSVRecord;
import org.example.hackentestapp.service.CsvService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/csv")
public class FileUploadController {

    private final CsvService csvService;

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            Path filePath = Paths.get(Objects.requireNonNull(file.getOriginalFilename()));
            Files.write(filePath, file.getBytes());

            csvService.parseCsvFile(filePath);

            Files.delete(filePath);

            return "CSV file was parsed successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "CSV file parsing failure";
        }
    }

    @GetMapping("/search")
    public List<CSVRecord> getCSVRecordByNameAndValue(@RequestParam String name, @RequestParam String value) {
        return csvService.dataSearch(name, value);
    }
}
