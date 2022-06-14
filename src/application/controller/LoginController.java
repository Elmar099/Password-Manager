package application.controller;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import application.helpers.DbConnect;
//import edu.sjsu.yazdankhah.crypto.util.PassUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

//Controller class for login
public class LoginController implements Initializable {
	//variables
		@FXML
	    private PasswordField pf_password;

	    @FXML
	    private TextField tf_username;
	    
	    @FXML
	    private Label errormsg;
	    
	    /* 
		 * This method is the action for the click of the button "login"
		 * once clicked it will check the database for the user name and password
		 * and if there is a match the button will take the user
		 * to the main home page. 
		 * */
	    
	    
	    @FXML
	    void login(MouseEvent event) throws IOException, SQLException {
	    	String username = tf_username.getText();
	    	String password = pf_password.getText();
	    	
	    	
	    	/**
			 * Password will be decrypted before being displayed on user's homescreen. Decrypted password will also 
			 * be shown on user console
			 */
			//PassUtil passUtil = new PassUtil();
	    	//password = passUtil.decrypt(password);
	    	//System.out.println(password);
	    	
	    	Connection connection = DbConnect.getInstance().getConnection();
	    	Statement statement = connection.createStatement();
	    	ResultSet resultSet = statement.executeQuery("select * from users where username = '"+username+"' and password = '"+password+"'");
	    	
	    	if (resultSet.next()&& !username.isEmpty()) {
	    		statement.executeUpdate("update users set iscurrent = 'Yes' where username = '"+username+"'");
	    		BorderPane root = (BorderPane) FXMLLoader.load(getClass().getClassLoader().getResource("view/homepage.fxml"));
				Node node = (Node) event.getSource();
				Stage stage = (Stage) node.getScene().getWindow();
				stage.setScene(new Scene(root));
				resultSet.close();
	    	}else {
	    		errormsg.setText("Wrong Username/Password or empty field.");
	    	} 
	    }
	    /* 
	  	 * This method is the action for the click of the text "reset",
		 * once clicked the text will take the user	  		 
		 * to the reset page. 
	     * */
	    @FXML
	    void reset(MouseEvent event) throws IOException {
	    	BorderPane root = (BorderPane) FXMLLoader.load(getClass().getClassLoader().getResource("view/reset.fxml"));
			Node node = (Node) event.getSource();
		
			Stage stage = (Stage) node.getScene().getWindow();
			stage.setScene(new Scene(root));
	    }

	    /* 
		 * This method is the action for the click of the text "signup",
		 * once clicked the text will take the user
		 * to the signup page. 
		 * */
	    @FXML
	    void signup(MouseEvent event) throws IOException {
	    	
	    	BorderPane root = (BorderPane) FXMLLoader.load(getClass().getClassLoader().getResource("view/signup.fxml"));
			Node node = (Node) event.getSource();
		
			Stage stage = (Stage) node.getScene().getWindow();
			stage.setScene(new Scene(root));

	    }

	    /*
	     * This is a method that needs to be included because of the 
	     * initializable interface
	     **/
		@Override
		public void initialize(URL location, ResourceBundle resources) {
			
		}

}