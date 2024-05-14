package com.ms.service.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/upload", produces = {"application/json"})
@Slf4j
@CrossOrigin("*")
public class UploadController {
    private final String pathFiles;

    public UploadController(@Value("${app.path.files}") String pathFiles){
        this.pathFiles = pathFiles;
    }

    @PostMapping("/file")
    public ResponseEntity<String> saveFile(@RequestParam("file") MultipartFile file) {
        log.info("Recebendo o arquivo: ", file.getOriginalFilename());
        var path = pathFiles + UUID.randomUUID() + "." + extractExtension(file.getOriginalFilename());

        log.info("Novo nome do arquivo: " + path);

        try {
            Files.copy(file.getInputStream(), Path.of(path), StandardCopyOption.REPLACE_EXISTING);
            return new ResponseEntity<>("{ \"mensagem\": \"Arquivo carregado com sucesso!\"}", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Erro ao processar arquivo", e);
            return new ResponseEntity<>("{ \"mensagem\": \"Erro ao carregar o arquivo!\"}", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String extractExtension(String nameFiles) {
        int i = nameFiles.lastIndexOf(".");
        return nameFiles.substring(i + 1);
    }

}
