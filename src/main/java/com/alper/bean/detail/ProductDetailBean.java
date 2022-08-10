package com.alper.bean.detail;

import com.alper.db.ProductOperations;
import com.alper.model.menu.Product;
import com.alper.util.CustomMessage;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.file.UploadedFile;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.util.*;

@ManagedBean(name="ProductDetailBean", eager = true)
@RequestScoped
@ViewScoped
@Named
@Getter
@Setter
public class ProductDetailBean {

    private List <Product> productList;
    private List <Product> productSelectionList;
    private Product product;
    private String productNameFill;
    private ProductOperations productOperations;
    // Image Upload Part
    private UploadedFile file;

    public ProductDetailBean(){
        product = new Product();
        productNameFill = "";
        productOperations = new ProductOperations();
    }

    @PostConstruct
    public void init() {

        setProductList(productOperations.listProducts());
        productSelectionList = new ArrayList<>();
    }

    public String saveProduct(){
        if(file == null){
            CustomMessage.addMessageError("Başarısız", "Fotoğraf eklemediniz!!");
            return "/fotograf-islemleri/yeni-foto.xhtml";
        }
        product.setProductName(productNameFill);
        product.setFile(file);
        product.setPhoto(file.getContent());
        if(validateProduct(getProduct())){
            CustomMessage.addMessageError("Başarısız", "Zorunlu olan alanlarda boşluklar var!!");
            return "/fotograf-islemleri/yeni-foto.xhtml";
        }

        if (productOperations.insertProduct(getProduct())) {
            CustomMessage.addMessageInfo("Onaylandı", "Fotoğraf başarıyla eklendi");
            setProductList(productOperations.listProducts());
            return "/fotograf-islemleri/index.xhtml";
        }
        CustomMessage.addMessageError("Başarısız", "Fotoğraf ekleme işlemi başarısız oldu.");
        return "/fotograf-islemleri/yeni-foto.xhtml";
    }

    public Boolean validateProduct(Product product){
        return  product.getProductName().length() <= 0;
    }
    /*
    public void deleteProduct(Product product){
        long deletingProductID = product.getProductID();
        if(productOperations.deleteProduct(deletingProductID)) {
            CustomMessage.addMessageInfo("Onaylandı", "Ürün başarıyla pasifleştirildi");
            setProductList(productOperations.listProducts());
        }else {
            CustomMessage.addMessageError("Başarısız", "Pasifleştirme işlemi başarısız oldu.");
        }
    }

    public void activateProduct(Product product){
        long activatingProductID = product.getProductID();
        if(productOperations.activateProduct(activatingProductID)) {
            CustomMessage.addMessageInfo("Onaylandı", "Ürün başarıyla aktifleştirildi");
            setProductList(productOperations.listProducts());
        }else {
            CustomMessage.addMessageError("Başarısız", "Aktive işlemi başarısız oldu.");
        }
    }
    */
}