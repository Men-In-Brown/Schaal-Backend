package com.nighthawk.spring_portfolio.mvc.linkr;

import com.nighthawk.spring_portfolio.mvc.calendar.Calendar;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter; 

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "User")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String position;
    private String email;
    private String password;
    private int followers;

    // change to hashmap
    private int ideas;
    private int joined; 

    private int investments; 

    @ManyToOne(fetch = FetchType.LAZY)
    private Calendar userCalendar;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "internship_id")
    private Internship internship;

    private UserRoles role;

    public User(String name, String position, String email, String password){
        this.name = name;
        this.position = position;
        this.email = email;
        this.password = password;
        this.ideas = 0;
        this.joined = 0;
        this.investments = 0;
    }

    public static User[] UserInit(){
        User e1 = new User("Tanay", "CEO", "tpatel@gmail.com", "123Tanay!");
        User e2 = new User("Varaprasad", "CTO", "vnibhanupudi@gmail.com", "123Vlu!");
        User e3 = new User("Paaras", "CFO", "ppurohit@gmail.com", "123Paras!");
        User e4 = new User("Tobias", "User", "toby@gmail.com", "123Toby");
        User e5 = new User("Hubert", "User", "hop@gmail.com", "123Hopper");
        User[] elist =  {e1, e2, e3, e4, e5};
        return elist;
    }
}