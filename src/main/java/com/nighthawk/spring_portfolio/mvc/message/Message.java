package com.nighthawk.spring_portfolio.mvc.message;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Message {

    @Id @GeneratedValue
    @Column(name = "message_id")
    private Long Id;

    private String text;
    private String writer;

    private String time;
    private String receiver;


    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getTime() { 
        return time; 
    }

    public void setTime(String time) { 
        this.time = time; 
    }

    public String getReceiver() { 
        return receiver; 
    }

    public void setReceiver(String receiver) { 
        this.receiver = receiver; 
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(Id, message.Id) &&
                Objects.equals(text, message.text) &&
                Objects.equals(writer, message.writer) &&
                Objects.equals(time, message.time) &&
                Objects.equals(receiver, message.receiver);
    }

    public static Message[] init() {
        // basics of class construction
        Message m1 = new Message();
        m1.setText("this is taking way tooo long");
        m1.setWriter("landoooc");
        m1.setTime("12:57");
        m1.setReceiver("1");

        Message m2 = new Message();
        m2.setText("INDICATORS!!!");
        m2.setWriter("mr_mort");
        m2.setTime("01:10");
        m2.setReceiver("1");

    Message messages[] = {m1, m2};
    return(messages);
    }
    public static void main(String[] args) {
        // obtain Person from initializer
        Message messages[] = init();

        // iterate using "enhanced for loop"
        for( Message message : messages) {
            System.out.println(message);  // print object
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, text, writer, time, receiver);
    }
}