/*
 * This class will run first and prompt the user to either create an account or log in
 * If they choose create account, it will initialize a new user object and update the db
 * If they log in, it will authenticate the credentials and send them to the app
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class RunMain extends Application {
	
	Stage window;
	Scene welcome;
	static Scene ca;
	Scene login;
	static Scene home;

	public static void main(String[] args)throws Exception {
//		getConnection();
//		createTable();
//		insertMealInfo("22222",300,"Famous Amos Cookies");
//		insertUserInfo("22223","2800","wacko mode","jtorres01");
		getUsersByName("test1");
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
	//public void start(Stage primaryStage) throws Exception {
	//	welcome(primaryStage);
	//}
	
	public static void welcome(Stage window) {
		//stage is entire window
		//scene is contents inside the window
		
		
		
		window.setTitle("Welcome to FitnessTracker"); //will be displayed on top left of window
		
		//create buttons with text
		
		
		Button caButton = new Button("Create Account");
		Button loginButton = new Button("Log In");
		
		caButton.setOnAction(e -> createAccount(window)); 
		loginButton.setOnAction(e -> login(window));

		HBox layout = new HBox(); //displays boxes side by side
		Image img = new Image("FitnessTracker.jpg");
		layout.getChildren().add(caButton);
		layout.getChildren().add(loginButton);
		layout.getChildren().add(new ImageView(img));
		
		
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
		GridPane.setConstraints(label, 30, 40);
		GridPane.setColumnSpan(label, 5);
		grid.getChildren().add(label);
		
		//~ TEXT FIELDS ~
		//username
		final TextField username = new TextField();
		username.setPromptText("Choose a username");
		username.setPrefColumnCount(10);
		username.getText();
		GridPane.setConstraints(username, 40, 40);
		grid.getChildren().add(username);
		
		//password
		final TextField password = new TextField();
		password.setPromptText("Enter a password");
		GridPane.setConstraints(password, 40, 41);
		grid.getChildren().add(password);
		
		//password check
		final TextField pwCheck = new TextField();
		//pwCheck.setPrefColumnCount(15);
		pwCheck.setPromptText("Re-enter your password");
		GridPane.setConstraints(pwCheck, 40, 42);
		grid.getChildren().add(pwCheck);
		
		//calorie goal
		final TextField calorieGoal = new TextField();
		calorieGoal.setPrefColumnCount(15);
		calorieGoal.setPromptText("Enter your daily calorie goal");
		GridPane.setConstraints(calorieGoal, 40, 43);
		grid.getChildren().add(calorieGoal);
		
		//~ BUTTONS ~
		//submit
		Button submit = new Button("Submit");
		GridPane.setConstraints(submit, 40, 45);
		grid.getChildren().add(submit);
		//clear
		Button clear = new Button("Clear");
		GridPane.setConstraints(clear, 41, 41);
		grid.getChildren().add(clear);
		//back
		Button back = new Button("Go Back");
		GridPane.setConstraints(back, 41, 40);
		grid.getChildren().add(back);
		//~~ END OF UI ELEMENTS ~~
		
		//Build the scene and display it on the stage
		ca = new Scene(grid, 800, 500);
		window.setScene(ca);

		//~~ BUTTON EVENT HANDLING ~~
		//Submit button
		submit.setOnAction(e -> {
			//Checks if passwords match and isn't empty
			if(!password.getText().equals(pwCheck.getText()) || password.getText().isEmpty() || pwCheck.getText().isEmpty()) {
				label.setText("Those passwords do not match, try again");
				password.clear();
		        pwCheck.clear();
			}
			else {
				//Check if username exists in db
				try {
					if(checkForUser(username.getText()) == true) {
						label.setText("That username is taken. Try a different one.");
						username.clear();
						password.clear();
					    pwCheck.clear();
					}
					else {
						//make new database entity
						//load home page
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				label.setText("Account created succesfully!"); 
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
	
	private static void login(Stage window) {
		//~~ UI ELEMENTS ~~
		
		//Creating a GridPane container
		//all ui elements will be added to the grid
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(5);
		grid.setHgap(5);
		
		//Label to say if username is incorrect
		final Label label = new Label();
		GridPane.setConstraints(label, 0, 4);
		GridPane.setColumnSpan(label, 2);
		grid.getChildren().add(label);
		
		//~ TEXT FIELDS ~
		//username
		final TextField username = new TextField();
		username.setPromptText("Enter your username");
		username.setPrefColumnCount(10);
		username.getText();
		GridPane.setConstraints(username, 0, 0);
		grid.getChildren().add(username);
		
		//password
		final TextField password = new TextField();
		password.setPromptText("Enter your password");
		GridPane.setConstraints(password, 0, 1);
		grid.getChildren().add(password);
		
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
			//AppHome(window); //calls home screen right away for testing
			
			//Check if username exists in db
			//If so, verify password and load home page
			try {
				//System.out.println("Does this print the username? " +username.getText());
				if(checkForUser(username.getText()) == true) {
					ArrayList<String> x = getUsersByName(username.getText());
					for(String y : x) {
						System.out.println(y);
					}
					//User x = new User (username.getText() , password.getText(),"");
					if(password.getText().equals(x.get(2))) {
						AppHome(window);
					}
				}
				else {
					label.setText("Username not found, please re-enter.");
					username.clear();
					password.clear();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		 });
		 
		//Clear button
		clear.setOnAction(e -> {
	        username.clear();
	        password.clear();
	        label.setText(null);
		});
		
		//Back button
		back.setOnAction(e -> welcome(window));
		//~~ END OF BUTTON EVENT HANDLING ~~
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
	public static ArrayList<String> getUsersByName(String name) throws Exception {
		try {
			Connection con = getConnection();
			PreparedStatement statement = con.prepareStatement("Select * From users where username = '" +name+"'" );
			
			ResultSet result = statement.executeQuery();
			
			ArrayList<String> array = new ArrayList<String>();
			while(result.next()) {
				System.out.print(result.getString("username"));
				System.out.print(" ");
				System.out.print(result.getString("password"));
				System.out.print(" ");
				System.out.print(result.getString("caloriegoal"));
				System.out.print(" ");
				System.out.println(result.getString("userId"));
			
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
	
	public static boolean checkForUser(String username) throws Exception {
		//query db with select statement to return username
		//if exist return true, else return false
		PreparedStatement statement = null;
		try {
			Connection con = getConnection();
			statement = con.prepareStatement("Select * From users where username = '" + username +"'" );
			ResultSet result = statement.executeQuery();
			
			result.next();
			System.out.println(result.getString(1));
			
			if(result.getString("username") == null) {
				return false;
			}
			else {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static User getUser(String name) throws Exception {
		ArrayList<String> users = getUsersByName(name);
		return new User(users.get(0), users.get(1), users.get(2));
	}
	
	
	//APP HOME PAGE, MAIN CONTROLLER 
	//IS CALLED AFTER LOGIN IS AUTHENTICATED
	public static void AppHome(Stage window) {
		//basic screen just to test that it works
		Text t = new Text(10, 50, "FitTrack Home Page");
		HBox layout = new HBox();
		layout.getChildren().add(t);
		
		home = new Scene(layout, 800, 500);
		window.setScene(home);
	}
}
	


