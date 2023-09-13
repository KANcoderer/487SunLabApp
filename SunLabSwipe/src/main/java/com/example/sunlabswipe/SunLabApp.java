package com.example.sunlabswipe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class SunLabApp extends Application {
    private static Scene InsertPage;
    private static Scene UpdatePage;
    private static Stage myStage;
    public static final CountDownLatch latch = new CountDownLatch(1);
    public static SunLabApp startUp = null;
    private static FXMLLoader dbLoader;
    public static void setSunLabApp(SunLabApp startUp0) {
        startUp = startUp0;
        latch.countDown();
    }
    public SunLabApp() {
        setSunLabApp(this);
    }

    @Override
    public void start(Stage stage) throws IOException {
        SunLabApp.myStage=stage;
        stage.setTitle("Sun Lab Access Database");

        dbLoader = new FXMLLoader(SunLabApp.class.getResource("InsertUserGUI.fxml"));
        InsertPage = new Scene(dbLoader.load(), 640, 400);
        dbLoader = new FXMLLoader(SunLabApp.class.getResource("UpdateUserGUI.fxml"));
        UpdatePage = new Scene(dbLoader.load(), 640, 400);
        loadHomePage();
        stage.show();
    }
    public static void loadHomePage() throws IOException {
        dbLoader= new FXMLLoader(SunLabApp.class.getResource("databaseGUI.fxml"));
        Scene homePage = new Scene(dbLoader.load(), 640, 400);
        SunLabApp.myStage.setScene(homePage);
        
    }
    public static void loadUserPage() throws IOException {
        dbLoader= new FXMLLoader(SunLabApp.class.getResource("userTableGUI.fxml"));
        Scene userPage = new Scene(dbLoader.load(), 640, 400);
        SunLabApp.myStage.setScene(userPage);

    }
    public static void loadInsertPage(){
        SunLabApp.myStage.setScene(InsertPage);
    }
    public static void loadUpdatePage(){
        SunLabApp.myStage.setScene(UpdatePage);
    }
    public static void main(String[] args) {
        launch();
    }
}