package application.controller;

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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ModifyController implements Initializable{
	//Textfields
	  	@FXML
	    private TextField tf_email;

	    @FXML
	    private PasswordField pf_password;

	    @FXML
	    private TextField tf_username;

	    @FXML
	    private TextField tf_website;


	    //modifies and updates account information given the website 
	    @FXML
	    void update(MouseEvent event) throws IOException, SQLException {
	    	String website, email, username, password;
	    	email = tf_email.getText();
	    	password = pf_password.getText();	    	
	    	username = tf_username.getText();
	    	website = tf_website.getText();
	    	//Encryption
	    	PassUtil passUtil = new PassUtil();
	    	password = passUtil.encrypt(password);
	    	
	    	//connectng to database
	    	Connection connection = DbConnect.getInstance().getConnection();
	    	Statement statement = connection.createStatement();
	    	//verification of entered website
	    	ResultSet resultSet = statement.executeQuery("select * from accounts where website = '"+website+"'");
	    	
	    	if(resultSet.next()) {
	    	//execution to database
	    	statement.executeUpdate("update accounts set username='"+username+"', password='"+password+"', email='"+email+"' where website = '"+website+"'");
	    	
	    	BorderPane root = (BorderPane) FXMLLoader.load(getClass().getClassLoader().getResource("view/homepage.fxml"));
			Node node = (Node) event.getSource();
			
			Stage stage = (Stage) node.getScene().getWindow();
			stage.setScene(new Scene(root));
			resultSet.close();
	    	}
	    }
	    
	    @FXML
	    void home(MouseEvent event) throws IOException {
	    	BorderPane root = (BorderPane) FXMLLoader.load(getClass().getClassLoader().getResource("view/homepage.fxml"));
	    	Node node = (Node) event.getSource();
	    	Stage stage = (Stage) node.getScene().getWindow();
	    	stage.setScene(new Scene(root));
	    }
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
}
