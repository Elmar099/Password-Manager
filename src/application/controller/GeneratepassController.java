package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GeneratepassController implements Initializable{
	 	@FXML
	    private TextField tf_capital;

	    @FXML
	    private TextField tf_lengthofPass;

	    @FXML
	    private TextField tf_passtobecopied;

	    @FXML
	    private TextField tf_specChar;
	    
	    @FXML
	    private Label errmes;
	    
	    @FXML
	    private Label copied;
	    

	    @FXML
	    void generate(MouseEvent event) {
	    	Random rand = new Random();
	    	int passLength = Integer.parseInt(tf_lengthofPass.getText());
	    	int capital = Integer.parseInt(tf_capital.getText());
	    	int specChar = Integer.parseInt(tf_specChar.getText());
	    	
	    	if(passLength != 0 && capital!= 0 && specChar!= 0) {
	    	
	    	String gen = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            String gen1 = "abcdefghijklmnopqrstuvxyz";  
            String gen2 = "!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";
            int len = capital + specChar;
	    	StringBuilder sb = new StringBuilder();
	    	for(int i = 0; i < passLength - len; i++) {
	    		int index = (int)(gen1.length()* Math.random());
	    		sb.append(gen1.charAt(index));
	    	}
	    	
	    	for(int j = 0; j < capital; j++) {
	    		int num = rand.nextInt(gen.length());
	    		sb.append(gen.charAt(num));
	    	}
	    	for(int k = 0; k < specChar; k++) {
	    		int num1 = rand.nextInt(gen2.length());
	    		sb.append(gen2.charAt(num1));
	    	}
	    	tf_passtobecopied.setText(sb.toString());
	    	
	    	}else {
	    		errmes.setText("Invalid Input!");
	    	}
	    }
	    @FXML
	    void copy(MouseEvent event) {
	    	String clip = tf_passtobecopied.getText();
	    	Clipboard clipboard = Clipboard.getSystemClipboard();
	        ClipboardContent content = new ClipboardContent();
	        content.putString(clip); // item is captured in the closure
	        clipboard.setContent(content); 
	        copied.setText("Copied to clipboard!");
	    }
	    //Back to home button
	    @FXML
	    void home(MouseEvent event) throws IOException {
	    	BorderPane root = (BorderPane) FXMLLoader.load(getClass().getClassLoader().getResource("view/homepage.fxml"));
			Node node = (Node) event.getSource();
			Stage stage = (Stage) node.getScene().getWindow();
			stage.setScene(new Scene(root));
	    }

	    //Initialize method
		@Override
		public void initialize(URL location, ResourceBundle resources) {
			// TODO Auto-generated method stub
			
		}
}
