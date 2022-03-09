/*
 * This class will run first and prompt the user to either create an account or log in
 * If they choose create account, it will initialize a new user object and update the db
 * If they log in, it will authenticate the credentials and send them to the app
 */
import java.util.*;

public class RunMain {

	public static void main(String[] args) {
		//display create account button
		//display login button
		
		//if click create account button
			//createAccount();
		
		//if click login button
			//login();
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
