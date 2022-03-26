package com.alper.model.menu;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class User {
    private long userID;
    private String username;
    private String password;
    private String email;

    public String hidePassword(String pwd){
        String pass = "*";
        for(int i = 1; i < pwd.length(); i++){
            pass += "*";
        }
        return pass;
    }
}
