package com.nighthawk.spring_portfolio.mvc.fileupload;

import org.springframework.beans.factory.annotation.Value;
// import org.springframework.core.io.UrlResource;
// import org.springframework.core.io.Resource;
// import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
// import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
// import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
// import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class FileApiController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    // @GetMapping("/files/{name}")
    // public ResponseEntity<List<String>> getFiles(@PathVariable("name") String name, @PathVariable("assignment") String assignment) {
    //     try {
    //         // Construct the directory path based on user name and assignment
    //         Path userDir = Paths.get(uploadDir, name, assignment);
    //         if (!Files.exists(userDir)) {
    //             return ResponseEntity.notFound().build();
    //         }

    //         // List all files in the directory
    //         try (Stream<Path> paths = Files.list(userDir)) {
    //             List<String> files = paths
    //                     .map(path -> path.getFileName().toString())
    //                     .collect(Collectors.toList());
    //             return ResponseEntity.ok().body(files);
    //         }
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    //     }
    // }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("name") String name, @RequestParam("assignment") String assignment) {
        try {
            // Ensure the base upload directory exists
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Ensure the subdirectory named after the 'name' parameter exists
            Path userDir = uploadPath.resolve(name).resolve(assignment);
            if (!Files.exists(userDir)) {
                Files.createDirectories(userDir);
            }

            int counter = 1;

            String originalFileName = file.getOriginalFilename();
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String baseFileName = name + "_" + assignment;
            String newFileName = baseFileName + "_" + counter + fileExtension;
            Path filePath = userDir.resolve(newFileName);

            // If file exists, rename it with an incrementing counter
            while (Files.exists(filePath)) {
                newFileName = baseFileName + "_" + counter + fileExtension;
                filePath = userDir.resolve(newFileName);
                counter++;
            }

            // Save the file to the specified subdirectory
            Files.copy(file.getInputStream(), filePath);

            System.out.println("Received name: " + name);
            System.out.println("Received assignment: " + assignment);
            System.out.println("Original file name: " + originalFileName);
            System.out.println("Base file name: " + baseFileName);
            System.out.println("Saved as: " + newFileName);

            return new ResponseEntity<>("File uploaded successfully", HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("File upload failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
