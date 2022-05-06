/*  Authors : Thomas Latona, Jonathon Torres, Alex Hootsel
 *  St. John's University
 *  CUS 1166 : Software Engineering
 *  Professor : Nikhil Yadav
 *  Term Project
 *  
 *  App Name: Fitness-Tracker
 *  
 *  Description: This program is a nutrition/fitness tracker which allows the user to create an account
 *   and set a personal goal for how many calories they want to consume per day. Each time they log in
 *   they can see how many calories they have eaten each day for that week and see if they were over or
 *   under their set goal, and even see the specific meals that they ate. They can select from all previous 
 *   weeks that they have been using the app, and see their progress. We created a repository of foods,
 *   which would obviously be expanded upon if we were building this for market, which contain many sample
 *   food items to choose from, all with a saved calorie amount per serving. The user then selects the 
 *   amount of portions they have eaten and it will do all the calculations. This app is a great way to 
 *   track your health and fitness, with a nice and simple user friends UI.
 * 
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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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
	}   
	
	public static void welcome(Stage window) {
		window.setTitle("Welcome to FITNESS TRACKER"); //message will be displayed on top left of window
		
		StackPane rootPane = new StackPane();
		
		//image hbox - center position
		HBox image = new HBox();
		image.setAlignment(Pos.TOP_CENTER);
		
		//buttons hbox
		HBox button = new HBox();
		button.setPadding(new Insets(330, 10, 10, 290));
		
		//import image and add it to hbox
		Image img = new Image("LogoFitnessTracker.png", 800, 600, true, true); //importing graphic that appears upon opening
		image.getChildren().add(new ImageView(img));

		//create buttons with text
		Button caButton = new Button("Create Account");
		Button loginButton = new Button("Log In");
		
		//add buttons to hbox
		button.getChildren().add(caButton);
		button.getChildren().add(loginButton);
		
		//event handling for buttons - method calls
		caButton.setOnAction(e -> createAccount(window)); 
		loginButton.setOnAction(e -> login(window));

		//build and display
		rootPane.getChildren().addAll(image, button);
		Scene scene = new Scene(rootPane, 800, 500);
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
		
		Text calGoalTitle = new Text(10, 50, "Please enter information below: ");
		GridPane.setConstraints(calGoalTitle, 40, 35);
		grid.getChildren().add(calGoalTitle);
		
		//Label to say if username is taken, or success message
		final Label label = new Label();
		GridPane.setConstraints(label, 40, 47);
		GridPane.setColumnSpan(label, 5);
		grid.getChildren().add(label);
		
		//~ TEXT FIELDS ~
		//Username
		final TextField username = new TextField();
		username.setPromptText("Choose a username");
		username.setPrefColumnCount(10);
		username.getText();
		GridPane.setConstraints(username, 40, 40);
		grid.getChildren().add(username);
		
		//Password
		final TextField password = new TextField();
		password.setPromptText("Enter a password");
		GridPane.setConstraints(password, 40, 41);
		grid.getChildren().add(password);
		
		//Password check
		final TextField pwCheck = new TextField();
		pwCheck.setPromptText("Re-enter your password");
		GridPane.setConstraints(pwCheck, 40, 42);
		grid.getChildren().add(pwCheck);
		
		//Drop down menu for selecting calorie goal
		ChoiceBox<String> calorieGoal = new ChoiceBox<String>();
		calorieGoal.setValue("1500");
		calorieGoal.getItems().addAll("1200", "1300", "1400", "1500", "1600", 
				"1700", "1800", "1900", "2000", "2100", "2200", "2300", "2400", 
				"2500", "2600", "2700", "2800", "2900", "3000", "3100", "3200", 
				"3300", "3400", "3500", "3600", "3700", "3800", "3900", "4000");
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
				try {
					//check for duplicate usernames
					if(checkForUser(username.getText()) == true) {
						label.setText("That username is taken. Try a different one.");
						username.clear();
						password.clear();
					    pwCheck.clear();
					}
					else {
						label.setText("Account created succesfully!");
						//create userID
						Random rand = new Random();
						String num = String.format("%04d",  rand.nextInt(9999));
						//add to db
						insertUserInfo(username.getText(), password.getText(), calorieGoal.getValue(), num);
						//load home page
						AppHome(window, num, calorieGoal.getValue(), 1, username.getText());
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}			
			}
		 });
		 
		//Clear button
		clear.setOnAction(e -> {
	        username.clear();
	        password.clear();
	        pwCheck.clear();
	        //calorieGoal.clear();
	        label.setText(null);
		});
		
		//Back button
		back.setOnAction(e -> welcome(window));
		//~~ END OF BUTTON EVENT HANDLING ~~
	}
	
	private static void login(Stage window) {
		//~~ UI ELEMENTS ~~
		
		//grid to hold ui elements
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(5);
		grid.setHgap(5);
		
		Text calGoalTitle = new Text(10, 50, "Please enter username and password: ");
		GridPane.setConstraints(calGoalTitle, 40, 35);
		grid.getChildren().add(calGoalTitle);
		
		//Label to say if username is incorrect
		final Label label = new Label();
		GridPane.setConstraints(label, 40, 47);
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
		

		
		//~~ BUTTON EVENT HANDLING ~~
		//Submit button
		submit.setOnAction(e -> {
			try {
				//Check if entered username is present in database
				if(checkForUser(username.getText()) == true) {
					
					//Returns the users table as an arraylist which holds password
					ArrayList<String> x = getUsersByName(username.getText());
					if(x != null) {
						//If passwords match, load home page for this user
						if(password.getText().equals(x.get(2))) {
							AppHome(window, x.get(0), x.get(1), 1, username.getText());
						
						}
						else {
							label.setText("Incorrect password, please re-enter.");
							password.clear();
						}
					}
				}
				else {
					//Username entered not found in database
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
		
		//Build the scene and display it on the stage
		ca = new Scene(grid, 800, 500);
		window.setScene(ca);
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
	
	//This function inserts the user's meal info into the database: usermeals table
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
			PreparedStatement insert = con.prepareStatement("Insert into usermeals(mealId, date, userID, weekNumber, totalCalories, mealname) values ('"+mid+"','"+date+"', '"+userID+"', '"+weekNum+"','"+totalCalories+"', '"+mealName+"')");
			insert.executeUpdate();
			
		}catch(Exception e) {System.out.println(e);}
			finally {
				System.out.println("Insert userMeal Funtion Completed");
			}
		}
	public static void insertUserInfo(String userName, String passW, String calGoal, String userId) throws Exception{	
		//Called from create account. Inserts new user into database with entered credentials
		try {
			Connection con = getConnection();
			PreparedStatement insert = con.prepareStatement("Insert into users(userId,caloriegoal,password,username) values ('"+userId+"','"+calGoal+"','"+passW+"','"+userName+"')");
			insert.executeUpdate();
		}catch(Exception e) {System.out.println(e);}
			finally {
				System.out.println("Insert userInfo Funtion Completed");
			}
		}
	
	public static ArrayList<String> getUsersByName(String name) throws Exception {
		//Returns the info from users table in database using just the username
		//Used at create account and login to check if the entered username is present
		try {
			Connection con = getConnection();
			PreparedStatement statement = con.prepareStatement("Select * From users where username = '" +name+"'" );
			
			ResultSet result = statement.executeQuery();
			
			ArrayList<String> array = new ArrayList<String>();
			while(result.next()) {
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
		//Returns true if user is present in database with the given username
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
		//Returns an arraylist containing Meal objects with all meals in database
		//Used on home page when adding new meal, arraylist is fed into drop down menu
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
		//Called before adding a new usermeal, it multiplies a meal's calories by the servings
		Connection con = getConnection();
		PreparedStatement statement = con.prepareStatement("Select caloriesPerServing From meals where foodItem = '"+mealName+"'");
		ResultSet result = statement.executeQuery();
		if(result.next()) {
			return (result.getInt("caloriesPerServing") * servings);
		}
		return 0;
	}
	
	public static ArrayList<Usermeal> getDaysCalories(String userID){
		//Returns all meals for a given user
		//Used for displaying meals by week and day
		try {
			Connection con = getConnection();
			PreparedStatement statement = con.prepareStatement("Select * From usermeals where userID = '"+userID+"'" );
			
			ResultSet result = statement.executeQuery();
			
			ArrayList<Usermeal> meals = new ArrayList<Usermeal>();
			while(result.next()) {
				Usermeal x = new Usermeal(
					result.getString("mealId"),
					result.getString("mealname"),
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
	//IS CALLED AFTER LOGIN IS AUTHENTICATED or NEW ACCOUNT IS CREATED
	public static void AppHome(Stage window, String userID, String calGoal, int weeknum, String username) throws Exception {
		window.setTitle("FitTrack Home"); //Set text on window
		
		//Stackpane for holding UI elements
		StackPane rootPane = new StackPane();
		
		//Fonts for bold text
		Font welcomeFont = Font.font("Verdana", FontWeight.BOLD, 18);
		Font font = Font.font("Verdana", FontWeight.BOLD, 12);
		
		//Grid for ui elements
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(5);
		grid.setHgap(5);
		
		//Hbox to hold FT logo in top left corner of home screen and display welcome message + username
		HBox image = new HBox();
		image.setPadding(new Insets(10, 10, 10, 10));
		image.setSpacing(15);
		Image img = new Image("FT.png", 100,40, true, true); 
		image.getChildren().add(new ImageView(img));
		Text usern = new Text("Welcome, " + username + "!");
		usern.setFont(welcomeFont);
		image.getChildren().add(usern);
		
		//gets all meals for this user
		ArrayList<Usermeal> usermeals = getDaysCalories(userID);
		
		//~~ TOP MENU BAR ~~
			//Displays user's calorie goal
			Text calGoalTitle = new Text(10, 50, "Calorie goal: "+calGoal);
			calGoalTitle.setFont(welcomeFont);
			GridPane.setConstraints(calGoalTitle, 40, 10);
			grid.getChildren().add(calGoalTitle);

			//Text for week selection
			Text weekSelectText = new Text(10, 50, "Select a week: ");
			weekSelectText.setFont(font);
			GridPane.setConstraints(weekSelectText, 3, 10);
			grid.getChildren().add(weekSelectText);
			
			//Week selection drop down menu
			ChoiceBox<Integer> weekSelect = new ChoiceBox<Integer>();
			weekSelect.setValue(weeknum);
			weekSelect.getItems().addAll(1, 2, 3, 4, 5, 6, 7);
			GridPane.setConstraints(weekSelect, 4, 10);
			grid.getChildren().add(weekSelect);
			
			//if week is changed, reload page with new week
			weekSelect.setOnAction(e -> { try {
				AppHome(window, userID, calGoal, weekSelect.getValue(), username);
			} catch (Exception e1) {
				e1.printStackTrace();
			} });
			
			//Button to load meal-view page with specific meals for each weekday
			Button weekview = new Button("View meals");
			GridPane.setConstraints(weekview, 6, 10);
			weekview.setPrefWidth(120);
			grid.getChildren().add(weekview);
			
			weekview.setOnAction(e -> {
				try {
					thisWeekMeals(window, username, userID, calGoal, weeknum, usermeals);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			});
		//~~ END OF TOP MENU BAR ~~
			
			
		//~~ THIS WEEK'S MEALS SECTION ~~
			//Displays current week days and total calories eaten compared to your goal
			Text weekviewTitle = new Text(10, 50, "This week's progress:");
			GridPane.setConstraints(weekviewTitle, 3, 12);
			grid.getChildren().add(weekviewTitle);
			
			//Iterates through all meals for this user at the current week 
			//and calculates the total calories eaten each day
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
			
			//Displays results with calorie goal for comparison
			Text Monday = new Text(10, 50, "Monday:              "+moncal+"/"+calGoal);
			GridPane.setConstraints(Monday, 3, 13);
			grid.getChildren().add(Monday);
			
			Text Tuesday = new Text(10, 50, "Tuesday:              "+tuescal+"/"+calGoal);
			GridPane.setConstraints(Tuesday, 3, 14);
			grid.getChildren().add(Tuesday);
			
			Text Wednesday = new Text(10, 50, "Wednesday:        "+wedcal+"/"+calGoal);
			GridPane.setConstraints(Wednesday, 3, 15);
			grid.getChildren().add(Wednesday);
			
			Text Thursday = new Text(10, 50, "Thursday:            "+thurscal+"/"+calGoal);
			GridPane.setConstraints(Thursday, 3, 16);
			grid.getChildren().add(Thursday);
			
			Text Friday = new Text(10, 50, "Friday:                 "+frical+"/"+calGoal);
			GridPane.setConstraints(Friday, 3, 17);
			grid.getChildren().add(Friday);
			
			Text Saturday = new Text(10, 50, "Saturday:            "+satcal+"/"+calGoal);
			GridPane.setConstraints(Saturday, 3, 18);
			grid.getChildren().add(Saturday);
			
			Text Sunday = new Text(10, 50, "Sunday:              "+suncal+"/"+calGoal);
			GridPane.setConstraints(Sunday, 3, 19);
			grid.getChildren().add(Sunday);
		//~~ END OF THIS WEEK'S MEALS SECTION ~~
		
		
		//~~ ADD MEAL SECTION ~~
			//Text description
			Text newMealTitle = new Text(10, 50, "Add new meal: Select food, servings, and day");
			GridPane.setConstraints(newMealTitle, 40, 12);
			grid.getChildren().add(newMealTitle);
			
			//Meal list for drop down menu
			ArrayList<Meal> meals = getMeals();
			ArrayList<String> mealNames = new ArrayList<String>();
			for(Meal x : meals) {
				mealNames.add(x.getName());
			}
			
			//DROP DOWN BOXES
			//Meal selection
			ChoiceBox<String> mealType = new ChoiceBox<>(FXCollections.observableArrayList(mealNames));
			mealType.getItems().addAll();
			GridPane.setConstraints(mealType, 40, 13);
			grid.getChildren().add(mealType);
			
			//Servings selection
			ChoiceBox<Integer> servings = new ChoiceBox<Integer>();
			servings.getItems().addAll(1, 2, 3, 4, 5, 6, 7);
			GridPane.setConstraints(servings, 40, 14);
			grid.getChildren().add(servings);
			
			//Weekday selection
			ChoiceBox<String> date = new ChoiceBox<String>();
			date.getItems().addAll("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
			GridPane.setConstraints(date, 40, 15);
			grid.getChildren().add(date);

			//ADD MEAL SUBMIT BUTTON
			Button submit = new Button("Submit");
			GridPane.setConstraints(submit, 40, 16);
			grid.getChildren().add(submit);
			
			submit.setOnAction(e -> {
				try {
					//Multiply calories per serving by number of servings - insert into database
					int calories = getTotalCalories(mealType.getValue(), servings.getValue());
					insertMealInfo(mealType.getValue(), date.getValue(), userID, weekSelect.getValue(), calories);
					
					//refresh screen to show changes every time a new meal is added 
					AppHome(window, userID, calGoal, weekSelect.getValue(), username);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			});
		//~~ END OF ADD MEAL SECTION ~~

		//Build and display
		rootPane.getChildren().addAll(image, grid);
		home = new Scene(rootPane, 800, 500);
		window.setScene(home);
		window.show();
	}
	
	public static void thisWeekMeals(Stage window, String username, String userID, String calGoal, int weeknum, ArrayList<Usermeal> meals) throws Exception {
		//Stackpane to hold all vbox's and grid
		StackPane rootPane = new StackPane();
		
		//Fonts for text
		Font welcomeFont = Font.font("Verdana", FontWeight.BOLD, 18);
		Font font = Font.font("Verdana", FontWeight.BOLD, 12);
		
		//Grid for back button and calorie goal
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(5);
		grid.setHgap(5);
		
		//Displays user's calorie goal
		Text calGoalTitle = new Text(10, 50, "Calorie goal: "+calGoal);
		calGoalTitle.setFont(welcomeFont);
		GridPane.setConstraints(calGoalTitle, 10, 2);
		grid.getChildren().add(calGoalTitle);
		
		//Display week number
		Text weeknumTitle = new Text(10, 50, "Week number: "+weeknum);
		GridPane.setConstraints(weeknumTitle, 15, 2);
		grid.getChildren().add(weeknumTitle);
		
		//displays the week days side by side
		HBox days = new HBox();
		days.setPadding(new Insets(80, 10, 10, 30));
		days.setSpacing(48);
		days.getChildren().addAll(
				new Text("MONDAY:"), 
				new Text("TUESDAY:"), 
				new Text("WEDNESDAY:"), 
				new Text("THURSDAY:"), 
				new Text("FRIDAY:"), 
				new Text("SATURDAY:"), 
				new Text("SUNDAY:"));
		
		//will contain all meals for each day
		VBox mon = new VBox();
		mon.setPadding(new Insets(110, 10 , 10, 30));
		mon.setSpacing(15);
		
		VBox tues = new VBox();
		tues.setPadding(new Insets(110, 10 , 10, 135));
		tues.setSpacing(15);
		
		VBox wed = new VBox();
		wed.setPadding(new Insets(110, 10 , 10, 240));
		wed.setSpacing(15);
		
		VBox thurs = new VBox();
		thurs.setPadding(new Insets(110, 10 , 10, 350));
		thurs.setSpacing(15);
		
		VBox fri = new VBox();
		fri.setPadding(new Insets(110, 10 , 10, 450));
		fri.setSpacing(15);
		
		VBox sat = new VBox();
		sat.setPadding(new Insets(110, 10 , 10, 555));
		sat.setSpacing(15);
		
		VBox sun = new VBox();
		sun.setPadding(new Insets(110, 10 , 10, 660));
		sun.setSpacing(15);
		
		//Add all meals for each respective day to its designated Vbox - to be displayed on screen
		for(Usermeal z : meals) {
			if(z.getWeekNum() == weeknum) {
				if(z.getWeekday().equals("Monday")) {
					mon.getChildren().add(new Text(z.getName() + ": " + z.getCalories()));
				}
				if(z.getWeekday().equals("Tuesday")) {
					tues.getChildren().add(new Text(z.getName() + ": " + z.getCalories()));
				}
				if(z.getWeekday().equals("Wednesday")) {
					wed.getChildren().add(new Text(z.getName() + ": " + z.getCalories()));
				}
				if(z.getWeekday().equals("Thursday")) {
					thurs.getChildren().add(new Text(z.getName() + ": " + z.getCalories()));
				}
				if(z.getWeekday().equals("Friday")) {
					fri.getChildren().add(new Text(z.getName() + ": " + z.getCalories()));
				}
				if(z.getWeekday().equals("Saturday")) {
					sat.getChildren().add(new Text(z.getName() + ": " + z.getCalories()));
				}
				if(z.getWeekday().equals("Sunday")) {
					sun.getChildren().add(new Text(z.getName() + ": " + z.getCalories()));
				}
			}
		}
		
		//Back button to return to home page
		Button back = new Button("Go back");
		GridPane.setConstraints(back, 5, 2);
		grid.getChildren().add(back);
		
		back.setOnAction(e -> {
			try {
				AppHome(window, userID, calGoal, weeknum, username);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		
		//Build and display
		rootPane.getChildren().addAll(mon, tues, wed, thurs, fri, sat, sun, days, grid);
		home = new Scene(rootPane, 800, 500);
		window.setScene(home);
		window.show();
	}
}
	