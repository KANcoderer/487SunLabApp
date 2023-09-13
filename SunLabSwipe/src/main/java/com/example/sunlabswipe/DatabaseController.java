package com.example.sunlabswipe;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DatabaseController implements Initializable {

    @FXML private TableView<Access> tableView;
    @FXML private TableColumn<Access, Integer> Id;
    @FXML private TableColumn<Access, String> Name;
    @FXML private TableColumn<Access, String> TimeStamp;
    @FXML private TableColumn<Access, String> Inside;
    @FXML private TextField searchId;
    @FXML private DatePicker accessDate;
    @FXML private TextField startTime;
    @FXML private TextField endTime;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Id.setCellValueFactory(new PropertyValueFactory<Access, Integer>("id"));
        Name.setCellValueFactory(new PropertyValueFactory<Access, String>("name"));
        TimeStamp.setCellValueFactory(new PropertyValueFactory<Access, String>("timestamp"));

        Inside.setCellValueFactory(new PropertyValueFactory<Access, String>("inside"));
        try {
            ScanSystem.removeAccess();
            ResultSet resultSet=ScanSystem.browse();
            tableView.getItems().setAll(dataBaseArrayList(resultSet));
        }catch (SQLException sqlException){
            System.out.println("Table not Initialized");
        }
    }

    private ArrayList<Access> dataBaseArrayList(ResultSet resultSet) throws SQLException {
        ArrayList<Access> data =  new ArrayList<>();
        while (resultSet.next()) {
            Access access = new Access();
            access.id.set(resultSet.getInt("id"));
            access.name.set(resultSet.getString("name"));
            access.timestamp.set(resultSet.getTimestamp("date").toString());
            if(resultSet.getBoolean("inside")){
                access.inside.set("In");
            }else{
                access.inside.set("Out");

            }
            data.add(access);
        }

        return data;
    }
    @FXML
    protected void onBrowseClick() throws IOException {
        SunLabApp.loadUserPage();
    }
    @FXML
    protected void onResetFilterClick() {
        try {
            searchId.clear();
            accessDate.setValue(null);
            startTime.clear();
            endTime.clear();
            ScanSystem.removeAccess();
            ResultSet resultSet=ScanSystem.browse();
            tableView.getItems().setAll(dataBaseArrayList(resultSet));
        }catch (SQLException sqlException){
            System.out.println("Table not Initialized");
        }
    }
    @FXML
    protected void onFilterClick(){

        try {
            ScanSystem.removeAccess();
            String aDate="";
            if(accessDate.getValue()!=null){
                aDate=accessDate.getValue().toString();
            }
            ResultSet resultSet=ScanSystem.filteredBrowse(searchId.getText(),aDate,startTime.getText(),endTime.getText());

            tableView.getItems().setAll(dataBaseArrayList(resultSet));
        }catch (SQLException sqlException){
            System.out.println("Filter Error");
        }
    }
    @FXML
    protected void onAddClick() {
        SunLabApp.loadInsertPage();
    }
    @FXML
    protected void onUpdateClick(){
        SunLabApp.loadUpdatePage();}

}