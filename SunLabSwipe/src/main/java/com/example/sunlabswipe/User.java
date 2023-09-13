package com.example.sunlabswipe;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class User {
    IntegerProperty id = new SimpleIntegerProperty();
    StringProperty name = new SimpleStringProperty();
    StringProperty type = new SimpleStringProperty();
    StringProperty status = new SimpleStringProperty();

    public IntegerProperty idProperty() { //name should be exactly like this [IntegerProperty variable name (id) + (Property) = idProperty] (case sensitive)
        return id;
    }

    public StringProperty nameProperty() {
        return name;
    }
    public StringProperty typeProperty() {
        return type;
    }
    public StringProperty statusProperty() {
        return status;
    }
    public User(int idValue, String nameValue, String typeValue, boolean statusValue) {
        id.set(idValue);
        name.set(nameValue);
        type.set(typeValue);
        if(statusValue){
            status.set("Active");
        }else{
            status.set("Inactive");
        }

    }

    User(){}
}
