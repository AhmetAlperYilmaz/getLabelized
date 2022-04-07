package com.alper.db;

import com.alper.model.menu.Label;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LabelOperations extends DBConnection {
    private static final Logger logger = Logger.getLogger(LabelOperations.class);

    public List<Label> listLabels() {
        List<Label> labelList = new ArrayList<>();

        try (Connection connection = getConnection()){
            try(PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from labels")) {
                preparedStatement.executeQuery();
                try(ResultSet resultSet = preparedStatement.getResultSet()) {
                    while (resultSet.next()) {
                        Label label = new Label();
                        // label infos

                        label.setLabelID(resultSet.getLong(1));
                        label.setLabelname(resultSet.getString(2));
                        // adding label
                        labelList.add(label);
                    }
                }
            }
        } catch (Throwable e) {
            // log4j kullanilacak
            logger.error("Listing failed "+e.getMessage(),e);
        }
        return labelList;
    }

    public Boolean insertLabel(Label label) {
        try (Connection connection = getConnection()){
            try(PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into label (label) values (?)")) {

                preparedStatement.setString(1, label.getLabelname());
                preparedStatement.executeUpdate();
                return true;
            }
        } catch (Throwable e) {
            // log4j kullanilacak
            logger.error("Inserting failed "+e.getMessage(),e);
        }
        return false;
    }

    public Boolean deleteLabel(long deletingLabelID) {
        try (Connection connection = getConnection()){
            try(PreparedStatement preparedStatement = connection.prepareStatement(
                    "delete from labels where label_id = ?")) {
                preparedStatement.setLong(1, deletingLabelID);
                preparedStatement.executeUpdate();
                return true;
            }
        } catch (Throwable e) {

            logger.error("Deletion failed "+e.getMessage(),e);
        }
        return false;
    }
}
