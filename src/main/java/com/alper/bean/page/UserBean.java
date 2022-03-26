package com.alper.bean.page;

import com.alper.db.UserOperations;
import com.alper.model.menu.User;
import com.alper.util.CustomMessage;
import lombok.Getter;
import lombok.Setter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;


@ManagedBean
@RequestScoped // kullanıcı güncelleme kısmı için gerekli
@Getter
@Setter
public class UserBean {

    private User user;
    private UserOperations userOperations;

    public UserBean() {
        user = new User();
        userOperations = new UserOperations();
    }

    public String updatePage(User user){
        try{
            this.user.setUserID(user.getUserID());
            this.user.setUsername(user.getUsername());
            this.user.setPassword(user.getPassword());
            this.user.setEmail(user.getEmail());
            return "/kullanici-islemleri/guncelle-user.xhtml";
        }
        catch(Throwable e){
            e.printStackTrace();
            CustomMessage.addMessageFatal("Başarısız","Güncelle sayfasına ulaşılamadı");
            return "/kullanici-islemleri/index.xhtml";
        }
    }

    public String updateUser() {

        Boolean checkUsername = validationCheck(user.getUsername());
        Boolean checkPassword = validationCheck(user.getPassword());
        Boolean checkEmail = validationCheck(user.getEmail());
        if (!(checkUsername && checkEmail && checkPassword)){
            CustomMessage.addMessageError("Başarısız", "Veriler en az 4 karakter uzunluğunda ve en fazla 64 karakter uzunluğunda olmalı!!");
            return "/kullanici-islemleri/guncelle-kullanici.xhtml";
        }

        if(!(emailCheck(user.getEmail()))){
            CustomMessage.addMessageError("Başarısız", "Yazdığınız email, email formatına uygun değil!!");
            return "/kullanici-islemleri/guncelle-kullanici.xhtml";
        }

        if(userOperations.updateUser(user)) {
            CustomMessage.addMessageInfo("Onaylandı", "Kullanıcı yeni verileri " + user.getUserID() + ", " + user.getUsername() + ", " +
                    user.getPassword() + ", " + user.getEmail() + " ile başarıyla güncellendi. ");
            return "/kullanici-islemleri/index.xhtml";
        }
        CustomMessage.addMessageError("Başarısız", "Güncelleme başarısız oldu.");
        return "/kullanici-islemleri/guncelle-kullanici.xhtml";
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
