package com.nighthawk.spring_portfolio.mvc.chat;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Entity (name = "chat")
public class Chat {
	
	public Chat() {}

    @Id 
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @NotNull
    @Column(name = "message")
    private String message;
    
    @NotNull
    @Column(name = "email")
    private String email;
    
    @NotNull
    @Column(name = "person_id")
    private Long personId;
    
    @NotNull
    @Column(name = "from_person_id")
    private Long fromPersonId;
    
    @NotNull
    @Column(name = "from_email")
    private String fromEmail;
    
    @NotNull
    @Column(name = "read_flag")
    private boolean readFlag = false;
    
    @Column(name = "date_sent")
    private Date dateSent;
    
    @Column(name = "date_read")
    private Date dateRead;
    

    /*@ManyToOne
    @JoinColumn(name = "id")
    private Person person;*/

    public Chat (String email, String message, String fromEmail, long personId, long fromPersonId){
        this.email = email;
        this.message = message;
        this.fromEmail = fromEmail;
        this.readFlag = false;
        this.personId = personId;
        this.fromPersonId = fromPersonId;
        this.dateSent = new Date(System.currentTimeMillis());
    }

    public Long getId() {
        return id;
    }
    
    public Long getPersonId() {
        return personId;
    }
    
    public Long getFromPersonId() {
        return fromPersonId;
    }

    public String getMessage() {
        return message;
    }

    public String getEmail() {
        return email;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public boolean isReadFlag(){
        return readFlag;
    }
    
    public void setReadFlag(boolean readFlag) {
    	this.readFlag = readFlag;
    }
    public Date getDateSent() {
		return dateSent;
	}

	public void setDateSent(Date dateSent) {
		this.dateSent = dateSent;
	}

	public Date getDateRead() {
		return dateRead;
	}

	public void setDateRead(Date dateRead) {
		this.dateRead = dateRead;
	}
	
	
}
