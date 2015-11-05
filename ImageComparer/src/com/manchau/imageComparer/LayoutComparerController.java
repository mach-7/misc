package com.manchau.imageComparer;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class LayoutComparerController implements Initializable {

    @FXML
    private ImageView defaultView;

    @FXML
    private ImageView modelView;

    @FXML
    private Button modelViewIndicator;

    @FXML
    private Button defaultViewIndicator;

    @FXML
    private Button duplicacyIndicator;

    @FXML
    private Label modelCount;

    @FXML
    private Label defaultCount;

    @FXML
    private Label duplicateCount;
    
    @FXML
    private Label imageCount;
    @FXML
    private Label result;
    
    private int counter;
    private int totalImageCount;
    private int modelViewOkCounter;
    private int defaultViewOkCounter;
    private int bothLookSameCounter;
    
    private final String DEFAULT_VIEW_IMAGES_LOCATION = "C:\\Users\\maneesh.chauhan\\scratch\\CADImportResearchData\\DefaultView";
    private final String MODEL_VIEW_IMAGES_LOCATION = "C:\\Users\\maneesh.chauhan\\scratch\\CADImportResearchData\\ModelView";
    
    private ArrayList<Image> defaultViewImageList;
    private ArrayList<Image> modelViewImageList;
    
    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {        
        counter = 1;
        modelViewOkCounter = 0;
        defaultViewOkCounter = 0;
        bothLookSameCounter = 0;
        
        File defaultViewDirectory = new File(DEFAULT_VIEW_IMAGES_LOCATION);
        File modelViewDirectory = new File(MODEL_VIEW_IMAGES_LOCATION); 
        
        defaultViewImageList = new ArrayList<>();
        modelViewImageList = new ArrayList<>();
        
        for(File file :  defaultViewDirectory.listFiles()) {
            Image image;
            try {
                image = new Image(file.toURI().toURL().toString()); 
                defaultViewImageList.add(image);
            } catch (MalformedURLException e1) {
                // nothing
            }           
        }
        
        for(File file :  modelViewDirectory.listFiles()) {
            Image image;
            try {
                image = new Image(file.toURI().toURL().toString()); 
                modelViewImageList.add(image);
            } catch (MalformedURLException e1) {
                // nothing
            }           
        }
        totalImageCount = defaultViewImageList.size() <= modelViewImageList.size() ? 
                                               defaultViewImageList.size() : modelViewImageList.size();
        
        // set count label and image views
        imageCount.setText(counter + "/" + totalImageCount);
        defaultView.setImage(defaultViewImageList.get(0));
        modelView.setImage(modelViewImageList.get(0));
        
        // set action of buttons
        modelViewIndicator.setOnAction(e -> {
            modelViewOkCounter++;
            Platform.runLater(() -> updateView());           
        });
        defaultViewIndicator.setOnAction(e -> {
            defaultViewOkCounter++;
            Platform.runLater(() -> updateView());           
        });
        
        duplicacyIndicator.setOnAction(e -> {
            bothLookSameCounter++;
            Platform.runLater(() -> updateView());           
        });
        
    }

    private void updateView() {
        modelCount.setText(Integer.toString(modelViewOkCounter));
        defaultCount.setText(Integer.toString(defaultViewOkCounter));
        duplicateCount.setText(Integer.toString(bothLookSameCounter)); 
        
        if(counter < totalImageCount) {                       
            defaultView.setImage(defaultViewImageList.get(counter));
            modelView.setImage(modelViewImageList.get(counter));
            counter++;
            imageCount.setText(counter + "/" + totalImageCount);
        } else { 
            duplicacyIndicator.setDisable(true);
            modelViewIndicator.setDisable(true);
            defaultViewIndicator.setDisable(true);          
            
            imageCount.setText("FINAL SCORE");
            imageCount.setFont(new Font("Arial", 25));
            imageCount.setTextFill(Color.web("#bbb6a3"));
        }      
                
    }    
}