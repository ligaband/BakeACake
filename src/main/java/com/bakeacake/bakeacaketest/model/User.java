package com.bakeacake.bakeacaketest.model;


import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;
    private String name;
    private String username;
    private String password;
    private String email;
    private String secretQuestion;
    private String secretAnswer;


    public User(Integer id, String name, String username, String email) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;

    }


    public User(String name, String username, String password, String email, String secretQuestion, String secretAnswer) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.secretQuestion = secretQuestion;
        this.secretAnswer = secretAnswer;
    }


}
