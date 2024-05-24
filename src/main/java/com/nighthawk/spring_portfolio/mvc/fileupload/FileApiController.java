package com.nighthawk.spring_portfolio.mvc.fileupload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/fileupload/")
public class FileApiController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    private FileJpaRepository repository;

    @GetMapping("/")
    public ResponseEntity<List<File>> getFileName() {
        return new ResponseEntity<>( repository.findAllByOrderByFileNameAsc(), HttpStatus.OK);
    }

    // @GetMapping("/{id}")
    // public ResponseEntity<Grade> getScore(@PathVariable long id) {
    //     Optional<Grade> optional = repository.findById(id);
    //     if (optional.isPresent()) {  // Good ID
    //         Grade activity = optional.get();  // value from findByID
    //         return new ResponseEntity<>(activity, HttpStatus.OK);  // OK HTTP response: status code, headers, and body
    //     }
    //     // Bad ID
    //     return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    // }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,
                                             @RequestParam("name") String name,
                                             @RequestParam("assignment") String assignment) {
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

            String updatedName = name.replace(" ", "%20");
            String updatedAssignment = assignment.replace(" ", "%20");
            String updatedFileName = newFileName.replace(" ", "%20");

            File upload = new File(newFileName, name, assignment, "/volumes/uploads/" + updatedName + "/" + updatedAssignment + "/" + updatedFileName);

            // File upload = new File(newFileName, name, assignment, "./volumes/uploads/" + name + "/" + assignment + "/" + newFileName);
            repository.save(upload);

            return new ResponseEntity<>("File uploaded successfully", HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("File upload failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
