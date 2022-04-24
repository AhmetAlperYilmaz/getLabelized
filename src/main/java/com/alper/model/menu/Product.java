package com.alper.model.menu;

import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.file.UploadedFile;

import java.io.Serializable;

@Getter
@Setter
public class Product implements Serializable {
    private long productID;
    private String productName;
    private UploadedFile file;
    private byte[] photo;
    private String base64Image;


    public Product() {
    }

    public Product(long productID, String productName, UploadedFile file, byte[] photo, String base64Image){
        this.productID = productID;
        this.productName = productName;
        this.file = file;
        this.photo = photo;
        this.base64Image = base64Image;
    }

}