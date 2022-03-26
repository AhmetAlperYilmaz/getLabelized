package com.alper.bean.util;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.ArrayList;
import java.util.List;
import com.alper.model.menu.Page;



@Getter
@Setter
@ManagedBean
@ApplicationScoped
public class MenuBean {

    private List<Page> pages;

    @PostConstruct
    public void init() {
        pages = new ArrayList<>();

        pages.add(new Page("/index.xhtml", "Anasayfa", "fa fa-home", true));
        pages.add(new Page("/kullanici-islemleri/index.xhtml", "Kullanıcı İşlemleri", "fa fa-user", true));

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