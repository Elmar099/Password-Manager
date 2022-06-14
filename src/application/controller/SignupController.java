package application.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
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
//Controller class for signup
public class SignupController implements Initializable{
	//variables
		@FXML
		private Label errmsg;
		
		@FXML
		private PasswordField pf_password;
		
		@FXML
		private TextField tf_answer;

		@FXML
		private TextField tf_question;

		@FXML
		private TextField tf_username;

		/* 
		 * This method is the action for the click of the text "login",
		 * once clicked the button will take the user
		 * to the login page. 
		 * */
		@FXML
		void login(MouseEvent event) throws IOException {
			BorderPane root = (BorderPane) FXMLLoader.load(getClass().getClassLoader().getResource("view/login.fxml"));
			Node node = (Node) event.getSource();
			
			Stage stage = (Stage) node.getScene().getWindow();
			stage.setScene(new Scene(root));
		}
	/* 
	 * This method is the action for the click of the button "signup"
	 * once clicked it will take all of the entered information and
	 * store it in the sqlite database and output "registered!" in the 
	 * system console. Once it has been registered, the button will take the user
	 * to the main home page. 
	 * */
	    @FXML
	    void signup(MouseEvent event) throws IOException, SQLException {
	    	Connection connection = DbConnect.getInstance().getConnection();
            try {
              String username = tf_username.getText();
        	  String password = pf_password.getText();
        	  String security = tf_question.getText();
        	  String answer = tf_answer.getText();
        	  Statement state = connection.createStatement();
        	  
        	  /**
   	    	 * Adding: Encryption feature. Passwords that users enter will be encrypted before being stored in the database.
   	    	 * The encrypted password will also be displayed on the user's console.
   	    	 */
   	    	// PassUtil passUtil = new PassUtil();
   	    	// password = passUtil.encrypt(password);
        	   
        	  if(!username.isEmpty() && !password.isEmpty() && !security.isEmpty() && !answer.isEmpty()) {
              int status = state.executeUpdate("insert into users (username,password,security,answer)" +
            		  " values('"+username+"', '"+password+"', '"+security+"', '"+answer+"')");    	  
              if (status > 0 && !username.isEmpty() && !password.isEmpty()) {
            	  System.out.println("registered!");
            	  BorderPane root = (BorderPane) FXMLLoader.load(getClass().getClassLoader().getResource("view/login.fxml"));
  				  Node node = (Node) event.getSource();
  				  Stage stage = (Stage) node.getScene().getWindow();
  				  stage.setScene(new Scene(root));
              } 
             }else {
            	  errmsg.setText("One or more empty field. please fill all fields.");
              }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
	    	
	    }
	    /*
	     * This is a method that needs to be included because of the 
	     * initializable interface
	     **/
		@Override
		public void initialize(URL location, ResourceBundle resources) {
			
		}

}
