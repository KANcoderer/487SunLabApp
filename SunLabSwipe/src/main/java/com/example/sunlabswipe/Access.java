package com.example.sunlabswipe;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Timestamp;

public class Access {
    IntegerProperty id = new SimpleIntegerProperty();
    StringProperty name = new SimpleStringProperty();
    StringProperty timestamp = new SimpleStringProperty();
    StringProperty inside = new SimpleStringProperty();

    public IntegerProperty idProperty() { //name should be exactly like this [IntegerProperty variable name (id) + (Property) = idProperty] (case sensitive)
        return id;
    }

    public StringProperty nameProperty() {
        return name;
    }
    public StringProperty timestampProperty() {
        return timestamp;
    }
    public StringProperty insideProperty() {
        return inside;
    }

    public Access(int idValue, String nameValue, Timestamp timestampValue, boolean insideValue) {
        id.set(idValue);
        name.set(nameValue);
        timestamp.set(timestampValue.toString());
        if(insideValue){
            inside.set("In");
        }else{
            inside.set("Out");
        }

    }

    Access(){}
}
