package com.example.exmpleadhi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    static int width = 600;
    static int height = 400;
    private static Stage primaryStage;
    private static Scene primaryScene;
    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("MainPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),width,height);
        primaryScene = scene;
        stage.setResizable(false);
        stage.setTitle("AimLite");
        stage.setScene(scene);
        stage.show();

    }
    public static Scene getScene() { return primaryScene; }
    public static Stage getStage() { return primaryStage; }
    public static void main(String[] args) {
        launch();
    }
}
