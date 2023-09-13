package com.example.sunlabswipe;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UpdateController implements Initializable{
    @FXML private TableView<User> display;
    @FXML private TableColumn<User, Integer> Id;
    @FXML private TableColumn<User, String> Name;
    @FXML private TableColumn<User, String> Type;
    @FXML private TableColumn<User, String> Status;
    @FXML private TextField SearchId;
    @FXML private Label newNameLbl;
    @FXML private TextField newName;
    @FXML private Button changeNameBtn;
    @FXML private Button statusBtn;
    @FXML private Button deleteBtn;
    @FXML private Label completionText;
    @FXML
    protected void onSearchClick() throws SQLException {

        ResultSet resultSet=ScanSystem.SearchUser(Integer.parseInt(SearchId.getText()));
        ArrayList<User> data =  new ArrayList<>();
        if (resultSet.next()) {
            User user = new User();
            user.id.set(resultSet.getInt("Id"));
            user.name.set(resultSet.getString("Name"));
            user.type.set(resultSet.getString("type"));

            if(resultSet.getBoolean("status")){
                user.status.set("Active");
                statusBtn.setText("Deactivate");
            }else{
                user.status.set("Inactive");
                statusBtn.setText("Activate");

            }
            data.add(user);
            display.getItems().setAll(data);
            newNameLbl.setVisible(true);
            newName.setVisible(true);
            changeNameBtn.setVisible(true);
            statusBtn.setVisible(true);
            deleteBtn.setVisible(true);
            SearchId.clear();
        }
    }
    @FXML
    protected void onHomeClick() throws IOException {
        reset();
        SunLabApp.loadHomePage();
    }
    @FXML
    protected void onChangeNameClick(){
        try{
            ScanSystem.editUserName(Id.getCellData(0),newName.getText());
            ScanSystem.editUserNameAccess(Id.getCellData(0),newName.getText());
            String name=ScanSystem.getName(Id.getCellData(0));
            ArrayList<User> data =  new ArrayList<>();
            User user = new User();
            user.id.set(Id.getCellData(0));
            user.name.set(name);
            user.type.set(Type.getCellData(0));

            if (Status.getCellData(0).equals("Active")) {
                user.status.set("Active");

            } else {
                user.status.set("Inactive");
            }
            data.add(user);
            display.getItems().setAll(data);
            completionText.setText("User Name Successfully Changed To '"+newName.getText()+"'");
            completionText.setStyle("-fx-text-fill: green");
            newName.clear();
        }catch(SQLException sqle){
            completionText.setText("Unable To Change User Name");
            completionText.setStyle("-fx-text-fill: red");
        }

    }
    @FXML
    protected void onDeleteClick(){
        try{
            String name=Name.getCellData(0);
            ScanSystem.removeUser(Id.getCellData(0));
            reset();
            completionText.setText("User '"+ name+"' Successfully Deleted");
            completionText.setStyle("-fx-text-fill: green");
        }catch (SQLException sqle){
            completionText.setText("Unable To Remove User");
            completionText.setStyle("-fx-text-fill: red");
        }

    }
    private void reset(){
        if(display.getItems().size()!=0) {
            display.getItems().remove(0);
        }
        newNameLbl.setVisible(false);
        newName.setVisible(false);
        changeNameBtn.setVisible(false);
        statusBtn.setVisible(false);
        deleteBtn.setVisible(false);
        statusBtn.setText("");
        completionText.setText("");
    }
    @FXML
    protected void onStatusBtnClick() throws SQLException {
        ScanSystem.changeStatus(Id.getCellData(0));
        boolean stat=ScanSystem.getStatus(Id.getCellData(0));
        ArrayList<User> data =  new ArrayList<>();
        User user = new User();
        user.id.set(Id.getCellData(0));
        user.name.set(Name.getCellData(0));
        user.type.set(Type.getCellData(0));

        if (stat) {
            user.status.set("Active");
            statusBtn.setText("Deactivate");
            completionText.setText("User Successfully Activated");

        } else {
            user.status.set("Inactive");
            statusBtn.setText("Activate");
            completionText.setText("User Successfully Deactivated");
        }
        completionText.setStyle("-fx-text-fill: green");
        data.add(user);
        display.getItems().setAll(data);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Id.setCellValueFactory(new PropertyValueFactory<User, Integer>("id"));
        Name.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        Type.setCellValueFactory(new PropertyValueFactory<User, String>("type"));
        Status.setCellValueFactory(new PropertyValueFactory<User, String>("status"));
    }
}
