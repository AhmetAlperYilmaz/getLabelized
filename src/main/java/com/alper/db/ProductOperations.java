package com.alper.db;

import com.alper.model.menu.Product;
import org.apache.log4j.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductOperations extends DBConnection{

    private static final Logger logger = Logger.getLogger(ProductOperations.class);

    public List<Product> listProducts() {
        List<Product> productList = new ArrayList<>();

        try (Connection connection = getConnection()){
            try(PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from images")) {
                preparedStatement.executeQuery();
                try(ResultSet resultSet = preparedStatement.getResultSet()) {
                    while (resultSet.next()) {
                        Product product = new Product();
                        // product infos
                        product.setProductID(resultSet.getLong(1));
                        product.setProductName(resultSet.getString(2));
                        product.setPhoto(resultSet.getBytes(3));
                        // adding product
                        productList.add(product);
                    }
                }
            }
        } catch (Throwable e) {
            // log4j kullanilacak
            logger.error("Listing failed "+e.getMessage(),e);
        }
        return productList;
    }

    public Boolean insertProduct(Product product) {
        try (Connection connection = getConnection()){
            try(PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into images (image_name, photo) values (?,?)")) {

                preparedStatement.setString(1, product.getProductName());

                preparedStatement.setBytes(2, product.getPhoto());
                preparedStatement.executeUpdate();
                return true;
            }
        } catch (Throwable e) {
            // log4j kullanilacak
            logger.error("Inserting failed "+e.getMessage(),e);
        }
        return false;
    }
    /*
    public Boolean deleteProduct(long deletingProductID){
        try (Connection connection = getConnection()){
            try(PreparedStatement preparedStatement = connection.prepareStatement(
                    "update products set activity = false where product_id = ?")) {
                preparedStatement.setLong(1, deletingProductID);
                preparedStatement.executeUpdate();
                return true;
            }
        } catch (Throwable e) {
            // log4j kullanilacak
            logger.error("Deletion failed "+e.getMessage(),e);
        }
        return false;
    }

    public Boolean activateProduct(long activatingProductID){
        try (Connection connection = getConnection()){
            try(PreparedStatement preparedStatement = connection.prepareStatement(
                    "update products set activity = true where product_id = ?")) {
                preparedStatement.setLong(1, activatingProductID);
                preparedStatement.executeUpdate();
                return true;
            }
        } catch (Throwable e) {
            // log4j kullanilacak
            logger.error("Activation failed "+e.getMessage(),e);
        }
        return false;
    }*/

    public Boolean updateProduct(Product product){
        try (Connection connection = getConnection()){
            try(PreparedStatement preparedStatement = connection.prepareStatement(
                    "update images set image_name = ?, photo = ? where image_id = ?")) {

                preparedStatement.setString(1, product.getProductName());

                preparedStatement.setBytes(2, product.getPhoto());
                preparedStatement.setLong(3, product.getProductID());
                preparedStatement.executeUpdate();
                return true;
            }
        } catch (Throwable e) {
            // log4j kullanilacak
            logger.error("Updating failed "+e.getMessage(),e);
        }
        return false;
    }
}