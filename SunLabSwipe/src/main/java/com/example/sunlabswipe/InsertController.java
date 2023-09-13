package com.example.sunlabswipe;


import javafx.fxml.FXML;

import javafx.scene.control.Label;

import javafx.scene.control.TextField;

import java.io.IOException;


public class InsertController {

    @FXML private TextField Id;
    @FXML private TextField Name;
    @FXML private TextField Type;
    @FXML private TextField Status;
    @FXML private Label completionText;
    @FXML
    protected void onAddClick(){

        if(ScanSystem.addUser(Id.getText(), Name.getText(), Type.getText(),Status.getText())){
            completionText.setText("User \""+Name.getText()+"\" Successfully Added");
            completionText.setStyle("-fx-text-fill: green");
        }else{
            completionText.setText("User Unsuccessfully Added");
            completionText.setStyle("-fx-text-fill: red");
        }
    }
    @FXML
    protected void onHomeClick() throws IOException {
        Id.clear();
        Name.clear();
        Type.clear();
        Status.clear();
        completionText.setText("");
        SunLabApp.loadHomePage();
    }

}

