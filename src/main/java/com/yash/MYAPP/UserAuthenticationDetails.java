package com.yash.MYAPP;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("userAuthenticationDetails")
public class UserAuthenticationDetails {

//    @Id
    private Long id; // 7-digit integer

    @Id
    private String username;

    private String password;

    private String salt;

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserAuthenticationDetails() {

    }

    public UserAuthenticationDetails(String username, String password, String salt) {
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.id = 1L;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
