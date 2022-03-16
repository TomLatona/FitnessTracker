/*
 * This class will run first and prompt the user to either create an account or log in
 * If they choose create account, it will initialize a new user object and update the db
 * If they log in, it will authenticate the credentials and send them to the app
 */

import java.sql.Connection;
import java.sql.DriverManager;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class RunMain extends Application implements EventHandler<ActionEvent> {
	
	Button caButton;
	Button loginButton;

	public static void main(String[] args)throws Exception {
		//display create account button
		//display login button
		
		//if click create account button
			//createAccount();
		
		//if click login button
			//login();
		
		launch(args);
		//creating connection to mysql database
		getConnection();
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		//stage is entire window
		//scene is contents inside the window
		primaryStage.setTitle("Welcome to FitnessTracker"); //will be displayed on top left of window
		
		//create buttons with text
		caButton = new Button("Create Account");
		loginButton = new Button("Log In");
		
		//when clicked it looks for the action in this class
		//could change 'this' for another class if event handling was more in depth
		caButton.setOnAction(this); 
		loginButton.setOnAction(this);

		HBox layout = new HBox(); //displays boxes side by side
		layout.getChildren().add(caButton);
		layout.getChildren().add(loginButton);
		
		Scene scene = new Scene(layout, 800, 500);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	@Override
	public void handle(ActionEvent event) {
		//depending which button is clicked, run respective method
		if(event.getSource() == caButton) {
			createAccount();
		}
		
		if(event.getSource() == loginButton) {
			login();
		}
	}
	
	
	private static void createAccount() {
		System.out.println("account created *test*");
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
		System.out.println("logged in *test*");
		//field for username
		//field for password
		//onClick submit button
			//save username
			//query db with username
			//if match
				//compare passwords
				//if match, load main menu class
	}
	//trying to do mysql inserts
	
	public static Connection getConnection() throws Exception{
		try {
			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://localHost:3306/fitnesstrackerdb";
			String username = "hey";
			String password = "password123";
			Class.forName(driver);
			
			Connection conn = DriverManager.getConnection(url,username,password);
			System.out.println("Connected");
			return conn;
			
		}catch(Exception e) {System.out.println(e);}
		
		
		return null;
	}
	public static void post() throws Exception{
		final String var1 = "John";
		final String var2 = "Miller";
		try {
			Connection con = getConnection();
			Pr
		}
	}
}
