package com.nighthawk.spring_portfolio.mvc.linkrAuthentication;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.nighthawk.spring_portfolio.mvc.person.Person;
import com.nighthawk.spring_portfolio.mvc.person.PersonDetailsService;
import com.nighthawk.spring_portfolio.mvc.person.PersonJpaRepository;

import groovy.transform.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.NoArgsConstructor;

@Entity
@Table
public class LinkrPAT {

    @Autowired
    PersonDetailsService personJpaRepository;

    @Id
    @Temporal(TemporalType.TIMESTAMP)
    private Date creation; 
    

    private String PAT;

    @OneToOne
    private Person user;

    //private Employee attachedUser;
    public LinkrPAT(){}
    public LinkrPAT(String email){
        this.creation = new Date();
        this.PAT = "Hello";
        this.user = personJpaRepository.getByEmail(email);
    }

    public void setPAT(String _PAT){
        this.PAT = _PAT;
    }

    public String getPAT() {
        return this.PAT;
    }

    public Person getUser(){
        return this.user;
    }

    public Date getDate(){
        return this.creation;
    }
    
    public static LinkrPAT[] init(){
        LinkrPAT l1 = new LinkrPAT("spk@gmail.com");
        LinkrPAT l2 = new LinkrPAT("mort@gmail.com");
        LinkrPAT l3 = new LinkrPAT("spark@gmail.com");
        LinkrPAT[] list = {l1, l2, l3};
        return list;
    }
    
}
