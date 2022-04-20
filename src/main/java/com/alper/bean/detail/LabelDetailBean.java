package com.alper.bean.detail;

import com.alper.db.LabelOperations;
import com.alper.model.menu.Label;
import com.alper.util.CustomMessage;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;
import java.util.List;

@ManagedBean
@SessionScoped
@ViewScoped
@RequestScoped
@Named
@Getter
@Setter
public class LabelDetailBean {
    private List<Label> labels;
    private String labelname;
    private LabelOperations labelOperations;

    public LabelDetailBean() {
        labelOperations = new LabelOperations();
    }

    @PostConstruct
    public void init() {
        setLabels(labelOperations.listLabels());
    }

    public Boolean validateLabel(String labelname){
        return labelname.length() > 0;
    }

    public String saveLabel() {

        // insertion check for label
        boolean check1 = validateLabel(getLabelname());
        if (check1) {
            boolean check2 = labelOperations.insertLabel(getLabelname());
            if(check2) {
                CustomMessage.addMessageInfo("Onaylandı", "Kullanıcı başarıyla eklendi");
                setLabels(labelOperations.listLabels());
            }else {
                CustomMessage.addMessageWarn("Başarısız", "Ekleme işlemi başarısız oldu. Bir hata oluştu");
            }
        }else {
            CustomMessage.addMessageError("Başarısız", "Ekleme işlemi başarısız oldu. Girdiğiniz kullanıcı adı veya e-posta daha önceden alınmış.");
        }
        return "/label-islemleri/index.xhtml";
    }

    public String deleteLabel(Label label){
        long deletingLabelID = label.getLabelID();
        if(labelOperations.deleteLabel(deletingLabelID)) {
            setLabels(labelOperations.listLabels());
            CustomMessage.addMessageInfo("Onaylandı", "Label başarıyla silindi.");
        }else {
            CustomMessage.addMessageError("Başarısız", "Silme işlemi başarısız oldu.");
        }
        return "/label-islemleri/index.xhtml?faces-redirect=true";
    }
}
