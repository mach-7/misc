package application;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;

public class ImageViewerController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private StackPane stackPane;

    @FXML
    private ImageView mainImageView;

    @FXML
    private Label navigatePrevious;

    @FXML
    private Label navigateNext;

    @FXML
    private Button closeButton;

    private LinkedList<Image> imageList;
    
	@Override
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		assert closeButton == null : "fx:id=\"closeButton\" was not injected: check your FXML file 'ImageViewer.fxml'.";
		assert navigatePrevious != null : "fx:id=\"navigatePrevious\" was not injected: check your FXML file 'ImageViewer.fxml'.";
		assert navigateNext != null : "fx:id=\"navigateNext\" was not injected: check your FXML file 'ImageViewer.fxml'.";
				
		closeButton.setOnAction(e-> Platform.exit());
		
		closeButton.setOnMouseEntered( e -> closeButton.setEffect(new Glow(0.7)) );
		closeButton.setOnMouseExited( e -> closeButton.setEffect(null) );
					
		navigatePrevious.setOnMouseClicked(e -> iterateNext());	
		navigateNext.setOnMouseClicked(e-> iteratePrevious());
			
		initImageList();
		mainImageView.setImage(imageList.getFirst());
	}
	
	private void iterateNext() {
		int next = imageList.indexOf(mainImageView.getImage()) + 1;
		if(next < imageList.size()) {
			mainImageView.setImage(imageList.get(next));
		} else {
			mainImageView.setImage(imageList.getFirst());
		}
	}
	private void iteratePrevious() {
		int prev = imageList.indexOf(mainImageView.getImage()) - 1;
		if(prev > 0) {
			mainImageView.setImage(imageList.get(prev));
		} else {
			mainImageView.setImage(imageList.getLast());
		}
	}
	
	private void initImageList() {
		File myPictures = new File("C:\\Users\\Public\\Pictures\\Sample Pictures");
		imageList = new LinkedList<>();
		for(File file : myPictures.listFiles()) {
			System.out.println(file.getPath());
			Image image;
			try {
				image = new Image(file.toURI().toURL().toString(), 800,600,true,true);
				imageList.add(image);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

}