package com.nighthawk.spring_portfolio.mvc.channel;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Channel {

    @Id @GeneratedValue
    @Column(name = "channel_id")
    private Long Id;
    private String name;
    private String desc;
    private String creator;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCreator() { 
        return creator; 
    }

    public void setCreator(String creator) { 
        this.creator = creator; 
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Channel channel = (Channel) o;
        return Objects.equals(Id, channel.Id) &&
                Objects.equals(name, channel.name) &&
                Objects.equals(desc, channel.desc) &&
                Objects.equals(creator, channel.creator);
    }

    public static Channel[] init() {
        // basics of class construction
        Channel c1 = new Channel();
        c1.setName("test channel");
        c1.setDesc("bruh why");
        c1.setCreator("landoooc");

        Channel c2 = new Channel();
        c2.setName("INDICATORS");
        c2.setDesc("i luv indicators");
        c2.setCreator("Mortenses");

    Channel channels[] = {c1, c2};
    return(channels);
    }
    public static void main(String[] args) {
        // obtain Person from initializer
        Channel channels[] = init();

        // iterate using "enhanced for loop"
        for( Channel channel : channels) {
            System.out.println(channel);  // print object
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, name, desc, creator);
    }
}