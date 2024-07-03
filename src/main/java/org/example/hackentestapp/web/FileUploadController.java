package org.example.hackentestapp.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.hackentestapp.domain.CSVRecord;
import org.example.hackentestapp.service.CsvService;
import org.springframework.http.HttpStatus;
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
@Tag(name = "CSV", description = "CSV API")
public class FileUploadController {

    private final CsvService csvService;

    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "This is endpoint to upload a new CSV file.", description = "Create request to upload your CSV file data.", tags = {"CSV"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. Your CSV file was successfully uploaded to database."),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified CSV request not found."),
            @ApiResponse(responseCode = "500", description = "Internal server error. Something in CSV logic went wrong.")})
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
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "This is endpoint to get CSV file record by name and value.", description = "Create request to get CSV file records by name and value.", tags = {"CSV"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. Your records was successfully  fetched from database."),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified CSV request not found."),
            @ApiResponse(responseCode = "500", description = "Internal server error. Something in CSV logic went wrong.")})
    public List<CSVRecord> getCSVRecordByNameAndValue(@RequestParam String name, @RequestParam String value) {
        return csvService.dataSearch(name, value);
    }
}
