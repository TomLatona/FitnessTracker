//Object for user: contains username, password, and their daily calorie goal

public class User {

	private String username;
	private String password;
	private int calorieGoal;
	
	public User(String usr, String pwd, int cg) {
		this.username = usr;
		this.password = pwd;
		this.calorieGoal = cg;
	}
	
	public int getCG() {
		return this.calorieGoal;
	}
	
	//returns a string with the full insert statement for the SQL database
	public String addUserToDB() {
		String addUser = "insert into *db* (column1, column2, column3) VALUES (" + this.username +", " + this.password +", "+this.calorieGoal+");";
		return addUser;
	}
}
