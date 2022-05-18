//Maksim Artemev

//COP2551

//Program that will allow the user to perform the following tasks:
//	Add New Languages to the Database
//	Delete Language from Database
//	View Language from Database


package application;
	

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
	
			Parent root = FXMLLoader.load(getClass().getResource("window.fxml")); //set Parent stage
			Scene scene = new Scene(root);	//set new scene and pass parent stage object to it
			primaryStage.setScene(scene);	//pass scene object to primaryStage parameter
			primaryStage.show();			//display the primary stage
		} catch(Exception e) {
			e.printStackTrace();	//base error
		}
	}
	
	public static void main(String[] args) {
		launch(args);	//launch application
	}
}
