package com.example.sunlabswipe;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UserController implements Initializable {
    @FXML private TableColumn<User, Integer> Id;
    @FXML private TableColumn<User, String> Name;
    @FXML private TableColumn<User, String> Type;
    @FXML private TableColumn<User, String> Status;
    @FXML private TableView<User> userTable;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Id.setCellValueFactory(new PropertyValueFactory<User, Integer>("id"));
        Name.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        Type.setCellValueFactory(new PropertyValueFactory<User, String>("type"));
        Status.setCellValueFactory(new PropertyValueFactory<User, String>("status"));
        try {
            ResultSet resultSet=ScanSystem.browseUsers();
            userTable.getItems().setAll(dataBaseArrayList(resultSet));
        }catch (SQLException sqlException){
            System.out.println("Table not Initialized");
        }
    }
    private ArrayList<User> dataBaseArrayList(ResultSet resultSet) throws SQLException {

        ArrayList<User> data =  new ArrayList<>();
        while (resultSet.next()) {
            User user = new User();
            user.id.set(resultSet.getInt("Id"));
            user.name.set(resultSet.getString("Name"));
            user.type.set(resultSet.getString("type"));

            if(resultSet.getBoolean("status")){
                user.status.set("Active");
            }else{
                user.status.set("Inactive");
            }
            data.add(user);
        }
        return data;
    }
    @FXML
    protected void onHomeClick() throws IOException {
        SunLabApp.loadHomePage();
    }
}
