package com.alper.bean.util;

import com.alper.model.menu.User;
import lombok.Getter;
import lombok.Setter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;



@ManagedBean
@SessionScoped
@Getter
@Setter

public class SessionsBean implements Serializable {

    private User user;
    public SessionsBean(){
        user = new User();
    }

}