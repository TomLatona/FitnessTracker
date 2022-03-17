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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class RunMain extends Application implements EventHandler<ActionEvent> {
	
	Button caButton;
	Button loginButton;

	public static void main(String[] args)throws Exception {
		getConnection();
		createTable();
		insertMealInfo("22222",300,"Famous Amos Cookies");
		insertUserInfo("22223","2800","wacko mode","jtorres01");
		getUsersById("22222");
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
	


