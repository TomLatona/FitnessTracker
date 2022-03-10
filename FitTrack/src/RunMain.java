/*
 * This class will run first and prompt the user to either create an account or log in
 * If they choose create account, it will initialize a new user object and update the db
 * If they log in, it will authenticate the credentials and send them to the app
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class RunMain extends Application{
	
	Button caButton;
	Button loginButton;

	public static void main(String[] args) {
		//display create account button
		//display login button
		
		//if click create account button
			//createAccount();
		
		//if click login button
			//login();
		
		launch(args);
		
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		//stage is entire window
		//scene is contents inside the window
		primaryStage.setTitle("Welcome to FitnessTracker"); //will be displayed on top left of window
		
		caButton = new Button("Create Account");	
		loginButton = new Button("Log In");

		HBox layout = new HBox(); //displays boxes side by side
		layout.getChildren().add(caButton);
		layout.getChildren().add(loginButton);
		
		Scene scene = new Scene(layout, 800, 500);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	private static void createAccount() {
		//field for username
		//field for password
		//field to re-enter password
		
		//submit button
		//onClick of submit button
			//String username = 
			//String password = 
			//String passwordCheck = 
		//check both passwords match
		
		//new field: enter daily calorie goal
		//int calGoal = 
		
		//User username = new User(username, password, calGoal);
		//String addUser = username.addUserToDB();
	}
	
	private static void login() {
		//field for username
		//field for password
		//onClick submit button
			//save username
			//query db with username
			//if match
				//compare passwords
				//if match, load main menu class
	}
}
