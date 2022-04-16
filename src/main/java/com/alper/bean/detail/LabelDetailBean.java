package com.alper.bean.detail;

import com.alper.db.LabelOperations;
import com.alper.model.menu.Label;
import com.alper.util.CustomMessage;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.List;

@ManagedBean
@ViewScoped
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

    public String saveLabel() {
        if (true) {
            CustomMessage.addMessageInfo("Onaylandı", "Kullanıcı başarıyla eklendi");
            setLabels(labelOperations.listLabels());
        }
        CustomMessage.addMessageError("Başarısız", "Ekleme işlemi başarısız oldu. Girdiğiniz kullanıcı adı veya e-posta daha önceden alınmış.");
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
