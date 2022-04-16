package com.alper.db;

import com.alper.model.menu.Label;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LabelOperations extends DBConnection {

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
            e.printStackTrace();
            e.getMessage();
        }
        return labelList;
    }

    public Boolean validateLabel(Label label){
        return label.getLabelname().length() > 0;
    }

    public Boolean insertLabel(Label label) {
        if(true) {
            try (Connection connection = getConnection()) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(
                        "insert into label (label) values (?)")) {

                    preparedStatement.setString(1, label.getLabelname());
                    preparedStatement.executeUpdate();
                    return true;
                }
            } catch (Throwable e) {
                e.printStackTrace();
                e.getMessage();
            }
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
            e.printStackTrace();
            e.getMessage();
        }
        return false;
    }
}
