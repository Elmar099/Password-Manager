package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			//loading the login page as the first page for when the application is opened
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getClassLoader().getResource("view/login.fxml"));
			//Making the stage and scene and making it visible
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getClassLoader().getResource("css/application.css").toExternalForm());
			primaryStage.setTitle("Personal Password Manager");
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	//Main method
	public static void main(String[] args) {
		launch(args);
	}
}
