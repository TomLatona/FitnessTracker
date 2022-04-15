/*
 * This class will run first and prompt the user to either create an account or log in
 * If they choose create account, it will initialize a new user object and update the db
 * If they log in, it will authenticate the credentials and send them to the app
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class RunMain extends Application {
	
	Stage window;
	Scene welcome;
	static Scene ca;
	Scene login;

	public static void main(String[] args)throws Exception {
//		getConnection();
//		createTable();
//		insertMealInfo("22222",300,"Famous Amos Cookies");
//		insertUserInfo("22223","2800","wacko mode","jtorres01");
//		getUsersById("22222");
		//display create account button
		//display login button
		
		//if click create account button
			//createAccount();
		
		//if click login button
			//login();
		
		launch(args);
		//creating connection to mysql database
		
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		welcome(primaryStage);
	}
	
	public static void welcome(Stage window) {
		//stage is entire window
		//scene is contents inside the window
		window.setTitle("Welcome to FitnessTracker"); //will be displayed on top left of window
		
		//create buttons with text
		Button caButton = new Button("Create Account");
		Button loginButton = new Button("Log In");
		
		caButton.setOnAction(e -> createAccount(window)); 
		loginButton.setOnAction(e -> login());

		HBox layout = new HBox(); //displays boxes side by side
		layout.getChildren().add(caButton);
		layout.getChildren().add(loginButton);
		
		Scene scene = new Scene(layout, 800, 500);
		window.setScene(scene);
		window.show();
	}
	
	
	private static void createAccount(Stage window) {
		
		//~~ UI ELEMENTS ~~
		
		//Creating a GridPane container
		//all ui elements will be added to the grid
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(5);
		grid.setHgap(5);
		
		//Label to say if username is taken, or success message
		final Label label = new Label();
		GridPane.setConstraints(label, 0, 4);
		GridPane.setColumnSpan(label, 2);
		grid.getChildren().add(label);
		
		//~ TEXT FIELDS ~
		//username
		final TextField username = new TextField();
		username.setPromptText("Choose a username");
		username.setPrefColumnCount(10);
		username.getText();
		GridPane.setConstraints(username, 0, 0);
		grid.getChildren().add(username);
		
		//password
		final TextField password = new TextField();
		password.setPromptText("Enter a password");
		GridPane.setConstraints(password, 0, 1);
		grid.getChildren().add(password);
		
		//password check
		final TextField pwCheck = new TextField();
		//pwCheck.setPrefColumnCount(15);
		pwCheck.setPromptText("Re-enter your password");
		GridPane.setConstraints(pwCheck, 0, 2);
		grid.getChildren().add(pwCheck);
		
		//calorie goal
		final TextField calorieGoal = new TextField();
		calorieGoal.setPrefColumnCount(15);
		calorieGoal.setPromptText("Enter your daily calorie goal");
		GridPane.setConstraints(calorieGoal, 0, 3);
		grid.getChildren().add(calorieGoal);
		
		//~ BUTTONS ~
		//submit
		Button submit = new Button("Submit");
		GridPane.setConstraints(submit, 0, 6);
		grid.getChildren().add(submit);
		//clear
		Button clear = new Button("Clear");
		GridPane.setConstraints(clear, 1, 1);
		grid.getChildren().add(clear);
		//back
		Button back = new Button("Go Back");
		GridPane.setConstraints(back, 1, 0);
		grid.getChildren().add(back);
		//~~ END OF UI ELEMENTS ~~
		
		//Build the scene and display it on the stage
		ca = new Scene(grid, 800, 500);
		window.setScene(ca);

		//~~ BUTTON EVENT HANDLING ~~
		//Submit button
		submit.setOnAction(e -> {
			//check with database query if username is already present
				//if so, update label with text (username + " is already taken, try again")
				//if not, push to database and load home page
			if(password != pwCheck) {
				label.setText("Those passwords do not match, try again");
				password.clear();
		        pwCheck.clear();
			}
			else {
				label.setText("Account created succesfully!"); //doesn't work, can't compare strings 
			}
		 });
		 
		//Clear button
		clear.setOnAction(e -> {
	        username.clear();
	        password.clear();
	        pwCheck.clear();
	        calorieGoal.clear();
	        label.setText(null);
		});
		
		//Back button
		back.setOnAction(e -> welcome(window));
		//~~ END OF BUTTON EVENT HANDLING ~~
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
	//creating sql tables
	public static void createTable() throws Exception{
		try {
			Connection con = getConnection();
			//This is the sql statment that will be run to create the table, can be changed to create any table
			PreparedStatement create = con.prepareStatement("CREATE TABLE IF NOT EXISTS tablename(id int NOT NULL AUTO_INCREMENT, firstname varchar(255), lastname varchar(255), primary Key(id))");
			create.executeUpdate();
		}catch(Exception e) {System.out.println(e);}
		finally{System.out.println("Table Function Complete");};
	}
	//establishes connection with database on localhost
	public static Connection getConnection() throws Exception{
		try {
			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://localHost:3306/fitnesstrackerdb";
			//this username and password is unique to who's computer you are running this on
			String username = "root";
			String password = "Absurdairplane123";
			Class.forName(driver);
			
			Connection conn = DriverManager.getConnection(url,username,password);
			System.out.println("Connected to Database");
			return conn;
			
		}catch(Exception e) {System.out.println(e);}
		
		
		return null;
	}
	//This function inserts the user's meal info into the database
	public static void insertMealInfo(String userId, int calorieNum,String foodItem ) throws Exception{
		String item = foodItem;
		int calories = calorieNum;
		String id = userId;
		try {
			Connection con = getConnection();
			PreparedStatement insert = con.prepareStatement("Insert into usermeals(userID,calories,fooditem) values ('"+id+"','"+calories+"','"+item+"')");
			
			insert.executeUpdate();
		}catch(Exception e) {System.out.println(e);}
			finally {
				System.out.println("Insert userMeal Funtion Completed");
			}
		}
	public static void insertUserInfo(String userId, String calGoal,String passW, String userNam ) throws Exception{
		String calorieGoal = calGoal;
		String password = passW;
		String id = userId;
		String userName = userNam;
		try {
			Connection con = getConnection();
			PreparedStatement insert = con.prepareStatement("Insert into users(userId,caloriegoal,password,username) values ('"+id+"','"+calorieGoal+"','"+password+"','"+userName+"')");
			
			insert.executeUpdate();
		}catch(Exception e) {System.out.println(e);}
			finally {
				System.out.println("Insert userInfo Funtion Completed");
			}
		}
	public static ArrayList<String> getUsersById(String id) throws Exception {
		try {
			Connection con = getConnection();
			PreparedStatement statement = con.prepareStatement("Select * From users where userId = " +id );
			
			ResultSet result = statement.executeQuery();
			
			ArrayList<String> array = new ArrayList<String>();
			while(result.next()) {
				System.out.print(result.getString("userId"));
				System.out.print(" ");
				System.out.print(result.getString("caloriegoal"));
				System.out.print(" ");
				System.out.print(result.getString("password"));
				System.out.print(" ");
				System.out.println(result.getString("username"));
			
				array.add(result.getString("userId"));
				array.add(result.getString("caloriegoal"));
				array.add(result.getString("password"));
				array.add(result.getString("username"));
			}
			System.out.println("All record have been selected");
			return array;
			
		}catch(Exception e) {System.out.println(e);}
		return null;
	}
}
	


