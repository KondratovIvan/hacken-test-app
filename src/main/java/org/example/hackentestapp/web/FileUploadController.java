package org.example.hackentestapp.web;

import lombok.RequiredArgsConstructor;
import org.example.hackentestapp.service.CsvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequiredArgsConstructor
public class FileUploadController {

    private final CsvService csvService;

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            // Сохраняем файл локально
            Path filePath = Paths.get(file.getOriginalFilename());
            Files.write(filePath, file.getBytes());

            // Парсим CSV файл и сохраняем в БД
            csvService.parseCsvFile(filePath);

            // Удаляем временный файл
            Files.delete(filePath);

            return "redirect:/success";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/failure";
        }
    }
}
