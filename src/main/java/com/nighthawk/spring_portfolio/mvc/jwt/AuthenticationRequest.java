package com.nighthawk.spring_portfolio.mvc.jwt;

public class AuthenticationRequest {
    private String email;
    private String password;
    private String pat;


    public AuthenticationRequest(String email, String password, String pat){
        this.email = email;
        this.password = password;
        this.pat = pat;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPAT(){
        return pat;
    }

    public String getPassword() {
        return this.password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
