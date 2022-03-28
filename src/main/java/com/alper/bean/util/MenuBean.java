package com.alper.bean.util;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.alper.model.menu.Page;
import com.alper.model.menu.User;

@Getter
@Setter
@ManagedBean
@ApplicationScoped
public class MenuBean {

    private List<Page> pages;
    private User menuUser;
    private Boolean menuControl;

    @PostConstruct
    public void init() {
        menuControl = false;
        menuUser = new User();
        pages = new ArrayList<>();
        pages.add(new Page("/index.xhtml", "Anasayfa", "fa fa-home", true));

    }

    public List<Page> getAllPages(User myUser){
        this.menuUser = myUser;
        if(!Objects.equals(this.menuUser.getUsername(), "Admin") && menuControl){
            pages.remove(pages.size() - 1);
            pages.remove(pages.size() - 1);
            menuControl = false;
        }

        if(Objects.equals(this.menuUser.getUsername(), "Admin") && !menuControl){
            pages.add(new Page("/kullanici-islemleri/index.xhtml", "Kullanıcı İşlemleri", "fa fa-user", true));
            pages.add(new Page("/fotograf-islemleri/index.xhtml", "Fotoğraf İşlemleri", "fa fa-cube", true));
            menuControl = true;
        }

        return this.pages;
    }

    public Page getPage(String uri) {
        if (uri.equals("/")) // anasayfa "/" yada "/index.xhtml" olabilir
            uri = "/index.xhtml";
        return searchInPages(pages, uri);
    }

    private Page searchInPages(List<Page> pages, String uri) {
        for (Page page : pages) {
            if (page.isParent()) {
                Page childPage = searchInPages(page.getChildPages(), uri);
                if (childPage != null)
                    return childPage;
            } else {
                if (page.getUri().equals(uri)) {
                    return page;
                }
            }
        }
        return null;
    }
}