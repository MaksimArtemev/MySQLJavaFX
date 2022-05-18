package application;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.TouchEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class windowController implements Initializable {
	
	private String[] choices = {"Insert New Language", "Delete Language", "View Languages Of Country"}; //array of type String that holds user options to pick
	
	private Logic logic = new Logic();	//instantiate an object of Logic.java
	
	//all the elements from the SceneBuilder
	@FXML
	private ChoiceBox<String> itemPicker;
	
	@FXML
	private Button queryType;				
	
	@FXML
	private Button queryRun;
	
	@FXML
	private Button dbCommit;
	
	@FXML
	private Button dbRollback;
	
	@FXML
	private TextField countryName;
	
	@FXML
	private TextField Language;
	
	@FXML
	private TextField IsOfficial;
	
	@FXML
	private TextField Percentage;

	@FXML 
	private TextArea resultsBox;
	

	//method to run the query after Run button was pressed
	@FXML
	public void QueryRunner(ActionEvent event) {
		try{
			if(itemPicker.getValue().toString() == "Insert New Language")	//checks if an item selected within item picker is "Insert New Language"
			{
				//go to addLanguage method in Logic.java and pass countryName, Language, IsOfficial, Percentage to it; run addLanguage() and assign its return value to variable temp of type String
				String temp = logic.addLanguage(countryName.getText().toString(), Language.getText().toString(), IsOfficial.getText().toString(), Percentage.getText().toString()); 
				resultsBox.setText(temp);	//set text within resultBox text area to "Language Added" if language added successfully or to "Error Occured Adding Language" if unseccessful
				this.disabler();	//call method to disable all user input fields
			}
			else if(itemPicker.getValue().toString() == "Delete Language") //checks if an item selected within item picker is "Delete Language"
			{
				String temp = logic.deleteLanguage(countryName.getText().toString(), Language.getText().toString());
				resultsBox.setText(temp);
				this.disabler();	//call method to disable all user input fields
			}
			else if(itemPicker.getValue().toString() == "View Languages Of Country")	//checks if an item selected within item picker is "View Languages Of Country"
			{
				String temp = logic.viewLanguage(countryName.getText().toString());
				resultsBox.setText(temp);
				this.disabler();	//call method to disable all user input fields
			}
			else
			{
				resultsBox.setText("Selection Error");	//error message
			}
		}
		catch(Exception e)
		{
			resultsBox.setText("Selection Error");	//base error message
		}
		
	}
	

	
	//method to get the item selected from itemPicker and enable and/or disable user input fields relative to what needs to be inputted
	@FXML
	public void QuerySelector(ActionEvent event) {
		disabler();	//call disabler method where we disabled all user input fields
		resultsBox.setText("");
		try{
			if(itemPicker.getValue().toString() == "Insert New Language") //checks if an item selected within item picker is "Insert New Language"
			{
				//enable all user input fields
				this.countryName.setDisable(false);	
				this.Language.setDisable(false);
				this.IsOfficial.setDisable(false);
				this.Percentage.setDisable(false);
			}
			else if(itemPicker.getValue().toString() == "Delete Language") //checks if an item selected within item picker is "Delete Language"
			{
				//enable only countryName and Language user input fields
				this.countryName.setDisable(false);
				this.Language.setDisable(false);
			}
			else if(itemPicker.getValue().toString() == "View Languages Of Country") //checks if an item selected within item picker is "View Languages Of Country"
			{
				//enable only countryName user input field
				this.countryName.setDisable(false);
			}
			else
			{
				//set text within resultBox text area to "Selection Error"
				resultsBox.setText("Selection Error");
			}
		}
		catch(Exception e)
		{
			resultsBox.setText("Selection Error");	//Error message
		}
	}
	
	@FXML
	public void dbCommit(ActionEvent event) {
		disabler();	//call disabler method where we disabled all user input fields
		resultsBox.setText("");	//set text within resultBox text area to ""  ***reset the text area
		resultsBox.setText(logic.saveDB());	//set text within resultBox text area to "DB Saved" if commit was successful
	}
	
	@FXML
	public void dbRollback(ActionEvent event) {
		disabler();	//call disabler method where we disabled all user input fields
		resultsBox.setText("");	//set text within resultBox text area to ""  ***reset the text area
		resultsBox.setText(logic.revertDB());	//set text within resultBox text area to "DB Undone" after performing rollback method within Logic.java
	}
	
	//method to disable all user input fields
	private void disabler()
	{
		//disable all user input fields: countryName, Language, IsOfficial, Percentage
		this.countryName.setDisable(true);
		this.Language.setDisable(true);
		this.IsOfficial.setDisable(true);
		this.Percentage.setDisable(true);
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1){
		// TODO Auto-generated method stub
		itemPicker.getItems().addAll(choices);	//add an array of type String that contains {"Insert New Language", "Delete Language", "View Languages Of Country"} to itemPicker
		disabler(); //call method to disable all user input fields
          
 
	}
}