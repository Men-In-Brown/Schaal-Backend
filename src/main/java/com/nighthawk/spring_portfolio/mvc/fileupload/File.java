package com.nighthawk.spring_portfolio.mvc.fileupload;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Lob
    private byte[] data;
    
    private String fileName;

    public File() {
        this.data = data;
        this.fileName = fileName;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] newData) {
        this.data = newData;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String newfileName) {
        this.fileName = newfileName;
    }
}
