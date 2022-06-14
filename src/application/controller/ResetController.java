package application.controller;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import application.helpers.DbConnect;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

//Controller class for reset
public class ResetController implements Initializable {
	//variables
	    @FXML
	    private PasswordField pf_password;

	    @FXML
	    private TextField tf_answer;
	    
	    @FXML
	    private TextField tf_username;
	    
	    
	    @FXML
	    private TextField tf_question;


	    /* 
		 * This method is the action for the click of the button "reset"
		 * once clicked it will check the database for the security question 
		 * and answer and if there is a match the user shall be allowed to 
		 * enter a new password which will than be updated in the database.
		 * After successful reset, the user will be taken to the login page. 
		 * */
	    @FXML
	    void reset(MouseEvent event) throws IOException, SQLException {
	    	String question, answer, password, username;
	    	
	    	password = pf_password.getText();
	    	question = tf_question.getText();
	    	username = tf_username.getText();
	    	answer = tf_answer.getText();
	    	
	    	Connection connection = DbConnect.getInstance().getConnection();
	    	Statement statement = connection.createStatement();
	    	ResultSet resultSet = statement.executeQuery("select * from users where security = '"+question+"' and answer = '"+answer+"'");
	    	
	    	
	    	if (resultSet.next() && !username.isEmpty()) {
	    		statement.executeUpdate("update users set password ='"+password+"' where username = '"+username+"'");
	    		BorderPane root = (BorderPane) FXMLLoader.load(getClass().getClassLoader().getResource("view/login.fxml"));
				Node node = (Node) event.getSource();
				Stage stage = (Stage) node.getScene().getWindow();
				stage.setScene(new Scene(root));
				resultSet.close();

	    	}
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
			// TODO Auto-generated method stub
			
		}

}