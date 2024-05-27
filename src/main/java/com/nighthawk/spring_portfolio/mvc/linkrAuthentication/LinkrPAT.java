package com.nighthawk.spring_portfolio.mvc.linkrAuthentication;

import java.util.Date;

import com.nighthawk.spring_portfolio.mvc.person.Person;
import com.vladmihalcea.hibernate.type.json.JsonType;

import groovy.transform.Generated;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor
@Table
@Convert(attributeName ="pat", converter = JsonType.class)
public class LinkrPAT {

    @Id
    @Temporal(TemporalType.TIMESTAMP)
    private Date creation; 
    
    private String PAT;

    private Person user;

    //private Employee attachedUser;

    public LinkrPAT(Person attachedUser){
        this.creation = new Date();
        this.PAT = "Hello";
        this.user = attachedUser;
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
        LinkrPAT l1 = new LinkrPAT(new Person());
        LinkrPAT l2 = new LinkrPAT(new Person());
        LinkrPAT l3 = new LinkrPAT(new Person());
        LinkrPAT[] list = {l1, l2, l3};
        return list;
    }
    
}
