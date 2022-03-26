package com.alper.db;

import com.alper.model.menu.User;
import org.apache.log4j.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.alper.util.CustomMessage;

public class UserOperations extends DBConnection{

    private static final Logger logger = Logger.getLogger(UserOperations.class);

    public List<User> listUsers() {
        List<User> userList = new ArrayList<>();

        try (Connection connection = getConnection()){
            try(PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from users")) {
                preparedStatement.executeQuery();
                try(ResultSet resultSet = preparedStatement.getResultSet()) {
                    while (resultSet.next()) {
                        User user = new User();
                        user.setUserID(resultSet.getLong(1));
                        user.setUsername(resultSet.getString(2));
                        user.setPassword(resultSet.getString(3));
                        user.setEmail(resultSet.getString(4));
                        userList.add(user);
                    }
                } 
            } 
        } catch (Throwable e) {

            logger.error("Listing failed "+e.getMessage(),e);
        }
        return userList;
    }

    // postgresql serial primary key
    public Boolean insertUser(User user) {
        try (Connection connection = getConnection()){
            try(PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into users (username, password, email) values (?,?,?)")) {
                preparedStatement.setString(1, user.getUsername());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.setString(3, user.getEmail());
                preparedStatement.executeUpdate();
                return true;
            } 
        } catch (Throwable e) {

            logger.error("Inserting failed "+e.getMessage(),e);
        }
        return false;
    }

    // userid ile silinecek
    public Boolean deleteUser(long deletingUserID) {
        try (Connection connection = getConnection()){
            try(PreparedStatement preparedStatement = connection.prepareStatement(
                    "delete from users where user_id = ?")) {
                preparedStatement.setLong(1, deletingUserID);
                preparedStatement.executeUpdate();
                return true;
            }
        } catch (Throwable e) {

            logger.error("Deletion failed "+e.getMessage(),e);
        }
        return false;
    }


    public Boolean updateUser(User user) {
        try (Connection connection = getConnection()){
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "update users set username = ?, password = ?, email = ? where user_id = ?")) {
                preparedStatement.setString(1, user.getUsername());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.setString(3, user.getEmail());
                preparedStatement.setLong(4, user.getUserID());
                preparedStatement.executeUpdate();
                return true;
            }
        } catch (Throwable e) {

            logger.error("Update failed "+e.getMessage(),e);
        }
        return false;
    }

    public Boolean userExists(String username, String password) {

        try (Connection connection = getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "select count(*) from users where username = ? and password = ?;")){

                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {

                    if (resultSet.next()) {
                        return resultSet.getInt("count") > 0;
                    }
                }
            }
        } catch (Throwable e) {
            CustomMessage.addMessageError("error occured","master");
            e.printStackTrace();
            logger.error("Exist checking failed "+e.getMessage(),e);
        }
        return false;
    }
}
