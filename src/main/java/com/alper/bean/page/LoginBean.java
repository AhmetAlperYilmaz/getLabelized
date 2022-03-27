package com.alper.bean.page;

import com.alper.db.UserOperations;
import com.alper.model.menu.Page;
import com.alper.model.menu.User;
import com.alper.util.CustomMessage;

import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.util.Objects;

@ManagedBean
public class LoginBean {

    private final UserOperations userOperations;

    public LoginBean() {userOperations = new UserOperations();}

    public void loginUser(User user) {
        FacesContext context = FacesContext.getCurrentInstance();
        try { // redirect için lazım
            if(userOperations.userExists(user.getUsername(), user.getPassword())) {
                CustomMessage.addMessageInfo("Giriş Başarılı, Hoş geldiniz, Sayın " + user.getUsername(), "");
                context.getExternalContext().getSessionMap().put("username", user.getUsername());
                //CustomMessage.addMessageInfo(context.getExternalContext().getRequestContextPath() + "/index.xhtml", "");
                context.getExternalContext().redirect(context.getExternalContext().getRequestContextPath() + "/index.xhtml");


            } else {
                CustomMessage.addMessageWarn("Yanlış Kullanıcı Adı ya da Parola!!" + " Lütfen kullanıcı adını ve parolayı doğru giriniz. Girilen değerler: "
                        + user.getUsername() + " ve " + user.getPassword(), "Lütfen kullanıcı adını ve parolayı doğru giriniz. Girilen değerler: "
                        + user.getUsername() + " ve " + user.getPassword());
            }
        }catch (Throwable e){
            e.printStackTrace();
            CustomMessage.addMessageFatal("Hata","Giriş doğrulanırken bir hata ile karşılaşıldı");
        }
    }

    public void logOut(User user) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().invalidateSession();
        try { // redirect için lazım
            CustomMessage.addMessageInfo("Çıkış Başarılı", "Hoşça kalın, Sayın" + user.getUsername());
            context.getExternalContext().redirect("/getLabelized/login.xhtml");
        } catch (Throwable e) {
            e.printStackTrace();
            CustomMessage.addMessageFatal("Hata","Çıkış yapılırken bir hata ile karşılaşıldı");
        }
    }
}