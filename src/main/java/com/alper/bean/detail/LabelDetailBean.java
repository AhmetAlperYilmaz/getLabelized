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
    private Label label;
    private LabelOperations labelOperations;

    public LabelDetailBean() {
        label = new Label();
        labelOperations = new LabelOperations();
    }

    @PostConstruct
    public void init() {
        setLabels(labelOperations.listLabels());
    }

    public Boolean validateLabel(Label label){
        return label.getLabelname().length() > 0;
    }

    public String saveLabel() {

        // insertion check for label
        boolean check1 = validateLabel(getLabel());
        //boolean check2 = labelOperations.insertLabel(getLabel());
        if (check1) {
            CustomMessage.addMessageInfo("Onaylandı", "Kullanıcı başarıyla eklendi");
            setLabels(labelOperations.listLabels());
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
