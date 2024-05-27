package com.nighthawk.spring_portfolio.mvc.hallpass;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import java.time.LocalDateTime;

@Entity
public class HallPass {

    @Id
    @GeneratedValue
    @Column(name = "hallpass_id")
    private Long id;

    @Column(name = "student_id")
    private String studentId;

    @Column(name = "teacher_id")
    private String teacherId;

    private String reason;

    @Column(name = "expiry_time")
    private LocalDateTime expiryTime;
    
    @Column(name = "status")
    private String status;  // New field

    // Constructors, Getters, and Setters

    public HallPass() {
    }

    public HallPass(String studentId, String teacherId, String reason, LocalDateTime expiryTime, String status) {
        this.studentId = studentId;
        this.teacherId = teacherId;
        this.reason = reason;
        this.expiryTime = expiryTime;
        this.status = status;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDateTime getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(LocalDateTime expiryTime) {
        this.expiryTime = expiryTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
