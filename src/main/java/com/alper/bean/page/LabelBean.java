package com.alper.bean.page;

import com.alper.db.LabelOperations;
import com.alper.model.menu.Label;
import lombok.Getter;
import lombok.Setter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Named;

@ManagedBean
@RequestScoped
@Named
@Getter
@Setter
public class LabelBean {
    private Label label;
    private LabelOperations labelOperations;

    public LabelBean(){
        label = new Label();
        labelOperations = new LabelOperations();
    }

    public Boolean validateLabel(Label label){
        return label.getLabelname().length() <= 0;
    }
}
