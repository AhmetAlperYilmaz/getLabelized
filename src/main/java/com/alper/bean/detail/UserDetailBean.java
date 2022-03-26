package com.alper.bean.detail;

import com.alper.db.UserOperations;
import com.alper.model.menu.User;
import com.alper.util.CustomMessage;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.*;


@ManagedBean
@ViewScoped
@Getter
@Setter
public class UserDetailBean {

    private List<User> users;
    private User user;
    private UserOperations userOperations;

    public UserDetailBean() {
        user = new User();
        userOperations = new UserOperations();
    }

    @PostConstruct
    public void init() {
        setUsers(userOperations.listUsers());
    }

    public String saveUser() {
        Boolean checkUsername = validationCheck(user.getUsername());
        Boolean checkPassword = validationCheck(user.getPassword());
        Boolean checkEmail = validationCheck(user.getEmail());
        if (!(checkUsername && checkEmail && checkPassword)) {
            CustomMessage.addMessageError("Başarısız", "Veriler en az 4 karakter uzunluğunda ve en fazla 64 karakter uzunluğunda olmalı!!");
            return "/kullanici-islemleri/yeni-kullanici.xhtml";
        }

        if(!(emailCheck(user.getEmail()))){
            CustomMessage.addMessageError("Başarısız", "Yazdığınız email, email formatına uygun değil!!");
            return "/kullanici-islemleri/yeni-kullanici.xhtml";
        }
        if (userOperations.insertUser(getUser())) {
            CustomMessage.addMessageInfo("Onaylandı", "Kullanıcı başarıyla eklendi");
            setUsers(userOperations.listUsers());
            return "/kullanici-islemleri/index.xhtml";
        }
        CustomMessage.addMessageError("Başarısız", "Ekleme işlemi başarısız oldu. Girdiğiniz kullanıcı adı veya e-posta daha önceden alınmış.");
        return "/kullanici-islemleri/yeni-kullanici.xhtml";
    }

    public String registerUser() {
        Boolean checkUsername = validationCheck(user.getUsername());
        Boolean checkPassword = validationCheck(user.getPassword());
        Boolean checkEmail = validationCheck(user.getEmail());
        if (!(checkUsername && checkEmail && checkPassword)) {
            CustomMessage.addMessageError("Başarısız. Veriler en az 4 karakter uzunluğunda ve en fazla 64 karakter uzunluğunda olmalı!!", "");
            return "/register.xhtml";
        }

        if(!(emailCheck(user.getEmail()))){
            CustomMessage.addMessageError("Başarısız. Yazdığınız email, email formatına uygun değil!!", "");
            return "/register.xhtml";
        }
        if (userOperations.insertUser(getUser())) {
            CustomMessage.addMessageInfo("Onaylandı. Başarıyla kaydoldunuz.", "");
            setUsers(userOperations.listUsers());
            return "/login.xhtml";
        }
        CustomMessage.addMessageError("Başarısız. Ekleme işlemi başarısız oldu. Girdiğiniz kullanıcı adı veya e-posta daha önceden alınmış.", "");
        return "/register.xhtml";
    }


    public String deleteUser(User user){
        long deletingUserID = user.getUserID();
        if(userOperations.deleteUser(deletingUserID)) {
            setUsers(userOperations.listUsers());
            CustomMessage.addMessageInfo("Onaylandı", "Kullanıcı başarıyla silindi.");
        }else {
            CustomMessage.addMessageError("Başarısız", "Silme işlemi başarısız oldu.");
        }
        return "/kullanici-islemleri/index.xhtml?faces-redirect=true";
    }

    public Boolean validationCheck(String word){
        int wordLength = word.length();
        return wordLength >= 4 && wordLength <= 64;
    }

    public Boolean emailCheck(String word){
        if(!(word.contains("@"))){
            return false;
        }else {
            String lastFourDigits = word.substring(word.length() - 4);
            return lastFourDigits.equals(".com");
        }
    }

}
