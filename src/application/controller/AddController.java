package application.controller;

//import java.awt.TextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import application.helpers.DbConnect;
import edu.sjsu.yazdankhah.crypto.util.PassUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AddController implements Initializable{
	
		@FXML
		private Label errmsg;

		@FXML
		private TextField tf_email;

		@FXML
		private TextField tf_expdate;

		@FXML
		private TextField tf_password;

		@FXML
		private TextField tf_username;

		@FXML
		private TextField tf_website;
		
	
	
	   @FXML
	    void home(MouseEvent event) throws IOException {
		   BorderPane root = (BorderPane) FXMLLoader.load(getClass().getClassLoader().getResource("view/homepage.fxml"));
			Node node = (Node) event.getSource();
			Stage stage = (Stage) node.getScene().getWindow();
			stage.setScene(new Scene(root));
	    }
	    	   

	  @FXML
	  void submit(MouseEvent event) throws IOException, SQLException  {
		  Connection connection = DbConnect.getInstance().getConnection();
	    try {
	    	String username = tf_username.getText();	    	
	    	String password = tf_password.getText();
	    	String website = tf_website.getText();
	    	String email = tf_email.getText();
	    	String expiredate = tf_expdate.getText();
	    	
	    	
	    	/*
	    	 * Adding: Encryption feature. Passwords that users enter will be encrypted before being stored in the database.
	    	 * The encrypted password will also be displayed on the user's console.
	    	 **/
	    	PassUtil passUtil = new PassUtil();
	    	password = passUtil.encrypt(password);
	    	
	    	
	    	
      	    Statement state = connection.createStatement();
      	    ResultSet rs = state.executeQuery("SELECT rowid FROM users WHERE iscurrent = 'Yes'");
      	    int val = ((Number) rs.getObject(1)).intValue();
      	  if(!username.isEmpty() && !password.isEmpty() && !website.isEmpty() && !email.isEmpty()&& !expiredate.isEmpty()) {
      	    int status1 = state.executeUpdate("insert into accounts (accountid,username,password,website,email,expdate)" +
          		  " values('"+val+"', '"+username+"', '"+password+"', '"+website+"', '"+email+"', '"+expiredate+"')");
      	  if (status1 > 0) {
        	  System.out.println("added!");
        	  BorderPane root = (BorderPane) FXMLLoader.load(getClass().getClassLoader().getResource("view/homepage.fxml"));
			  Node node = (Node) event.getSource();
			  Stage stage = (Stage) node.getScene().getWindow();
			  stage.setScene(new Scene(root));
			  

      	  } 
      	  }else {
        	  errmsg.setText("One or more empty field. please fill all fields.");
          } 
      	  rs.close();
      	  
	    } catch (SQLException e) {
	    	System.out.println(e.getMessage());
	    }

	    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}
