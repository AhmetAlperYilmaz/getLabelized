package com.alper.model.menu;

import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.file.UploadedFile;

@Getter
@Setter
public class Product {
    private long productID;
    private String productName;
    private UploadedFile file;
    private byte[] photo;
    private String base64Image;
}