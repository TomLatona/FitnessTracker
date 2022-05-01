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
import java.util.Random;

import javafx.application.Application;
import javafx.collections.FXCollections;
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
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class RunMain extends Application {
	
	Stage window;
	Scene welcome;
	static Scene ca;
	Scene login;
	static Scene home;

	public static void main(String[] args)throws Exception {
		launch(args); //runs start method
		
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		welcome(primaryStage);
		//AppHome(primaryStage, "tom"); //for testing
	}   
	
	public static void welcome(Stage window) {
		window.setTitle("Welcome to FITNESS TRACKER"); //message will be displayed on top left of window
		GridPane grid = new GridPane();
		Image img = new Image("FitnessTrackerLogo.jpg"); //importing graphic that appears upon opening
		//grid.getChildren().add(new ImageView(img));
		
		final TextField caButton1 = new TextField(); // the text field "Welcome to... will appear"
		caButton1.setPromptText("Welcome to Fitness Tracker");
		caButton1.getText();
		GridPane.setConstraints(caButton1, 320, 200);
		
		//create buttons with text
		Button caButton = new Button("Create Account");
		Button loginButton = new Button("Log In");
		ImageView pic = new ImageView(img);
		Button go = new Button ("Go");
		
		caButton.setOnAction(e -> createAccount(window)); 
		loginButton.setOnAction(e -> login(window));

		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(5);
		grid.setHgap(5);
		
		GridPane.setConstraints(caButton, 40, 40);
		GridPane.setConstraints(loginButton, 40, 42);
		GridPane.setConstraints(pic, 50, 45);
		grid.getChildren().add(caButton);
		grid.getChildren().add(loginButton);
		grid.getChildren().add(pic);
		
		Scene scene = new Scene(grid, 800, 500);
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
		
		HBox layout = new HBox(); //displays boxes side by side
		Image img = new Image("FitnessTrackerLogo.jpg"); // logo in bottom right corner
		layout.getChildren().add(new ImageView(img));
		
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
				//check if calorie goal is a number and is not null
//				if(calorieGoal.getText().chars().allMatch(Character :: isDigit) || calorieGoal == null) {
//					label.setText("That calorie goal is invalid. Try again");
//					calorieGoal.clear();
//				}
				
				//check if calorie goal is within range
//				if(Integer.parseInt(calorieGoal.getText()) > 1200 && Integer.parseInt(calorieGoal.getText()) < 10000){
//					label.setText("That calorie goal is unsafe to be consuming, try something more realistic.");
//					calorieGoal.clear();
//				}
				
				try {
					//check for duplicate usernames
					if(checkForUser(username.getText()) == true) {
						label.setText("That username is taken. Try a different one.");
						username.clear();
						password.clear();
					    pwCheck.clear();
					}
					else {
						Random rand = new Random();
						String num = String.format("%04d",  rand.nextInt(9999));
						insertUserInfo(username.getText(), password.getText(), calorieGoal.getText(), num);
						AppHome(window, num, calorieGoal.getText(), 1);
					}
				} catch (Exception e2) {
					e2.printStackTrace();
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
		GridPane.setConstraints(username, 40, 40);
		grid.getChildren().add(username);
		
		//password
		final TextField password = new TextField();
		password.setPromptText("Enter your password");
		GridPane.setConstraints(password, 40, 41);
		grid.getChildren().add(password);
		
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
			//Check if username exists in db
			//If so, verify password and load home page
			try {
				//System.out.println("Does this print the username? " +username.getText());
				//if(checkForUser(username.getText()) == true) {
				ArrayList<String> x = getUsersByName(username.getText());
				if(x != null) {
					if(password.getText().equals(x.get(2))) {
						AppHome(window, x.get(0), x.get(1), 1);
					
					}
					else {
						label.setText("Incorrect password, please re-enter.");
						password.clear();
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
	public static void insertMealInfo(String mealName, String date, String userID, int weekNum, int totalCalories) throws Exception{
		try {
			Connection con = getConnection();
			
			//get the meal ID using meal name
			PreparedStatement getMealID = con.prepareStatement("Select userMealId from meals where foodItem = '" +mealName+"'");
			ResultSet result = getMealID.executeQuery();
			String mid = "";
			if(result.next()) {
				mid = result.getString("userMealId");	
			}
			
			//Insert meal into db
			PreparedStatement insert = con.prepareStatement("Insert into usermeals(mealId, date, userID, weekNumber, totalCalories) values ('"+mid+"','"+date+"', '"+userID+"', '"+weekNum+"','"+totalCalories+"')");
			insert.executeUpdate();
			
		}catch(Exception e) {System.out.println(e);}
			finally {
				System.out.println("Insert userMeal Funtion Completed");
			}
		}
	public static void insertUserInfo(String userName, String passW, String calGoal, String userId) throws Exception{
		//generate userID
		
		try {
			Connection con = getConnection();
			
			
			//PreparedStatement id = con.prepareStatement("select userID from users where userID = '"+num+"'");
			PreparedStatement insert = con.prepareStatement("Insert into users(userId,caloriegoal,password,username) values ('"+userId+"','"+calGoal+"','"+passW+"','"+userName+"')");
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
		Connection con = getConnection();
		PreparedStatement statement = con.prepareStatement("Select username From users where username = '" + username +"'" );
		ResultSet result = statement.executeQuery();
		if(result.next()) {
			System.out.println(result.getString("username"));
			if(username.equals(result.getString("username"))) {
				return true;
			}
		}
		return false;
	}
	
	public static User getUser(String name) throws Exception {
		ArrayList<String> users = getUsersByName(name);
		return new User(users.get(0), users.get(1), users.get(2), users.get(3));
	}
	
	
	public static ArrayList<Meal> getMeals(){
		try {
			Connection con = getConnection();
			PreparedStatement statement = con.prepareStatement("Select * From meals" );
			
			ResultSet result = statement.executeQuery();
			
			ArrayList<String> array = new ArrayList<String>();
			while(result.next()) {
				array.add(result.getString("foodItem"));
				array.add(result.getString("caloriesPerServing"));
				array.add(result.getString("userMealId"));
			}
			ArrayList<Meal> meals = new ArrayList<Meal>();
			for(int i = 0; i< array.size(); i+=3) {
				Meal x = new Meal(array.get(i), array.get(i+1), array.get(i+2));
				meals.add(x);
			}
			return meals;
			
		}catch(Exception e) {System.out.println(e);}
		return null;
	}
	
	public static int getTotalCalories(String mealName, int servings) throws Exception {
		//this method is called before adding a new usermeal, it multiplies a meal's calories by the servings
		Connection con = getConnection();
		PreparedStatement statement = con.prepareStatement("Select caloriesPerServing From meals where foodItem = '"+mealName+"'");
		ResultSet result = statement.executeQuery();
		if(result.next()) {
			return (result.getInt("caloriesPerServing") * servings);
		}
		return 0;
	}
	
	public static ArrayList<Usermeal> getDaysCalories(String userID){
		//this method will get all meals from a given user
		//and store them in Usermeal objects
		
		try {
			Connection con = getConnection();
			PreparedStatement statement = con.prepareStatement("Select * From usermeals where userID = '"+userID+"'" );
			
			ResultSet result = statement.executeQuery();
			
			ArrayList<Usermeal> meals = new ArrayList<Usermeal>();
			while(result.next()) {
				Usermeal x = new Usermeal(
					result.getString("mealId"), 
					result.getInt("totalCalories"),
					result.getString("date"), 
					result.getInt("weekNumber"));
				meals.add(x);
			}
			return meals;
		}catch(Exception e) {System.out.println(e);}
		return null;
	}
	
	
	//APP HOME PAGE, MAIN CONTROLLER 
	//IS CALLED AFTER LOGIN IS AUTHENTICATED
	public static void AppHome(Stage window, String userID, String calGoal, int weeknum) throws Exception {
		window.setTitle("FitTrack Home");
		
		//Grid for all ui elements
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(5);
		grid.setHgap(5);
		
		//Displays user's calorie goal
		Text calGoalTitle = new Text(10, 50, "Calorie goal: "+calGoal);
		GridPane.setConstraints(calGoalTitle, 40, 2);
		grid.getChildren().add(calGoalTitle);

		//~~ THIS WEEK'S MEALS SECTION ~~
			String def = "in deficit!";
			String notDef = "not in deficit.";
			
			Text weekviewTitle = new Text(10, 50, "This week's meals:");
			GridPane.setConstraints(weekviewTitle, 3, 3);
			grid.getChildren().add(weekviewTitle);
			
			ChoiceBox<Integer> weekSelect = new ChoiceBox<Integer>();
			weekSelect.setValue(weeknum);
			weekSelect.getItems().addAll(1, 2, 3, 4, 5, 6, 7);
			GridPane.setConstraints(weekSelect, 30, 2);
			grid.getChildren().add(weekSelect);
			
			//if week is changed, reload page with new week
			weekSelect.setOnAction(e -> { try {
				AppHome(window, userID, calGoal, weekSelect.getValue());
			} catch (Exception e1) {
				e1.printStackTrace();
			} });
			
			//gets all meals for this user
			ArrayList<Usermeal> usermeals = getDaysCalories(userID);
			
			
			//MAKE NEW METHOD WITH THIS SO IT CAN BE CALLED AFTER SUBMIT TO LIVE UPDATE
			//OR make the page refresh every time a new week selection is made
			int moncal=0, tuescal=0, wedcal=0, thurscal=0, frical=0, satcal=0, suncal=0;
			for(Usermeal z : usermeals) {
				if(z.getWeekday().equals("Monday") && z.getWeekNum() == weeknum) {
					moncal += z.getCalories();
				}
				if(z.getWeekday().equals("Tuesday") && z.getWeekNum() == weeknum) {
					tuescal += z.getCalories();
				}
				if(z.getWeekday().equals("Wednesday") && z.getWeekNum() == weeknum) {
					wedcal += z.getCalories();
				}
				if(z.getWeekday().equals("Thursday") && z.getWeekNum() == weeknum) {
					thurscal += z.getCalories();
				}
				if(z.getWeekday().equals("Friday") && z.getWeekNum() == weeknum) {
					frical += z.getCalories();
				}
				if(z.getWeekday().equals("Saturday") && z.getWeekNum() == weeknum) {
					satcal += z.getCalories();
				}
				if(z.getWeekday().equals("Sunday") && z.getWeekNum() == weeknum) {
					suncal += z.getCalories();
				}
			}
			
			Text Monday = new Text(10, 50, "Monday: "+moncal+"/"+calGoal);
			GridPane.setConstraints(Monday, 3, 6);
			grid.getChildren().add(Monday);
			
			Text Tuesday = new Text(10, 50, "Tuesday: "+tuescal+"/"+calGoal);
			GridPane.setConstraints(Tuesday, 3, 8);
			grid.getChildren().add(Tuesday);
			
			Text Wednesday = new Text(10, 50, "Wednesday: "+wedcal+"/"+calGoal);
			GridPane.setConstraints(Wednesday, 3, 10);
			grid.getChildren().add(Wednesday);
			
			Text Thursday = new Text(10, 50, "Thursday: "+thurscal+"/"+calGoal);
			GridPane.setConstraints(Thursday, 3, 12);
			grid.getChildren().add(Thursday);
			
			Text Friday = new Text(10, 50, "Friday: "+frical+"/"+calGoal);
			GridPane.setConstraints(Friday, 3, 14);
			grid.getChildren().add(Friday);
			
			Text Saturday = new Text(10, 50, "Saturday: "+satcal+"/"+calGoal);
			GridPane.setConstraints(Saturday, 3, 16);
			grid.getChildren().add(Saturday);
			
			Text Sunday = new Text(10, 50, "Sunday: "+suncal+"/"+calGoal);
			GridPane.setConstraints(Sunday, 3, 18);
			grid.getChildren().add(Sunday);
		//~~ END OF THIS WEEK'S MEALS SECTION ~~
		
		
		//~~ ADD MEAL SECTION ~~
			Text newMealTitle = new Text(10, 50, "Add new meal:");
			GridPane.setConstraints(newMealTitle, 60, 3);
			grid.getChildren().add(newMealTitle);
			
			//For displaying meal choices drop down menu
			ArrayList<Meal> meals = getMeals();
			ArrayList<String> mealNames = new ArrayList<String>();
			for(Meal x : meals) {
				mealNames.add(x.getName());
			}
			
			//DROP DOWN BOXES
			ChoiceBox<String> mealType = new ChoiceBox<>(FXCollections.observableArrayList(mealNames));
			mealType.getItems().addAll();
			GridPane.setConstraints(mealType, 60, 7);
			grid.getChildren().add(mealType);
			
			ChoiceBox<Integer> servings = new ChoiceBox<Integer>();
			servings.getItems().addAll(1, 2, 3, 4, 5, 6, 7);
			GridPane.setConstraints(servings, 60, 8);
			grid.getChildren().add(servings);
			
			ChoiceBox<String> date = new ChoiceBox<String>();
			date.getItems().addAll("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
			GridPane.setConstraints(date, 60, 9);
			grid.getChildren().add(date);

			
			Button submit = new Button("Submit");
			GridPane.setConstraints(submit, 60, 14);
			grid.getChildren().add(submit);
		//~~ END OF ADD MEAL SECTION ~~
			
		submit.setOnAction(e -> {
			try {
				int calories = getTotalCalories(mealType.getValue(), servings.getValue());
				insertMealInfo(mealType.getValue(), date.getValue(), userID, weekSelect.getValue(), calories);
				
				//refresh screen to show changes 
				AppHome(window, userID, calGoal, weekSelect.getValue());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		
		//Build and display
		home = new Scene(grid, 800, 500);
		window.setScene(home);
		window.show();
	}
}
	