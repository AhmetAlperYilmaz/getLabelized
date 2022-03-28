package com.alper.bean.page;

import com.alper.db.ProductOperations;
import com.alper.model.menu.Product;
import com.alper.util.CustomMessage;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.file.UploadedFile;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@ManagedBean
@RequestScoped
@Named
@Getter
@Setter
public class ProductBean {

    private Product product;
    private ProductOperations productOperations;
    // Image Upload Part
    private UploadedFile file;

    public ProductBean(){
        product = new Product();
        productOperations = new ProductOperations();
    }

    public Boolean validateProduct(Product product){
        return product.getProductName().length() <= 0;
    }

    public String updateProductPage(Product product){
        try{
            this.product.setProductID(product.getProductID());
            this.product.setProductName(product.getProductName());
            return "/fotograf-islemleri/guncelle-foto.xhtml";
        }
        catch(Throwable e){
            e.printStackTrace();
            CustomMessage.addMessageFatal("Başarısız","Güncelle sayfasına ulaşılamadı");
            return "/fotograf-islemleri/index.xhtml";
        }
    }

    public String updateProduct(){
        if(file == null){
            CustomMessage.addMessageError("Başarısız", "Fotoğraf eklemediniz!!");
            return "/fotograf-islemleri/guncelle-foto.xhtml";
        }
        product.setFile(file);
        product.setPhoto(file.getContent());
        if(validateProduct(getProduct())){
            CustomMessage.addMessageError("Başarısız", "Zorunlu olan alanlarda boşluklar var!!");
            return "/fotograf-islemleri/guncelle-foto.xhtml";
        }


        if (productOperations.updateProduct(getProduct())) {
            CustomMessage.addMessageInfo("Onaylandı", "Fotoğraf " + this.product.getProductID() + " başarıyla güncellendi");
            return "/fotograf-islemleri/index.xhtml";
        }
        CustomMessage.addMessageError("Başarısız", "Fotoğraf güncelleme işlemi başarısız oldu.");
        return "/fotograf-islemleri/guncelle-foto.xhtml";
    }

}