package com.nighthawk.spring_portfolio.mvc.fileupload;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity 
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    private Long id;
    private String fileName;
    private String name;
    private String assignment;
    private String path;

    public File(String fileName, String name, String assignment, String path) {
        this.fileName = fileName;
        this.name = name;
        this.assignment = assignment;
        this.path = path;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String newfileName) {
        this.fileName = newfileName;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public String getAssignment() {
        return assignment;
    }

    public void setAssignment(String newAssignment) {
        this.fileName = newAssignment;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String newPath) {
        this.path = newPath;
    }

    public String toString() {
        return "student_submission [id=" + id + ", name=" + name + ", assignment=" + assignment + ", path=" + path + "fileName=" + fileName + "]";
    }
}
