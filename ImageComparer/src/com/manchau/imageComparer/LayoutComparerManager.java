package com.manchau.imageComparer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LayoutComparerManager extends Application {

    public static void main(String[] args) {
        Application.launch(LayoutComparerManager.class, (java.lang.String[])null);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            AnchorPane page = (AnchorPane) FXMLLoader.load(LayoutComparerManager.class.getResource("layout_comparer.fxml"));
            Scene scene = new Scene(page);
            primaryStage.setScene(scene);
            primaryStage.setTitle("DefaultView Vs Model View");
            primaryStage.centerOnScreen();
            primaryStage.sizeToScene();
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
    }

   

}
