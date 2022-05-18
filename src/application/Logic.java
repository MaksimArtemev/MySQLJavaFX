package application;

import javafx.scene.Scene;
import java.sql.*;

public class Logic implements java.io.Serializable {
	private Connection con;	//attribute of type data used to make a connection with MySQL and Java ***mysql connector

	
	//method of return type string to add language in our world db
	//passing countryName, language, isOfficial, percentage as parameters from windowController.java **user inputs
	public String addLanguage(String countryName, String language, String isOfficial, String percentage) {

		try {
			PreparedStatement sql = con.prepareStatement("SELECT Code FROM country WHERE Name = ? LIMIT 1"); //Code statement we want to pass to mySQL to execute **we want to get the country code
			sql.setString(1, countryName);	//set the "?" on previous line to countryName parameter
			ResultSet rs = sql.executeQuery();	//to hold results we use ResultSet datatype variable and we tell it to execute query we stored at sql object
			rs.next();	//skip header of the table
			String countryCode = rs.getString(1); // put first column of rs to countryCode variable of type String
			
			sql = con.prepareStatement("INSERT INTO countrylanguage VALUES( ?, ?, ?, ?)"); //Code statement we want to pass to mySQL to execute **we want to insert countryCode, language, isOfficial, percentage into countrylanguage
			sql.setString(1, countryCode);	//set the first "?" in INSERT INTO statement to countryCode
			sql.setString(2, language);		//set the second "?" in INSERT INTO statement to countryCode
			sql.setString(3, isOfficial);	//set the third "?" in INSERT INTO statement to countryCode
			sql.setFloat(4, Float.parseFloat(percentage));	//set the fourth "?" in INSERT INTO statement to countryCode
			
			if(sql.executeUpdate() == 1)	//checks if update run is successful
			{
				return "Language Added";	//return a string "Language Added"
			}
			else							//if update run is NOT successful
			{
				throw new Exception();		//base error
			}
			
		} catch (Exception e) {
			return "Error Occured Adding Language";	//error message
		}

	}
	
	//method of return type string to delete currently existing language in our world db
	//passing countryName, language, as parameters from windowController.java **user inputs
	public String deleteLanguage(String countryName, String language) {

		try {
			
			PreparedStatement sql = con.prepareStatement("SELECT Code FROM country WHERE Name = ? LIMIT 1");	//Code statement we want to pass to mySQL to execute **we want to get the country code
			sql.setString(1, countryName);		//set the "?" on previous line to countryName parameter
			ResultSet rs = sql.executeQuery();	//to hold results we use ResultSet datatype variable and we tell it to execute query we stored at sql object
			rs.next();	//skip header of the table
			String countryCode = rs.getString(1);	// put first column of rs to countryCode variable of type String
			
			
			sql = con.prepareStatement("DELETE FROM countrylanguage WHERE CountryCode = ? AND Language = ?");	//Code statement we want to pass to mySQL to execute **we want to delete language, of certain countryCode witin countrylanguage
			sql.setString(1, countryCode);	//set the first "?" in INSERT INTO statement to countryCode
			sql.setString(2, language);		//set the second "?" in INSERT INTO statement to language
			if(sql.executeUpdate() == 1)	//checks if update run is successful
			{
				return "Language Added";	//return a string "Language Added"
			}
			else							//if update run is NOT successful
			{
				throw new Exception();		//base error
			}
			
		} catch (Exception e) {
			return "Error Occured Adding Language";	//error message
		}

	}
	
	//method that makes sure if user is satisfied with the commit and wants to proceed with it
	public String saveDB()
	{
		try {
			con.commit();	//perform the commit()
			return "DB Saved" ;	//return a string "DB Saved"
		} catch (SQLException e) {
			return "Error DB NOT Saved" ;	//error message
		}
	}
	
	//method if user is not satisfied with the commit and wants to cancel the commit/rollback
	public String revertDB()
	{
		try {
			con.rollback();	//perform the rollback()
			return "DB Undone" ;	//return a string "DB Saved"
		} catch (SQLException e) {
			return "Error DB NOT Undone" ;	//error message
		}
	}
	
	//method of return type string to view all languages of currently existing Country in our world db
	//passing countryName as parameter from windowController.java **user inputs
	public String viewLanguage(String countryName) {

		try {
			String total = "";		//initialize the variable total of type String
			PreparedStatement sql = con.prepareStatement("SELECT Code FROM country WHERE Name = ? LIMIT 1");	//Code statement we want to pass to mySQL to execute **we want to get the country code
			sql.setString(1, countryName);			//set the "?" on previous line to countryName parameter
			ResultSet rs = sql.executeQuery();		//to hold results we use ResultSet datatype variable and we tell it to execute query we stored at sql object
			rs.next();	//skip table header
			String countryCode = rs.getString(1);	// put first column of rs to countryCode variable of type String

			sql = con.prepareStatement("SELECT * FROM countrylanguage WHERE CountryCode =  ? "); //prepareStatement to select all the columns from countryLanguage table with countryCode == "***"
			sql.setString(1, countryCode);	//set the "?" on previous line to countryCode
			rs = sql.executeQuery(); 		//to hold results we use ResultSet datatype variable and we tell it to execute query we stored at sql object
			if (rs != null) {	//if execute was successful
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {	//i =1 bc in sql we start with 1 instead of 0, get meta data about our table and get the number of columns in the table; step is +1
					total = total + String.format("%-30s", rs.getMetaData().getColumnName(i));	//add to total with 30 character alignment
				}
				total = total + "\n";	// add total and  line breaker to current total; move to next row
			}
			while (rs.next()) {	//go through all the rows of the column
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) { //i =1 bc in sql we start with 1 instead of 0, get meta data about our table and get the number of columns in the table; step is +1
					total = total + String.format("%-30s\t", rs.getString(i));	//add to total with 30 character alignment
				}
				total = total + "\n";	// add total and  line breaker to current total; move to next row
			}

			return total; //return String total
		} catch (Exception e) {
			return "Error Please Check Country Name"; //error message
		}
	}

	public Logic() {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");	//get Java Database Connectivity from MySQL
			this.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/world", "root", "Maxtenis9971");  //set connection, pass database url, username, password
			this.con.setAutoCommit(false); //dont let MySQL to perform auto commits
		} catch (Exception e) {
			e.printStackTrace();	//base error message
		}
	}

}
