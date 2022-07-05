package com.example.lightnovelcrawler;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.IOException;

public class LNCrawler extends Application {
    @FXML
    private Label messageBar;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LNCrawler.class.getResource("lightnovel-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1458, 912);
        stage.setResizable(false);
        stage.setTitle("Dark Novel Crawler");
        stage.setScene(scene);
//        String iconlocation = "";
//        try{
//            File[] files = new File("com\\example\\lightnovelcrawler").listFiles();
//
//            if (files != null) {
//                for(File file : files){
//                    if(file.isFile() && file.getName().endsWith(".png")){
//                        iconlocation = file.getAbsolutePath();
//                        System.out.println(iconlocation);
//                    }
//                }
//            }
//        }catch (Exception e){
//            messageBar.setText(e.getMessage());
//        }
        stage.getIcons().add(new Image("C:\\Users\\GameDemons\\IdeaProjects\\Lightnovel Crawler\\src\\main\\java\\com\\example\\lightnovelcrawler\\icon.png"));
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                Platform.exit();
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }
}