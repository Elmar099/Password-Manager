package application.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import application.AccountSearchModel;
import application.helpers.DbConnect;
import edu.sjsu.yazdankhah.crypto.util.PassUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

//Controller class for homepage
public class HomeController implements Initializable{
	
	 	 @FXML
	 	 private TableView<AccountSearchModel> accountTableView;
	 	
	 	 @FXML
	     private TableColumn<AccountSearchModel, String> emailColumn;

	     @FXML
	     private TableColumn<AccountSearchModel, String> passColumn;

	     @FXML
	     private TableColumn<AccountSearchModel, String> userColumn;

	     @FXML
	     private TableColumn<AccountSearchModel, String> webColumn;

	     @FXML
	     private TextField tf_searchbar;
	 	
	     ObservableList<AccountSearchModel> AccountSearchModelObservableList = FXCollections.observableArrayList();
	 	
	     
	 	@FXML
	    void add(MouseEvent event) throws IOException {
	 		BorderPane root = (BorderPane) FXMLLoader.load(getClass().getClassLoader().getResource("view/addpage.fxml"));
			Node node = (Node) event.getSource();
			Stage stage = (Stage) node.getScene().getWindow();
			stage.setScene(new Scene(root));

	    }
	 	// Goes to the modify page
	 	@FXML
	    void modify(MouseEvent event) throws SQLException, IOException {
	 		BorderPane root = (BorderPane) FXMLLoader.load(getClass().getClassLoader().getResource("view/modify.fxml"));
			Node node = (Node) event.getSource();
			Stage stage = (Stage) node.getScene().getWindow();
			stage.setScene(new Scene(root));
	    }
	 	//delete button
	 	@FXML
	    void delete(MouseEvent event) throws SQLException, IOException {
		    	Connection connection = DbConnect.getInstance().getConnection();		    		    		
		    	try {
		    		AccountSearchModel selectedItem = accountTableView.getSelectionModel().getSelectedItem();  		
		    		//delete from database
		    		if (!selectedItem.equals(null) ) {
		    			PreparedStatement statement = connection.prepareStatement("DELETE FROM accounts WHERE username = ?");
		    			statement.setString(1, selectedItem.getUsername());
		    			statement.execute();
		    			
		    			BorderPane root = (BorderPane) FXMLLoader.load(getClass().getClassLoader().getResource("view/homepage.fxml"));
		    			Node node = (Node) event.getSource();
		    			Stage stage = (Stage) node.getScene().getWindow();
		    			stage.setScene(new Scene(root));
		    		}
		    	}catch (SQLException e) {
	               System.out.println(e.getMessage());
	            }catch (NullPointerException e) {
	            	 System.out.println("nothing selected!");
	            }
		    }

	 	//Goes to the generate page
	    @FXML
	    void generate(MouseEvent event) throws IOException {
	    	BorderPane root = (BorderPane) FXMLLoader.load(getClass().getClassLoader().getResource("view/generatepass.fxml"));
			Node node = (Node) event.getSource();
			Stage stage = (Stage) node.getScene().getWindow();
			stage.setScene(new Scene(root));
	    }

	    //goes to the login page
	    @FXML
	    void login(MouseEvent event) throws IOException, SQLException {
	    	Connection connection = DbConnect.getInstance().getConnection();
	    	Statement statement = connection.createStatement();
	    	statement.executeUpdate("update users set iscurrent ='No'");
	    	BorderPane root = (BorderPane) FXMLLoader.load(getClass().getClassLoader().getResource("view/login.fxml"));
			Node node = (Node) event.getSource();
			
			Stage stage = (Stage) node.getScene().getWindow();
			stage.setScene(new Scene(root));
			
	    }

	    //Initialize method to view and update tableview
		@Override
		public void initialize(URL location, ResourceBundle resources) {	
			
	    	try {
	    		//SQlite Connection
	    		Connection connection = DbConnect.getInstance().getConnection();	    		
	    		Statement statement = connection.createStatement(); 
	    		
	    		ResultSet rs = statement.executeQuery("select rowid from users where iscurrent = 'Yes'");
	    		int val = ((Number) rs.getObject(1)).intValue();
	    		String accountViewQuery = "select username, password, website, email FROM accounts where accountid =  '"+val+"'";	    			    		
	    		ResultSet resultSet = statement.executeQuery(accountViewQuery);
	    		
	    		//populating the table 	    		
	    		while (resultSet.next()) {
	    			String username = resultSet.getString("username");
	    			String password = resultSet.getString("password");
	    			String email = resultSet.getString("email");
	    			String website = resultSet.getString("website");
	    				    			
	    			/*
	    			 * Displaying: Decryption feature. Users will now be able to view their decrypted password. Passwords stored in the 
	    			 * database are encrypted, which are decrypted before being displayed to users. 
	    			 **/	
	    			PassUtil passUtil = new PassUtil();
	    	    	password = passUtil.decrypt(password);
	    			
	    	    	
	    	    	//Populating the observable list	    				    	    	
	    			AccountSearchModelObservableList.add(new AccountSearchModel(username, password, email, website));
	    		
	    		}
	    		//Value factory corresponds to each accountSearchModel fields
	    		//accountTableView.setEditable(true);
	    		userColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
	    		passColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
	    		emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
	    		webColumn.setCellValueFactory(new PropertyValueFactory<>("website"));
	    		accountTableView.setItems(AccountSearchModelObservableList);
	    		
	    		//Search function implementation
	    		FilteredList<AccountSearchModel> filteredData = new FilteredList<>(AccountSearchModelObservableList, b -> true);
	    		tf_searchbar.textProperty().addListener((observable, oldValue, newValue) -> {
	    			filteredData.setPredicate(accountSearchModel -> {
	    			
	    				//if no search value found, then just display all records
	    				if (newValue.isEmpty() || newValue == null) {
	    					return true;
	    				}
	    				
	    				
	    				String searchKeyword = newValue.toLowerCase();
	    				//searching for either username or website
	    				if(accountSearchModel.getUsername().toLowerCase().indexOf(searchKeyword) != -1) {
	    					return true; // found match in username
	    				} else if(accountSearchModel.getWebsite().toLowerCase().indexOf(searchKeyword) != -1){
	    					return true; //found match in website name
	    				} else 
	    					return false;
	    			});
	    		});
	    		
	    		SortedList<AccountSearchModel> sortedData = new SortedList<>(filteredData);	    		
	    		sortedData.comparatorProperty().bind(accountTableView.comparatorProperty());
	    		//Applying sorted and filtered data
	    		accountTableView.setItems(sortedData);
	    		resultSet.close();

	    	}catch(Exception e) {
	    		e.printStackTrace();
	    }
	}
}
