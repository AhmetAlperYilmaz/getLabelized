package com.alper.model.menu;

import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.file.UploadedFile;

import java.io.Serializable;
import java.util.Objects;

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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + (int) (this.productID ^ (this.productID >>> 32));
        hash = 23 * hash + Objects.hashCode(this.base64Image);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Product other = (Product) obj;
        if (this.productID != other.productID) {
            return false;
        }
        return Objects.equals(this.base64Image, other.base64Image);
    }


}